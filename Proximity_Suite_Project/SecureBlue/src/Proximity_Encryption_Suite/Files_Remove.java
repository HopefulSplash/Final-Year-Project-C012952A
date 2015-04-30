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
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * The Files_Remove.Java Class implements an application that allows a user to
 * remove files from the system.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Files_Remove extends javax.swing.JDialog implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        private int background_Progress = 0;
        private Object background_Object;
        private String background_Status;

        /**
         * a method to get the status
         *
         * @return
         */
        public String getBackground_Status() {
            return background_Status;
        }

        /**
         * a method to set the status
         *
         * @param background_Status
         */
        public void setBackground_Status(String background_Status) {
            this.background_Status = background_Status;
        }

        /**
         * a method to set the object.
         *
         * @param o1
         */
        public void setBackground_Object(Object background_Object) {
            this.background_Object = background_Object;
        }

        @Override
        public Void doInBackground() {
            int counter = 0;
            background_Progress = 0;
            //setup GUI
            progressBar.setValue(0);
            progressBar.setMaximum(filelist.size());
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            type_ComboBox.setEnabled(false);
            remove_ComboBox.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if ("accept".equals(background_Status)) {
                while (counter != filelist.size()) {
                    progressBar.setIndeterminate(true);
                    //remove all the selected files.
                    for (int i = 0; i < filelist.size(); i++) {
                        removeFiles(filelist.get(i));
                        counter++;
                    }

                }
            }
            return null;
        }

        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor
            //setup GUI
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMaximum());
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            type_ComboBox.setEnabled(true);
            remove_ComboBox.setEnabled(true);

            if ("accept".equals(task.getBackground_Status())) {
                //confirms the files have been removed
                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) background_Object,
                        "All Files Have Been Removed.",
                        "File Removal Successful!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                didRemove = true;
                //closes the fomr
                cancel_Button.doClick();

            }
        }
        /**
         * a method that will remove a file and perform decryption if needed.
         * 
         * @param file 
         */
        private void removeFiles(File file) {
            boolean didDecrypt = false;
            int fileID = 0;

            if (type_ComboBox.getSelectedIndex() == 1) {
                if (remove_ComboBox.getSelectedIndex() == 0) {
                    removeFile(file, "Folder");

                } else if (remove_ComboBox.getSelectedIndex() == 1) {

                    if (getFileEncryptionStatus(file.getAbsolutePath()).equals("AES Encryption")) {
                        didDecrypt = decryptAES(file);
                        removeFile(file, "Folder");

                    } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("DES Encryption")) {
                        didDecrypt = decryptDES(file);
                        removeFile(file, "Folder");

                    } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("Triple DES Encryption")) {
                        didDecrypt = decryptTripleDES(file);
                        removeFile(file, "Folder");
                    }

                }
            } else if (type_ComboBox.getSelectedIndex() == 2) {
                if (remove_ComboBox.getSelectedIndex() == 0) {
                    removeFile(file, "Account");

                } else if (remove_ComboBox.getSelectedIndex() == 1) {

                    if (getFileEncryptionStatus(file.getAbsolutePath()).equals("AES Encryption")) {
                        didDecrypt = decryptAES(file);
                        removeFile(file, "Account");

                    } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("DES Encryption")) {
                        didDecrypt = decryptDES(file);
                        removeFile(file, "Account");

                    } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("Triple DES Encryption")) {
                        didDecrypt = decryptTripleDES(file);
                        removeFile(file, "Account");
                    }
                }

            }

        }

        /**
         * a method that will get the type of encryption that has been used on a
         * file
         *
         * @param file_Path
         * @return
         */
        public String getFileEncryptionStatus(String file_Path) {

            String fileID = null;

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

                String sql = "SELECT file_EType FROM File_Details WHERE file_Directory = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, file_Path);
                ResultSet rs = pStmt.executeQuery();

                while (rs.next()) {
                    fileID = rs.getString("file_EType");
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

        /**
         * a method that will decrypt files that are encrypted
         *
         * @param file
         * @return
         */
        private boolean decryptAES(File file) {
            boolean decrypted = false;

            if (file.canRead() && file.canWrite() && file.canExecute()) {

                try {

                    File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                    String key = "squirrel123"; // needs to be at least 8 characters for DES
                    int length = key.length();
                    if (length > 16 && length != 16) {
                        key = key.substring(0, 15);
                    }
                    if (length < 16 && length != 16) {
                        for (int i = 0; i < 16 - length; i++) {
                            key = key + "0";
                        }
                    }

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_AES aes = new Encryption_AES();
                    aes.decrypt(key, originalInput, encryptedOutput);

                    if (aes.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            aes.doCopy(encryptedInput, originalOutput);
                            temp.delete();
                            decrypted = true;
                        } catch (Exception e) {
                            decrypted = false;
                        }
                    } else {
                        decrypted = false;
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return decrypted;
        }

        /**
         * a method that will decrypt files that are encrypted
         *
         * @param file
         * @return
         */
        private boolean decryptDES(File file) {

            boolean encrypted = false;
            if (file.canRead() && file.canWrite() && file.canExecute()) {
                try {

                    File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                    String key = "squirrel123"; // needs to be at least 8 characters for DES

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_DES des = new Encryption_DES();
                    des.decrypt(key, originalInput, encryptedOutput);

                    if (des.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            des.doCopy(encryptedInput, originalOutput);
                            temp.delete();
                            encrypted = true;
                        } catch (Exception e) {
                            encrypted = false;
                        }

                    } else {
                        encrypted = false;
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return encrypted;

        }

        /**
         * a method that will decrypt files that are encrypted
         *
         * @param file
         * @return
         */
        private boolean decryptTripleDES(File file) {
            boolean encrypted = false;
            if (file.canRead() && file.canWrite() && file.canExecute()) {
                try {

                    File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                    String key = "squirrel123"; // needs to be at least 8 characters for DES
                    int length = key.length();
                    if (length > 16 && length != 16) {
                        key = key.substring(0, 15);
                    }
                    if (length < 16 && length != 16) {
                        for (int i = 0; i < 16 - length; i++) {
                            key = key + "0";
                        }
                    }

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_Triple_DES tdes = new Encryption_Triple_DES();
                    tdes.decrypt(key, originalInput, encryptedOutput);

                    if (tdes.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            tdes.doCopy(encryptedInput, originalOutput);
                            temp.delete();
                            encrypted = true;
                        } catch (IOException e) {
                            encrypted = false;
                        }

                    } else {
                        encrypted = false;
                    }
                } catch (Throwable e) {
                }
            }
            return encrypted;

        }

        /**
         * a method that will remove a file from the database.
         *
         * @param File
         * @param tpye
         */
        public void removeFile(File File, String tpye) {

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

                switch (tpye) {
                    case "Account": {
                        String sql = "DELETE FROM folder_file_list WHERE file_Details_ID = ?;";
                        PreparedStatement pStmt = conn.prepareStatement(sql);
                        pStmt.setInt(1, getFileID(File.getAbsolutePath()));
                        pStmt.executeUpdate();
                        sql = "DELETE FROM file_details WHERE file_Details_ID = ?;";
                        pStmt = conn.prepareStatement(sql);
                        pStmt.setInt(1, getFileID(File.getAbsolutePath()));
                        pStmt.executeUpdate();
                        break;
                    }
                    case "Folder": {
                        String sql = "DELETE FROM folder_file_list WHERE file_Details_ID = ? AND folder_Details_ID = ?;";
                        PreparedStatement pStmt = conn.prepareStatement(sql);
                        pStmt.setInt(2, getSelectedFolder(Current_Folder));
                        pStmt.setInt(1, getFileID(File.getAbsolutePath()));
                        pStmt.executeUpdate();

                        if (checkFileInOtherFolders(getFileID(File.getAbsolutePath())) == false) {
                            sql = "DELETE FROM file_details WHERE file_Details_ID = ?;";
                            pStmt = conn.prepareStatement(sql);
                            pStmt.setInt(1, getFileID(File.getAbsolutePath()));
                            pStmt.executeUpdate();

                        }
                        break;
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
         * a method that will check if the file is in another folder if is it
         * then it will only remove it from the folder not the whole account
         *
         * @param fileID
         * @return
         */
        private boolean checkFileInOtherFolders(int fileID) {
            boolean is = false;
            int file = 0;
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

                String sql = "SELECT file_Details_ID FROM folder_file_list WHERE file_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, fileID);
                ResultSet rs = pStmt.executeQuery();

                while (rs.next()) {
                    file = rs.getInt("folder_Details_ID");

                }

                if (file != 0) {
                    is = true;
                } else {
                    is = false;
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

            return is;
        }

        /**
         * a method that will get the id of a file
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

    /**
     * Creates new form Files_Remove
     *
     * @param parent
     * @param Account_ID
     * @param modal
     * @param Files_Encryption
     * @param Folder
     */
    public Files_Remove(java.awt.Frame parent, boolean modal, int Account_ID, ArrayList<File> Files_Encryption, String Folder) {
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
        //setup GUI
        listModel = new DefaultListModel();
        Current_Folder = Folder;
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
        this.Account_ID = Account_ID;
        accept_Button.requestFocus();
        //adds all the files to GUI
        addFilesList(Files_Encryption);

    }

    /**
     * a method that will return f the files have been removed
     *
     * @return
     */
    public boolean isDidRemove() {
        return didRemove;
    }

    /**
     * a method to set if the files have been removed
     *
     * @param didRemove
     */
    public void setDidRemove(boolean didRemove) {
        this.didRemove = didRemove;
    }

    /**
     * a method that will add all the files to the GUI
     *
     * @param Files_Encryption
     */
    private void addFilesList(ArrayList<File> Files_Encryption) {

        for (int i = 0; i < Files_Encryption.size(); i++) {
            listModel.addElement(Files_Encryption.get(i).getAbsolutePath() + "\n");
            filelist.add(Files_Encryption.get(i));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        file_Panel = new javax.swing.JPanel();
        file_ScrollPane = new javax.swing.JScrollPane();
        file_List = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        button_Panel = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        folder_Panel = new javax.swing.JPanel();
        type_ComboBox = new javax.swing.JComboBox();
        type_Label = new javax.swing.JLabel();
        remove_Label = new javax.swing.JLabel();
        remove_ComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | File Remove");
        setModal(true);
        setResizable(false);

        file_Panel.setBackground(new java.awt.Color(255, 255, 255));
        file_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Files To Remove"));

        file_List.setModel(listModel);
        file_List.setFocusable(false);
        file_List.setRequestFocusEnabled(false);
        file_List.setSelectionBackground(new java.awt.Color(255, 255, 255));
        file_List.setSelectionForeground(new java.awt.Color(0, 0, 0));
        file_ScrollPane.setViewportView(file_List);

        progressBar.setMaximum(1000000000);

        javax.swing.GroupLayout file_PanelLayout = new javax.swing.GroupLayout(file_Panel);
        file_Panel.setLayout(file_PanelLayout);
        file_PanelLayout.setHorizontalGroup(
            file_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(file_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(file_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(file_ScrollPane))
                .addGap(6, 6, 6))
        );
        file_PanelLayout.setVerticalGroup(
            file_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, file_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(file_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select An Removal Method", "Remove From Folder", "Remove From Account" }));
        type_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type_ComboBoxActionPerformed(evt);
            }
        });

        type_Label.setText("Select Removal Type:");

        remove_Label.setText("Remove / Decrypt:");

        remove_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Remove Files", "Remove And Decrypt  Files" }));

        javax.swing.GroupLayout folder_PanelLayout = new javax.swing.GroupLayout(folder_Panel);
        folder_Panel.setLayout(folder_PanelLayout);
        folder_PanelLayout.setHorizontalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, folder_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(remove_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(type_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(remove_ComboBox, 0, 651, Short.MAX_VALUE)))
        );
        folder_PanelLayout.setVerticalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, folder_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(remove_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remove_Label))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(file_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(file_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * a method that will start the remove process.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed

        if (type_ComboBox.getSelectedIndex() != 0) {

            Object[] options = {"Confirm", "Cancel"};
            int n = JOptionPane.showOptionDialog(this,
                    "Are You Sure You Want to Remove The Files?",
                    "Confirm Folder Removal",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title

            // if the user has clicked confirm.
            if (n == 0) {
                task = new Task();
                task.setBackground_Status("accept");
                task.addPropertyChangeListener(this);
                task.execute();

            }
        } else {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog(this,
                    "No Remove Metho Selected. Please Try Again.",
                    "File Removal Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }

    }//GEN-LAST:event_accept_ButtonActionPerformed

    /**
     * a method that will close the form
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will disable or enable GUI components depending on user
     * selection
     *
     * @param evt
     */
    private void type_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type_ComboBoxActionPerformed

        //checks if the user has selected a question.
        if (type_ComboBox.getSelectedIndex() != 0) {
            accept_Button.setEnabled(true);
        } //resets the fields if the user have change the combo box to index 0.
        else {
            accept_Button.setEnabled(false);
        }
    }//GEN-LAST:event_type_ComboBoxActionPerformed
    /**
     * a method that will get the current folders ID
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JList file_List;
    private javax.swing.JPanel file_Panel;
    private javax.swing.JScrollPane file_ScrollPane;
    private javax.swing.JPanel folder_Panel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JComboBox remove_ComboBox;
    private javax.swing.JLabel remove_Label;
    private javax.swing.JComboBox type_ComboBox;
    private javax.swing.JLabel type_Label;
    // End of variables declaration//GEN-END:variables
    private final DefaultListModel listModel;
    private final int Account_ID;
    private final String Current_Folder;
    private boolean didRemove = false;
    private Task task;
    private ArrayList<File> filelist = new ArrayList();

}
