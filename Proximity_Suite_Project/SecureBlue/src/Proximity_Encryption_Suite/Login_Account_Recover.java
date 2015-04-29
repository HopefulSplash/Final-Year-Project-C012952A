/**
 * Defines to package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Frame;
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

/**
 * The Login_Account_Recover.Java Class implements an application that allows a
 * users recover certain account details so why can be given access to the
 * Proximity Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Login_Account_Recover extends javax.swing.JFrame {

    /**
     * Creates new form Login_Account_Recover
     */
    public Login_Account_Recover() {

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

        //sets the default values for the buttons and fields
        recover_Username_Button.setEnabled(false);
        reset_Password_Button.setEnabled(false);
        password_Field.setEditable(false);
        question_ComboBox.setEnabled(false);
        answer_Field.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reset_Password_Panel = new javax.swing.JPanel();
        question_ComboBox = new javax.swing.JComboBox();
        answer_Field = new javax.swing.JTextField();
        username_Field = new javax.swing.JTextField();
        question_Label = new javax.swing.JLabel();
        username_Label = new javax.swing.JLabel();
        reset_Password_Button = new javax.swing.JButton();
        answer_Label = new javax.swing.JLabel();
        recover_Username_Panel = new javax.swing.JPanel();
        email_Field = new javax.swing.JTextField();
        email_Label = new javax.swing.JLabel();
        recover_Username_Button = new javax.swing.JButton();
        password_Label = new javax.swing.JLabel();
        password_Field = new javax.swing.JPasswordField();
        email_Status_Label = new javax.swing.JLabel();
        button_Panel = new javax.swing.JPanel();
        clear_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Account Recovery");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(null);
        setMinimumSize(null);
        setName("accountRecoveryFrame"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        reset_Password_Panel.setBackground(new java.awt.Color(255, 255, 255));
        reset_Password_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reset Password"));

        question_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select A Question", "What was your childhood nickname?", "In what city did you meet your spouse/significant other?", "What is the name of your favorite childhood friend?", "What street did you live on in third grade?", "What is the middle name of your oldest child?", "What school did you attend for sixth grade?", "What the name of your first pet?", "What was the name of your first stuffed animal?", "In what city or town did your mother and father meet?", "Where were you when you had your first kiss?", "What is the first name of the boy or girl that you first kissed?", "What was the last name of your third grade teacher?", "In what city does your nearest sibling live?", "In what city or town was your first job?", "What is the name of the place your wedding reception was held?" }));
        question_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                question_ComboBoxActionPerformed(evt);
            }
        });

        answer_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                answer_FieldCaretUpdate(evt);
            }
        });

        username_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                username_FieldCaretUpdate(evt);
            }
        });

        question_Label.setText("Recovery Question:");

        username_Label.setText("Username:");

        reset_Password_Button.setText("Reset Password");
        reset_Password_Button.setFocusPainted(false);
        reset_Password_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_Password_ButtonActionPerformed(evt);
            }
        });

        answer_Label.setText("Answer:");

        javax.swing.GroupLayout reset_Password_PanelLayout = new javax.swing.GroupLayout(reset_Password_Panel);
        reset_Password_Panel.setLayout(reset_Password_PanelLayout);
        reset_Password_PanelLayout.setHorizontalGroup(
            reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reset_Password_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reset_Password_PanelLayout.createSequentialGroup()
                        .addComponent(answer_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(answer_Field)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset_Password_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reset_Password_PanelLayout.createSequentialGroup()
                        .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(question_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(username_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(question_ComboBox, 0, 374, Short.MAX_VALUE)
                            .addComponent(username_Field))))
                .addGap(6, 6, 6))
        );
        reset_Password_PanelLayout.setVerticalGroup(
            reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reset_Password_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username_Label)
                    .addComponent(username_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(question_Label)
                    .addComponent(question_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reset_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer_Label)
                    .addComponent(answer_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reset_Password_Button))
                .addGap(6, 6, 6))
        );

        recover_Username_Panel.setBackground(new java.awt.Color(255, 255, 255));
        recover_Username_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Recover Username"));

        email_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                email_FieldCaretUpdate(evt);
            }
        });

        email_Label.setText("Email Address:");

        recover_Username_Button.setText("Recover Username");
        recover_Username_Button.setFocusPainted(false);
        recover_Username_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recover_Username_ButtonActionPerformed(evt);
            }
        });

        password_Label.setText("Password:");

        password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                password_FieldCaretUpdate(evt);
            }
        });

        email_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        email_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        email_Status_Label.setText("Email");

        javax.swing.GroupLayout recover_Username_PanelLayout = new javax.swing.GroupLayout(recover_Username_Panel);
        recover_Username_Panel.setLayout(recover_Username_PanelLayout);
        recover_Username_PanelLayout.setHorizontalGroup(
            recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recover_Username_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(password_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(email_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(recover_Username_PanelLayout.createSequentialGroup()
                        .addComponent(password_Field)
                        .addGap(6, 6, 6)
                        .addComponent(recover_Username_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(email_Field)
                    .addComponent(email_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        recover_Username_PanelLayout.setVerticalGroup(
            recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recover_Username_PanelLayout.createSequentialGroup()
                .addGroup(recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(email_Label)
                    .addComponent(email_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(email_Status_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recover_Username_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_Label)
                    .addComponent(recover_Username_Button)
                    .addComponent(password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        clear_Button.setText("Clear");
        clear_Button.setFocusPainted(false);
        clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_ButtonActionPerformed(evt);
            }
        });

        cancel_Button.setText("Cancel");
        cancel_Button.setFocusPainted(false);
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_PanelLayout.createSequentialGroup()
                .addComponent(clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clear_Button)
                    .addComponent(cancel_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reset_Password_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recover_Username_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(recover_Username_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reset_Password_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        getContentPane().setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that when the window closes it will open the login window
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //opens the login window
        new Login_Account().setVisible(true);

    }//GEN-LAST:event_formWindowClosing
    /**
     * a method that will allow the user to reset the password for their
     * account.
     *
     * @param evt
     */
    private void reset_Password_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_Password_ButtonActionPerformed
        int accountID = 0;
        String tempPass = null;
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
        Statement stmt = null;
        try {
            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            stmt = conn.createStatement();
            String sql = "SELECT account_Details_ID, account_Password FROM Account_Details "
                    + "WHERE account_Username = '" + username_Field.getText().trim()
                    + "' AND account_Question = '" + question_ComboBox.getSelectedItem().toString()
                    + "' AND account_Answer = '" + answer_Field.getText().trim() + "';";
            /*
             * extracts the data from the results of the SQL statment.
             */
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (!rs.isBeforeFirst()) {
                    // a popup that will appear when the information a user has entered is incorrect.
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog(this,
                            "No Account With These Details Exists On This System. Please Try Again.",
                            "Password Reset Error",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);
                } else {
                    while (rs.next()) {
                        accountID = rs.getInt("account_Details_ID");
                        tempPass = rs.getString("account_Password");
                    }
                    // transfers the data to new window so the user can enter the desired password.
                    Login_Account_Recover_Password resetPassword = new Login_Account_Recover_Password((Frame) this.getParent(), true);
                    resetPassword.setAnswer(answer_Field.getText().trim());
                    resetPassword.setQuestion(question_ComboBox.getSelectedItem().toString());
                    resetPassword.setUsername(username_Field.getText().trim());
                    resetPassword.setVisible(true);

                    if (resetPassword.isShouldAdd() == true) {
                        /*
                         * creates and executes an SQL statement to insert into the database.
                         */
                        stmt = conn.createStatement();
                        sql = "UPDATE Account_Details "
                                + "SET account_Password = '" + resetPassword.getPasswordSha1() + "' WHERE account_Username = '" + username_Field.getText().trim()
                                + "' AND account_Question = '" + question_ComboBox.getSelectedItem().toString()
                                + "' AND account_Answer = '" + answer_Field.getText().trim() + "';";
                        stmt.executeUpdate(sql);

                        if (!resetPassword.getPasswordSha1().equals(tempPass)) {
                            Encryption_Script des = new Encryption_Script(tempPass, "Decrypt", accountID);
                            Encryption_Script ees = new Encryption_Script(resetPassword.getPasswordSha1(), "Encrypt", accountID);
                        }

                        // a popup that will appear when the information a user has entered is correct showing that the data has changed.
                        Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                        JOptionPane.showMessageDialog(this,
                                "Congratulations Your Accounts Password Has Been Changed",
                                "Password Reset",
                                JOptionPane.INFORMATION_MESSAGE,
                                tickIcon);

                        // clearing the data after reseting the password.
                        answer_Field.setText(null);
                        question_ComboBox.setSelectedIndex(0);
                        username_Field.setText(null);
                    }
                    stmt.close();
                    conn.close();

                }
            }
        } catch (SQLException se) {
        } catch (ClassNotFoundException | HeadlessException e) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException se) {
            }
        }
    }//GEN-LAST:event_reset_Password_ButtonActionPerformed

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

        /*
         * builds the new string so that it is in the correct format for storage.
         */
        byte[] result = mDigest.digest(input.getBytes());
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
     * a method that will allow the user to see what their username is after
     * entering other account details.
     *
     * @param evt
     */
    private void recover_Username_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recover_Username_ButtonActionPerformed

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
        Statement stmt = null;

        /*
         * creates the SHA1 hash of the password the user has entered.
         */
        String passwordSha1 = null;
        try {
            String strPassword = new String(password_Field.getPassword());
            passwordSha1 = convertToSha1(strPassword);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login_Account_Create.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            stmt = conn.createStatement();
            String sql = "SELECT account_Username FROM Account_Details "
                    + "WHERE account_Password = '" + passwordSha1 + "' AND account_Email = '" + email_Field.getText() + "';";

            /*
             * extracts the data from the results of the SQL statment.
             */
            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (!rs.isBeforeFirst()) {

                    // a popup that will appear when the information a user has entered is incorrect.
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog(this,
                            "No Account With These Details Exists On This System. Please Try Again.",
                            "Account Recovery Error",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);

                } else {

                    while (rs.next()) {
                        //stroing the username in a variable in which is can be used.
                        String usernameResult = rs.getString("account_Username");

                        // a popup that will appear when the information a user has entered is correct information showing their username.
                        Icon userIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_User_Icon.png"));
                        JOptionPane.showMessageDialog(this,
                                "<HTML>Account Username: <b>" + usernameResult + "<b>",
                                "Account Recovered",
                                JOptionPane.INFORMATION_MESSAGE,
                                userIcon);

                        //clears all the data a user has entered.
                        email_Field.setText(null);
                        password_Field.setText(null);
                    }

                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
        } catch (ClassNotFoundException | HeadlessException e) {
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException se) {
            }
        }


    }//GEN-LAST:event_recover_Username_ButtonActionPerformed

    /**
     * a method that validates the email address a user enters.
     *
     * @param evt
     */
    private void email_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_email_FieldCaretUpdate

        //checks if the email address is valid and changes the status
        if (validateEmail(email_Field.getText()) && !email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Address Valid");
            email_Status_Label.setForeground(darkGreen);
            password_Field.setEditable(true);

        } //checks if the email field is empty and changes the status
        else if (email_Field.getText().length() == 0 && !email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Addresses Place Holder");
            email_Status_Label.setForeground(Color.WHITE);
            email_Status_Label.setForeground(Color.WHITE);
            recover_Username_Button.setEnabled(false);
            password_Field.setEditable(false);
            password_Field.setText(null);

        } //checks if the email adddresss isnt valid and changes the status
        else if (email_Field.getText().length() != 0 && validateEmail(email_Field.getText()) == false && !email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Addresses Is Invalid");
            email_Status_Label.setForeground(darkRed);
            recover_Username_Button.setEnabled(false);
            password_Field.setEditable(false);
            password_Field.setText(null);

        } else {
            email_Status_Label.setText("Email Addresses Cannot Start With A Space");
            email_Status_Label.setForeground(darkRed);

        }


    }//GEN-LAST:event_email_FieldCaretUpdate

    /**
     * a method that validates the password a user enters.
     *
     * @param evt
     */
    private void password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_password_FieldCaretUpdate

        if (password_Field.getPassword().length > 0) {
            recover_Username_Button.setEnabled(true);

        } //clears the data from the relavant fields 
        else {
            recover_Username_Button.setEnabled(false);

        }


    }//GEN-LAST:event_password_FieldCaretUpdate
    /**
     * a method that validates the username a user enters.
     *
     * @param evt
     */
    private void username_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_username_FieldCaretUpdate
        // checks the length of the text in the username field.
        if (username_Field.getText().length() > 0) {
            question_ComboBox.setEnabled(true);

        } //clears the data from the relavant fields 
        else {
            question_ComboBox.setEnabled(false);
            answer_Field.setEditable(false);
            question_ComboBox.setSelectedIndex(0);
            answer_Field.setText(null);
            reset_Password_Button.setEnabled(false);

        }
    }//GEN-LAST:event_username_FieldCaretUpdate

    /**
     * a method that will clear the contents of the components a user has
     * entered data in.
     *
     * @param evt
     */
    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed
        // clears all the data a user may have entered.
        answer_Field.setText(null);
        email_Field.setText(null);
        password_Field.setText(null);
        question_ComboBox.setSelectedIndex(0);
        username_Field.setText(null);

    }//GEN-LAST:event_clear_ButtonActionPerformed

    /**
     * a method that will close the window and open the standard login window.
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed

        //when the window closes it opens the login screen so that a user can login with thier new details.
        this.dispose();
        new Login_Account().setVisible(true);
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    /**
     * a method that will check is the user has selected a question to answer.
     *
     * @param evt
     */
    private void question_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_question_ComboBoxActionPerformed

        //checks if the a question is selected not just he placeholder text.
        if (question_ComboBox.getSelectedIndex() > 0) {
            answer_Field.setEditable(true);
        } //clears the data from the answer fields 
        else {
            answer_Field.setEditable(false);
            answer_Field.setText(null);
            reset_Password_Button.setEnabled(false);

        }
    }//GEN-LAST:event_question_ComboBoxActionPerformed
    /**
     * a method that will check is a user has entered a answer to the security
     * question.
     *
     * @param evt
     */
    private void answer_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_answer_FieldCaretUpdate

