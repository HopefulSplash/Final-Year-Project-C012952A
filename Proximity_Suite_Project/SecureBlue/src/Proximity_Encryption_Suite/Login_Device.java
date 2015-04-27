package Proximity_Encryption_Suite;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/*
 * To change this licefsdfsddfsdsfsdfdsfsdfnse header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class Login_Device extends javax.swing.JFrame {

    private Map<Integer, List<String>> mapDevicePosition = new HashMap<Integer, List<String>>();

    class Task extends SwingWorker<Void, Void> {

        private Map<String, List<String>> mapReturnResult = new HashMap<String, List<String>>();
        int counter = 0;
        String status;

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public Void doInBackground() {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            counter = 0;

            //Initialize progress property.
            setProgress(0);

            while (counter != 1) {

                if ("Setup".equals(status)) {
                    Suite_Database d = new Suite_Database();
                    d.startDatabase();
                    counter++;

                } else if ("Scan".equals(status)) {

                    jComboBox1.setEnabled(false);

                    int intDevicePosition = 0;

                    /* Create an object of ServicesSearch */
                    ServicesSearch ss = new ServicesSearch();
                    /* Get bluetooth device details */
                    mapReturnResult = ss.getBluetoothDevices();


                    /* Add devices in JList */
                    for (Map.Entry<String, List<String>> entry : mapReturnResult.entrySet()) {
                        jComboBox1.addItem(entry.getValue().get(0));
                        mapDevicePosition.put(intDevicePosition, entry.getValue());
                        intDevicePosition++;
                    }

                    counter++;
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

            if ("Scan".equals(status)) {
                jComboBox1.removeItemAt(0);
                jComboBox1.setEnabled(true);
                passwordField.setEnabled(true);
                login_Button.setEnabled(true);
                account_Recover_Button1.setEnabled(true);
                jButton1.setEnabled(true);
                account_Creation_Button1.setEnabled(true);
            }
        }

        /**
         * a method that converts a user entered password into SHA1 so it can be
         * stored in the database securely.
         *
         * @param input
         * @return String
         */
        private String convertToSha1(String input) throws NoSuchAlgorithmException {

            /*
             * converts to string input into a SHA1 byte array.
             */
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());

            /*
             * builds the new string so that it is in the correct format for storage.
             */
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            /*
             * returns the string so it can be used.
             */
            return sb.toString();
        }

    }

    public void propertyChange(PropertyChangeEvent evt) {

    }

    /**
     * Creates new form TestLogin
     */
    public Login_Device() {
        this.getContentPane().setBackground(Color.WHITE);
        /**
         * Declares the icons used for the windows icon and the frames icon.
         */
        URL icon16URL = getClass().getResource("/Proximity/graphic_Logos/Logo_Small.png");
        URL icon32URL = getClass().getResource("/Proximity/graphic_Logos/Logo_Large.png");

        /**
         * Image list to store the icons in.
         */
        final ArrayList<Image> icons = new ArrayList<>();

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

        Task task = new Task();
        task.setStatus("Setup");
        task.execute();

        jComboBox1.setEnabled(false);
        login_Button.requestFocus();

    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginDetailsPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        changeLoginLabel = new javax.swing.JLabel();
        login_Button = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        login_Logo_Panel3 = new javax.swing.JPanel();
        login_Logo_Image3 = new javax.swing.JLabel();
        creation_Recovery_Panel1 = new javax.swing.JPanel();
        account_Creation_Button1 = new javax.swing.JButton();
        account_Recover_Button1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SecureBlue | Device Login");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(null);
        setMinimumSize(null);
        setName("accountLoginFrame"); // NOI18N
        setResizable(false);

        loginDetailsPanel.setBackground(new java.awt.Color(255, 255, 255));
        loginDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Login Details"));
        loginDetailsPanel.setName(""); // NOI18N

        usernameLabel.setText("Device:");
        usernameLabel.setPreferredSize(new java.awt.Dimension(55, 14));

        passwordLabel.setText("Passcode:");

        passwordField.setToolTipText("");
        passwordField.setEnabled(false);
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        PlainDocument document = (PlainDocument) passwordField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 6) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });

        changeLoginLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        changeLoginLabel.setForeground(new java.awt.Color(43, 85, 166));
        changeLoginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changeLoginLabel.setText("<HTML><U>Click Here To Login Using A Bluetooth Device <U><HTML>");
        changeLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeLoginLabelMouseClicked(evt);
            }
        });

        login_Button.setText("Login");
        login_Button.setFocusPainted(false);
        login_Button.setRequestFocusEnabled(false);
        login_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_ButtonActionPerformed(evt);
            }
        });

        jComboBox1.setMaximumRowCount(20);
        jComboBox1.setLightWeightPopupEnabled(false);
        jComboBox1.setName(""); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Refresh.png"))); // NOI18N
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginDetailsPanelLayout = new javax.swing.GroupLayout(loginDetailsPanel);
        loginDetailsPanel.setLayout(loginDetailsPanelLayout);
        loginDetailsPanelLayout.setHorizontalGroup(
            loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(changeLoginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginDetailsPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(login_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(6, 6, 6))
        );
        loginDetailsPanelLayout.setVerticalGroup(
            loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginDetailsPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(login_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeLoginLabel)
                .addGap(6, 6, 6))
        );

        login_Logo_Panel3.setBackground(new java.awt.Color(255, 255, 255));
        login_Logo_Panel3.setMaximumSize(null);

        login_Logo_Image3.setBackground(new java.awt.Color(255, 255, 255));
        login_Logo_Image3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        login_Logo_Image3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Login_Logo.png"))); // NOI18N
        login_Logo_Image3.setName("login_Logo_Image"); // NOI18N

        javax.swing.GroupLayout login_Logo_Panel3Layout = new javax.swing.GroupLayout(login_Logo_Panel3);
        login_Logo_Panel3.setLayout(login_Logo_Panel3Layout);
        login_Logo_Panel3Layout.setHorizontalGroup(
            login_Logo_Panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(login_Logo_Image3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
        );
        login_Logo_Panel3Layout.setVerticalGroup(
            login_Logo_Panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, login_Logo_Panel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(login_Logo_Image3))
        );

        creation_Recovery_Panel1.setBackground(new java.awt.Color(255, 255, 255));
        creation_Recovery_Panel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Creation / Recovery"));
        creation_Recovery_Panel1.setMaximumSize(null);

        account_Creation_Button1.setText("Create Account");
        account_Creation_Button1.setFocusPainted(false);
        account_Creation_Button1.setMaximumSize(null);
        account_Creation_Button1.setMinimumSize(null);
        account_Creation_Button1.setPreferredSize(null);
        account_Creation_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_Creation_Button1ActionPerformed(evt);
            }
        });

        account_Recover_Button1.setText("Recover Account");
        account_Recover_Button1.setFocusPainted(false);
        account_Recover_Button1.setMaximumSize(null);
        account_Recover_Button1.setMinimumSize(null);
        account_Recover_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_Recover_Button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout creation_Recovery_Panel1Layout = new javax.swing.GroupLayout(creation_Recovery_Panel1);
        creation_Recovery_Panel1.setLayout(creation_Recovery_Panel1Layout);
        creation_Recovery_Panel1Layout.setHorizontalGroup(
            creation_Recovery_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creation_Recovery_Panel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(account_Creation_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(account_Recover_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        creation_Recovery_Panel1Layout.setVerticalGroup(
            creation_Recovery_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creation_Recovery_Panel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(creation_Recovery_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(account_Creation_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(account_Recover_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(login_Logo_Panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(creation_Recovery_Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(login_Logo_Panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(loginDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creation_Recovery_Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        getContentPane().setBackground(Color.WHITE);

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
        passwordField.setFocusable(false);
        passwordField.setFocusable(true);
        login_Button.doClick();

    }//GEN-LAST:event_passwordFieldActionPerformed

    private void changeLoginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeLoginLabelMouseClicked
        // TODO add your handling code here:
        Login_Account dLSameple = new Login_Account();
        dLSameple.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_changeLoginLabelMouseClicked

    private void account_Creation_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Creation_Button1ActionPerformed
        Login_Account_Create cASameple = new Login_Account_Create("Login");
        cASameple.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_account_Creation_Button1ActionPerformed

    private void account_Recover_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Recover_Button1ActionPerformed
        // TODO add your handling code here:
        Login_Account_Recover rASameple = new Login_Account_Recover();
        rASameple.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_account_Recover_Button1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        mapDevicePosition.clear();
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Scanning...");

        passwordField.setText("");
        // Variables declaration - do not modify                     

        passwordField.setEnabled(false);
        login_Button.setEnabled(false);
        account_Recover_Button1.setEnabled(false);
        jButton1.setEnabled(false);
        account_Creation_Button1.setEnabled(false);
        Task task = new Task();
        task.setStatus("Scan");
        task.execute();

    }//GEN-LAST:event_jButton1ActionPerformed

    private String convertToSha1(String input) throws NoSuchAlgorithmException {

        /*
         * converts to string input into a SHA1 byte array.
         */
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());

        /*
         * builds the new string so that it is in the correct format for storage.
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        /*
         * returns the string so it can be used.
         */
        return sb.toString();
    }

    private void login_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_ButtonActionPerformed

        if (!mapDevicePosition.isEmpty()) {
            List<String> tmpDeviceDetails = mapDevicePosition.get(jComboBox1.getSelectedIndex());
            /* Set bluetooth device name */

            /* Set bluetooth device Address */
            String address = tmpDeviceDetails.get(1);

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
            String passwordSha1 = null;

            /*
             * creates the SHA1 hash of the password the user has entered.
             */
            try {
                String strPassword = new String(passwordField.getPassword());
                passwordSha1 = convertToSha1(strPassword);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login_Account_Create.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            try {
                /*
                 * Register JDBC driver.
                 */
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                /*
                 * creates and executes an SQL statement to be run against the database.
                 */
                stmt = conn.createStatement();
                String sql = "SELECT device_Details_ID FROM device_Details "
                        + "WHERE device_Address = '" + address
                        + "' AND device_Password = '" + passwordSha1 + "';";
                /*
                 * extracts the data from the results of the SQL statment and checks
                 * if they are empty or not
                 */
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    /*
                     * extracts the data from the results of the SQL statment and checks
                     * if they are empty or not
                     */
                    if (!rs.isBeforeFirst()) {

                        /*
                         * shows an error message due to the username or password being
                         * incorrect or not existing.
                         */
                        Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                        JOptionPane.showMessageDialog(this,
                                "No Account With These Details Exists On This System. Please Try Again.",
                                "Account Login Error",
                                JOptionPane.INFORMATION_MESSAGE,
                                crossIcon);

                    } else {

                        /*
                         * saves the account_Details_ID into a variable and passes it into
                         * the main window and opens it while closing the old window.
                         */
                        while (rs.next()) {
                            getDeviceID(address);
                            //retrieves the information and puts it into a variable
                            Suite_Window mWSameple = new Suite_Window(-1, "Device", address, dID, dName);
                            mWSameple.setVisible(true);

                            this.dispose();
                        }

                    }
                }
            } catch (SQLException se) {
            } catch (ClassNotFoundException | HeadlessException e) {
            } finally {
                //finally block used to close resources
                try {
                    if (stmt != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                }
            }
        } else {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog(this,
                    "Select A Device",
                    "Account Login Error",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }
    }//GEN-LAST:event_login_ButtonActionPerformed
    String dName;
    int dID = -1;

    private void getDeviceID(String deviceAddress) {

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

            String sql = "SELECT device_Details_ID, device_Name FROM device_Details WHERE device_Address = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, deviceAddress);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                dID = rs.getInt("device_Details_ID");

                dName = rs.getString("device_Name");

            }

            pStmt.close();
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
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login_Device().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton account_Creation_Button1;
    private javax.swing.JButton account_Recover_Button1;
    private javax.swing.JLabel changeLoginLabel;
    private javax.swing.JPanel creation_Recovery_Panel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel loginDetailsPanel;
    private javax.swing.JButton login_Button;
    private javax.swing.JLabel login_Logo_Image3;
    private javax.swing.JPanel login_Logo_Panel3;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables

}
