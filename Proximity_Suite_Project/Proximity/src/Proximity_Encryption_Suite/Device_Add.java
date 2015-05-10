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
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

/**
 * The Device_Add.Java Class implements an application that allows a users to
 * link a mobile device to their account.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Add extends javax.swing.JDialog {

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        //defining the variables for use.
        private int background_Counter = 0;
        private boolean not_Taken = false;
        private final boolean add_Device = false;
        private String background_Status;
        private Object background_Object;

        /**
         * a method that will return the counter.
         *
         * @return
         */
        public int getBackground_Counter() {
            return background_Counter;
        }

        /**
         * a method that will set the counter
         *
         * @param background_Counter
         */
        public void setBackground_Counter(int background_Counter) {
            this.background_Counter = background_Counter;
        }

        /**
         * a method that will get the status
         *
         * @return
         */
        public String getBackground_Status() {
            return background_Status;
        }

        /**
         * a method that will set the status
         *
         * @param background_Status
         */
        public void setBackground_Status(String background_Status) {
            this.background_Status = background_Status;
        }

        /**
         * a method that will set the object.
         *
         * @param background_Object
         */
        public void setBackground_Object(Object background_Object) {
            this.background_Object = background_Object;
        }

        @Override
        public Void doInBackground() {
            // setup GUI
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            device_Scan_Button.setEnabled(false);
            pin_Field.setEnabled(false);
            confirm_Pin_Field.setEnabled(false);
            bluetooth_Devices_List.setFocusable(false);
            pin_Field.setFocusable(false);
            confirm_Pin_Field.setFocusable(false);
            device_ProgressBar.setValue(0);
            device_ProgressBar.setIndeterminate(true);

            //Initialize progress property.
            if ("Loading".equals(background_Status)) {

                int device_Position = 0;
                bluetooth_Devices_List.setModel(default_Model);
                /* Create an object of Device_Service */
                Device_Service ss = new Device_Service();
                /* Get bluetooth device details */
                map_Return_Result = ss.getBluetoothDevices();

                /* Add devices in JList */
                for (Map.Entry<String, List<String>> entry : map_Return_Result.entrySet()) {
                    default_Model.addElement(entry.getValue().get(0));
                    map_Device_Position.put(device_Position, entry.getValue());
                    device_Position++;
                }
            }

            if ("Adding".equals(background_Status)) {
                // check if the password is valid
                if (validPass == true) {
                    not_Taken = check_Taken(address_Field.getText());
                    // check if the device is taken
                    if (not_Taken == false) {
                        add_Device(name_Field.getText());

                    } else {
                        Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                        JOptionPane.showMessageDialog((Component) background_Object,
                                "Device Already Link To An Account. Please Try Again.",
                                "Device Linking Error!",
                                JOptionPane.INFORMATION_MESSAGE,
                                crossIcon);
                    }
                }
            }
            return null;
        }

        /**
         * a method that will check if the device is linked to another account.
         *
         * @param deviceAddress
         * @return
         */
        public boolean check_Taken(String deviceAddress) {
            // define variable.
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
            return isTaken;
        }

        /**
         * a method that will add the device to the database.
         *
         * @param deviceName
         */
        private void add_Device(String deviceName) {
            // define variables
            String passwordSha1 = null;
            int tempID = 0;

            /*
             * creates the SHA1 hash of the password the user has entered.
             */
            try {
                String strPassword = new String(pin_Field.getPassword());
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
            try {

                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                String sql = "INSERT INTO Device_Details VALUES (NULL, ? , ? , ? , DEFAULT);";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, name_Field.getText());
                pStmt.setString(2, address_Field.getText());
                pStmt.setString(3, passwordSha1);
                pStmt.executeUpdate();

                sql = "SELECT device_Details_ID FROM device_Details WHERE device_Name = ? ;";

                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, name_Field.getText());

                ResultSet rs = pStmt.executeQuery();
                while (rs.next()) {

                    tempID = rs.getInt("device_Details_ID");

                }
                //add to device list
                sql = "INSERT INTO account_device_list VALUES (NULL, ? , ? );";

                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, accountID);
                pStmt.setInt(2, tempID);
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

            if ("Loading".equals(background_Status)) {
                //setup GUI
                device_ProgressBar.setValue(0);
                device_ProgressBar.setIndeterminate(false);
                device_Scan_Button.setEnabled(true);
                bluetooth_Devices_List.setFocusable(true);
                bluetooth_Devices_List.setSelectedIndex(0);
                cancel_Button.setEnabled(true);
                accept_Button.setEnabled(false);
            }
            if ("Adding".equals(background_Status)) {
                //setup GUI
                device_ProgressBar.setValue(0);
                device_ProgressBar.setIndeterminate(false);
                device_Scan_Button.setEnabled(true);
                bluetooth_Devices_List.setFocusable(true);
                bluetooth_Devices_List.setSelectedIndex(0);
                cancel_Button.setEnabled(true);
                accept_Button.setEnabled(false);

                if (not_Taken == false) {
                    Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                    JOptionPane.showMessageDialog((Component) background_Object,
                            "Device Linking Successfully.",
                            "Device Linking Successful!",
                            JOptionPane.INFORMATION_MESSAGE,
                            tickIcon);
                    //close application
                    cancel_Button.doClick();
                } else {
                    //setup GUI
                    pin_Field.setText("");
                    confirm_Pin_Field.setText("");
                    pin_Field.setEnabled(true);
                    pin_Field.setEditable(true);
                    pin_Field.setFocusable(true);
                }
            }
        }
    }

    /**
     * a method that will create a new form of Device_Add
     *
     * @param parent
     * @param modal
     * @param account_ID
     */
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
        //setup account id
        this.accountID = account_ID;
        //GUI setup
        cancel_Button.requestFocus();
        default_Model = new DefaultListModel();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (cancel_Button.isEnabled()) {
                    e.getWindow().dispose();
                } else {
                    Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                    JOptionPane.showMessageDialog((Component) e.getWindow(),
                            "Wating For Scan To Finish. Please Wait.",
                            "Device Linking!",
                            JOptionPane.INFORMATION_MESSAGE,
                            crossIcon);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        accept_Button = new javax.swing.JButton();
        device_Info_Panel = new javax.swing.JPanel();
        address_Label = new javax.swing.JLabel();
        services_Label = new javax.swing.JLabel();
        services_ScrollPane = new javax.swing.JScrollPane();
        services_Area = new javax.swing.JTextArea();
        name_Label = new javax.swing.JLabel();
        address_Field = new javax.swing.JTextField();
        name_Field = new javax.swing.JTextField();
        device_Panel = new javax.swing.JPanel();
        bluetooth_Device_ScrollPane = new javax.swing.JScrollPane();
        bluetooth_Devices_List = new javax.swing.JList();
        device_ProgressBar = new javax.swing.JProgressBar();
        device_Scan_Button = new javax.swing.JButton();
        device_Details_Panel = new javax.swing.JPanel();
        pin_Label = new javax.swing.JLabel();
        confirm_Pin_Label = new javax.swing.JLabel();
        pin_Field = new javax.swing.JPasswordField();
        confirm_Pin_Field = new javax.swing.JPasswordField();
        strength_Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Proximity Suite | Device Add");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
                .addContainerGap(230, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(accept_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        device_Info_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Info_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Details"));

        address_Label.setText("Bluetooth Device Address");

        services_Label.setText("Service Details");

        services_Area.setEditable(false);
        services_Area.setColumns(20);
        services_Area.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        services_Area.setRows(5);
        services_Area.setFocusable(false);
        services_ScrollPane.setViewportView(services_Area);

        name_Label.setText("Bluetooth Device Name");

        address_Field.setEditable(false);
        address_Field.setBackground(new java.awt.Color(255, 255, 255));
        address_Field.setFocusable(false);

        name_Field.setEditable(false);
        name_Field.setBackground(new java.awt.Color(255, 255, 255));
        name_Field.setFocusable(false);

        javax.swing.GroupLayout device_Info_PanelLayout = new javax.swing.GroupLayout(device_Info_Panel);
        device_Info_Panel.setLayout(device_Info_PanelLayout);
        device_Info_PanelLayout.setHorizontalGroup(
            device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Info_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(services_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addGroup(device_Info_PanelLayout.createSequentialGroup()
                        .addComponent(services_Label)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(device_Info_PanelLayout.createSequentialGroup()
                        .addGroup(device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address_Label)
                            .addComponent(name_Label))
                        .addGap(6, 6, 6)
                        .addGroup(device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name_Field)
                            .addComponent(address_Field))))
                .addGap(6, 6, 6))
        );
        device_Info_PanelLayout.setVerticalGroup(
            device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Info_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(device_Info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address_Label)
                    .addComponent(address_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(services_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(services_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        device_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Device"));

        bluetooth_Devices_List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bluetooth_Device_ScrollPane.setViewportView(bluetooth_Devices_List);
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if(bluetooth_Devices_List.getSelectedIndex()>-1){
                    /* Get bluetooth device details from temporary list */
                    List<String> tmpDeviceDetails = map_Device_Position.get(bluetooth_Devices_List.getSelectedIndex());
                    /* Set bluetooth device name */
                    name_Field.setText(tmpDeviceDetails.get(0));
                    /* Set bluetooth device Address */
                    address_Field.setText(tmpDeviceDetails.get(1));

                    if (tmpDeviceDetails.size() > 2 && tmpDeviceDetails.get(2) != null) {
                        /* Set bluetooth device service name and URL */
                        services_Area.setText(tmpDeviceDetails.get(2));
                    } else {
                        services_Area.setText("Service not found");
                    }
                }

                pin_Field.setText("");
                confirm_Pin_Field.setText("");
                pin_Field.setEnabled(true);
                pin_Field.setEditable(true);
                pin_Field.setFocusable(true);

            }
        };
        bluetooth_Devices_List.addListSelectionListener(listSelectionListener);

        device_Scan_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Refresh.png"))); // NOI18N
        device_Scan_Button.setFocusPainted(false);
        device_Scan_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_Scan_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout device_PanelLayout = new javax.swing.GroupLayout(device_Panel);
        device_Panel.setLayout(device_PanelLayout);
        device_PanelLayout.setHorizontalGroup(
            device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bluetooth_Device_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(device_ProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(device_Scan_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        device_PanelLayout.setVerticalGroup(
            device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_PanelLayout.createSequentialGroup()
                .addComponent(bluetooth_Device_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(device_ProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(device_Scan_Button)
                .addGap(6, 6, 6))
        );

        device_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Password"));

        pin_Label.setText("Enter Device PIN (Length 6): ");

        confirm_Pin_Label.setText("Confirm Device PIN (Length 6): ");

        pin_Field.setColumns(6);
        PlainDocument document = (PlainDocument) pin_Field.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 6) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
        pin_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                pin_FieldCaretUpdate(evt);
            }
        });

        confirm_Pin_Field.setColumns(6);
        PlainDocument document1 = (PlainDocument) confirm_Pin_Field.getDocument();
        document1.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 6) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
        confirm_Pin_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                confirm_Pin_FieldCaretUpdate(evt);
            }
        });

        strength_Label.setForeground(new java.awt.Color(255, 255, 255));
        strength_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        strength_Label.setText("Place Holder");

        javax.swing.GroupLayout device_Details_PanelLayout = new javax.swing.GroupLayout(device_Details_Panel);
        device_Details_Panel.setLayout(device_Details_PanelLayout);
        device_Details_PanelLayout.setHorizontalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confirm_Pin_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pin_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pin_Field)
                    .addComponent(confirm_Pin_Field))
                .addGap(6, 6, 6))
        );
        device_Details_PanelLayout.setVerticalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pin_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pin_Label))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_Pin_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirm_Pin_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(device_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(device_Info_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(device_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(device_Info_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(device_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(device_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * a method that will start searching for devices when the window opens.
     *
     * @param evt
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //creates a new task to search for devices
        Task task = new Task();
        task.setBackground_Status("Loading");
        task.setBackground_Counter(0);
        task.setBackground_Object(this);
        task.execute();

    }//GEN-LAST:event_formWindowOpened
    /**
     * closes the form.
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will start the process of adding a device to the account.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed

        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Add This Device?",
                "Confirm Device Linking",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {
            Task task = new Task();
            task.setBackground_Status("Adding");
            task.setBackground_Object(this);
            task.execute();
        }
    }//GEN-LAST:event_accept_ButtonActionPerformed

    private void device_Scan_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_Scan_ButtonActionPerformed
        //GUI setup
        default_Model.clear();
        map_Return_Result.clear();
        map_Device_Position.clear();
        services_Area.setText(null);
        address_Field.setText(null);
        name_Field.setText(null);
        pin_Field.setText("");
        confirm_Pin_Field.setText("");
        pin_Field.setFocusable(false);
        confirm_Pin_Field.setFocusable(false);
        // starts a new task to scan for devices
        Task task = new Task();
        task.setBackground_Status("Loading");
        task.setBackground_Object(this);
        task.setBackground_Counter(0);
        task.execute();


    }//GEN-LAST:event_device_Scan_ButtonActionPerformed
    /**
     * a method that will check if the pin is valid
     *
     * @param evt
     */
    private void pin_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_pin_FieldCaretUpdate

        if (pin_Field.getPassword().length < 6 && pin_Field.getPassword().length != 0) {
            validPass = false;
            strength_Label.setForeground(darkRed);
            confirm_Pin_Field.setText("");
            strength_Label.setText("PIN Too Short");
            confirm_Pin_Field.setEditable(false);
            confirm_Pin_Field.setEnabled(false);
            accept_Button.setEnabled(false);

            confirm_Pin_Field.setFocusable(false);
        } else if (pin_Field.getPassword().length == 0) {
            validPass = false;
            confirm_Pin_Field.setText("");
            strength_Label.setForeground(Color.WHITE);
            strength_Label.setText("Place Holder");
            confirm_Pin_Field.setEditable(false);
            confirm_Pin_Field.setEnabled(false);
            confirm_Pin_Field.setFocusable(false);
            accept_Button.setEnabled(false);

        } else if (pin_Field.getPassword().length == 6 && !Arrays.equals(pin_Field.getPassword(), confirm_Pin_Field.getPassword())) {
            validPass = false;
            accept_Button.setEnabled(false);

            strength_Label.setForeground(darkRed);
            strength_Label.setText("PIN's Do Not Match");
            confirm_Pin_Field.setEditable(true);
            confirm_Pin_Field.setEnabled(true);
            confirm_Pin_Field.setFocusable(true);
        } else if (pin_Field.getPassword().length != 6 || !pin_Field.getPassword().equals(confirm_Pin_Field.getPassword())) {
        } else {
            validPass = true;
            accept_Button.setEnabled(true);

            strength_Label.setForeground(darkGreen);
            strength_Label.setText("PIN Confrimed");
        }

    }//GEN-LAST:event_pin_FieldCaretUpdate
    /**
     * a method that will check if the pin is valid
     * 
     * @param evt 
     */
    private void confirm_Pin_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Pin_FieldCaretUpdate
        // TODO add your handling code here:

        if (confirm_Pin_Field.getPassword().length == 0 && pin_Field.getPassword().length == 0) {
            validPass = false;
            accept_Button.setEnabled(false);

            strength_Label.setForeground(Color.WHITE);
            strength_Label.setText("Place Holder");
        } else if (confirm_Pin_Field.getPassword().length <= 6 && !Arrays.equals(confirm_Pin_Field.getPassword(), pin_Field.getPassword())) {
            validPass = false;
            strength_Label.setForeground(darkRed);
            accept_Button.setEnabled(false);

            strength_Label.setText("PIN's Do Not Match");
        } else if (Arrays.equals(confirm_Pin_Field.getPassword(), pin_Field.getPassword())) {
            validPass = true;
            accept_Button.setEnabled(true);
            strength_Label.setForeground(darkGreen);
            strength_Label.setText("PIN Confrimed");
        }
    }//GEN-LAST:event_confirm_Pin_FieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JTextField address_Field;
    private javax.swing.JLabel address_Label;
    private javax.swing.JScrollPane bluetooth_Device_ScrollPane;
    private javax.swing.JList bluetooth_Devices_List;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JPasswordField confirm_Pin_Field;
    private javax.swing.JLabel confirm_Pin_Label;
    private javax.swing.JPanel device_Details_Panel;
    private javax.swing.JPanel device_Info_Panel;
    private javax.swing.JPanel device_Panel;
    private javax.swing.JProgressBar device_ProgressBar;
    private javax.swing.JButton device_Scan_Button;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JPasswordField pin_Field;
    private javax.swing.JLabel pin_Label;
    private javax.swing.JTextArea services_Area;
    private javax.swing.JLabel services_Label;
    private javax.swing.JScrollPane services_ScrollPane;
    private javax.swing.JLabel strength_Label;
    // End of variables declaration//GEN-END:variables
    private final Color darkGreen = new Color(0x006400);
    private final Color darkRed = new Color(0x640000);
    private final DefaultListModel default_Model;
    private Map<String, List<String>> map_Return_Result = new HashMap<>();
    private final Map<Integer, List<String>> map_Device_Position = new HashMap<>();
    private final int accountID;
    private boolean validPass = false;

}
