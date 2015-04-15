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
import java.sql.Connection;
import java.sql.DriverManager;
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
 * The Login_Account_Create.Java Class implements an application that allows a
 * users to create an account so why can be given access to the Proximity
 * Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Login_Account_Create extends javax.swing.JFrame {

    /**
     * Creates new form Login_Account_Create
     */
    public Login_Account_Create() {

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

        //sets the focus to the accept_Button
        this.getRootPane().setDefaultButton(accept_Button);

        //sets the default values for the buttons and fields
        confirm_password_Field.setEditable(false);
        confirm_Answer_Field.setEditable(false);
        answer_Field.setEditable(false);
        confirm_email_Field.setEditable(false);
        accept_Button.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        account_Details_Panel = new javax.swing.JPanel();
        username_Label = new javax.swing.JLabel();
        username_Field = new javax.swing.JTextField();
        Password_Label = new javax.swing.JLabel();
        password_Field = new javax.swing.JPasswordField();
        confirm_Password_Label = new javax.swing.JLabel();
        confirm_password_Field = new javax.swing.JPasswordField();
        email_Label = new javax.swing.JLabel();
        email_Field = new javax.swing.JTextField();
        confirm_Email_Label = new javax.swing.JLabel();
        confirm_email_Field = new javax.swing.JTextField();
        username_Status_Label = new javax.swing.JLabel();
        password_Status_Label = new javax.swing.JLabel();
        email_Status_Label = new javax.swing.JLabel();
        password_Strength_Label = new javax.swing.JLabel();
        createPasswordLabel1 = new javax.swing.JLabel();
        account_Recovery_Panel = new javax.swing.JPanel();
        answer_Label = new javax.swing.JLabel();
        quesion_ComboBox = new javax.swing.JComboBox();
        answer_Field = new javax.swing.JTextField();
        confirm_Answer_Field = new javax.swing.JTextField();
        confirm_Answer_Label = new javax.swing.JLabel();
        answer_Status_Label = new javax.swing.JLabel();
        button_Panel = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Create Account");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(null);
        setMinimumSize(null);
        setName("createAccountFrame"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        account_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        account_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Details"));

        username_Label.setText("Username:");

        username_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                username_FieldCaretUpdate(evt);
            }
        });

        Password_Label.setText("Password:");

        password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                password_FieldCaretUpdate(evt);
            }
        });

        confirm_Password_Label.setText("Confirm Password:");

        confirm_password_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_password_FieldCaretUpdate(evt);
            }
        });

        email_Label.setText("Email Address:");

        email_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                email_FieldCaretUpdate(evt);
            }
        });

        confirm_Email_Label.setText("Confirm Email Address:");

        confirm_email_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_email_FieldCaretUpdate(evt);
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
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(account_Details_PanelLayout.createSequentialGroup()
                        .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirm_Email_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(email_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Password_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confirm_Password_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(username_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(account_Details_PanelLayout.createSequentialGroup()
                        .addComponent(createPasswordLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(email_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username_Status_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(password_Field)
                    .addComponent(confirm_password_Field)
                    .addComponent(email_Field)
                    .addComponent(confirm_email_Field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(password_Strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username_Field))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password_Label)
                    .addComponent(password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(account_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirm_Password_Label)
                    .addComponent(confirm_password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(confirm_email_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(email_Status_Label)
                .addGap(6, 6, 6))
        );

        account_Recovery_Panel.setBackground(new java.awt.Color(255, 255, 255));
        account_Recovery_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account Recovery Question"));

        answer_Label.setText("Answer:");

        quesion_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select A Question", "What was your childhood nickname?", "In what city did you meet your spouse/significant other?", "What is the name of your favorite childhood friend?", "What street did you live on in third grade?", "What is the middle name of your oldest child?", "What school did you attend for sixth grade?", "What the name of your first pet?", "What was the name of your first stuffed animal?", "In what city or town did your mother and father meet?", "Where were you when you had your first kiss?", "What is the first name of the boy or girl that you first kissed?", "What was the last name of your third grade teacher?", "In what city does your nearest sibling live?", "In what city or town was your first job?", "What is the name of the place your wedding reception was held?" }));
        quesion_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quesion_ComboBoxActionPerformed(evt);
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
                    .addComponent(quesion_ComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(quesion_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        accept_Button.setText("Accept");
        accept_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_ButtonActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accept_Button)
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
                    .addComponent(account_Recovery_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(account_Details_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(account_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(account_Recovery_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        getContentPane().setBackground(Color.WHITE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method what activates when the user clicks the accept button and will
     * add the data to the database if the correct conditions are met.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed

        // creares the variables that will be added to the databse.
        String username = username_Field.getText();
        String passwordSha1 = null;
        String email = email_Field.getText();
        String question = quesion_ComboBox.getSelectedItem().toString();
        String answer = answer_Field.getText();
        int accountID = 0;

        /*
         * creates the SHA1 hash of the password the user has entered.
         */
        try {
            String strPassword = new String(password_Field.getPassword());
            passwordSha1 = convertToSha1(strPassword);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login_Account_Create.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         * declares and new instance of the Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Database d = new Database();
        d.startDatabase();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        Statement stmt = null;

        //popup window asking the user if they are sure they want to create the account.
        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Create This Account?",
                "Confirm Account Creation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {

            //checks if the data the user has entered is correct before storing it in the database.
            if (validPass == true && validEmail == true && validUser == true && validAnswer) {
                try {
                    // Register JDBC driver
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                    //creates and SQL statement and executes it.
                    stmt = conn.createStatement();
                    String createAccount = "INSERT INTO Account_Details "
                            + "VALUES (NULL"
                            + ", '" + username + "'"
                            + ", '" + passwordSha1 + "'"
                            + ", '" + email + "'"
                            + ", '" + question + "'"
                            + ", '" + answer + "'" + ")";
                    stmt.executeUpdate(createAccount);

                    stmt = conn.createStatement();
                    String sql = "SELECT account_Details_ID FROM Account_Details "
                            + "WHERE account_Username = '" + username + "';";

                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        accountID = rs.getInt("account_Details_ID");
                    }

                    stmt = conn.createStatement();
                    String createFolder = "INSERT INTO Folder_Details "
                            + "VALUES (NULL, " + accountID + ", '" + username + "''s"+ " Default Folder'" + ", 'Personal'" + ", 'Account Default Folder', DEFAULT );";
                    stmt.executeUpdate(createFolder);

                    stmt = conn.createStatement();
                    String createSettings = "INSERT INTO Settings_Details "
                            + "VALUES (NULL"
                            + ", " + accountID
                            + ", '" + username + "''s"+ " Default Folder'"
                            + ", DEFAULT"
                            + ", DEFAULT"
                            + ", DEFAULT"
                            + ", DEFAULT);";

                    stmt.executeUpdate(createSettings);
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
                        "Your Account Has Been Created Successfully.",
                        "Account Created!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                //clears all the data a user has entered.
                quesion_ComboBox.setSelectedIndex(0);
                password_Field.setText(null);
                confirm_password_Field.setText(null);
                username_Field.setText(null);
                email_Field.setText(null);
                answer_Field.setText(null);
                confirm_Answer_Field.setText(null);
                quesion_ComboBox.setSelectedIndex(0);
                confirm_email_Field.setText(null);
                username_Status_Label.setForeground(Color.WHITE);
                password_Status_Label.setForeground(Color.WHITE);
                email_Status_Label.setForeground(Color.WHITE);
                answer_Status_Label.setForeground(Color.WHITE);
                password_Strength_Label.setForeground(Color.WHITE);

                //closes this window and opens the login window.
                this.dispose();
                new Login_Account().setVisible(true);

            } else {

                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Account Creation Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }
        }
    }//GEN-LAST:event_accept_ButtonActionPerformed
    /**
     * a method that checks if the user has selected a question.
     *
     * @param evt
     */
    private void quesion_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quesion_ComboBoxActionPerformed
        //checks if the user has selected a question.
        if (quesion_ComboBox.getSelectedIndex() != 0) {
            answer_Field.setEditable(true);
        } //resets the fields if the user have change the combo box to index 0.
        else {
            answer_Field.setText(null);
            answer_Field.setEditable(false);
            confirm_Answer_Field.setText(null);
            confirm_Answer_Field.setEditable(false);
        }


    }//GEN-LAST:event_quesion_ComboBoxActionPerformed

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
     * a method that will clear the contents of the components a user has
     * entered data in.
     *
     * @param evt
     */
    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed
        // clears all the data a user may have entered.
        quesion_ComboBox.setSelectedIndex(0);
        password_Field.setText(null);
        confirm_password_Field.setText(null);
        username_Field.setText(null);
        email_Field.setText(null);
        answer_Field.setText(null);
        confirm_Answer_Field.setText(null);
        quesion_ComboBox.setSelectedIndex(0);
        confirm_email_Field.setText(null);
        username_Status_Label.setForeground(Color.WHITE);
        password_Status_Label.setForeground(Color.WHITE);
        email_Status_Label.setForeground(Color.WHITE);
        answer_Status_Label.setForeground(Color.WHITE);
        password_Strength_Label.setForeground(Color.WHITE);

    }//GEN-LAST:event_clear_ButtonActionPerformed
    /**
     * a method that is close the window and open the login window for the user.
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        //when the window closes it opens the login screen so that a user can login with thier new details.
        this.dispose();
        new Login_Account().setVisible(true);

    }//GEN-LAST:event_formWindowClosing
    /**
     * a method that will check if the username entered by the user already
     * exists in the database on the system.
     *
     * @param username
     * @return
     */
    private boolean checkUsernameExsists(String username) {
        // creates a variable call isTaken and sets it to false.
        boolean isTaken = false;
        /*
         * declares and new instance of the Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Database d = new Database();
        d.startDatabase();
        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        Statement stmt = null;

        try {
            /*
             * Register JDBC driver
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            stmt = conn.createStatement();
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
         * declares and new instance of the Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Database d = new Database();
        d.startDatabase();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        Statement stmt = null;

        try {
            /*
             * Register JDBC driver
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());
            /*
             * creates and executes an SQL statement to be run against the database.
             */
            stmt = conn.createStatement();
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

        //returns isTaken value so it can be used.
        return isTaken;
    }

    /**
     * a method that validates the username a user enters.
     *
     * @param evt
     */
    private void username_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_username_FieldCaretUpdate
        //checks if the email address is in the database already and stores it in a variable.
        boolean userIsTaken = checkUsernameExsists(username_Field.getText());

        // checks if the username is taken and updates the status.
        if (userIsTaken == true && username_Field.getText().length() > 0) {
            username_Status_Label.setText("Username Already In Use");
            username_Status_Label.setForeground(darkRed);
            validUser = false;

        } // checks if the username field is empty and updates the status.
        else if (username_Field.getText().length() == 0) {
            username_Status_Label.setText("Username Place Holder");
            username_Status_Label.setForeground(Color.WHITE);
            validUser = false;

        } // checks if the username is too long for the database and updates the status.
        else if (username_Field.getText().length() > 255) {
            username_Status_Label.setText("Username Is Too Long");
            username_Status_Label.setForeground(darkRed);
            validUser = false;
        } //if non of the above statements are met then they username is approved and the status updated.
        else {
            validUser = true;
            username_Status_Label.setText("Username Approved");
            username_Status_Label.setForeground(darkGreen);
        }
    }//GEN-LAST:event_username_FieldCaretUpdate

    /**
     * a method that will validate the user entering the password they want to
     * use for their account.
     *
     * @param evt
     */
    private void password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_password_FieldCaretUpdate
        // checks if the passwords the user has entered match eachother.
        if (Arrays.equals(password_Field.getPassword(), confirm_password_Field.getPassword()) && confirm_password_Field.getPassword().length > 0) {
            validPass = true;
            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);

        } // if the password field is empty then they status is updated. 
        else if (password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            password_Strength_Label.setForeground(Color.WHITE);
            confirm_password_Field.setEditable(false);
            confirm_password_Field.setText(null);
            validPass = false;

        } // if the passwords do not match the statements above then they do not match eachother.
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            confirm_password_Field.setEditable(true);
            validPass = false;

        }

        // checks if the password entered is not equal to 0 in length.
        if (password_Field.getPassword().length != 0) {

            // stores the password and gets it strength value.
            int passwordScore = 0;
            String passString = new String(password_Field.getPassword());
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


    }//GEN-LAST:event_password_FieldCaretUpdate
    /**
     * a method that will validate the user entering the password they want to
     * use for their account.
     *
     * @param evt
     */
    private void confirm_password_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_password_FieldCaretUpdate

        // checks if the passwords the user has entered match eachother.
        if (Arrays.equals(confirm_password_Field.getPassword(), password_Field.getPassword()) && password_Field.getPassword().length > 0) {
            validPass = true;
            password_Status_Label.setText("Passowrd Approved");
            password_Status_Label.setForeground(darkGreen);
        } // if the password field is empty then they status is updated.
        else if (confirm_password_Field.getPassword().length == 0 && password_Field.getPassword().length == 0) {
            password_Status_Label.setText("A Password Place Holder");
            password_Status_Label.setForeground(Color.WHITE);
            password_Strength_Label.setForeground(Color.WHITE);
            confirm_password_Field.setEditable(false);
            validPass = false;
        } // if the passwords do not match the statements above then they do not match eachother.
        else {
            password_Status_Label.setText("Passwords Do Not Match");
            password_Status_Label.setForeground(darkRed);
            validPass = false;
        }
    }//GEN-LAST:event_confirm_password_FieldCaretUpdate

    /**
     * a method that will check the data the user is entering and check if it is
     * a valid email address.
     *
     * @param evt
     */
    private void email_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_email_FieldCaretUpdate

        //checks if the email address is in the database already and stores it in a variable.
        boolean isTaken = checkEmailExsists(email_Field.getText());

        //checks if the email address is in the database already and updates the status.
        if (isTaken == true && email_Field.getText().length() > 0) {
            email_Status_Label.setText("Email Address Already In Use");
            email_Status_Label.setForeground(darkRed);
            confirm_email_Field.setEditable(false);
            confirm_email_Field.setText(null);
            validEmail = false;

        } /**
         * if the email addresses match and are not already in the database and
         * in the valid format then the status will be updated and approved.
         */
        else if (email_Field.getText().equals(confirm_email_Field.getText()) && validateEmail(email_Field.getText()) == true && email_Field.getText().length() != 0 && isTaken == false) {
            validEmail = true;
            email_Status_Label.setText("Email Address Approved");
            email_Status_Label.setForeground(darkGreen);

        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (email_Field.getText().length() == 0) {
            email_Status_Label.setText("Email Addresses Place Holder");
            email_Status_Label.setForeground(Color.WHITE);
            email_Status_Label.setForeground(Color.WHITE);
            confirm_email_Field.setEditable(false);
            confirm_email_Field.setText(null);
            validEmail = false;

        } /**
         * if the email addresses are invalid then the status is updated.
         */
        else if (email_Field.getText().length() != 0 && validateEmail(email_Field.getText()) == false) {
            email_Status_Label.setText("Email Addresses Is Invalid");
            email_Status_Label.setForeground(darkRed);
            confirm_email_Field.setEditable(false);
            confirm_email_Field.setText(null);
            validEmail = false;

        } /**
         * if the email address do not met the above if statements then they do
         * not match.
         */
        else if (isTaken == false) {
            email_Status_Label.setText("Email Addresses Do Not Match");
            email_Status_Label.setForeground(darkRed);
            confirm_email_Field.setEditable(true);
            validEmail = false;

        }
    }//GEN-LAST:event_email_FieldCaretUpdate

    /**
     * a method that will check the data the user is entering and check if it
     * matches the other answer they have entered.
     *
     * @param evt
     */
    private void answer_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_answer_FieldCaretUpdate

//checks if the answers the user has given match eachother.
        if (answer_Field.getText().equals(confirm_Answer_Field.getText()) == true && answer_Field.getText().length() != 0) {
            validAnswer = true;
            accept_Button.setEnabled(true);
            answer_Status_Label.setText("Security Answer Approved");
            answer_Status_Label.setForeground(darkGreen);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (answer_Field.getText().length() == 0) {
            answer_Status_Label.setText("Security Answers Place Holder");
            answer_Status_Label.setForeground(Color.WHITE);
            answer_Status_Label.setForeground(Color.WHITE);
            confirm_Answer_Field.setEditable(false);
            confirm_Answer_Field.setText(null);
            accept_Button.setEnabled(false);

            validAnswer = false;

        } // if the answers given do not match the status is updated.
        else {
            answer_Status_Label.setText("Security Answers Do Not Match ");
            answer_Status_Label.setForeground(darkRed);
            confirm_Answer_Field.setEditable(true);
            accept_Button.setEnabled(false);

            validAnswer = false;

        }

    }//GEN-LAST:event_answer_FieldCaretUpdate

    /**
     * a method that will check the data the user is entering and check if it
     * matches the other answer they have entered.
     *
     * @param evt
     */
    private void confirm_Answer_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Answer_FieldCaretUpdate

        //checks if the answers the user has given match eachother.
        if (confirm_Answer_Field.getText().equals(answer_Field.getText()) == true && confirm_Answer_Field.getText().length() != 0) {
            validAnswer = true;
            accept_Button.setEnabled(true);
            answer_Status_Label.setText("Security Answer Approved");
            answer_Status_Label.setForeground(darkGreen);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (confirm_Answer_Field.getText().length() == 0 && answer_Field.getText().length() == 0) {
            answer_Status_Label.setText("Security Answers Place Holder");
            answer_Status_Label.setForeground(Color.WHITE);
            answer_Status_Label.setForeground(Color.WHITE);
            accept_Button.setEnabled(false);

            validAnswer = false;

        } // if the answers given do not match the status is updated.
        else {
            answer_Status_Label.setText("Security Answers Do Not Match ");
            answer_Status_Label.setForeground(darkRed);
            accept_Button.setEnabled(false);

            validAnswer = false;

        }
    }//GEN-LAST:event_confirm_Answer_FieldCaretUpdate

    /**
     * a method that will check the data the user is entering and check if it is
     * a valid email address.
     *
     * @param evt
     */
    private void confirm_email_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_email_FieldCaretUpdate

        //checks if the email address is in the database already.
        boolean isTaken = checkEmailExsists(email_Field.getText());

        /**
         * if the email addresses match and are not already in the database and
         * in the valid format then the status will be updated and approved.
         */
        if (confirm_email_Field.getText().equals(email_Field.getText()) && validateEmail(email_Field.getText()) == true && email_Field.getText().length() != 0 && isTaken == false) {
            validEmail = true;
            email_Status_Label.setText("Email Address Approved");
            email_Status_Label.setForeground(darkGreen);
        } /**
         * if the email address field is empty then the status text will become
         * hidden.
         */
        else if (confirm_email_Field.getText().length() == 0 && email_Field.getText().length() == 0) {
            email_Status_Label.setText("Email Addresses Place Holder");
            email_Status_Label.setForeground(Color.WHITE);
            email_Status_Label.setForeground(Color.WHITE);
            validEmail = false;

        } /**
         * if the email address do not met the above if statements then they do
         * not match.
         */
        else if (isTaken == false) {
            email_Status_Label.setText("Email Addresses Do Not Match");
            email_Status_Label.setForeground(darkRed);
            validEmail = false;

        }
    }//GEN-LAST:event_confirm_email_FieldCaretUpdate

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
            java.util.logging.Logger.getLogger(Login_Account_Create.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login_Account_Create().setVisible(true);
            }
        });
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
    private javax.swing.JButton clear_Button;
    private javax.swing.JTextField confirm_Answer_Field;
    private javax.swing.JLabel confirm_Answer_Label;
    private javax.swing.JLabel confirm_Email_Label;
    private javax.swing.JLabel confirm_Password_Label;
    private javax.swing.JTextField confirm_email_Field;
    private javax.swing.JPasswordField confirm_password_Field;
    private javax.swing.JLabel createPasswordLabel1;
    private javax.swing.JTextField email_Field;
    private javax.swing.JLabel email_Label;
    private javax.swing.JLabel email_Status_Label;
    private javax.swing.JPasswordField password_Field;
    private javax.swing.JLabel password_Status_Label;
    private javax.swing.JLabel password_Strength_Label;
    private javax.swing.JComboBox quesion_ComboBox;
    private javax.swing.JTextField username_Field;
    private javax.swing.JLabel username_Label;
    private javax.swing.JLabel username_Status_Label;
    // End of variables declaration//GEN-END:variables
    private boolean validUser = false;
    private boolean validPass = false;
    private boolean validEmail = false;
    private boolean validAnswer = false;
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);

}
