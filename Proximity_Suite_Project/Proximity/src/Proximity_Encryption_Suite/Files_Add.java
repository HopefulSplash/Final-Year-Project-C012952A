/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * The Files_Add.Java Class implements an application that allows a user to add
 * files to system, they can add files, folders, drives or external devices.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Files_Add extends javax.swing.JDialog implements PropertyChangeListener {

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        private int background_Progress = 0;
        private int file_Index;
        private boolean Add_File = false;
        private Object background_Object;
        private String background_Status;
        private boolean alreadyInFolder = false;
        private ArrayList<String> dupList = new ArrayList<>();

        /**
         * a method that will get the status of the task
         *
         * @return
         */
        public String getStatus() {
            return background_Status;
        }

        /**
         * a method that will set the status of the task
         *
         * @param status
         */
        public void setStatus(String status) {
            this.background_Status = status;
        }

        /**
         * a method that will get the list of duplicate files
         *
         * @return
         */
        public ArrayList<String> getDupList() {
            return dupList;
        }

        /**
         * a method that will set the duplicate file list.
         *
         * @param dupList
         */
        public void setDupList(ArrayList<String> dupList) {
            this.dupList = dupList;
        }

        /**
         * a method that will get the file index
         *
         * @return
         */
        public int getFileIndex() {
            return file_Index;
        }

        /**
         * a method that will set the file index
         *
         * @param fileIndex
         */
        public void setFileIndex(int fileIndex) {
            this.file_Index = fileIndex;
        }

        @Override
        public Void doInBackground() {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //setup variables to use
            int counter = 0;
            background_Progress = 0;
            //setup GUI
            progressBar.setValue(0);
            create_Button.setEnabled(false);
            add_Button.setEnabled(false);
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            folder_ComboBox.setEnabled(false);
            type_ComboBox.setEnabled(false);

            //Initialize background_Progress property.
            setProgress(0);

            if ("adding".equals(background_Status)) {
                while (background_Progress < filelist.size()) {
                    //setupGUI
                    progressBar.setMaximum(filelist.size());
                    progressBar.setIndeterminate(true);
                    //add the files to the list
                    for (int i = 0; i < filelist.size(); i++) {
                        Search(filelist.get(i));
                        background_Progress += 1;
                        setProgress(background_Progress);
                    }

                }
            } else if ("accept".equals(background_Status)) {
                while (counter != addFilesList.size()) {
                    //setup GUI
                    progressBar.setMaximum(addFilesList.size());
                    progressBar.setIndeterminate(true);
                    //send the file details
                    for (int i = 0; i < addFilesList.size(); i++) {
                        sendFileDetials(i);
                        counter++;
                    }
                }
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            //setup GUI
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMaximum());
            create_Button.setEnabled(true);
            add_Button.setEnabled(true);
            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            folder_ComboBox.setEnabled(true);
            type_ComboBox.setEnabled(true);

            if ("adding".equals(background_Status)) {
                if (addFilesList.isEmpty()) {

                    if (!listModel.isEmpty()) {
                        //GUI setup
                        remove_Button.setEnabled(true);
                        clear_Button.setEnabled(true);

                    } else {
                        //GUI setup
                        progressBar.setValue(0);
                        remove_Button.setEnabled(false);
                        clear_Button.setEnabled(false);
                    }
                    /*
                     * shows an error message due not files being found
                     */
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog((Component) background_Object,
                            "No Files Where Found. Please Try Again.",
                            "File Adding Error!",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);

                }

            } else if ("accept".equals(task.getStatus())) {

                if (!task.getDupList().isEmpty()) {
                    //shows the user duplicate files
                    Files_Add_Duplicates aw = new Files_Add_Duplicates((Frame) background_Object, true, task.getDupList());
                    aw.setVisible(true);

                } else {
                    //confirms the files have been added to the user.
                    Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                    JOptionPane.showMessageDialog((Component) background_Object,
                            "All Files Have Been Added.",
                            "File Adding Successful!",
                            JOptionPane.INFORMATION_MESSAGE,
                            tickIcon);

                }
                //close the form
                clear_Button.doClick();
            }
        }

        /**
         * a method that will search for all the file in a directory and add
         * them into a list to be returned to the main GUI
         *
         * @param file
         */
        public void Search(File file) {

            if (file.exists()) {

                if (file.isDirectory()) {
                    if (file.canRead()) {

                        File[] listOfFiles = file.listFiles();
                        if (listOfFiles != null) {
                            for (int i = 0; i < listOfFiles.length; i++) {
                                Search(listOfFiles[i]);

                            }
                        }
                    }
                } else if (file.isFile()) {

                    if (file.canRead()) {

                        if (addFilesList.contains(file)) {

                            Add_File = false;
                        } else {
                            addFilesList.add(file);

                            file_Index = addFilesList.size() - 1;

                            Add_File = true;

                        }

                    }

                }
            }
        }

        /**
         * a method that will send all the file details to the database.
         *
         * @param i
         */
        private void sendFileDetials(int i) {

            int fileID = 0;
            int newFileID = 0;

            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                fileID = 0;
                Statement stmt = conn.createStatement();
                String sql = "SELECT file_Details_ID, file_Directory FROM File_Details "
                        + "WHERE file_Directory = ?;";

                PreparedStatement getFolderID = conn.prepareStatement(sql);
                getFolderID.setString(1, addFilesList.get(i).getAbsolutePath());

                /*
                 * extracts the data from the results of the SQL statment
                 */
                try (ResultSet rs = getFolderID.executeQuery()) {
                    while (rs.next()) {
                        fileID = rs.getInt("file_Details_ID");

                    }
                }

                if (fileID == 0) {

                    addFile(addFilesList.get(i).getAbsolutePath());

                    newFileID = getFileID(addFilesList.get(i).getAbsolutePath());

                    if (getdupFile(newFileID, Current_Folder_ID) == true) {

                        dupList.add(addFilesList.get(i).getAbsolutePath());
                    } else {

                        addFileFolder(newFileID);
                    }
                } else {

                    if (getdupFile(fileID, Current_Folder_ID) == true) {

                        dupList.add(addFilesList.get(i).getAbsolutePath());

                    } else {

                        addFileFolder(fileID);

                    }

                }

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                    }
                }

            }
        }

        /**
         * a method that will check if the file is a duplicate and already
         * exists in the folder
         *
         * @param FileID
         * @param FolderID
         * @return
         */
        private boolean getdupFile(int FileID, int FolderID) {

            boolean dup = true;
            int temp = 0;

            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "SELECT folder_Details_ID FROM Folder_File_List WHERE folder_Details_ID = ? AND file_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, FolderID);
                pStmt.setInt(2, FileID);

                ResultSet rs = pStmt.executeQuery();
                while (rs.next()) {

                    temp = rs.getInt("folder_Details_ID");

                }

            } catch (SQLException | ClassNotFoundException se) {
            } finally {

                if (temp > 0) {
                    dup = true;
                } else {
                    dup = false;
                }

                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                    }
                }

            }
            return dup;
        }

        /**
         * a method that will add the file details into the database
         *
         * @param filePath
         */
        public void addFile(String filePath) {

            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO File_Details VALUES (NULL, ? , ? , 0 , DEFAULT);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, filePath);
                pStmt.setInt(2, 0);

                pStmt.executeUpdate();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {

                        conn.close();
                    } catch (SQLException ex) {
                    }
                }

            }
        }

        /**
         * a method that will add the files to the account and relevant folder.
         *
         * @param FileID
         */
        public void addFileFolder(int FileID) {

            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO Folder_File_List VALUES (NULL, ?, ?);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, Current_Folder_ID);
                pStmt.setInt(2, FileID);
                pStmt.executeUpdate();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {

                    }
                }

            }

        }

        /**
         * a method that will get the id for a file
         *
         * @param file_Path
         * @return
         */
        public int getFileID(String file_Path) {

            int fileID = 0;
            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "SELECT file_Details_ID FROM File_Details WHERE file_Directory = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, file_Path);

                ResultSet rs = pStmt.executeQuery();

                while (rs.next()) {
                    fileID = rs.getInt("file_Details_ID");
                }

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                    }
                }
            }
            return fileID;
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ("adding".equals(task.getStatus())) {
            if ("progress".equals(evt.getPropertyName())) {
                // adding all the files to display them to the user
                if (!addFilesList.isEmpty()) {
                    for (int i = task.getFileIndex(); i >= temp; i--) {

                        listModel.addElement(addFilesList.get(i).getAbsolutePath() + "\n");

                    }
                }
                temp = task.getFileIndex();
            }
        }
    }

    /**
     *
     *
     * Creates new form Files_Add
     *
     * @param parent
     * @param Current_Folder
     * @param Account_ID
     * @param modal
     */
    public Files_Add(java.awt.Frame parent, boolean modal, int Account_ID, String Current_Folder) {
        super(parent, modal);

        this.getContentPane().setBackground(Color.WHITE);
        /**
         * Declares the icons used for the windows icon and the frames icon.
         */
        URL icon16URL = getClass().getResource("/Proximity/graphic_Logos/Logo_Small.png");
        URL icon32URL = getClass().getResource("/Proximity/graphic_Logos/Logo_Large.png");

        /**
         * Image list to store the icons in.
         */
        final List<Image> icons = new ArrayList<>();

        /**
         * loads the icons into the image list.
         */
        try {
            icons.add(ImageIO.read(icon16URL));
        } catch (IOException ex) {
            Logger.getLogger(Suite_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            icons.add(ImageIO.read(icon32URL));
        } catch (IOException ex) {
            Logger.getLogger(Suite_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        listModel = new DefaultListModel();
        initComponents();

        /**
         * sets the location of the application to the middle of the screen.
         */
        this.setLocationRelativeTo(this.getParent());
        /**
         * loads the appropriate icons.
         */
        this.setIconImages(icons);
        // setup variables
        this.Current_Folder = Current_Folder;
        this.Account_ID = Account_ID;
        accept_Button.requestFocus();
        //get all the account variables
        getAccountFolders();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        files_Adding_Panel = new javax.swing.JPanel();
        files_Label = new javax.swing.JLabel();
        select_Label = new javax.swing.JLabel();
        type_ComboBox = new javax.swing.JComboBox();
        add_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();
        remove_Button = new javax.swing.JButton();
        file_ScrollPane = new javax.swing.JScrollPane();
        file_List = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        button_Panel = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        folder_Panel = new javax.swing.JPanel();
        folder_Label = new javax.swing.JLabel();
        folder_ComboBox = new javax.swing.JComboBox();
        create_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | File Add");
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        files_Adding_Panel.setBackground(new java.awt.Color(255, 255, 255));
        files_Adding_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Files To Add Details"));

        files_Label.setText("Selected Files:");

        select_Label.setText("Select Item To Add:");

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "File", "Folder", "Drive", "External Device" }));

        add_Button.setText("Add");
        add_Button.setFocusPainted(false);
        add_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_ButtonActionPerformed(evt);
            }
        });

        clear_Button.setText("Clear");
        clear_Button.setEnabled(false);
        clear_Button.setFocusPainted(false);
        clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_ButtonActionPerformed(evt);
            }
        });

        remove_Button.setText("Remove");
        remove_Button.setEnabled(false);
        remove_Button.setFocusPainted(false);
        remove_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_ButtonActionPerformed(evt);
            }
        });

        file_List.setForeground(new java.awt.Color(51, 51, 51));
        file_List.setModel(listModel);
        file_List.setFocusable(false);
        file_ScrollPane.setViewportView(file_List);

        javax.swing.GroupLayout files_Adding_PanelLayout = new javax.swing.GroupLayout(files_Adding_Panel);
        files_Adding_Panel.setLayout(files_Adding_PanelLayout);
        files_Adding_PanelLayout.setHorizontalGroup(
            files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                        .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                                .addComponent(file_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(add_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(remove_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                                .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(files_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(select_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(type_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))))
        );
        files_Adding_PanelLayout.setVerticalGroup(
            files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, files_Adding_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(select_Label)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(files_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(files_Adding_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(files_Adding_PanelLayout.createSequentialGroup()
                        .addComponent(add_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remove_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear_Button))
                    .addComponent(file_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        accept_Button.setText("Accept");
        accept_Button.setEnabled(false);
        accept_Button.setFocusPainted(false);
        accept_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        accept_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        accept_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        accept_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_ButtonActionPerformed(evt);
            }
        });

        cancel_Button.setText("Cancel");
        cancel_Button.setFocusPainted(false);
        cancel_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        cancel_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        cancel_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        folder_Panel.setBackground(new java.awt.Color(255, 255, 255));
        folder_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        folder_Label.setText("Select Folder:");

        folder_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_ComboBoxActionPerformed(evt);
            }
        });

        create_Button.setText("Create");
        create_Button.setFocusPainted(false);
        create_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        create_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        create_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        create_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout folder_PanelLayout = new javax.swing.GroupLayout(folder_Panel);
        folder_Panel.setLayout(folder_PanelLayout);
        folder_PanelLayout.setHorizontalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        folder_PanelLayout.setVerticalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folder_Label)
                    .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(files_Adding_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(files_Adding_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * a method that will return if any files were added
     *
     * @return
     */
    public boolean isDidAdd() {
        return didAdd;
    }

    /**
     * a method that can set if any files have been added
     *
     * @param didAdd
     */
    public void setDidAdd(boolean didAdd) {
        this.didAdd = didAdd;
    }

    /**
     * a method that will start the process of addingthe files
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        if (!listModel.isEmpty()) {
            Object[] options = {"Confirm", "Cancel"};
            int n = JOptionPane.showOptionDialog(this,
                    "Are You Sure You Want to Add The Files?",
                    "Confirm File Adding",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title

            // if the user has clicked confirm.
            if (n == 0) {
                didAdd = true;
                task = new Task();
                task.setStatus("accept");
                task.addPropertyChangeListener(this);
                task.execute();

            }
        } else {
            this.dispose();
        }

    }//GEN-LAST:event_accept_ButtonActionPerformed

    /**
     * a method that ask the user to select files to add to the system.
     *
     * @param evt
     */
    private void add_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_ButtonActionPerformed

        File[] files = null;
        int n = 5;

        if (type_ComboBox.getSelectedIndex() == 0) {
            n = 0;
        } else if (type_ComboBox.getSelectedIndex() == 1) {
            n = 1;
        } else if (type_ComboBox.getSelectedIndex() == 2) {
            n = 2;
        } else if (type_ComboBox.getSelectedIndex() == 3) {
            n = 3;
        }
        // depending on the users choice a window will open for them to select files
        if (n == 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + System.getProperty("user.name") + File.separator + "Documents"));

            javax.swing.Action details = chooser.getActionMap().get("viewTypeList");
            details.actionPerformed(null);
            chooser.setMultiSelectionEnabled(true);
            chooser.setDragEnabled(true);
            chooser.setDialogTitle("Select A File");
            chooser.setApproveButtonText("Add File");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int returnVal = chooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser.getSelectedFiles();
            }

        } else if (n == 1) {
            JFileChooser chooser1 = new JFileChooser();
            javax.swing.Action details = chooser1.getActionMap().get("viewTypeList");
            details.actionPerformed(null);
            chooser1.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + System.getProperty("user.name") + File.separator + "Documents"));
            chooser1.setMultiSelectionEnabled(true);

            chooser1.setDialogTitle("Select A Folder");
            chooser1.setApproveButtonText("Add Folder");
            chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser1.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser1.getSelectedFiles();

            }
        } else if (n == 2) {
            JFileChooser chooser2 = new JFileChooser();
            javax.swing.Action details = chooser2.getActionMap().get("viewTypeList");
            details.actionPerformed(null);
            chooser2.setMultiSelectionEnabled(true);

            chooser2.setCurrentDirectory(chooser2.getFileSystemView().getParentDirectory(new File(System.getProperty("user.home")).getParentFile().getParentFile()));
            chooser2.setDialogTitle("Select A Drive");
            chooser2.setApproveButtonText("Add Drive");
            chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser2.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser2.getSelectedFiles();

            }
        } else if (n == 3) {
            JFileChooser chooser3 = new JFileChooser();
            javax.swing.Action details = chooser3.getActionMap().get("viewTypeList");
            details.actionPerformed(null);
            chooser3.setMultiSelectionEnabled(true);

            chooser3.setCurrentDirectory(chooser3.getFileSystemView().getParentDirectory(new File(System.getProperty("user.home")).getParentFile().getParentFile()));
            chooser3.setDialogTitle("Select A External Device");
            chooser3.setApproveButtonText("Add External");
            chooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser3.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser3.getSelectedFiles();

            }
        }
        // adds all the files a user has selected 
        if (files != null) {

            filelist.clear();

            for (int i = 0; i < files.length; i++) {

                filelist.add(files[i]);

            }
            // starts a new task to add the files
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new Task();
            task.setStatus("adding");

            if (listModel.isEmpty()) {
                task.setFileIndex(0);
                temp = 0;

            } else {
                task.setFileIndex(task.getFileIndex());
                temp = listModel.getSize();
            }
            //starts the adding of the files
            task.addPropertyChangeListener(this);
            task.execute();

        }


    }//GEN-LAST:event_add_ButtonActionPerformed
    /**
     * a method that will close the form
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will clear all the data on the form
     *
     * @param evt
     */
    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed
