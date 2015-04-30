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
 * The Files_Encryption.Java Class implements an application that allows a user
 * to encrypt the files on the system.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Files_Encryption extends javax.swing.JDialog implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
  //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        private int background_Counter = 0;
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
        public void setO1(Object o1) {
            this.background_Object = o1;
        }

        @Override
        public Void doInBackground() {
            background_Counter = 0;
            //setup GUI
            progressBar.setValue(0);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            encrypt_ComboBox.setEnabled(false);

            //Initialize progress property.
            setProgress(0);

            if ("accept".equals(background_Status)) {
                while (background_Counter != filelist.size()) {
                    //setup GUI
                    progressBar.setMaximum(filelist.size());
                    progressBar.setIndeterminate(true);

                    //encrypt files
                    for (int i = 0; i < filelist.size(); i++) {
                        encryptFiles(filelist.get(i));
                        background_Counter++;
                    }

                }
            }
            return null;
        }

        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMaximum());
            //setup GUI
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            encrypt_ComboBox.setEnabled(true);

            if ("accept".equals(task.getBackground_Status())) {

                //confirm files encrypted
                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) background_Object,
                        "All Files Have Been Encrypted.",
                        "File Encryption Successful!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                //close the form
                didEncrypt = true;
                cancel_Button.doClick();

            }
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
         * a method that will check if a files needs to be encrypted.
         *
         * @param file
         */
        private void encryptFiles(File file) {
            boolean didEncrypt = false;
            int fileID = 0;

            if (encrypt_ComboBox.getSelectedIndex() == 1) {
                didEncrypt = encryptAES(file);

            } else if (encrypt_ComboBox.getSelectedIndex() == 2) {
                didEncrypt = encryptDES(file);

            } else if (encrypt_ComboBox.getSelectedIndex() == 3) {
                didEncrypt = encryptTripleDES(file);
            } else {
                didEncrypt = false;
            }

            if (didEncrypt == false) {
            } else {
                fileID = getFileID(file.getAbsolutePath());
                if (fileID != 0) {
                    updateFile(fileID, true, (String) encrypt_ComboBox.getSelectedItem());
                } else {
                    //file does not exsist
                }
            }

        }

        /**
         * a method that will encrypt files that are meant to be encrypted
         *
         * @param file
         * @return
         */
        private boolean encryptAES(File file) {
            boolean encrypted = false;

            if (file.canRead() && file.canWrite() && file.canExecute()) {

                try {

                    File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                    int length = key.length();
                    String eKey = generateKey(key);

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
                    aes.encrypt(eKey, originalInput, encryptedOutput);

                    if (aes.isEncrypted()) {
                        FileInputStream encryptedInput = new FileInputStream(temp);
                        try {
                            FileOutputStream originalOutput = new FileOutputStream(file);
                            aes.doCopy(encryptedInput, originalOutput);
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
         * a method that will encrypt files that are meant to be encrypted
         *
         * @param file
         * @return
         */
        private boolean encryptDES(File file) {

            boolean encrypted = false;
            if (file.canRead() && file.canWrite() && file.canExecute()) {
                try {

                    File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                    String eKey = generateKey(key);

                    FileInputStream originalInput = new FileInputStream(file);
                    FileOutputStream encryptedOutput = new FileOutputStream(temp);

                    Encryption_DES des = new Encryption_DES();
                    des.encrypt(eKey, originalInput, encryptedOutput);

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
         * a method that will encrypt files that are meant to be encrypted
         *
         * @param file
         * @return
         */
        private boolean encryptTripleDES(File file) {
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
                    tdes.encrypt(eKey, originalInput, encryptedOutput);

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

        /**
         * a method that will update the file information on the database
         *
         * @param filePath
         * @param encrypted
         * @param tpye
         */
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
     *
     *
     * Creates new form Files_Encryption
     *
     * @param parent
     * @param Account_ID
     * @param Files_Encryption
     * @param modal
     * @param key
     */
    public Files_Encryption(java.awt.Frame parent, boolean modal, int Account_ID, ArrayList<File> Files_Encryption, String key) {
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
        //setup variables
        this.Account_ID = Account_ID;
        this.key = key;
        accept_Button.requestFocus();
        //add files to GUI
        addFilesList(Files_Encryption);

    }
    /**
     * a method that gets if the files have been encrypted
     * 
     * @return 
     */
    public boolean isDidEncrypt() {
        return didEncrypt;
    }
    /**
     * a method that will set if the file has been encrypted
     * 
     * @param didEncrypt 
     */
    public void setDidEncrypt(boolean didEncrypt) {
        this.didEncrypt = didEncrypt;
    }

    /**
     * a method that will add all the files into the list for the user to see
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
        select_Panel = new javax.swing.JPanel();
        encrypt_ComboBox = new javax.swing.JComboBox();
        select_Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | File Encryption");
        setModal(true);
        setResizable(false);

        file_Panel.setBackground(new java.awt.Color(255, 255, 255));
        file_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Files To Encrypt"));

        file_List.setModel(listModel);
        file_List.setFocusable(false);
        file_List.setRequestFocusEnabled(false);
        file_List.setSelectionBackground(new java.awt.Color(255, 255, 255));
        file_List.setSelectionForeground(new java.awt.Color(0, 0, 0));
        file_ScrollPane.setViewportView(file_List);

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

        select_Panel.setBackground(new java.awt.Color(255, 255, 255));
        select_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        encrypt_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select An Encryption Method", "AES Encryption", "DES Encryption", "Triple DES Encryption" }));
        encrypt_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encrypt_ComboBoxActionPerformed(evt);
            }
        });

        select_Label.setText("Select Encryption Method:");

        javax.swing.GroupLayout select_PanelLayout = new javax.swing.GroupLayout(select_Panel);
        select_Panel.setLayout(select_PanelLayout);
        select_PanelLayout.setHorizontalGroup(
            select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, select_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(select_Label)
                .addGap(6, 6, 6)
                .addComponent(encrypt_ComboBox, 0, 651, Short.MAX_VALUE))
        );
        select_PanelLayout.setVerticalGroup(
            select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(select_PanelLayout.createSequentialGroup()
                .addGroup(select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encrypt_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select_Label))
                .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(select_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(select_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(file_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * a method that will start the encryption process
     * 
     * @param evt 
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // TODO add your handling code here:

        if (encrypt_ComboBox.getSelectedIndex() != 0) {

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
                task.setBackground_Status("accept");
                task.addPropertyChangeListener(this);
                task.execute();

            }
        } else {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog(this,
                    "No Encryption Method Selected. Please Try Again.",
                    "File Encyption Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }

    }//GEN-LAST:event_accept_ButtonActionPerformed
    /**
     * a method tht will close the form
     * 
     * @param evt 
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will disable or enable GUI components depending on selection
     * 
     * @param evt 
     */
    private void encrypt_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encrypt_ComboBoxActionPerformed
        // TODO add your handling code here:

        //checks if the user has selected a question.
        if (encrypt_ComboBox.getSelectedIndex() != 0) {
            accept_Button.setEnabled(true);
        } //resets the fields if the user have change the combo box to index 0.
        else {
            accept_Button.setEnabled(false);
        }
    }//GEN-LAST:event_encrypt_ComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JComboBox encrypt_ComboBox;
    private javax.swing.JList file_List;
    private javax.swing.JPanel file_Panel;
    private javax.swing.JScrollPane file_ScrollPane;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel select_Label;
    private javax.swing.JPanel select_Panel;
    // End of variables declaration//GEN-END:variables
    private final DefaultListModel listModel;
    private final int Account_ID;
    private final String key;
    boolean didEncrypt = false;
    private Task task;
    private final ArrayList<File> filelist = new ArrayList();

}
