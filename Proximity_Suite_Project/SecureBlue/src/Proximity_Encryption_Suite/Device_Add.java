package Proximity_Encryption_Suite;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class Device_Add extends javax.swing.JDialog {

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

            jButton1.setEnabled(false);
            jButton3.setEnabled(false);
            jPasswordField1.setEnabled(false);
            jPasswordField2.setEnabled(false);
            JListBluetoothDevices.setFocusable(false);
            jPasswordField1.setFocusable(false);
            jPasswordField2.setFocusable(false);

            progressBar.setValue(0);
            progressBar.setIndeterminate(true);

            //Initialize progress property.
            if ("Loading".equals(status)) {

                int intDevicePosition = 0;
                JListBluetoothDevices.setModel(defaultModel);

                /* Create an object of ServicesSearch */
                ServicesSearch ss = new ServicesSearch();
                /* Get bluetooth device details */
                mapReturnResult = ss.getBluetoothDevices();


                /* Add devices in JList */
                for (Map.Entry<String, List<String>> entry : mapReturnResult.entrySet()) {
                    defaultModel.addElement(entry.getValue().get(0));
                    mapDevicePosition.put(intDevicePosition, entry.getValue());
                    intDevicePosition++;
                }

            }

            if ("Adding".equals(status)) {

                if (validPass == true) {
                    notTaken = checkifTaken(lblRuntimeDeviceAddress.getText());

                    if (notTaken == false) {
                        addDevice(lblRuntimeDeviceName.getText());

                    } else {
                        Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                        JOptionPane.showMessageDialog((Component) o1,
                                "Device Already Taken.",
                                "File Addirion Successful!",
                                JOptionPane.INFORMATION_MESSAGE,
                                crossIcon);
                    }
                }

            }

            return null;
        }

        private boolean checkifTaken(String deviceAddress) {

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
                String sql = "SELECT device_Address FROM device_Details";

                PreparedStatement getFolderID = conn.prepareStatement(sql);

                try (ResultSet rs = getFolderID.executeQuery()) {
                    while (rs.next()) {
                        String device = rs.getString("device_Address");

                        if (device.equals(deviceAddress)) {
                            isTaken = true;
                        }

                    }
                }
                conn.close();
                stmt.close();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                //finally block used to close resources
                try {
                    if (stmt != null) {
                        conn.close();
                        stmt.close();
                    }
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (conn != null) {
                        conn.close();
                        stmt.close();
                    }
                } catch (SQLException se) {
                }

            }

            return isTaken;
        }

        private void addDevice(String deviceName) {

            String passwordSha1 = null;
            int tempID = 0;

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

            /*
             * declares the variables for use in connecting and checking the database.
             */
            Connection conn = null;
            Statement stmt = null;
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO Device_Details VALUES (NULL, ? , ? , ? , DEFAULT);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, lblRuntimeDeviceName.getText());
                pStmt.setString(2, lblRuntimeDeviceAddress.getText());
                pStmt.setString(3, passwordSha1);
                pStmt.executeUpdate();

                pStmt.close();

                sql = "SELECT device_Details_ID FROM device_Details WHERE device_Name = ? ;";

                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, lblRuntimeDeviceName.getText());

                ResultSet rs = pStmt.executeQuery();
                while (rs.next()) {

                    tempID = rs.getInt("device_Details_ID");

                }
                pStmt.close();

                //add to device list
                sql = "INSERT INTO account_device_list VALUES (NULL, ? , ? );";

                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, accountID);
                pStmt.setInt(2, tempID);
                pStmt.executeUpdate();

                pStmt.close();
                conn.close();

            } catch (SQLException | ClassNotFoundException se) {
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                        stmt.close();
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

            if ("Loading".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);
                jButton3.setEnabled(true);

                JListBluetoothDevices.setFocusable(true);
                JListBluetoothDevices.setSelectedIndex(0);

                jButton1.setEnabled(true);
                jButton2.setEnabled(false);

            }
            if ("Adding".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);
                jButton3.setEnabled(true);

                JListBluetoothDevices.setFocusable(true);
                JListBluetoothDevices.setSelectedIndex(0);

                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
                if (notTaken == false) {
                    Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                    JOptionPane.showMessageDialog((Component) o1,
                            "Device Add Successfully.",
                            "File Addirion Successful!",
                            JOptionPane.INFORMATION_MESSAGE,
                            tickIcon);
                    jButton1.doClick();
                } else {
                    jPasswordField1.setText("");
                    jPasswordField2.setText("");
                    jPasswordField1.setEnabled(true);
                    jPasswordField1.setEditable(true);
                    jPasswordField1.setFocusable(true);
                }
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {

    }

    /* DefaultListModel to attach it with JList */
    private DefaultListModel defaultModel;
    /* Map to get device details list */
    private Map<String, List<String>> mapReturnResult = new HashMap<String, List<String>>();
    /* Map to identify device on user click of JList */
    private Map<Integer, List<String>> mapDevicePosition = new HashMap<Integer, List<String>>();

    private int accountID;

    public Device_Add(java.awt.Frame parent, boolean modal, int account_ID) {
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
        jButton1.requestFocus();
        defaultModel = new DefaultListModel();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (jButton1.isEnabled()) {
                    e.getWindow().dispose();
                } else {
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog((Component) e.getWindow(),
                            "Wating for search to finish",
                            "Account Creation Error!",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblDeviceAddress = new javax.swing.JLabel();
        lblServiceDetails = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTextAreaServiceDetails = new javax.swing.JTextArea();
        lblDeviceName = new javax.swing.JLabel();
        lblRuntimeDeviceAddress = new javax.swing.JTextField();
        lblRuntimeDeviceName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JListBluetoothDevices = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Bluecove Bluetooth Discovery");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
                .addContainerGap(230, Short.MAX_VALUE)
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

        lblDeviceAddress.setText("Bluetooth Device Address");

        lblServiceDetails.setText("Service Details");

        JTextAreaServiceDetails.setEditable(false);
        JTextAreaServiceDetails.setColumns(20);
        JTextAreaServiceDetails.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        JTextAreaServiceDetails.setRows(5);
        JTextAreaServiceDetails.setFocusable(false);
        jScrollPane2.setViewportView(JTextAreaServiceDetails);

        lblDeviceName.setText("Bluetooth Device Name");

        lblRuntimeDeviceAddress.setEditable(false);
        lblRuntimeDeviceAddress.setBackground(new java.awt.Color(255, 255, 255));
        lblRuntimeDeviceAddress.setFocusable(false);

        lblRuntimeDeviceName.setEditable(false);
        lblRuntimeDeviceName.setBackground(new java.awt.Color(255, 255, 255));
        lblRuntimeDeviceName.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblServiceDetails)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDeviceAddress)
                            .addComponent(lblDeviceName))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRuntimeDeviceName)
                            .addComponent(lblRuntimeDeviceAddress))))
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceName)
                    .addComponent(lblRuntimeDeviceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceAddress)
                    .addComponent(lblRuntimeDeviceAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(lblServiceDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Device"));

        JListBluetoothDevices.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JListBluetoothDevices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListBluetoothDevicesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JListBluetoothDevices);
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if(JListBluetoothDevices.getSelectedIndex()>-1){
                    /* Get bluetooth device details from temporary list */
                    List<String> tmpDeviceDetails = mapDevicePosition.get(JListBluetoothDevices.getSelectedIndex());
                    /* Set bluetooth device name */
                    lblRuntimeDeviceName.setText(tmpDeviceDetails.get(0));
                    /* Set bluetooth device Address */
                    lblRuntimeDeviceAddress.setText(tmpDeviceDetails.get(1));

                    if (tmpDeviceDetails.size() > 2 && tmpDeviceDetails.get(2) != null) {
                        /* Set bluetooth device service name and URL */
                        JTextAreaServiceDetails.setText(tmpDeviceDetails.get(2));
                    } else {
                        JTextAreaServiceDetails.setText("Service not found");
                    }
                }

                jPasswordField1.setText("");
                jPasswordField2.setText("");
                jPasswordField1.setEnabled(true);
                jPasswordField1.setEditable(true);
                jPasswordField1.setFocusable(true);

            }
        };
        JListBluetoothDevices.addListSelectionListener(listSelectionListener);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Refresh.png"))); // NOI18N
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(6, 6, 6))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Password"));

        jLabel1.setText("Enter Device PIN (Length 6): ");

        jLabel2.setText("Confirm Device PIN (Length 6): ");

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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField1)
                    .addComponent(jPasswordField2))
                .addGap(6, 6, 6))
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
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* Search for bluetooth device when window opened */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        Task task = new Task();
        task.setStatus("Loading");
        task.setCounter(0);
        task.setO1(this);
        task.execute();

    }//GEN-LAST:event_formWindowOpened

    /* On click of any item in List Box */
    private void JListBluetoothDevicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListBluetoothDevicesMouseClicked


    }//GEN-LAST:event_JListBluetoothDevicesMouseClicked

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        defaultModel.clear();
        mapReturnResult.clear();
        mapDevicePosition.clear();
        JTextAreaServiceDetails.setText(null);
        lblRuntimeDeviceAddress.setText(null);
        lblRuntimeDeviceName.setText(null);
        jPasswordField1.setText("");
        jPasswordField2.setText("");
        jPasswordField1.setFocusable(false);
        jPasswordField2.setFocusable(false);
        Task task = new Task();
        task.setStatus("Loading");
        task.setO1(this);
        task.setCounter(0);
        task.execute();


    }//GEN-LAST:event_jButton3ActionPerformed
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList JListBluetoothDevices;
    private javax.swing.JTextArea JTextAreaServiceDetails;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDeviceAddress;
    private javax.swing.JLabel lblDeviceName;
    private javax.swing.JTextField lblRuntimeDeviceAddress;
    private javax.swing.JTextField lblRuntimeDeviceName;
    private javax.swing.JLabel lblServiceDetails;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);

}
