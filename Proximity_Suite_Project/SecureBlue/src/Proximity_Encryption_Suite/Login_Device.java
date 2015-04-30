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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * The Login_Device.Java Class implements an application that allows a users
 * select their device and enter a password so why can be given access to the
 * Proximity Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Login_Device extends javax.swing.JFrame {

    /**
     * Creates new Login_Device.
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

        //creates a new task and sets the status to Setup then executes it 
        Task task = new Task();
        task.set_Background_Status("Setup");
        task.execute();

        //setup for the GUI
        device_ComboBox.setEnabled(false);
        login_Button.requestFocus();

        //creates a random number between 1 and 5 to be used for security.
        Random random_Number = new Random();
        attempt_Random_Counter = random_Number.nextInt(5) + 1;
    }

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        //defining variables for use.
        private Map<String, List<String>> map_Return_Result = new HashMap<>();
        private int background_Counter;
        private String background_Status;

        /**
         * a setter for the background_Status status.
         *
         * @param status
         */
        public void set_Background_Status(String status) {
            this.background_Status = status;
        }

        @Override
        public Void doInBackground() {
            // changes to cursor to the wating system.
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // setting the counter to 0;
            background_Counter = 0;

            //a while loop that will continue untill the processes are finished.
            while (background_Counter != 1) {

                if ("Setup".equals(background_Status)) {
                    //creates a the database if it hasnt been created already.
                    Suite_Database d = new Suite_Database();
                    d.startDatabase();
                    background_Counter++;

                } else if ("Scan".equals(background_Status)) {
                    // reset the device_Position to 0.
                    int device_Position = 0;
                    /* Create an object of Device_Service. */
                    Device_Service ss = new Device_Service();
                    /* Get bluetooth device details. */
                    map_Return_Result = ss.getBluetoothDevices();
                    /* Add devices in JList */
                    for (Map.Entry<String, List<String>> entry : map_Return_Result.entrySet()) {
                        device_ComboBox.addItem(entry.getValue().get(0));
                        map_Device_Position.put(device_Position, entry.getValue());
                        device_Position++;
                    }
                    //completes all the processes so ends to background task.
                    background_Counter++;
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

            if ("Scan".equals(background_Status)) {
                //setting the relvant feilds to enabled when the search is completed.
                device_ComboBox.removeItemAt(0);
                device_ComboBox.setEnabled(true);
                password_Field.setEnabled(true);
                login_Button.setEnabled(true);
                account_Recover_Button1.setEnabled(true);
                device_Scan_Button.setEnabled(true);
                account_Creation_Button1.setEnabled(true);
                login_Button.requestFocus();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginDetailsPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        password_Field = new javax.swing.JPasswordField();
        changeLoginLabel = new javax.swing.JLabel();
        login_Button = new javax.swing.JButton();
        device_ComboBox = new javax.swing.JComboBox();
        device_Scan_Button = new javax.swing.JButton();
        login_Logo_Panel3 = new javax.swing.JPanel();
        login_Logo_Image3 = new javax.swing.JLabel();
        creation_Recovery_Panel1 = new javax.swing.JPanel();
        account_Creation_Button1 = new javax.swing.JButton();
        account_Recover_Button1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proximity Suite | Device Login");
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

        password_Field.setToolTipText("");
        password_Field.setEnabled(false);
        password_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_FieldActionPerformed(evt);
            }
        });
        PlainDocument document = (PlainDocument) password_Field.getDocument();
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

        device_ComboBox.setMaximumRowCount(20);
        device_ComboBox.setLightWeightPopupEnabled(false);
        device_ComboBox.setName(""); // NOI18N

        device_Scan_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Refresh.png"))); // NOI18N
        device_Scan_Button.setFocusPainted(false);
        device_Scan_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_Scan_ButtonActionPerformed(evt);
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
                        .addComponent(device_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(device_Scan_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(login_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_Field, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(6, 6, 6))
        );
        loginDetailsPanelLayout.setVerticalGroup(
            loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginDetailsPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(device_Scan_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(device_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(loginDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(password_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    /**
     * when a user preforms an action the password field it will attempt to
     * login.
     *
     * @param evt
     */
    private void password_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_FieldActionPerformed
        // TODO add your handling code here:
        password_Field.setFocusable(false);
        password_Field.setFocusable(true);
        login_Button.doClick();

    }//GEN-LAST:event_password_FieldActionPerformed
    /**
     * when the user wants to change login type the new window is opened and the
     * old one is closed.
     *
     * @param evt
     */
    private void changeLoginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeLoginLabelMouseClicked
        Login_Account dLSameple = new Login_Account();
        dLSameple.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_changeLoginLabelMouseClicked
    /**
     * when a user wants to create an account this will open the right form.
     *
     * @param evt
     */
    private void account_Creation_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Creation_Button1ActionPerformed
        Login_Account_Create cASameple = new Login_Account_Create(this, true, "Login");
        cASameple.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_account_Creation_Button1ActionPerformed
    /**
     * when a user wants to recover an account this will open the right form.
     *
     * @param evt
     */
    private void account_Recover_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_Recover_Button1ActionPerformed
        // TODO add your handling code here:
        Login_Account_Recover rASameple = new Login_Account_Recover();
        rASameple.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_account_Recover_Button1ActionPerformed
    /**
     * when the user clicks the scan button it will start the process.
     *
     * @param evt
     */
    private void device_Scan_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_Scan_ButtonActionPerformed

        //clears and setups the process of scanning for devices.
        map_Device_Position.clear();
        device_ComboBox.removeAllItems();
        device_ComboBox.addItem("Scanning...");
        password_Field.setText("");
        password_Field.setEnabled(false);
        login_Button.setEnabled(false);
        account_Recover_Button1.setEnabled(false);
        device_Scan_Button.setEnabled(false);
        account_Creation_Button1.setEnabled(false);
        device_ComboBox.setEnabled(false);

        //starts a new task so that it can scan for devices in the background of the application.
        Task task = new Task();
        task.set_Background_Status("Scan");
        task.execute();

    }//GEN-LAST:event_device_Scan_ButtonActionPerformed
    /**
     * this method will check if the application is in lockout mode
     *
     * @return
     */
    private boolean checkTimeout() {
// sets up variables;
        boolean timeout = false;
        Date databaseDate = null;
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
             * Register JDBC driver.
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            /*
             * creates and executes an SQL statement to be run against the database.
             */
            Statement stmt = conn.createStatement();
            String sql = "SELECT program_Timeout_Date FROM program_Timeout;";

            ResultSet rs = stmt.executeQuery(sql);

            //uses the results of the query.
            while (rs.next()) {
                databaseDate = new Date(rs.getTimestamp("program_Timeout_Date").getTime());
            }

            //creates a format for the data and gets the current date and time.
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date d1 = new java.util.Date();

            try {
                //generates the time difference in different formats.
                long time_Difference = d1.getTime() - databaseDate.getTime();
                long difference_Minutes = time_Difference / (60 * 1000) % 60;
                long difference_Hour = time_Difference / (60 * 60 * 1000) % 24;
                //checks if the time difference is withn the lockout period.
                if (difference_Minutes >= 15 || difference_Hour != 0) {
                    timeout = false;
                } else {
                    timeout = true;
                }
            } catch (Exception e) {
            }

        } catch (SQLException se) {
        } catch (ClassNotFoundException | HeadlessException e) {
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
        }
        return timeout;
    }

    /**
     * if the user fails to login several times this method will start the
     * lockout period.
     */
    private void startTimeout() {
        //setup variables.
        Date databaseDate = null;
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
             * Register JDBC driver.
             */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            /*
             * creates and executes an SQL statement to be run against the database.
             */
            Statement stmt = conn.createStatement();
            String createTimeout = "UPDATE program_Timeout SET program_Timeout_Date = NOW() ORDER BY program_Timeout_ID DESC LIMIT 1;";
            stmt.executeUpdate(createTimeout);

        } catch (SQLException se) {
        } catch (ClassNotFoundException | HeadlessException e) {
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
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
        MessageDigest m_Digest = MessageDigest.getInstance("SHA1");
        byte[] result_String = m_Digest.digest(input.getBytes());
        /*
         * builds the new string so that it is in the correct format for storage.
         */
        StringBuilder string_Builder = new StringBuilder();
        for (int i = 0; i < result_String.length; i++) {
            string_Builder.append(Integer.toString((result_String[i] & 0xff) + 0x100, 16).substring(1));
        }
        /*
         * returns the string so it can be used.
         */
        return string_Builder.toString();
    }

    /**
     * performs the login function when the login button is pressed.
     *
     * @param evt
     */
    private void login_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_ButtonActionPerformed
//checks if the tineout period is still active.
        if (checkTimeout() == false) {

            if (!map_Device_Position.isEmpty()) {
                List<String> tmpDeviceDetails = map_Device_Position.get(device_ComboBox.getSelectedIndex());
                /* Set bluetooth device name */

                /* Set bluetooth device Address */
                String device_Address = tmpDeviceDetails.get(1);
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
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT device_Details_ID FROM device_Details "
                            + "WHERE device_Address = '" + device_Address
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
                            //increments the counter
                            attempt_Counter++;
                            /*
                             * shows an error message due to the username or password being
                             * incorrect or not existing.
                             */
                            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                            JOptionPane.showMessageDialog(this,
                                    "No Device With These Details Exists On The System. Please Try Again.",
                                    "Deivce Login Error",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    crossIcon);
                        } else {
                            /*
                             * saves the account_Details_ID into a variable and passes it into
                             * the main window and opens it while closing the old window.
                             */
                            while (rs.next()) {
                                get_Device_ID(device_Address);
                                //retrieves the information and puts it into a variable
                                Suite_Window mWSameple = new Suite_Window(-1, "Device", device_Address, device_ID, device_Name);
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
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException se) {
                    }
                }
            } else {
                //increments the login counter.
                attempt_Counter++;
                //checks if the max attempts have been reached.
                if (attempt_Counter == attempt_Random_Counter) {
                    //starts the timeout period.
                    startTimeout();
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog(this,
                            "Login Failed, Application Lockout Enabled.",
                            "Device Login Error",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);
                } else {
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog(this,
                            "No Device Selected, Please Try Again.",
                            "Device Login Error",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);
                }
            }
        } else {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog(this,
                    "Program Timeout Still In Effect, Please Wait For It Expire",
                    "Device Login Error",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }
    }//GEN-LAST:event_login_ButtonActionPerformed

    /**
     * gets the devices ID relating the the MAC address of the mobile device.
     *
     * @param deviceAddress
     */
    private void get_Device_ID(String deviceAddress) {
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
            // executes the statement
            String sql = "SELECT device_Details_ID, device_Name FROM device_Details WHERE device_Address = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, deviceAddress);
            ResultSet rs = pStmt.executeQuery();
            //uses the device information and stores it in variables.
            while (rs.next()) {
                device_ID = rs.getInt("device_Details_ID");
                device_Name = rs.getString("device_Name");
            }
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            //closes the connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    /**
     * the main function that will create the form so the user can see it.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
    private javax.swing.JComboBox device_ComboBox;
    private javax.swing.JButton device_Scan_Button;
    private javax.swing.JPanel loginDetailsPanel;
    private javax.swing.JButton login_Button;
    private javax.swing.JLabel login_Logo_Image3;
    private javax.swing.JPanel login_Logo_Panel3;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField password_Field;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
    private String device_Name;
    private int device_ID = -1;
    private int attempt_Counter = 0;
    private final int attempt_Random_Counter;
    private final Map<Integer, List<String>> map_Device_Position = new HashMap<>();
}