//checks if the answers the user has given match eachother.
        if (answer_Field.getText().length() != 0) {
            reset_Password_Button.setEnabled(true);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (answer_Field.getText().length() == 0) {
            reset_Password_Button.setEnabled(false);
        } else {
            reset_Password_Button.setEnabled(false);
        }

    }//GEN-LAST:event_answer_FieldCaretUpdate

    /**
     * a method that will check if the user has entered a valid email address by
     * using simple pattern matching.
     *
     * @param emailAddress
     * @return
     */
    private boolean validateEmail(String emailAddress) {

        /*
         * regular expression to use for checking the email address is valid.
         */
        String emailPattern
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        /*
         * checks the emailAddress matches the emailPattern.
         */
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailPattern);
        java.util.regex.Matcher m = p.matcher(emailAddress);
        /*
         * retruns a boolean depending on if the pattern matches or not.
         */
        return m.matches();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answer_Field;
    private javax.swing.JLabel answer_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton clear_Button;
    private javax.swing.JTextField email_Field;
    private javax.swing.JLabel email_Label;
    private javax.swing.JLabel email_Status_Label;
    private javax.swing.JPasswordField password_Field;
    private javax.swing.JLabel password_Label;
    private javax.swing.JComboBox question_ComboBox;
    private javax.swing.JLabel question_Label;
    private javax.swing.JButton recover_Username_Button;
    private javax.swing.JPanel recover_Username_Panel;
    private javax.swing.JButton reset_Password_Button;
    private javax.swing.JPanel reset_Password_Panel;
    private javax.swing.JTextField username_Field;
    private javax.swing.JLabel username_Label;
    // End of variables declaration//GEN-END:variables
    Color darkGreen = new Color(0x006400);
    Color darkRed = new Color(0x640000);

}
