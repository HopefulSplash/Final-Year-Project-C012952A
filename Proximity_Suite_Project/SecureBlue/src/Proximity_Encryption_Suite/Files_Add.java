package Proximity_Encryption_Suite;

import static Proximity_Encryption_Suite.Files_Add.addFilesList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class Files_Add extends javax.swing.JDialog implements ActionListener,
        PropertyChangeListener {

    private DefaultListModel listModel;
    private String Current_Folder;
    private final int Account_ID;

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class Task extends SwingWorker<Void, Void> {

        int progress = 0;
        int fileIndex;
        boolean addedFile = false;
        Object o1;
        String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<String> getDupList() {
            return dupList;
        }

        public void setDupList(ArrayList<String> dupList) {
            this.dupList = dupList;
        }

        public void setO1(Object o1) {
            this.o1 = o1;
        }

        public int getFileIndex() {
            return fileIndex;
        }

        public void setFileIndex(int fileIndex) {
            this.fileIndex = fileIndex;
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            setCursor (Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
             int counter = 0;
            progress = 0;
            progressBar.setValue(0);

            create_Button.setEnabled(false);
            add_Button.setEnabled(false);
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            jComboBox2.setEnabled(false);
            jComboBox1.setEnabled(false);

            //Initialize progress property.
            setProgress(0);

            if ("adding".equals(status)) {
                while (progress < filelist.size()) {
                    progressBar.setMaximum(filelist.size());
                    progressBar.setIndeterminate(true);

                    for (int i = 0; i < filelist.size(); i++) {
                        Search(filelist.get(i));
                        progress += 1;
                        setProgress(progress);
                    }

                }
            } else if ("accept".equals(status)) {
                while (counter != addFilesList.size()) {

                    progressBar.setMaximum(addFilesList.size());
                    progressBar.setIndeterminate(true);
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
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMaximum());
            create_Button.setEnabled(true);
            add_Button.setEnabled(true);
            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            jComboBox2.setEnabled(true);
            jComboBox1.setEnabled(true);

            if ("adding".equals(status)) {
                if (addFilesList.isEmpty()) {

                    if (!listModel.isEmpty()) {

                        remove_Button.setEnabled(true);
                        clear_Button.setEnabled(true);

                    } else {
                        progressBar.setValue(0);
                        remove_Button.setEnabled(false);
                        clear_Button.setEnabled(false);
                    }
                    /*
                     * shows an error message due one or more fields being incorrect.
                     */
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog((Component) o1,
                            "No Files Where Found. Please Try Again.",
                            "Account Creation Error!",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);

                }

            } else if ("accept".equals(task.getStatus())) {

                if (!task.getDupList().isEmpty()) {
                    Files_Add_Duplicates aw = new Files_Add_Duplicates((Frame) o1, true, task.getDupList());
                    aw.setVisible(true);

                } else {
                    Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                    JOptionPane.showMessageDialog((Component) o1,
                            "All Files Have Been Added.",
                            "File Addirion Successful!",
                            JOptionPane.INFORMATION_MESSAGE,
                            tickIcon);

                }

                clear_Button.doClick();
            }
        }

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

                            addedFile = false;
                        } else {
                            addFilesList.add(file);

                            fileIndex = addFilesList.size() - 1;

                            addedFile = true;

                        }

                    }

                }
            }
        }

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
            Statement stmt = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                fileID = 0;
                stmt = conn.createStatement();
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
                getFolderID.close();
                conn.close();

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
            Statement stmt = null;
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
                 pStmt.close();
                    conn.close();

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
            Statement stmt = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO File_Details VALUES (NULL, ? , ? , 0 , DEFAULT);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, filePath);
                pStmt.setInt(2, 0);

                pStmt.executeUpdate();
                pStmt.close();
                conn.close();

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
        boolean alreadyInFolder = false;
        ArrayList<String> dupList = new ArrayList<>();

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
            Statement stmt = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO Folder_File_List VALUES (NULL, ?, ?);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, Current_Folder_ID);
                pStmt.setInt(2, FileID);

                pStmt.executeUpdate();

                pStmt.close();
                conn.close();

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
            Statement stmt = null;
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

                pStmt.close();
                conn.close();

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

    int temp = 0;

    public void propertyChange(PropertyChangeEvent evt) {

        if ("adding".equals(task.getStatus())) {
            if ("progress".equals(evt.getPropertyName())) {
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
     * Creates new form AddWindow
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

        this.Current_Folder = Current_Folder;
        this.Account_ID = Account_ID;
        accept_Button.requestFocus();

        getAccountFolders();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        add_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();
        remove_Button = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taskOutput = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        create_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Files");
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("File Addition Details"));

        jLabel1.setText("Selected Files:");

        jLabel2.setText("Select Item To Add:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "File", "Folder", "Drive", "External Device" }));

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

        taskOutput.setForeground(new java.awt.Color(51, 51, 51));
        taskOutput.setModel(listModel);
        taskOutput.setFocusable(false);
        jScrollPane2.setViewportView(taskOutput);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(add_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(remove_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(add_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remove_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear_Button))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        jLabel3.setText("Select Folder:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
boolean didAdd = false;

    public boolean isDidAdd() {
        return didAdd;
    }

    public void setDidAdd(boolean didAdd) {
        this.didAdd = didAdd;
    }
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // TODO add your handling code here:
        if (!listModel.isEmpty()) {
            Object[] options = {"Confirm", "Cancel"};
            int n = JOptionPane.showOptionDialog(this,
                    "Are You Sure You Want to Add The Files?",
                    "Confirm Folder Creation",
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

    static ArrayList<File> addFilesList = new ArrayList<>();

    private void add_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_ButtonActionPerformed
        // TODO add your handling code here:

        File[] files = null;
        int n = 5;

        if (jComboBox1.getSelectedIndex() == 0) {
            n = 0;
        } else if (jComboBox1.getSelectedIndex() == 1) {
            n = 1;
        } else if (jComboBox1.getSelectedIndex() == 2) {
            n = 2;
        } else if (jComboBox1.getSelectedIndex() == 3) {
            n = 3;
        }

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
            chooser1.setApproveButtonText("Add");
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
            chooser2.setApproveButtonText("Add");
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
            chooser3.setApproveButtonText("Add");
            chooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser3.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser3.getSelectedFiles();

            }
        }

        if (files != null) {

            filelist.clear();

            for (int i = 0; i < files.length; i++) {

                filelist.add(files[i]);

            }
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            task = new Task();
            task.setO1(this);
            task.setStatus("adding");

            if (listModel.isEmpty()) {
                task.setFileIndex(0);
                temp = 0;

            } else {
                task.setFileIndex(task.getFileIndex());
                temp = listModel.getSize();
            }
            task.addPropertyChangeListener(this);
            task.execute();

        }


    }//GEN-LAST:event_add_ButtonActionPerformed
    ArrayList<File> filelist = new ArrayList();

    private Task task;

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed
        // TODO add your handling code here:
        addFilesList.clear();
        filelist.clear();
        listModel.clear();
        progressBar.setValue(0);

        if (!listModel.isEmpty()) {

            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);

        } else {

            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
        }


    }//GEN-LAST:event_clear_ButtonActionPerformed

    private void remove_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_ButtonActionPerformed
        // TODO add your handling code here:

        DefaultListModel dlm = (DefaultListModel) taskOutput.getModel();

        if (taskOutput.getSelectedIndices().length > 0) {

            int[] selectedIndices = taskOutput.getSelectedIndices();

            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.removeElementAt(selectedIndices[i]);
                addFilesList.remove(selectedIndices[i]);
            }

        }

        if (!listModel.isEmpty()) {

            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);

        } else {
            progressBar.setValue(0);
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
        }


    }//GEN-LAST:event_remove_ButtonActionPerformed
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
        Statement stmt = null;
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
            pStmt.close();
            conn.close();

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
    int Current_Folder_ID;

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed

        Current_Folder_ID = getSelectedFolder((String) jComboBox2.getSelectedItem());
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_ButtonActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        Folder_Create af = new Folder_Create((Frame) this.getParent(), true, Account_ID);
        af.setVisible(true);

        if (af.isCreatedFolder() == true) {
            folderNameList.clear();
            getAccountFolders();
            jComboBox2.setSelectedIndex(jComboBox2.getItemCount() - 1);
        } else {
        }
    }//GEN-LAST:event_create_ButtonActionPerformed

    public String getCurrent_Folder() {
        return Current_Folder;
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        Current_Folder = (String) jComboBox2.getSelectedItem();

    }//GEN-LAST:event_formWindowClosing

    ArrayList<String> folderNameList = new ArrayList<>();

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
        Statement stmt = null;

        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            stmt = conn.createStatement();
            String sql = "SELECT folder_Details_ID, folder_Name FROM Folder_Details "
                    + "WHERE account_Details_ID = " + Account_ID + ";";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                folderName = rs.getString("folder_Name");
                folderNameList.add(folderName);
            }
            stmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }

        updateFolderListGUI();
    }

    private void updateFolderListGUI() {

        String tempFolder = Current_Folder;
        jComboBox2.removeAllItems();

        for (int i = 0; i < folderNameList.size(); i++) {
            if (!folderNameList.isEmpty()) {

                jComboBox2.addItem(folderNameList.get(i));

            }
        }
        jComboBox2.setSelectedItem(tempFolder);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JButton add_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton clear_Button;
    private javax.swing.JButton create_Button;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton remove_Button;
    private javax.swing.JList taskOutput;
    // End of variables declaration//GEN-END:variables
}
