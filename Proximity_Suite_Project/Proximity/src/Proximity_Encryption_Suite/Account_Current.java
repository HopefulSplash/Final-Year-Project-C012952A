/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
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

/**
 * The Account_Current.Java Class implements an application that allows a users
 * to view some details about the account they are logged into.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Account_Current extends javax.swing.JDialog {

    int accountID;

    /**
     * Creates new form Account_Current
     *
     * @param parent
     * @param modal
     * @param account_ID
     */
    public Account_Current(java.awt.Frame parent, boolean modal, int account_ID) {

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

        //gets the account information.
        this.accountID = account_ID;
        getAccountDetails(accountID);
        //GUI setup
        ok_Button.requestFocus();
    }

    /**
     * a method what will get the account details from the database.
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
            String sql = "SELECT account_Username, account_Email, account_Question FROM Account_Details "
                    + "WHERE account_Details_ID = '" + accountID + "';";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tempUser = rs.getString("account_Username");
                tempEmail = rs.getString("account_Email");
                tempQuestion = rs.getString("account_Question");
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
        //update the GUI with the information gathered.
        updateGUI(tempUser, tempEmail, tempQuestion);
    }

    /**
     * a method that will update the GUI with the relevant account information.
     *
     * @param user
     * @param email
     * @param question
     */
    private void updateGUI(String user, String email, String question) {

        username_Field.setText(user);
        email_Field.setText(email);
        question_Field.setText(question);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        modify_Button = new javax.swing.JButton();
        ok_Button = new javax.swing.JButton();
        account_Details_Panel = new javax.swing.JPanel();
        username_Label = new javax.swing.JLabel();
        username_Field = new javax.swing.JTextField();
        email_Label = new javax.swing.JLabel();
        email_Field = new javax.swing.JTextField();
        email_Label1 = new javax.swing.JLabel();
        question_Field = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Current Account");
        setMaximumSize(null);
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

        modify_Button.setText("Modify");
        modify_Button.setFocusPainted(false);
        modify_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_ButtonActionPerformed(evt);
            }
        });

        ok_Button.setText("OK");
        ok_Button.setFocusPainted(false);
        ok_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_PanelLayout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(ok_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modify_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(modify_Button)
                    .addComponent(ok_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        account_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        account_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Details"));

        username_Label.setText("Username:");

        username_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        username_Field.setFocusable(false);

        email_Label.setText("Email Address:");

        email_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        email_Field.setFocusable(false);

        email_Label1.setText("Security Question:");

        question_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        question_Field.setFocusable(false);

        javax.swing.GroupLayout account_Details_PanelLayout = new javax.swing.GroupLayout(account_Details_Panel);
        account_Details_Panel.setLayout(account_Details_PanelLayout);
        account_Details_PanelLayout.setHorizontalGroup(
            account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, account_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(email_Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(email_Label1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(username_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(question_Field)
                    .addComponent(username_Field)
                    .addComponent(email_Field))
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
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email_Label)
                    .addComponent(email_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email_Label1)
                    .addComponent(question_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(account_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(account_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that will close the form
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will open the modify account form.
     *
     * @param evt
     */
    private void modify_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_ButtonActionPerformed
        Account_Management j1 = new Account_Management((Frame) this.getParent(), true, accountID);
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_modify_ButtonActionPerformed
    /**
     * a method that will close the form once the user is finished.
     *
     * @param evt
     */
    private void ok_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_ok_ButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel account_Details_Panel;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField email_Field;
    private javax.swing.JLabel email_Label;
    private javax.swing.JLabel email_Label1;
    private javax.swing.JButton modify_Button;
    private javax.swing.JButton ok_Button;
    private javax.swing.JTextField question_Field;
    private javax.swing.JTextField username_Field;
    private javax.swing.JLabel username_Label;
    // End of variables declaration//GEN-END:variables
    private String tempUser;
    private String tempEmail;
    private String tempQuestion;

}
