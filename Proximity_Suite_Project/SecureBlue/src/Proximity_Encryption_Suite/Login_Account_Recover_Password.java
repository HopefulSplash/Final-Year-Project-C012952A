/**
 * Defines to package to class belongs to.
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * The Login_Account_Recover_Password.Java Class implements an application that
 * allows a users chose the new password for their account so why can be given
 * access to the Proximity Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Login_Account_Recover_Password extends java.awt.Dialog {

    /**
     * Creates new form ResetPassword
     *
     * @param parent
     * @param modal
     */
    public Login_Account_Recover_Password(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
       
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
        this.setLocationRelativeTo(null);

        /**
         * loads the appropriate icons.
         */
        this.setIconImages(icons);
        
        // changing the color of the background and setting up the buttons.
        this.setBackground(Color.WHITE);
        create_Button.setEnabled(false);
        confirm_Password_Field.setEditable(false);

    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        password_Details_Panel = new javax.swing.JPanel();
        password_Status_Label = new javax.swing.JLabel();
        password_Strength_Status_Label = new javax.swing.JLabel();
        password_Strength_Label = new javax.swing.JLabel();
        Password_Label = new javax.swing.JLabel();
        password_Field = new javax.swing.JPasswordField();
        confirm_Password_Label = new javax.swing.JLabel();
        confirm_Password_Field = new javax.swing.JPasswordField();
        button_Panel = new javax.swing.JPanel();
        create_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        setIconImages(null);
        setName(""); // NOI18N
        setResizable(false);
        setTitle("Proximity Suite | Reset Password");
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        password_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));

        password_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        password_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_Status_Label.setText("Password");

        password_Strength_Status_Label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        password_Strength_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        password_Strength_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_Strength_Status_Label.setText("jLabel5");

        password_Strength_Label.setText("Password Strength:");

        Password_Label.setText("Password:");

        password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                password_FieldCaretUpdate(evt);
            }
        });

        confirm_Password_Label.setText("Confirm Password:");

        confirm_Password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_Password_FieldCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout password_Details_PanelLayout = new javax.swing.GroupLayout(password_Details_Panel);
        password_Details_Panel.setLayout(password_Details_PanelLayout);
        password_Details_PanelLayout.setHorizontalGroup(
            password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, password_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(confirm_Password_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_Strength_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Password_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirm_Password_Field)
                    .addComponent(password_Field)
                    .addComponent(password_Strength_Status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        password_Details_PanelLayout.setVerticalGroup(
            password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(password_Details_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Password_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_Password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirm_Password_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(password_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_Strength_Status_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(password_Strength_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password_Status_Label)
                .addGap(6, 6, 6))
        );

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        create_Button.setText("Accept");
        create_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_ButtonActionPerformed(evt);
            }
        });

        clear_Button.setText("Clear");
        clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_ButtonActionPerformed(evt);
            }
        });

        cancel_Button.setText("Cancel");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(create_Button)
                    .addComponent(clear_Button)
                    .addComponent(cancel_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(password_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that will close the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        // closing the dialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    /**
     * a method that will validate the password entered in the system.
     *
     * @param evt
     */
    private void password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_password_FieldCaretUpdate

        //checks that the passwords entered are the same and changes the status.
        if (Arrays.equals(password_Field.getPassword(), confirm_Password_Field.getPassword()) && confirm_Password_Field.getPassword().length > 0) {
            validPass = true;
            create_Button.setEnabled(true);

            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);

        } //checks if the password field is empty and changes the status.
        else if (password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            password_Strength_Status_Label.setForeground(Color.WHITE);
            confirm_Password_Field.setEditable(false);
            confirm_Password_Field.setText(null);
            validPass = false;
            create_Button.setEnabled(false);

        } // if the passwords are different it changes the status
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            confirm_Password_Field.setEditable(true);
            validPass = false;
            create_Button.setEnabled(false);

        }

        //checks if the password length is not equal to 0
        if (password_Field.getPassword().length != 0) {

            //gets a password strength
            int passwordScore = 0;
            String passString = new String(password_Field.getPassword());
            passwordScore = passwordStrength(passString);

            //depending on the password strength it will change the text to show the status
            if (passwordScore >= 20) {
                password_Strength_Status_Label.setText("Very Weak");
                password_Strength_Status_Label.setForeground(darkRed);
            }
            if (passwordScore >= 40) {
                password_Strength_Status_Label.setText("Weak");
                password_Strength_Status_Label.setForeground(darkRed);
            }
            if (passwordScore >= 60) {
                password_Strength_Status_Label.setText("Good");
                password_Strength_Status_Label.setForeground(darkGreen);
            }
            if (passwordScore >= 80) {
                password_Strength_Status_Label.setText("Strong");
                password_Strength_Status_Label.setForeground(darkGreen);

            }
            if (passwordScore >= 100) {
                password_Strength_Status_Label.setText("Very Strong");
                password_Strength_Status_Label.setForeground(darkGreen);

            }
        }

    }//GEN-LAST:event_password_FieldCaretUpdate
    /**
     * a method that will validate the password entered in the system.
     *
     * @param evt
     */
    private void confirm_Password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Password_FieldCaretUpdate
        //checks that the passwords entered are the same and changes the status.
        if (Arrays.equals(confirm_Password_Field.getPassword(), password_Field.getPassword()) && password_Field.getPassword().length > 0) {
            validPass = true;
            create_Button.setEnabled(true);

            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);
        } //checks if the password field is empty and changes the status.
        else if (confirm_Password_Field.getPassword().length == 0 && password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            password_Strength_Status_Label.setForeground(Color.WHITE);
            confirm_Password_Field.setEditable(false);
            create_Button.setEnabled(false);

            validPass = false;
        } // if the passwords are different it changes the status
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            validPass = false;
            create_Button.setEnabled(false);

        }

    }//GEN-LAST:event_confirm_Password_FieldCaretUpdate
    /**
     * a method that will generate a strength rating for a password using a
     * simple pattern matching formula.
     *
     * @param password
     * @return
     */
    private int passwordStrength(String password) {
        //patterns to match.
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
     * a method that will change the password if the correct information has
     * been given.
     *
     * @param evt
     */
    private void create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_ButtonActionPerformed

        //checks if the passwords are valie
        if (validPass == true) {

            Object[] options = {"Confirm", "Cancel"};
            int n = JOptionPane.showOptionDialog(this,
                    "Are You Sure You Want to Reset This Accounts Password?",
                    "Confirm Password Reset",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title

            // when the user has clicked the confirm option
            if (n == 0) {
                try {
                    // the password is converted into SHA1 string.
                    String strPassword = new String(password_Field.getPassword());
                    passwordSha1 = convertToSha1(strPassword);

                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Login_Account_Create.class.getName()).log(Level.SEVERE, null, ex);
                }

                //the boolean is change to true so the password can be changed.
                shouldAdd = true;
                // the data is cleared.
                password_Field.setText(null);
                confirm_Password_Field.setText(null);
                password_Status_Label.setForeground(Color.WHITE);
                password_Strength_Status_Label.setForeground(Color.WHITE);

                // the window is closed.
                this.dispose();

            } //when the password is incorrect.
            else {
                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/SecureBlue/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Account Creation Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }
        }


    }//GEN-LAST:event_create_ButtonActionPerformed

    /**
     * a method that will clear all the data a user has entered.
     *
     * @param evt
     */
    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed

        //clear all the data that may have been entered
        password_Field.setText(null);
        confirm_Password_Field.setText(null);
        password_Status_Label.setForeground(Color.WHITE);
        password_Strength_Status_Label.setForeground(Color.WHITE);

    }//GEN-LAST:event_clear_ButtonActionPerformed

    /**
     * a method to will cancel the operation of the window clear the data and
     * close the window.
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed

        //clear all the data that may have been entered
        password_Field.setText(null);
        confirm_Password_Field.setText(null);
        password_Status_Label.setForeground(Color.WHITE);
        password_Strength_Status_Label.setForeground(Color.WHITE);
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    /**
     * This is the main method which launches the frame.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login_Account_Recover_Password dialog = new Login_Account_Recover_Password(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    /**
     * a method to set the variable username.
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * a method to set the variable question.
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * a method to set the variable answer.
     *
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * a method what will return a boolean to be used by other methods.
     *
     * @return
     */
    public boolean isShouldAdd() {
        return shouldAdd;
    }

    /**
     * a method that returns the value in string.
     *
     * @return
     */
    public String getPasswordSha1() {
        return passwordSha1;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Password_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton clear_Button;
    private javax.swing.JPasswordField confirm_Password_Field;
    private javax.swing.JLabel confirm_Password_Label;
    private javax.swing.JButton create_Button;
    private javax.swing.JPanel password_Details_Panel;
    private javax.swing.JPasswordField password_Field;
    private javax.swing.JLabel password_Status_Label;
    private javax.swing.JLabel password_Strength_Label;
    private javax.swing.JLabel password_Strength_Status_Label;
    // End of variables declaration//GEN-END:variables
    Color darkGreen = new Color(0x006400);
    Color darkRed = new Color(0x640000);
    public String username;
    public String question;
    public String answer;
    public boolean validPass;
    public boolean shouldAdd = false;
    public String passwordSha1 = null;
}
