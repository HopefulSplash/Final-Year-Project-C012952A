package Proximity_Encryption_Suite;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class Files_Decryption extends javax.swing.JDialog implements ActionListener,
        PropertyChangeListener {

    private DefaultListModel listModel;
    private final int Account_ID;

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    class Task extends SwingWorker<Void, Void> {

        int counter = 0;

        Object o1;
        String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<String> getDupList() {
            return notSupportedList;
        }

        public void setDupList(ArrayList<String> dupList) {
            this.notSupportedList = dupList;
        }

        public void setO1(Object o1) {
            this.o1 = o1;
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            counter = 0;
            progressBar.setValue(0);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);

            //Initialize progress property.
            setProgress(0);

            if ("accept".equals(status)) {
                while (counter != filelist.size()) {
                    progressBar.setMaximum(filelist.size());
                    progressBar.setIndeterminate(true);

                    //Sleep for up to one second.
                    for (int i = 0; i < filelist.size(); i++) {
                        encryptFiles(filelist.get(i));
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

            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);

            if ("accept".equals(task.getStatus())) {

                if (!task.getDupList().isEmpty()) {
                    Files_Add_Duplicates aw = new Files_Add_Duplicates((Frame) o1, true, task.getDupList());
                    aw.setVisible(true);

                } else {
                    Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                    JOptionPane.showMessageDialog((Component) o1,
                            "One Or More Fields Are Incorrect. Please Try Again.",
                            "Account Creation Error!",
                            JOptionPane.INFORMATION_MESSAGE,
                            tickIcon);

                }

                didAdd = true;
                cancel_Button.doClick();

            }
        }

        private void encryptFiles(File file) {
            boolean didDecrypt = false;
            int fileID = 0;

            if (getFileEncryptionStatus(file.getAbsolutePath()).equals("AES Encryption")) {
                didDecrypt = decryptAES(file);

            } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("DES Encryption")) {
                didDecrypt = decryptDES(file);

            } else if (getFileEncryptionStatus(file.getAbsolutePath()).equals("Triple DES Encryption")) {
                didDecrypt = decryptTripleDES(file);
            } else {
                didDecrypt = false;
            }

            if (didDecrypt == false) {
                notSupportedList.add(file.getAbsolutePath());
            } else {
                fileID = getFileID(file.getAbsolutePath());
                if (fileID != 0) {
                    updateFile(fileID, false, "0");
                } else {
                    //file does not exsist
                }
            }

        }

        private String generateKey(String key) {

            String toString = new StringBuilder(key).reverse().toString();

            return toString;
        }

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

        public void updateFile(int filePath, boolean encrypted, String tpye) {

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

                String sql = "UPDATE File_Details SET file_EStatus = ? , file_EType = ? WHERE file_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setBoolean(1, encrypted);
                pStmt.setString(2, tpye);
                pStmt.setInt(3, filePath);

                pStmt.executeUpdate();

                pStmt.close();
                conn.close();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {
                        stmt.close();
                        conn.close();
                    } catch (SQLException ex) {
                    }
                }

            }

        }
        ArrayList<String> notSupportedList = new ArrayList<>();

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
                        stmt.close();
                        conn.close();
                    } catch (SQLException ex) {
                    }
                }
            }
            return fileID;
        }

    }

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
        Statement stmt = null;
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

            pStmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            if (conn != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
        return fileID;
    }
    private String key;

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
    public Files_Decryption(java.awt.Frame parent, boolean modal, int Account_ID, ArrayList<File> Files_Encryption, String key) {
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

        this.Account_ID = Account_ID;
        this.key = key;
        accept_Button.requestFocus();

        addFilesList(Files_Encryption);

    }

    public boolean isDidAdd() {
        return didAdd;
    }

    public void setDidAdd(boolean didAdd) {
        this.didAdd = didAdd;
    }

    ArrayList<File> filelist = new ArrayList();

    public void addFilesList(ArrayList<File> Files_Encryption) {

        for (int i = 0; i < Files_Encryption.size(); i++) {
            listModel.addElement(Files_Encryption.get(i).getAbsolutePath() + "\n");
            filelist.add(Files_Encryption.get(i));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taskOutput = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

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
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Encryption FIles"));

        taskOutput.setModel(listModel);
        taskOutput.setFocusable(false);
        taskOutput.setRequestFocusEnabled(false);
        taskOutput.setSelectionBackground(new java.awt.Color(255, 255, 255));
        taskOutput.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(taskOutput);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        accept_Button.setText("Accept");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // TODO add your handling code here:

        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Encrypt The Files?",
                "Confirm Folder Creation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {
            task = new Task();
            task.setStatus("accept");
            task.addPropertyChangeListener(this);
            task.execute();

        }


    }//GEN-LAST:event_accept_ButtonActionPerformed
    boolean didAdd = false;
    private Task task;

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JList taskOutput;
    // End of variables declaration//GEN-END:variables
}
