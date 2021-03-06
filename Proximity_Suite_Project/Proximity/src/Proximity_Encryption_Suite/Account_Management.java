/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Image;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * The Account_Management.Java Class implements an application that allows a
 * users to modify their account details such as username, password, email and
 * security options.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Account_Management extends javax.swing.JDialog {

    private final int accountID;

    /**
     * *
     * Creates new form Account_Management
     *
     * @param parent
     * @param modal
     * @param AccountID
     */
    public Account_Management(java.awt.Frame parent, boolean modal, int AccountID) {

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
        //loads the account details
        this.accountID = AccountID;
        getAccountDetails(accountID);
        //GUI setup
        accept_Button.requestFocus();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        accept_Button = new javax.swing.JButton();
        account_Details_Panel = new javax.swing.JPanel();
        username_Label = new javax.swing.JLabel();
        username_Field = new javax.swing.JTextField();
        Password_Label = new javax.swing.JLabel();
        new_Password_Field = new javax.swing.JPasswordField();
        confirm_Password_Label = new javax.swing.JLabel();
        confirm_New_Password_Field = new javax.swing.JPasswordField();
        email_Label = new javax.swing.JLabel();
        email_Field = new javax.swing.JTextField();
        confirm_Email_Label = new javax.swing.JLabel();
        confirm_Email_Field = new javax.swing.JTextField();
        username_Status_Label = new javax.swing.JLabel();
        password_Status_Label = new javax.swing.JLabel();
        email_Status_Label = new javax.swing.JLabel();
        password_Strength_Label = new javax.swing.JLabel();
        createPasswordLabel1 = new javax.swing.JLabel();
        account_Recovery_Panel = new javax.swing.JPanel();
        answer_Label = new javax.swing.JLabel();
        question_ComboBox = new javax.swing.JComboBox();
        answer_Field = new javax.swing.JTextField();
        confirm_Answer_Field = new javax.swing.JTextField();
        confirm_Answer_Label = new javax.swing.JLabel();
        answer_Status_Label = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Modify Account");
        setIconImage(null);
        setIconImages(null);
        setMinimumSize(null);
        setModal(true);
        setResizable(false);

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
                .addContainerGap(241, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cancel_Button)
                .addComponent(accept_Button))
        );

        account_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        account_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Details"));

        username_Label.setText("Username:");

        username_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                username_FieldCaretUpdate(evt);
            }
        });

        Password_Label.setText("New Password:");

        new_Password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                new_Password_FieldCaretUpdate(evt);
            }
        });

        confirm_Password_Label.setText("Confirm New Password:");

        confirm_New_Password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_New_Password_FieldCaretUpdate(evt);
            }
        });

        email_Label.setText("Email Address:");

        email_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                email_FieldCaretUpdate(evt);
            }
        });

        confirm_Email_Label.setText("Confirm Email Address:");

        confirm_Email_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_Email_FieldCaretUpdate(evt);
            }
        });

        username_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        username_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        username_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        username_Status_Label.setText("Please Enter A Username");
        username_Status_Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        password_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        password_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_Status_Label.setText("Password");

        email_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        email_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        email_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        email_Status_Label.setText("Email");

        password_Strength_Label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        password_Strength_Label.setForeground(new java.awt.Color(255, 255, 255));
        password_Strength_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_Strength_Label.setText("jLabel5");

        createPasswordLabel1.setText("Password Strength:");

        javax.swing.GroupLayout account_Details_PanelLayout = new javax.swing.GroupLayout(account_Details_Panel);
        account_Details_Panel.setLayout(account_Details_PanelLayout);
        account_Details_PanelLayout.setHorizontalGroup(
            account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(email_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirm_Email_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirm_Password_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createPasswordLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Password_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(email_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(new_Password_Field)
                    .addComponent(email_Field)
                    .addComponent(confirm_Email_Field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(password_Strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username_Field)
                    .addComponent(confirm_New_Password_Field))
                .addGap(6, 6, 6))
        );
        account_Details_PanelLayout.setVerticalGroup(
            account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_Details_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username_Label)
                    .addComponent(username_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username_Status_Label)
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password_Label)
                    .addComponent(new_Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirm_Password_Label)
                    .addComponent(confirm_New_Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_Strength_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createPasswordLabel1))
                .addGap(6, 6, 6)
                .addComponent(password_Status_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email_Label)
                    .addComponent(email_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_Email_Label)
                    .addComponent(confirm_Email_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(email_Status_Label)
                .addGap(6, 6, 6))
        );

        account_Recovery_Panel.setBackground(new java.awt.Color(255, 255, 255));
        account_Recovery_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Recovery Question"));

        answer_Label.setText("Answer:");

        question_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "What was your childhood nickname?", "In what city did you meet your spouse/significant other?", "What is the name of your favorite childhood friend?", "What street did you live on in third grade?", "What is the middle name of your oldest child?", "What school did you attend for sixth grade?", "What the name of your first pet?", "What was the name of your first stuffed animal?", "In what city or town did your mother and father meet?", "Where were you when you had your first kiss?", "What is the first name of the boy or girl that you first kissed?", "What was the last name of your third grade teacher?", "In what city does your nearest sibling live?", "In what city or town was your first job?", "What is the name of the place your wedding reception was held?" }));
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

        confirm_Answer_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_Answer_FieldCaretUpdate(evt);
            }
        });

        confirm_Answer_Label.setText("Confirm Answer:");

        answer_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        answer_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        answer_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        answer_Status_Label.setText("Answer");

        javax.swing.GroupLayout account_Recovery_PanelLayout = new javax.swing.GroupLayout(account_Recovery_Panel);
        account_Recovery_Panel.setLayout(account_Recovery_PanelLayout);
        account_Recovery_PanelLayout.setHorizontalGroup(
            account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, account_Recovery_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(question_ComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answer_Status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, account_Recovery_PanelLayout.createSequentialGroup()
                        .addGroup(account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(confirm_Answer_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(answer_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirm_Answer_Field)
                            .addComponent(answer_Field))))
                .addGap(6, 6, 6))
        );
        account_Recovery_PanelLayout.setVerticalGroup(
            account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_Recovery_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(question_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer_Label)
                    .addComponent(answer_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Recovery_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_Answer_Label)
                    .addComponent(confirm_Answer_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(answer_Status_Label)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(account_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(account_Recovery_Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(account_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(account_Recovery_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that will close the form.
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    /**
     * the method that will start the saving account details.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // TODO add your handling code here:
        sendAccountDetails("Accept");
    }//GEN-LAST:event_accept_ButtonActionPerformed

    /**
     * a method that will return if the account is modified.
     *
     * @return
     */
    public boolean isModifyAccount() {
        return modifyAccount;
    }

    /**
     * a method that will set if the account is modified.
     *
     * @param modifyFolder
     */
    public void setModifyAccount(boolean modifyFolder) {
        this.modifyAccount = modifyFolder;
    }

    /**
     * a method that will check if the username is valid.
     *
     * @param evt
     */
    private void username_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_username_FieldCaretUpdate
        //checks if the email address is in the database already and stores it in a variable.
        boolean userIsTaken = checkUsernameExists(username_Field.getText());

        // checks if the username is taken and updates the status.
        if (userIsTaken == true && !username_Field.getText().startsWith(" ") && !username_Field.getText().equals(tempUser)) {
            username_Status_Label.setText("Username Already In Use");
            username_Status_Label.setForeground(darkRed);
            validUser = false;

        } // checks if the username field is empty and updates the status.
        else if (username_Field.getText().length() == 0 || username_Field.getText().equals(tempUser)) {
            username_Status_Label.setText("Username Place Holder");
            username_Status_Label.setForeground(Color.WHITE);

            if (username_Field.getText().equals(tempUser)) {
                username_Status_Label.setText("Username Place Holder");
                username_Status_Label.setForeground(Color.WHITE);
                validUser = true;
            } else {
                username_Status_Label.setText("Username Cannot Be Blank");
                username_Status_Label.setForeground(darkRed);
                validUser = false;
            }

        } // checks if the username is too long for the database and updates the status.
        else if (username_Field.getText().length() > 255 && !username_Field.getText().startsWith(" ") && !username_Field.getText().equals(tempUser)) {
            username_Status_Label.setText("Username Is Too Long");
            username_Status_Label.setForeground(darkRed);
            validUser = false;
        } //if non of the above statements are met then they username is approved and the status updated.
        else if (!username_Field.getText().startsWith(" ") && !username_Field.getText().equals(tempUser)) {
            validUser = true;
            username_Status_Label.setText("Username Approved");
            username_Status_Label.setForeground(darkGreen);
        } else {
            username_Status_Label.setText("Username Cannot Start With A Space");
            username_Status_Label.setForeground(darkRed);
            validUser = false;
        }
    }//GEN-LAST:event_username_FieldCaretUpdate
    /**
     * a method that will check if the password is valid and give a strength
     * rating.
     *
     * @param evt
     */
    private void new_Password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_new_Password_FieldCaretUpdate
        // checks if the passwords the user has entered match eachother.
        if (Arrays.equals(new_Password_Field.getPassword(), confirm_New_Password_Field.getPassword()) && confirm_New_Password_Field.getPassword().length > 0) {
            validPass = true;
            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);

        } // if the password field is empty then they status is updated.
        else if (new_Password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            confirm_New_Password_Field.setText(null);
            confirm_New_Password_Field.setEditable(false);
            validPass = true;

        } // if the passwords do not match the statements above then they do not match eachother.
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            confirm_New_Password_Field.setEditable(true);
            validPass = false;

        }

        // checks if the password entered is not equal to 0 in length.
        if (new_Password_Field.getPassword().length != 0) {

            // stores the password and gets it strength value.
            int passwordScore = 0;
            String passString = new String(new_Password_Field.getPassword());
            passwordScore = passwordStrength(passString);

            // changes the text depending on the strength value of the password.
            if (passwordScore >= 20) {
                password_Strength_Label.setText("Very Weak");
                password_Strength_Label.setForeground(darkRed);
            }
            if (passwordScore >= 40) {
                password_Strength_Label.setText("Weak");
                password_Strength_Label.setForeground(darkRed);
            }
            if (passwordScore >= 60) {
                password_Strength_Label.setText("Good");
                password_Strength_Label.setForeground(darkGreen);
            }
            if (passwordScore >= 80) {
                password_Strength_Label.setText("Strong");
                password_Strength_Label.setForeground(darkGreen);

            }
            if (passwordScore >= 100) {
                password_Strength_Label.setText("Very Strong");
                password_Strength_Label.setForeground(darkGreen);

            }
        }

    }//GEN-LAST:event_new_Password_FieldCaretUpdate
    /**
     * a method that will check if the password is valid and gives it a strength
     * rating.
     *
     * @param evt
     */
    private void confirm_New_Password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_New_Password_FieldCaretUpdate

        // checks if the passwords the user has entered match eachother.
        if (Arrays.equals(confirm_New_Password_Field.getPassword(), new_Password_Field.getPassword()) && new_Password_Field.getPassword().length > 0) {
            validPass = true;
            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);
        } // if the password field is empty then they status is updated.
        else if (confirm_New_Password_Field.getPassword().length == 0 && new_Password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            validPass = true;

        } // if the passwords do not match the statements above then they do not match eachother.
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            validPass = false;
        }
    }//GEN-LAST:event_confirm_New_Password_FieldCaretUpdate
    /**
     * a method that will check that the username is valid.
     *
     * @param evt
     */
    private void email_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_email_FieldCaretUpdate

        //checks if the email address is in the database already and stores it in a variable.
        boolean isTaken = checkEmailExsists(email_Field.getText());

        //checks if the email address is in the database already and updates the status.
        if (isTaken == true && !email_Field.getText().equals(tempEmail) && email_Field.getText().length() > 0) {
            email_Status_Label.setText("Email Address Already In Use");
            email_Status_Label.setForeground(darkRed);
            confirm_Email_Field.setText(null);
            confirm_Email_Field.setEditable(false);
            confirm_Email_Field.setEnabled(false);
            validEmail = false;

        } /**
         * if the email addresses match and are not already in the database and
         * in the valid format then the status will be updated and approved.
         */
        else if (email_Field.getText().equals(confirm_Email_Field.getText()) && validateEmail(email_Field.getText()) == true && email_Field.getText().length() != 0 && isTaken == false && !email_Field.getText().startsWith(" ")) {
            validEmail = true;
            email_Status_Label.setText("Email Address Approved");
            email_Status_Label.setForeground(darkGreen);
            confirm_Email_Field.setEditable(true);
            confirm_Email_Field.setEnabled(true);

        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (email_Field.getText().length() == 0 && !email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Addresses Cannot Be Blank");
            email_Status_Label.setForeground(darkRed);
            confirm_Email_Field.setText(null);
            confirm_Email_Field.setEditable(false);
            confirm_Email_Field.setEnabled(false);
            validEmail = false;

        } /**
         * if the email addresses are invalid then the status is updated.
         */
        else if (email_Field.getText().length() != 0 && validateEmail(email_Field.getText()) == false && !email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Addresses Is Invalid");
            email_Status_Label.setForeground(darkRed);
            confirm_Email_Field.setText(null);
            confirm_Email_Field.setEditable(false);
            confirm_Email_Field.setEnabled(false);
            validEmail = false;

        } else if (email_Field.getText().equals(tempEmail) && confirm_Email_Field.getText().equals(tempEmail)) {
            email_Status_Label.setText("Email Addresses Has Not Been Changed");
            email_Status_Label.setForeground(darkRed);
            validEmail = true;
        } /**
         * if the email address do not met the above if statements then they do
         * not match.
         */
        else if (isTaken == false && !email_Field.getText().startsWith(" ") && !email_Field.getText().equals(tempEmail)) {
            if (validateEmail(confirm_Email_Field.getText()) == false && !confirm_Email_Field.getText().isEmpty()) {
                email_Status_Label.setText("Email Addresses Is Invalid");
                email_Status_Label.setForeground(darkRed);
                validEmail = false;
            } else {
                email_Status_Label.setText("Email Addresses Do Not Match");
                email_Status_Label.setForeground(darkRed);
                confirm_Email_Field.setEditable(true);
                confirm_Email_Field.setEnabled(true);
                validEmail = false;
            }

        } else if (email_Field.getText().startsWith(" ") && !email_Field.getText().equals(tempEmail)) {
            email_Status_Label.setText("Email Addresses Cannot Start With A Space");
            email_Status_Label.setForeground(darkRed);
            confirm_Email_Field.setText(null);
            confirm_Email_Field.setEditable(false);
            confirm_Email_Field.setEnabled(false);

            validEmail = false;
        } else if (email_Field.getText().equals(tempEmail) && confirm_Email_Field.getText().length() == 0) {
            email_Status_Label.setText("Email Addresses Place Holder");
            email_Status_Label.setForeground(Color.WHITE);
            confirm_Email_Field.setEditable(true);
            confirm_Email_Field.setEnabled(true);

            validEmail = true;
        } else {
            if (validateEmail(confirm_Email_Field.getText()) == false && !confirm_Email_Field.getText().isEmpty()) {
                email_Status_Label.setText("Email Addresses Is Invalid");
                email_Status_Label.setForeground(darkRed);
                validEmail = false;
            } else {
                email_Status_Label.setText("Email Addresses Do Not Match");
                email_Status_Label.setForeground(darkRed);
                confirm_Email_Field.setEditable(true);
                confirm_Email_Field.setEnabled(true);

                validEmail = false;
            }
        }
    }//GEN-LAST:event_email_FieldCaretUpdate
    /**
     * a method that will check that the email is valid.
     *
     * @param evt
     */
    private void confirm_Email_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Email_FieldCaretUpdate
        //checks if the email address is in the database already and stores it in a variable.
        boolean isTaken = checkEmailExsists(email_Field.getText());

        //checks if the email address is in the database already and updates the status.
        if (isTaken == true && !email_Field.getText().equals(tempEmail) && email_Field.getText().length() > 0) {
            email_Status_Label.setText("Email Address Already In Use");
            email_Status_Label.setForeground(darkRed);
            validEmail = false;

        } /**
         * if the email addresses match and are not already in the database and
         * in the valid format then the status will be updated and approved.
         */
        else if (confirm_Email_Field.getText().equals(email_Field.getText()) && validateEmail(confirm_Email_Field.getText()) == true && confirm_Email_Field.getText().length() != 0 && isTaken == false && !confirm_Email_Field.getText().startsWith(" ")) {
            validEmail = true;
            email_Status_Label.setText("Email Address Approved");
            email_Status_Label.setForeground(darkGreen);

        } /**
         * if the email addresses are invalid then the status is updated.
         */
        else if (confirm_Email_Field.getText().length() != 0 && validateEmail(confirm_Email_Field.getText()) == false && !confirm_Email_Field.getText().startsWith(" ")) {
            email_Status_Label.setText("Email Addresses Is Invalid");
            email_Status_Label.setForeground(darkRed);
            validEmail = false;

        } else if (confirm_Email_Field.getText().equals(tempEmail) && email_Field.getText().equals(tempEmail)) {
            email_Status_Label.setText("Email Addresses Has Not Been Changed");
            email_Status_Label.setForeground(darkRed);
            validEmail = true;
        } /**
         * if the email address do not met the above if statements then they do
         * not match.
         */
        else if (confirm_Email_Field.getText().startsWith(" ") && !confirm_Email_Field.getText().equals(tempEmail)) {
            email_Status_Label.setText("Email Addresses Cannot Start With A Space");
            email_Status_Label.setForeground(darkRed);
            validEmail = false;
        } else if (email_Field.getText().equals(tempEmail) && email_Field.getText().length() == 0 || (confirm_Email_Field.getText().length() == 0 && !confirm_Email_Field.getText().startsWith(" "))) {
            email_Status_Label.setText("Email Addresses Place Holder");
            email_Status_Label.setForeground(Color.WHITE);

            validEmail = true;
        } else if (!confirm_Email_Field.getText().isEmpty()) {
            email_Status_Label.setText("Email Addresses Do Not Match");
            email_Status_Label.setForeground(darkRed);
            validEmail = false;
        }
    }//GEN-LAST:event_confirm_Email_FieldCaretUpdate

    /**
     * a method that will enable or disable GUI components.
     *
     * @param evt
     */
    private void question_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_question_ComboBoxActionPerformed
        //checks if the user has selected a question.
        if (question_ComboBox.getSelectedIndex() != 0 && !question_ComboBox.getSelectedItem().equals(tempQuestion)) {
            answer_Field.setText(null);
            confirm_Answer_Field.setText(null);
            tempAnswer = null;
        } else {
            getAccountDetails(accountID);
            confirm_Answer_Field.setText(null);
            answer_Field.setText(tempAnswer);
            confirm_Answer_Field.setEditable(true);

        }
    }//GEN-LAST:event_question_ComboBoxActionPerformed

    /**
     * a method that will check if the answer is valid.
     *
     * @param evt
     */
    private void answer_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_answer_FieldCaretUpdate

        //checks if the answers the user has given match eachother.
        if (answer_Field.getText().equals(confirm_Answer_Field.getText()) == true && answer_Field.getText().length() != 0 && !answer_Field.getText().startsWith(" ") && !answer_Field.getText().equals(tempAnswer)) {
            validAnswer = true;
            answer_Status_Label.setText("Security Answer Approved");
            answer_Status_Label.setForeground(darkGreen);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (answer_Field.getText().length() == 0 && !answer_Field.getText().startsWith(" ") && !answer_Field.getText().equals(tempAnswer)) {
            answer_Status_Label.setText("Security Answers Cannot Be Blank");
            answer_Status_Label.setForeground(darkRed);
            confirm_Answer_Field.setEditable(false);
            confirm_Answer_Field.setText(null);
            validAnswer = false;

        } // if the answers given do not match the status is updated.
        else if (answer_Field.getText().startsWith(" ") && !answer_Field.getText().equals(tempAnswer)) {
            answer_Status_Label.setText("Security Answers Cannot Start With A Space");
            answer_Status_Label.setForeground(darkRed);
            confirm_Answer_Field.setEditable(false);
            confirm_Answer_Field.setText(null);

            validAnswer = false;
        } else if (!confirm_Answer_Field.getText().equals(answer_Field.getText()) || !answer_Field.getText().equals(confirm_Answer_Field.getText())) {
            if ((confirm_Answer_Field.getText().length() == 0 || confirm_Answer_Field.getText().length() != 0) && !answer_Field.getText().equals(tempAnswer)) {
                answer_Status_Label.setText("Security Answers Do Not Match");
                answer_Status_Label.setForeground(darkRed);
                confirm_Answer_Field.setEditable(true);
                validAnswer = false;
            } else if (answer_Field.getText().equals(tempAnswer) && confirm_Answer_Field.getText().isEmpty()) {
                answer_Status_Label.setForeground(Color.WHITE);
                answer_Status_Label.setText("Security Answers Place Holder");
                validAnswer = true;
            } else {
                answer_Status_Label.setText("Security Answers Do Not Match");
                answer_Status_Label.setForeground(darkRed);
                confirm_Answer_Field.setEditable(true);
                validAnswer = false;
            }
        }
    }//GEN-LAST:event_answer_FieldCaretUpdate
    /**
     * a method that check if the answer is valid.
     *
     * @param evt
     */
    private void confirm_Answer_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Answer_FieldCaretUpdate

        //checks if the answers the user has given match eachother.
        if (confirm_Answer_Field.getText().equals(answer_Field.getText()) == true && confirm_Answer_Field.getText().length() != 0 && !confirm_Answer_Field.getText().startsWith(" ") && !confirm_Answer_Field.getText().equals(tempAnswer)) {
            validAnswer = true;
            answer_Status_Label.setText("Security Answer Approved");
            answer_Status_Label.setForeground(darkGreen);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (answer_Field.getText().equals(tempAnswer) && confirm_Answer_Field.getText().length() == 0 && !confirm_Answer_Field.getText().startsWith(" ") && !confirm_Answer_Field.getText().equals(tempAnswer)) {
            answer_Status_Label.setText("Security Answers Place Holder");
            answer_Status_Label.setForeground(Color.WHITE);
            validAnswer = false;

        } // if the answers given do not match the status is updated.
        else if (confirm_Answer_Field.getText().startsWith(" ") && !confirm_Answer_Field.getText().equals(tempAnswer)) {
            answer_Status_Label.setText("Security Answers Cannot Start With A Space");
            answer_Status_Label.setForeground(darkRed);
            validAnswer = false;
        } else if (!confirm_Answer_Field.getText().equals(answer_Field.getText()) && !confirm_Answer_Field.getText().equals(tempAnswer)) {
            if (!confirm_Answer_Field.getText().equals(tempAnswer)) {
                answer_Status_Label.setText("Security Answers Do Not Match");
                answer_Status_Label.setForeground(darkRed);
                validAnswer = false;
            } else if (confirm_Answer_Field.getText().equals(tempAnswer)) {
                answer_Status_Label.setText("Security Answers Place Holder");

                validAnswer = true;
            }
        } else if (confirm_Answer_Field.getText().equals(answer_Field.getText()) && confirm_Answer_Field.getText().equals(tempAnswer)) {
            answer_Status_Label.setText("Security Answers Has Not Changed");
            answer_Status_Label.setForeground(darkRed);
            validAnswer = true;
        } else {
            answer_Status_Label.setText("Security Answers Do Not Match");
            answer_Status_Label.setForeground(darkRed);
            validAnswer = false;
        }
    }//GEN-LAST:event_confirm_Answer_FieldCaretUpdate

    /**
     * a method that will get all the account details for a specified account ID
     *
     * @param accountID
     */
    private void getAccountDetails(int accountID) {

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
            String sql = "SELECT account_Username, account_Password, account_Email, account_Question, account_Answer FROM Account_Details "
                    + "WHERE account_Details_ID = '" + accountID + "';";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tempUser = rs.getString("account_Username");
                tempPass = rs.getString("account_Password");
                tempEmail = rs.getString("account_Email");
                tempQuestion = rs.getString("account_Question");
                tempAnswer = rs.getString("account_Answer");
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
        //update the GUI with the relevant data.
        updateGUI(tempUser, tempEmail, tempQuestion, tempAnswer);
    }

    /**
     * a method that will update GUI components.
     *
     * @param user
     * @param email
     * @param question
     * @param answer
     */
    private void updateGUI(String user, String email, String question, String answer) {

        username_Field.setText(user);
        email_Field.setText(email);
        question_ComboBox.setSelectedItem(question);
        answer_Field.setText(answer);
        confirm_New_Password_Field.setEditable(false);
        confirm_Answer_Field.setEditable(true);

    }

    /**
     * a method that will save the account details
     *
     * @param buttonPressed
     */
    private void sendAccountDetails(String buttonPressed) {

        //define the variables for the account information.
        String username = username_Field.getText().trim();
        String passwordSha1 = null;
        String tempPasswordSha1 = null;
        String email = email_Field.getText().trim();
        String question = question_ComboBox.getSelectedItem().toString();
        String answer = answer_Field.getText().trim();

        /*
         * creates the SHA1 hash of the password the user has entered.
         */
        if (new_Password_Field.getPassword().length != 0) {
            try {
                String strPassword = new String(new_Password_Field.getPassword());
                passwordSha1 = convertToSha1(strPassword);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login_Account_Create.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            passwordSha1 = tempPass;
        }
        /*
         * declares and new instance of the Suite_Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Suite_Database d = new Suite_Database();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;

        //popup window asking the user if they are sure they want to create the account.
        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Modify This Account?",
                "Confirm Account Modification",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {

            //checks if the data the user has entered is correct before storing it in the database.
            if (validPass == true && validEmail == true && validUser == true && validAnswer && true) {
                try {
                    // Register JDBC driver
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                    //creates and SQL statement and executes it.
                    String aSpql = "UPDATE Account_Details SET account_Username = ? , account_Password = ?, account_Email = ? , account_Question = ? , account_Answer = ? WHERE account_Details_ID = ? ;";

                    PreparedStatement updateFolder = conn.prepareStatement(aSpql);
                    updateFolder.setString(1, username);
                    updateFolder.setString(2, passwordSha1);
                    updateFolder.setString(3, email);
                    updateFolder.setString(4, question);
                    updateFolder.setString(5, answer);
                    updateFolder.setInt(6, accountID);
                    updateFolder.executeUpdate();

                    if (!passwordSha1.equals(tempPass)) {
                        Encryption_Script des = new Encryption_Script(tempPass, "Decrypt", accountID);
                        Encryption_Script ees = new Encryption_Script(passwordSha1, "Encrypt", accountID);
                    }

                } catch (SQLException | ClassNotFoundException se) {
                } finally {
                    //finally block used to close resources.
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                    }
                }

                //a popup windows telling the user thier account have been created
                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "Your Account Has Been Modified Successfully.",
                        "Account Modified!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                //when the window closes it opens the relevant screen so that a user can login with thier new details.
                if (buttonPressed.equals("Apply")) {
                    getAccountDetails(accountID);
                    modifyAccount = true;
                } else if (buttonPressed.equals("Accept")) {
                    //clears all the data a user has entered.
                    username_Field.setText(null);
                    email_Field.setText(null);
                    question_ComboBox.setSelectedItem(0);
                    answer_Field.setText(null);
                    confirm_New_Password_Field.setText(null);
                    confirm_Answer_Field.setText(null);
                    modifyAccount = true;
                    this.dispose();
                }

            } else {

                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Account Modification Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }
        }
    }

    /**
     * a method that will check if the username exists.
     *
     * @param username
     * @return
     */
    private boolean checkUsernameExists(String username) {
        // creates a variable call isTaken and sets it to false.
        boolean isTaken = false;
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
            /*
             * Register JDBC driver
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            Statement stmt = conn.createStatement();
            String sql = "SELECT account_Username FROM Account_Details";

            /*
             * extracts the data from the results of the SQL statment
             */
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //retrieves the information and puts it into a variable
                    String usernameCheck = rs.getString("account_Username");
                    //checks if the username is present in the database and changing the value of isTaken if it is taken.
                    if (usernameCheck.equals(username)) {
                        isTaken = true;
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }

        }
        //returns isTaken value so it can be used.
        return isTaken;
    }

    /**
     * a method that will check if the email address entered by the user already
     * exists in the database on the system.
     *
     * @param email
     * @return
     */
    private boolean checkEmailExsists(String email) {

        // creates a variable call isTaken and sets it to false.
        boolean isTaken = false;

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
            /*
             * Register JDBC driver
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            Statement stmt = conn.createStatement();
            String sql = "SELECT account_Email FROM Account_Details";

            /*
             * extracts the data from the results of the SQL statment.
             */
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //retrieves the information and puts it into a variable.
                    String emailCheck = rs.getString("account_Email");
                    //checks if the email is present in the database and changing the value of isTaken if it is taken.
                    if (emailCheck.equals(email)) {
                        isTaken = true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }

        }

        //returns isTaken value so it can be used.
        return isTaken;
    }

    /**
     * a method that will check if the email is in a valid format.
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

    /**
     * a method that will generate a strength rating for a password using a
     * simple pattern matching formula.
     *
     * @param password
     * @return
     */
    private int passwordStrength(String password) {
        //patterns to match 
        int statusPercentage = 0;
        String[] simpleChecks = {".*[a-z]+.*", // lower
            ".*[A-Z]+.*", // upper
            ".*[\\d]+.*", // digits
            ".*[@#$%]+.*" // symbols
    };

        //adds points depending what patterns are in the password.
        if (password.matches(simpleChecks[0])) {
            statusPercentage += 20;
        }
        if (password.matches(simpleChecks[1])) {
            statusPercentage += 20;
        }
        if (password.matches(simpleChecks[2])) {
            statusPercentage += 20;
        }
        if (password.matches(simpleChecks[3])) {
            statusPercentage += 20;
        }
        if (password.length() >= 8) {
            statusPercentage += 20;
        }

        //returns the score to be used.
        return statusPercentage;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Password_Label;
    private javax.swing.JButton accept_Button;
    private javax.swing.JPanel account_Details_Panel;
    private javax.swing.JPanel account_Recovery_Panel;
    private javax.swing.JTextField answer_Field;
    private javax.swing.JLabel answer_Label;
    private javax.swing.JLabel answer_Status_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField confirm_Answer_Field;
    private javax.swing.JLabel confirm_Answer_Label;
    private javax.swing.JTextField confirm_Email_Field;
    private javax.swing.JLabel confirm_Email_Label;
    private javax.swing.JPasswordField confirm_New_Password_Field;
    private javax.swing.JLabel confirm_Password_Label;
    private javax.swing.JLabel createPasswordLabel1;
    private javax.swing.JTextField email_Field;
    private javax.swing.JLabel email_Label;
    private javax.swing.JLabel email_Status_Label;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JPasswordField new_Password_Field;
    private javax.swing.JLabel password_Status_Label;
    private javax.swing.JLabel password_Strength_Label;
    private javax.swing.JComboBox question_ComboBox;
    private javax.swing.JTextField username_Field;
    private javax.swing.JLabel username_Label;
    private javax.swing.JLabel username_Status_Label;
    // End of variables declaration//GEN-END:variables
    private boolean validUser = true;
    private boolean validPass = true;
    private boolean validEmail = true;
    private boolean validAnswer = true;
    private final Color darkGreen = new Color(0x006400);
    private final Color darkRed = new Color(0x640000);
    private String tempUser;
    private String tempPass;
    private String tempEmail;
    private String tempQuestion;
    private String tempAnswer;
    private boolean modifyAccount = false;
}
