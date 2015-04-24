/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * The Login_Account.Java Class implements an application that allows a users
 * enter their username and password so why can be given access to the Proximity
 * Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Login_Account extends javax.swing.JFrame {

    /**
     * Creates new Login_Account.
     */
    public Login_Account() {

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
    }

    class Task extends SwingWorker<Void, Void> {

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

        }

    }

    /**
     * This method is called from within the constructor to initialise the form
     * and loads all the components that make us the GUI for this application.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login_Details_Panel = new javax.swing.JPanel();
        username_Label = new javax.swing.JLabel();
        password_Label = new javax.swing.JLabel();
        username_Field = new javax.swing.JTextField();
        password_Field = new javax.swing.JPasswordField();
        device_Login_Label = new javax.swing.JLabel();
        login_Button = new javax.swing.JButton();
        creation_Recovery_Panel = new javax.swing.JPanel();
        account_Creation_Button = new javax.swing.JButton();
        account_Recover_Button = new javax.swing.JButton();
        login_Logo_Panel = new javax.swing.JPanel();
        login_Logo_Image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proximity Suite | Account Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(null);
        setMinimumSize(null);
        setName("Login_Account"); // NOI18N
        setResizable(false);

        login_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        login_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Login Details"));
        login_Details_Panel.setMaximumSize(null);
        login_Details_Panel.setName(""); // NOI18N

        username_Label.setBackground(new java.awt.Color(255, 255, 255));
        username_Label.setText("Username: ");

        password_Label.setBackground(new java.awt.Color(255, 255, 255));
        password_Label.setText("Password: ");

        username_Field.setToolTipText("");
        username_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_FieldActionPerformed(evt);
            }
        });

        password_Field.setToolTipText("");
        password_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_FieldActionPerformed(evt);
            }
        });

        device_Login_Label.setBackground(new java.awt.Color(255, 255, 255));
        device_Login_Label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        device_Login_Label.setForeground(new java.awt.Color(43, 85, 166));
        device_Login_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        device_Login_Label.setText("<HTML><U>Click Here To Login Using A Bluetooth Device <U><HTML>");
        device_Login_Label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        device_Login_Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                device_Login_LabelMouseClicked(evt);
            }
        });

        login_Button.setText("Login");
        login_Button.setFocusPainted(false);
        login_Button.setMaximumSize(null);
        login_Button.setMinimumSize(null);
        login_Button.setPreferredSize(null);
        login_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout login_Details_PanelLayout = new javax.swing.GroupLayout(login_Details_Panel);
        login_Details_Panel.setLayout(login_Details_PanelLayout);
        login_Details_PanelLayout.setHorizontalGroup(
            login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(device_Login_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, login_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password_Label)
                    .addComponent(username_Label))
                .addGap(6, 6, 6)
                .addGroup(login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(password_Field)
                    .addComponent(username_Field)
                    .addComponent(login_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        login_Details_PanelLayout.setVerticalGroup(
            login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username_Label)
                    .addComponent(username_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(login_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_Label)
                    .addComponent(password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(login_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(device_Login_Label)
                .addGap(6, 6, 6))
        );

        creation_Recovery_Panel.setBackground(new java.awt.Color(255, 255, 255));
        creation_Recovery_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Creation / Recovery"));
        creation_Recovery_Panel.setMaximumSize(null);

        account_Creation_Button.setText("Create Account");
        account_Creation_Button.setFocusPainted(false);
        account_Creation_Button.setMaximumSize(null);
        account_Creation_Button.setMinimumSize(null);
        account_Creation_Button.setPreferredSize(null);
        account_Creation_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_Creation_ButtonActionPerformed(evt);
            }
        });

        account_Recover_Button.setText("Recover Account");
        account_Recover_Button.setFocusPainted(false);
        account_Recover_Button.setMaximumSize(null);
        account_Recover_Button.setMinimumSize(null);
        account_Recover_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_Recover_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout creation_Recovery_PanelLayout = new javax.swing.GroupLayout(creation_Recovery_Panel);
        creation_Recovery_Panel.setLayout(creation_Recovery_PanelLayout);
        creation_Recovery_PanelLayout.setHorizontalGroup(
            creation_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creation_Recovery_PanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(account_Creation_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(account_Recover_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        creation_Recovery_PanelLayout.setVerticalGroup(
            creation_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creation_Recovery_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(creation_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(account_Creation_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(account_Recover_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        login_Logo_Panel.setBackground(new java.awt.Color(255, 255, 255));
        login_Logo_Panel.setMaximumSize(null);

        login_Logo_Image.setBackground(new java.awt.Color(255, 255, 255));
        login_Logo_Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        login_Logo_Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Login_Logo.png"))); // NOI18N
        login_Logo_Image.setName("login_Logo_Image"); // NOI18N

        javax.swing.GroupLayout login_Logo_PanelLayout = new javax.swing.GroupLayout(login_Logo_Panel);
        login_Logo_Panel.setLayout(login_Logo_PanelLayout);
        login_Logo_PanelLayout.setHorizontalGroup(
            login_Logo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_Logo_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(login_Logo_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        login_Logo_PanelLayout.setVerticalGroup(
            login_Logo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_Logo_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(login_Logo_Image)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(login_Logo_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creation_Recovery_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(login_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(login_Logo_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(login_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(creation_Recovery_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        getContentPane().setBackground(Color.WHITE);
        login_Button.requestFocus();

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that when the user presses enter on on the Password_Field the
     * login button is pressed.
     *
     * @param evt
     */
    private void password_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_FieldActionPerformed
        /*
         * clicks the Login_Button 
         */
        login_Button.doClick();

    }//GEN-LAST:event_password_FieldActionPerformed

    /**
     * a method that converts a user entered password into SHA1 so it can be
     * stored in the database securely.
     *
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
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

    /**
     * a method that when the user clicks the Login_Button it will check that
     * the details are correct, output the correct message if they are incorrect
     * and login to the Proximity Encryption Suite.
     *
     * @param evt
     */
    private void login_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_ButtonActionPerformed
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
            String strPassword = new String(password_Field.getPassword());
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
            String sql = "SELECT account_Details_ID FROM Account_Details "
                    + "WHERE account_Username = '" + username_Field.getText()
                    + "' AND account_Password = '" + passwordSha1 + "';";
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
                        //retrieves the information and puts it into a variable
                        int account_ID = rs.getInt("account_Details_ID");
                        Suite_Window mWSameple = new Suite_Window(account_ID, "Account", null);
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
    }//GEN-LAST:event_login_ButtonActionPerformed

    /**
     * a method that when a user presses enter on the Username_Field it will
     * advanced them to the Password_Field.
     *
     * @param evt
     */
    private void username_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_FieldActionPerformed

        password_Field.requestFocusInWindow();
    }//GEN-LAST:event_username_FieldActionPerformed

    /**
     * a method that creates a new instance of the Login_Account_Create class
     *
     * @param evt
     */
    private void account_Creation_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Creation_ButtonActionPerformed
        Login_Account_Create cASameple = new Login_Account_Create("Login");
        cASameple.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_account_Creation_ButtonActionPerformed

    /**
     * a method that creates a new instance of the Login_Device class
     *
     * @param evt
     */
    private void device_Login_LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_device_Login_LabelMouseClicked
        DeviceLoginSample dLSameple = new DeviceLoginSample();
        dLSameple.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_device_Login_LabelMouseClicked

    /**
     * a method that creates a new instance of the Login_Account_Recover class
     *
     * @param evt
     */
    private void account_Recover_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Recover_ButtonActionPerformed
        // TODO add your handling code here:
        Login_Account_Recover rASameple = new Login_Account_Recover();
        rASameple.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_account_Recover_ButtonActionPerformed

    /**
     * This is the main method which launches the frame.
     *
     * @param args
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Account.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login_Account().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton account_Creation_Button;
    private javax.swing.JButton account_Recover_Button;
    private javax.swing.JPanel creation_Recovery_Panel;
    private javax.swing.JLabel device_Login_Label;
    private javax.swing.JButton login_Button;
    private javax.swing.JPanel login_Details_Panel;
    private javax.swing.JLabel login_Logo_Image;
    private javax.swing.JPanel login_Logo_Panel;
    private javax.swing.JPasswordField password_Field;
    private javax.swing.JLabel password_Label;
    private javax.swing.JTextField username_Field;
    private javax.swing.JLabel username_Label;
    // End of variables declaration//GEN-END:variables
}
