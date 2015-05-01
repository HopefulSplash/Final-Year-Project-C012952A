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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.sql.Statement;
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
 * The Folder_Delete.Java Class implements an application that allows a user to
 * delete a folder on the system.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Folder_Delete extends java.awt.Dialog implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        private int background_Progress = 0;
        private Object background_Object;
        private String background_Status;
        /**
         * a method that will get the status
         * 
         * @return 
         */
        public String getBackground_Status() {
            return background_Status;
        }
        /**
         * a method that will set the status
         * 
         * @param background_Status 
         */
        public void setBackground_Status(String background_Status) {
            this.background_Status = background_Status;
        }
        /**
         * a method that will set the Object
         * 
         * @param o1 
         */
        public void setO1(Object o1) {
            this.background_Object = o1;
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            background_Progress = 0;
            progressBar.setValue(0);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            folder_ComboBox.setEnabled(false);

            //Initialize background_Progress property.
            setProgress(0);

            if ("accept".equals(background_Status)) {
                progressBar.setMaximum(1);
                progressBar.setIndeterminate(true);
                //starts the process of deleting the foler
                getFolderFiles((String) folder_ComboBox.getSelectedItem());
                background_Progress += 1;
                setProgress(background_Progress);

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
            //setup GUI
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            folder_ComboBox.setEnabled(true);

            Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
            JOptionPane.showMessageDialog((Component) background_Object,
                    "Folder Deleted Successfully.",
                    "Folder Removal Successful!",
                    JOptionPane.INFORMATION_MESSAGE,
                    tickIcon);
            //close form
            cancel_Button.doClick();

        }

        /**
         * a method that will get all the files from a folder
         *
         * @param folderDelete
         */
        private void getFolderFiles(String folderDelete) {

            ArrayList<Integer> fileIDList = new ArrayList<>();

            int folderID = 0;
            int fileID = 0;


            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();
            d.startDatabase();

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "SELECT folder_Details_ID FROM Folder_Details WHERE account_Details_ID = " + accountID + " AND folder_Name = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, folderDelete);

                ResultSet rs = pStmt.executeQuery();

                while (rs.next()) {
                    folderID = rs.getInt("folder_Details_ID");

                }

                Statement stmt = conn.createStatement();
                sql = "SELECT file_Details_ID FROM Folder_File_List "
                        + "WHERE folder_Details_ID = " + folderID + ";";

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    fileID = rs.getInt("file_Details_ID");
                    fileIDList.add(fileID);

                }
                ;

                for (int i = 0; i < fileIDList.size(); i++) {

                    if (getFileEncryptionStatus(fileIDList.get(i)).equals("AES Encryption")) {
                        decryptAES(new File(getFileID(fileIDList.get(i))));
                        removeFile(fileIDList.get(i), folderID);

                    } else if (getFileEncryptionStatus(fileIDList.get(i)).equals("DES Encryption")) {
                        decryptDES(new File(getFileID(fileIDList.get(i))));
                        removeFile(fileIDList.get(i), folderID);

                    } else if (getFileEncryptionStatus(fileIDList.get(i)).equals("Triple DES Encryption")) {
                        decryptTripleDES(new File(getFileID(fileIDList.get(i))));
                        removeFile(fileIDList.get(i), folderID);
                    } else {
                        removeFile(fileIDList.get(i), folderID);
                    }

                }

                deleteFolder(folderID);

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
         * a method that will delete a folder
         *
         * @param folderid
         */
        public void deleteFolder(int folderid) {
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

                String sql = "DELETE FROM folder_Details WHERE folder_Details_ID = ?;";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, folderid);
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
         * a method that will get the type of encryption that has been used on a
         * file
         *
         * @param file_Path
         * @return
         */
        public String getFileEncryptionStatus(int file_Path) {

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

                String sql = "SELECT file_EType FROM File_Details WHERE file_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, file_Path);
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

                    String eKey = generateKey(key);
                    int length = eKey.length();

                    if (length > 16 && length != 16) {
                        eKey = eKey.substring(0, 16);
                    }
                    if (length < 16 && length != 16) {
                        for (int i = 0; i < 16 - length; i++) {
                            eKey = eKey + "0";
                        }
                    }

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_AES aes = new Encryption_AES();
                    aes.decrypt(eKey, originalInput, encryptedOutput);

                    if (aes.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            aes.doCopy(encryptedInput, originalOutput);
                            temp.delete();
                            decrypted = true;
                        } catch (IOException e) {
                            decrypted = false;
                        }
                    } else {
                        decrypted = false;
                    }

                } catch (Throwable e) {
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

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    String eKey = generateKey(key);

                    Encryption_DES des = new Encryption_DES();
                    des.decrypt(eKey, originalInput, encryptedOutput);

                    if (des.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            des.doCopy(encryptedInput, originalOutput);
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
         * a method that will generate a key for the encryption.
         *
         * @param key
         * @return
         */
        private String generateKey(String key) {

            String toString = new StringBuilder(key).reverse().toString();

            return toString;
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

                    String eKey = generateKey(key);
                    int length = eKey.length();
                    if (length > 16 && length != 16) {
                        eKey = eKey.substring(0, 15);
                    }
                    if (length < 16 && length != 16) {
                        for (int i = 0; i < 16 - length; i++) {
                            eKey = eKey + "0";
                        }
                    }

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_Triple_DES tdes = new Encryption_Triple_DES();
                    tdes.decrypt(eKey, originalInput, encryptedOutput);

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
         * a method that will remove a file
         *
         * @param fileid
         * @param folderid
         */
        public void removeFile(int fileid, int folderid) {

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

                String sql = "DELETE FROM folder_file_list WHERE file_Details_ID = ? AND folder_Details_ID = ?;";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(2, folderid);
                pStmt.setInt(1, fileid);
                pStmt.executeUpdate();

                if (checkFileInOtherFolders(fileid) == false) {
                    sql = "DELETE FROM file_details WHERE file_Details_ID = ?;";
                    pStmt = conn.prepareStatement(sql);
                    pStmt.setInt(1, fileid);
                    pStmt.executeUpdate();
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
         * a method that will check if the file is in more than 1 folder
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

    }

    /**
     * a method that will get the files ID
     *
     */
    public String getFileID(int file_Path) {

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

            String sql = "SELECT file_Directory FROM File_Details WHERE file_Details_ID = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, file_Path);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                fileID = rs.getString("file_Directory");
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
     * Creates new form Folder_Delete
     *
     * @param parent
     * @param modal
     * @param accountID
     */
    public Folder_Delete(java.awt.Frame parent, boolean modal, int accountID) {
        super(parent, modal);

        this.setBackground(Color.WHITE);
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
        this.accountID = accountID;
        getAccountDetails();
        this.key = accountPass;
        getAccountFolders();
    }

    /**
     * a method that will get the account details so the files can be decrypted
     * when the folder is deleted
     */
    private void getAccountDetails() {
        /*
         * declares and new instance of the Suite_Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Suite_Database d = new Suite_Database();
        d.startDatabase();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            Statement stmt = conn.createStatement();
            String sql = "SELECT account_Username, account_Password FROM Account_Details "
                    + "WHERE account_Details_ID = '" + accountID + "';";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                accountPass = rs.getString("account_Password");
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
     * This method is called from within the constructor to initialize the form.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        accept_Button = new javax.swing.JButton();
        folder_Panel = new javax.swing.JPanel();
        folder_ComboBox = new javax.swing.JComboBox();
        folder_Label = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setTitle("Proximity Suite | Delete Folder");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        cancel_Button.setText("Cancel");
        cancel_Button.setFocusPainted(false);
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        accept_Button.setText("Accept");
        accept_Button.setFocusPainted(false);
        accept_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(accept_Button))
                .addGap(0, 0, 0))
        );

        folder_Panel.setBackground(new java.awt.Color(255, 255, 255));
        folder_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        folder_Label.setText("Select Folder:");

        javax.swing.GroupLayout folder_PanelLayout = new javax.swing.GroupLayout(folder_Panel);
        folder_Panel.setLayout(folder_PanelLayout);
        folder_PanelLayout.setHorizontalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, folder_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Label)
                .addGap(6, 6, 6)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_ComboBox, 0, 508, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        folder_PanelLayout.setVerticalGroup(
            folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folder_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that will close the form
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.dispose();
    }//GEN-LAST:event_closeDialog
    /**
     * a method that will return if folder is deleted
     *
     * @return
     */
    public boolean isDidDelete() {
        return didDelete;
    }

    /**
     * a method to set if the folder was delete
     *
     * @param didDelete
     */
    public void setDidDelete(boolean didDelete) {
        this.didDelete = didDelete;
    }

    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed

        Object[] options = {"Yes",
            "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want To Delete This Folder ",
                "Create Folder",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {

            if (folder_ComboBox.getSelectedIndex() != 0) {
                didDelete = true;
                task = new Task();
                task.setBackground_Status("accept");
                task.addPropertyChangeListener(this);
                task.execute();

            } else {
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "You Cannot Delete Your Default Folder. Please Try Again.",
                        "Folder Removal Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }

        }


    }//GEN-LAST:event_accept_ButtonActionPerformed
    /**
     * a method that will get all the folders belong to the account
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
                    + "WHERE account_Details_ID = " + accountID + ";";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                folderName = rs.getString("folder_Name");
                folderIDList.add(folderID);
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
        updateFolderListGUI();
    }

    private void updateFolderListGUI() {

        folder_ComboBox.removeAllItems();

        for (int i = 0; i < folderIDList.size(); i++) {
            if (!folderIDList.isEmpty()) {

                folder_ComboBox.addItem(folderNameList.get(i));
            }
        }

    }

    /**
     * a method that will close the form
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JComboBox folder_ComboBox;
    private javax.swing.JLabel folder_Label;
    private javax.swing.JPanel folder_Panel;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    private final ArrayList<Integer> folderIDList = new ArrayList<>();
    private final ArrayList<String> folderNameList = new ArrayList<>();
    private DefaultListModel listModel;
    private boolean didDelete = false;
    private Task task;
    private String accountPass;
    private int accountID = 0;
    private String key;

}
