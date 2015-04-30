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
import javax.swing.SwingWorker;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
/**
 * The Device_Management.Java Class implements an application that allows a user to
 * alter a devices details such as the pin that is connected to the device.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Management extends javax.swing.JDialog {

    class Task extends SwingWorker<Void, Void> {

        int counter = 0;
        boolean notTaken = false;

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        boolean addedFile = false;

        String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        Object o1;

        public void setO1(Object o1) {
            this.o1 = o1;
        }
        /*
         * Main task. Executed in background thread.
         */

        @Override
        public Void doInBackground() {

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jPasswordField1.setEnabled(false);
            jPasswordField2.setEnabled(false);
            jPasswordField1.setFocusable(false);
            jPasswordField2.setFocusable(false);

            progressBar.setValue(0);
            progressBar.setIndeterminate(true);

            //Initialize progress property.
            if ("Loading".equals(status)) {

                /* Get bluetooth device details */
                getAccountDevices();

                if (deviceIDList.isEmpty()) {
                    //print error message
                } else {
                    for (int i = 0; i < deviceIDList.size(); i++) {

                        getDeviceDetails(deviceIDList.get(i));

                        for (int u = 0; u < deviceName.size(); u++) {
                            jComboBox1.addItem(deviceName.get(i));
                        }
                    }
                }

            }

            if ("Adding".equals(status)) {

                if (validPass == true) {

                    addDevice();

                }

            }

            return null;
        }

        private void getAccountDevices() {
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
                String sql = "SELECT device_Details_ID FROM account_device_list WHERE account_Details_ID = ?";

                PreparedStatement getFolderID = conn.prepareStatement(sql);
                getFolderID.setInt(1, accountID);

                try (ResultSet rs = getFolderID.executeQuery()) {
                    while (rs.next()) {
                        int device = rs.getInt("device_Details_ID");

                        deviceIDList.add(device);

                    }
                }
                getFolderID.close();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                //finally block used to close resources
                try {
                    if (conn != null) {
                         conn.close();

                    }
                } catch (SQLException se) {
                }// do nothing

            }

        }

        private void getDeviceDetails(int deviceID) {

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
                String sql = "SELECT device_Name, device_Address, device_Created FROM device_details WHERE device_Details_ID = ?";

                PreparedStatement getFolderID = conn.prepareStatement(sql);
                getFolderID.setInt(1, deviceID);

                try (ResultSet rs = getFolderID.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString("device_Name");
                        String address = rs.getString("device_Address");
                        String created = rs.getString("device_Created");

                        deviceName.add(name);
                        deviceAddress.add(address);
                        deviceCreated.add(created);
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
                }// do nothing
                

            }

        }

        private void addDevice() {

            String passwordSha1 = null;

            /*
             * creates the SHA1 hash of the password the user has entered.
             */
            try {
                String strPassword = new String(jPasswordField1.getPassword());
                passwordSha1 = convertToSha1(strPassword);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login_Account_Create.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*
             * declares and new instance of the Suite_Database class and then checks if the
             * the database exists and if is does not then creates it for the system.
             */
            Suite_Database d = new Suite_Database();

            System.out.println(deviceIDList.get(jComboBox1.getSelectedIndex() - 1));
            System.out.println(passwordSha1);

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
             try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "UPDATE device_details SET device_Password = ? WHERE device_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(2, deviceIDList.get(jComboBox1.getSelectedIndex() - 1));
                pStmt.setString(1, passwordSha1);
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

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor
            jButton3.setEnabled(true);

            if ("Loading".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);

                jButton2.setEnabled(false);

            }
            if ("Adding".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);

                jPasswordField1.setText("");
                jPasswordField2.setText("");

                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) o1,
                        "Device Updated Successfully.",
                        "File Addirion Successful!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);
                jButton1.doClick();

            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {

    }

    private int accountID;
    private int deviceID;

    private ArrayList<Integer> deviceIDList = new ArrayList<>();

    private ArrayList<String> deviceName = new ArrayList<>();
    private ArrayList<String> deviceAddress = new ArrayList<>();
    private ArrayList<String> deviceCreated = new ArrayList<>();

    public Device_Management(java.awt.Frame parent, boolean modal, int account_ID, int deviceID) {
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
        this.accountID = account_ID;
        this.deviceID = deviceID;
        jButton1.requestFocus();

        Task task = new Task();
        task.setStatus("Loading");
        task.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblDeviceAddress = new javax.swing.JLabel();
        lblDeviceName = new javax.swing.JLabel();
        lblRuntimeDeviceAddress = new javax.swing.JTextField();
        lblRuntimeDeviceName = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bluecove Bluetooth Discovery");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Cancel");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Accept");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Details"));

        lblDeviceAddress.setText("Bluetooth Device Address:");

        lblDeviceName.setText("Bluetooth Device Name:");

        lblRuntimeDeviceAddress.setEditable(false);
        lblRuntimeDeviceAddress.setBackground(new java.awt.Color(255, 255, 255));
        lblRuntimeDeviceAddress.setFocusable(false);

        lblRuntimeDeviceName.setEditable(false);
        lblRuntimeDeviceName.setBackground(new java.awt.Color(255, 255, 255));
        lblRuntimeDeviceName.setFocusable(false);

        jButton3.setText("Add");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Device Created:");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDeviceAddress)
                    .addComponent(lblDeviceName)
                    .addComponent(jLabel4))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblRuntimeDeviceName, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(lblRuntimeDeviceAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceName)
                    .addComponent(lblRuntimeDeviceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceAddress)
                    .addComponent(lblRuntimeDeviceAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Device"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select A Device" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Select Device:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Password"));

        jLabel1.setText("Enter New Device PIN (6 Digit): ");

        jLabel2.setText("Confirm New Device PIN (6 Digit): ");

        jPasswordField1.setColumns(6);
        PlainDocument document = (PlainDocument) jPasswordField1.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 6) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
        jPasswordField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jPasswordField1CaretUpdate(evt);
            }
        });

        jPasswordField2.setColumns(6);
        PlainDocument document1 = (PlainDocument) jPasswordField2.getDocument();
        document1.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 6) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
        jPasswordField2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jPasswordField2CaretUpdate(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Place Holder");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPasswordField2)
                            .addComponent(jPasswordField1))
                        .addGap(6, 6, 6))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Modify This Folder?",
                "Confirm Folder Modification",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {
            Task task = new Task();
            task.setStatus("Adding");
            task.setO1(this);
            task.execute();
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    boolean validPass = false;
    private void jPasswordField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jPasswordField1CaretUpdate
        // TODO add your handling code here:

        if (jPasswordField1.getPassword().length < 6 && jPasswordField1.getPassword().length != 0) {
            validPass = false;
            jLabel3.setForeground(darkRed);
            jPasswordField2.setText("");
            jLabel3.setText("PIN Too Short");
            jPasswordField2.setEditable(false);
            jPasswordField2.setEnabled(false);
            jButton2.setEnabled(false);

            jPasswordField2.setFocusable(false);
        } else if (jPasswordField1.getPassword().length == 0) {
            validPass = false;
            jPasswordField2.setText("");
            jLabel3.setForeground(Color.WHITE);
            jLabel3.setText("Place Holder");
            jPasswordField2.setEditable(false);
            jPasswordField2.setEnabled(false);
            jPasswordField2.setFocusable(false);
            jButton2.setEnabled(false);

        } else if (jPasswordField1.getPassword().length == 6 && !Arrays.equals(jPasswordField1.getPassword(), jPasswordField2.getPassword())) {
            validPass = false;
            jButton2.setEnabled(false);

            jLabel3.setForeground(darkRed);
            jLabel3.setText("PIN's Do Not Match");
            jPasswordField2.setEditable(true);
            jPasswordField2.setEnabled(true);
            jPasswordField2.setFocusable(true);
        } else if (jPasswordField1.getPassword().length != 6 || !jPasswordField1.getPassword().equals(jPasswordField2.getPassword())) {
        } else {
            validPass = true;
            jButton2.setEnabled(true);

            jLabel3.setForeground(darkGreen);
            jLabel3.setText("PIN Confrimed");
        }

    }//GEN-LAST:event_jPasswordField1CaretUpdate

    private void jPasswordField2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jPasswordField2CaretUpdate
        // TODO add your handling code here:

        if (jPasswordField2.getPassword().length == 0 && jPasswordField1.getPassword().length == 0) {
            validPass = false;
            jButton2.setEnabled(false);

            jLabel3.setForeground(Color.WHITE);
            jLabel3.setText("Place Holder");
        } else if (jPasswordField2.getPassword().length <= 6 && !Arrays.equals(jPasswordField2.getPassword(), jPasswordField1.getPassword())) {
            validPass = false;
            jLabel3.setForeground(darkRed);
            jButton2.setEnabled(false);

            jLabel3.setText("PIN's Do Not Match");
        } else if (Arrays.equals(jPasswordField2.getPassword(), jPasswordField1.getPassword())) {
            validPass = true;
            jButton2.setEnabled(true);
            jLabel3.setForeground(darkGreen);
            jLabel3.setText("PIN Confrimed");
        }
    }//GEN-LAST:event_jPasswordField2CaretUpdate

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        if (jComboBox1.getSelectedIndex() == 0) {
            lblRuntimeDeviceName.setText(null);
            lblRuntimeDeviceAddress.setText(null);
            jTextField1.setText(null);
            jPasswordField1.setText("");
            jPasswordField2.setText("");
            jButton4.setEnabled(false);

        } else {
            jPasswordField1.setText("");
            jPasswordField2.setText("");
            jPasswordField1.setEnabled(true);
            jPasswordField1.setEditable(true);
            jPasswordField1.setFocusable(true);
            jButton4.setEnabled(true);

            lblRuntimeDeviceName.setText(deviceName.get(jComboBox1.getSelectedIndex() - 1));
            lblRuntimeDeviceAddress.setText(deviceAddress.get(jComboBox1.getSelectedIndex() - 1));
            jTextField1.setText(deviceCreated.get(jComboBox1.getSelectedIndex() - 1));
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Device_Add j1 = new Device_Add((Frame) this.getParent(), true, accountID);
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Device_Delete j1 = new Device_Delete((Frame) this.getParent(), true, accountID, deviceID);
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDeviceAddress;
    private javax.swing.JLabel lblDeviceName;
    private javax.swing.JTextField lblRuntimeDeviceAddress;
    private javax.swing.JTextField lblRuntimeDeviceName;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);

}