//clears the lists       
        addFilesList.clear();
        filelist.clear();
        listModel.clear();
        progressBar.setValue(0);

        if (!listModel.isEmpty()) {
            //setup GUI
            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);

        } else {
            //setup GUI
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
        }


    }//GEN-LAST:event_clear_ButtonActionPerformed
    /**
     * a method that will remove the selected files from the list
     *
     * @param evt
     */
    private void remove_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_ButtonActionPerformed
        DefaultListModel dlm = (DefaultListModel) file_List.getModel();

        if (file_List.getSelectedIndices().length > 0) {

            int[] selectedIndices = file_List.getSelectedIndices();
            //removes the files from the list
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.removeElementAt(selectedIndices[i]);
                addFilesList.remove(selectedIndices[i]);
            }

        }

        if (!listModel.isEmpty()) {
            //setup GUI
            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);

        } else {
            //setup GUI
            progressBar.setValue(0);
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
        }


    }//GEN-LAST:event_remove_ButtonActionPerformed
    /**
     * a method that gets the selected folders ID
     *
     * @param folder_Name
     * @return
     */
    public int getSelectedFolder(String folder_Name) {

        int folderID = 0;

        /*
         * declares and new instance of the Suite_Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Suite_Database d = new Suite_Database();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            String sql = "SELECT folder_Details_ID FROM Folder_Details WHERE account_Details_ID = " + Account_ID + " AND folder_Name = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, folder_Name);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
            }

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return folderID;
    }

    /**
     * a method that will get the current folder id when a user changes the
     * selected folder
     *
     * @param evt
     */
    private void folder_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_ComboBoxActionPerformed

        Current_Folder_ID = getSelectedFolder((String) folder_ComboBox.getSelectedItem());
    }//GEN-LAST:event_folder_ComboBoxActionPerformed
    /**
     * a method that will open up the create folder form
     *
     * @param evt
     */
    private void create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_ButtonActionPerformed

        Folder_Create af = new Folder_Create((Frame) this.getParent(), true, Account_ID);
        af.setVisible(true);

        if (af.isCreatedFolder() == true) {
            //update the GUI after a folder has been created
            folderNameList.clear();
            getAccountFolders();
            folder_ComboBox.setSelectedIndex(folder_ComboBox.getItemCount() - 1);
        } else {
        }
    }//GEN-LAST:event_create_ButtonActionPerformed
    /**
     * a method that will return the current folder
     *
     * @return
     */
    public String getCurrent_Folder() {
        return Current_Folder;
    }

    /**
     * when the form is closing the current folder is saved
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        Current_Folder = (String) folder_ComboBox.getSelectedItem();

    }//GEN-LAST:event_formWindowClosing

    
    /**
     * gets all the folders related to an account
     */
    private void getAccountFolders() {

        int folderID;
        String folderName;

        /*
         * declares and new instance of the Suite_Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Suite_Database d = new Suite_Database();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;

        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            Statement stmt = conn.createStatement();
            String sql = "SELECT folder_Details_ID, folder_Name FROM Folder_Details "
                    + "WHERE account_Details_ID = " + Account_ID + ";";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                folderName = rs.getString("folder_Name");
                folderNameList.add(folderName);
            }

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
         //updates the GUI
        updateFolderListGUI();
    }
    /**
     * a method that will update the GUI
     */
    private void updateFolderListGUI() {

        String tempFolder = Current_Folder;
        folder_ComboBox.removeAllItems();

        for (int i = 0; i < folderNameList.size(); i++) {
            if (!folderNameList.isEmpty()) {

                folder_ComboBox.addItem(folderNameList.get(i));

            }
        }
        folder_ComboBox.setSelectedItem(tempFolder);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JButton add_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton clear_Button;
    private javax.swing.JButton create_Button;
    private javax.swing.JList file_List;
    private javax.swing.JScrollPane file_ScrollPane;
    private javax.swing.JPanel files_Adding_Panel;
    private javax.swing.JLabel files_Label;
    private javax.swing.JComboBox folder_ComboBox;
    private javax.swing.JLabel folder_Label;
    private javax.swing.JPanel folder_Panel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton remove_Button;
    private javax.swing.JLabel select_Label;
    private javax.swing.JComboBox type_ComboBox;
    // End of variables declaration//GEN-END:variables
    private final DefaultListModel listModel;
    private String Current_Folder;
    private final int Account_ID;
    private int temp = 0;
    private boolean didAdd = false;
    private static final ArrayList<File> addFilesList = new ArrayList<>();
    private final ArrayList<File> filelist = new ArrayList();
    private final ArrayList<String> folderNameList = new ArrayList<>();
    private Task task;
    private int Current_Folder_ID;

}
