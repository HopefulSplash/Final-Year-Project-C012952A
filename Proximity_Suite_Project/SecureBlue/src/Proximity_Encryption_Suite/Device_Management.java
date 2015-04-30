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
 * The Device_Management.Java Class implements an application that allows a user
 * to alter a devices details such as the pin that is connected to the device.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Management extends javax.swing.JDialog {

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
            //GUI setup
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            add_Button.setEnabled(false);
            delete_Button.setEnabled(false);
            pin_Field.setEnabled(false);
            confirm_Pin_Field.setEnabled(false);
            pin_Field.setFocusable(false);
            confirm_Pin_Field.setFocusable(false);
            progressBar.setValue(0);
            progressBar.setIndeterminate(true);

            //Initialize progress property.
            if ("Loading".equals(background_Status)) {

                /* Get bluetooth device details */
                get_Detail_ID();

                if (device_ID_List.isEmpty()) {
                } else {
                    for (int i = 0; i < device_ID_List.size(); i++) {

                        get_Device_Details(device_ID_List.get(i));

                        for (int u = 0; u < device_Name.size(); u++) {
                            device_ComboBox.addItem(device_Name.get(i));
                        }
                    }
                }

            }

            if ("Adding".equals(background_Status)) {

                if (validPass == true) {

                    update_Device();

                }

            }

            return null;
        }

        /**
         * a method that will get the relevant device id's.
         */
        private void get_Detail_ID() {
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

                        device_ID_List.add(device);

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
                }

            }

        }

        /**
         * a method that will get the device details
         *
         * @param deviceID
         */
        private void get_Device_Details(int deviceID) {

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

                        device_Name.add(name);
                        device_Address.add(address);
                        device_Created.add(created);
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

        }

        /**
         * a method that will update the device details.
         */
        private void update_Device() {

            String passwordSha1 = null;

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

                String sql = "UPDATE device_details SET device_Password = ? WHERE device_Details_ID = ?;";

                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(2, device_ID_List.get(device_ComboBox.getSelectedIndex() - 1));
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
            add_Button.setEnabled(true);

            if ("Loading".equals(background_Status)) {
                //setup GUI
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);
                accept_Button.setEnabled(false);

            }
            if ("Adding".equals(background_Status)) {
                //setup GUI
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);
                pin_Field.setText("");
                confirm_Pin_Field.setText("");
                //error message
                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) background_Object,
                        "Device Modified Successfully.",
                        "Device Modification Successful!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);
                //closes form.
                cancel_Button.doClick();

            }
        }
    }

    /**
     * a method that will create a new Device Management Form.
     *
     * @param parent
     * @param modal
     * @param account_ID
     * @param deviceID
     */
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

        //setup variables
        this.accountID = account_ID;
        this.deviceID = deviceID;
        //setup GUI
        cancel_Button.requestFocus();
        //start new task for loading
        Task task = new Task();
        task.setBackground_Status("Loading");
        task.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        accept_Button = new javax.swing.JButton();
        device_Details_Panel = new javax.swing.JPanel();
        address_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        address_Field = new javax.swing.JTextField();
        name_Field = new javax.swing.JTextField();
        add_Button = new javax.swing.JButton();
        delete_Button = new javax.swing.JButton();
        created_Button = new javax.swing.JLabel();
        created_Field = new javax.swing.JTextField();
        select_Panel = new javax.swing.JPanel();
        device_ComboBox = new javax.swing.JComboBox();
        device_Label = new javax.swing.JLabel();
        device_Password_Panel = new javax.swing.JPanel();
        pin_Label = new javax.swing.JLabel();
        confirm_Label = new javax.swing.JLabel();
        pin_Field = new javax.swing.JPasswordField();
        confirm_Pin_Field = new javax.swing.JPasswordField();
        strength_Label = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Device Modify");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        device_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Details"));

        address_Label.setText("Bluetooth Device Address:");

        name_Label.setText("Bluetooth Device Name:");

        address_Field.setEditable(false);
        address_Field.setBackground(new java.awt.Color(255, 255, 255));
        address_Field.setFocusable(false);

        name_Field.setEditable(false);
        name_Field.setBackground(new java.awt.Color(255, 255, 255));
        name_Field.setFocusable(false);

        add_Button.setText("Add");
        add_Button.setFocusPainted(false);
        add_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_ButtonActionPerformed(evt);
            }
        });

        delete_Button.setText("Delete");
        delete_Button.setFocusPainted(false);
        delete_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_ButtonActionPerformed(evt);
            }
        });

        created_Button.setText("Device Created:");

        created_Field.setEditable(false);
        created_Field.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout device_Details_PanelLayout = new javax.swing.GroupLayout(device_Details_Panel);
        device_Details_Panel.setLayout(device_Details_PanelLayout);
        device_Details_PanelLayout.setHorizontalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(address_Label)
                    .addComponent(name_Label)
                    .addComponent(created_Button))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(device_Details_PanelLayout.createSequentialGroup()
                        .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(name_Field, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(address_Field))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(created_Field))
                .addGap(6, 6, 6))
        );
        device_Details_PanelLayout.setVerticalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_Button))
                .addGap(3, 3, 3)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address_Label)
                    .addComponent(address_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_Button))
                .addGap(3, 3, 3)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(created_Button)
                    .addComponent(created_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        select_Panel.setBackground(new java.awt.Color(255, 255, 255));
        select_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Device"));

        device_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select A Device" }));
        device_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_ComboBoxActionPerformed(evt);
            }
        });

        device_Label.setText("Select Device:");

        javax.swing.GroupLayout select_PanelLayout = new javax.swing.GroupLayout(select_Panel);
        select_Panel.setLayout(select_PanelLayout);
        select_PanelLayout.setHorizontalGroup(
            select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(select_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(device_Label)
                .addGap(6, 6, 6)
                .addComponent(device_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        select_PanelLayout.setVerticalGroup(
            select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(select_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(device_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(device_Label))
                .addGap(6, 6, 6))
        );

        device_Password_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Password_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Password"));

        pin_Label.setText("Enter New Device PIN (6 Digit): ");

        confirm_Label.setText("Confirm New Device PIN (6 Digit): ");

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

        javax.swing.GroupLayout device_Password_PanelLayout = new javax.swing.GroupLayout(device_Password_Panel);
        device_Password_Panel.setLayout(device_Password_PanelLayout);
        device_Password_PanelLayout.setHorizontalGroup(
            device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Password_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confirm_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pin_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                    .addComponent(confirm_Pin_Field)
                    .addComponent(pin_Field))
                .addGap(6, 6, 6))
        );
        device_Password_PanelLayout.setVerticalGroup(
            device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Password_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pin_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pin_Label))
                .addGap(6, 6, 6)
                .addGroup(device_Password_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirm_Pin_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirm_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(strength_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(device_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(select_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(device_Password_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(select_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(device_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(device_Password_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
     * a method that will start the modification process.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Modify This Device?",
                "Confirm Device Modification",
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
     * a method that will check the pin is valid
     * 
     * @param evt 
     */
    private void confirm_Pin_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_confirm_Pin_FieldCaretUpdate
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
    /**
     * a method that will change the data displayed depending on which device is selected.
     * 
     * @param evt 
     */
    private void device_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_ComboBoxActionPerformed

        if (device_ComboBox.getSelectedIndex() == 0) {
            name_Field.setText(null);
            address_Field.setText(null);
            created_Field.setText(null);
            pin_Field.setText("");
            confirm_Pin_Field.setText("");
            delete_Button.setEnabled(false);

        } else {
            pin_Field.setText("");
            confirm_Pin_Field.setText("");
            pin_Field.setEnabled(true);
            pin_Field.setEditable(true);
            pin_Field.setFocusable(true);
            delete_Button.setEnabled(true);

            name_Field.setText(device_Name.get(device_ComboBox.getSelectedIndex() - 1));
            address_Field.setText(device_Address.get(device_ComboBox.getSelectedIndex() - 1));
            created_Field.setText(device_Created.get(device_ComboBox.getSelectedIndex() - 1));
        }
    }//GEN-LAST:event_device_ComboBoxActionPerformed
    /**
     * a method that will open the Device_add form
     * 
     * @param evt 
     */
    private void add_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_ButtonActionPerformed
        Device_Add j1 = new Device_Add((Frame) this.getParent(), true, accountID);
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_add_ButtonActionPerformed
    /**
     * a method that will open the Device_Delete form
     * 
     * @param evt 
     */
    private void delete_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_ButtonActionPerformed
        // TODO add your handling code here:
        Device_Delete j1 = new Device_Delete((Frame) this.getParent(), true, accountID, deviceID);
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_delete_ButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JButton add_Button;
    private javax.swing.JTextField address_Field;
    private javax.swing.JLabel address_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JLabel confirm_Label;
    private javax.swing.JPasswordField confirm_Pin_Field;
    private javax.swing.JLabel created_Button;
    private javax.swing.JTextField created_Field;
    private javax.swing.JButton delete_Button;
    private javax.swing.JComboBox device_ComboBox;
    private javax.swing.JPanel device_Details_Panel;
    private javax.swing.JLabel device_Label;
    private javax.swing.JPanel device_Password_Panel;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JPasswordField pin_Field;
    private javax.swing.JLabel pin_Label;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JPanel select_Panel;
    private javax.swing.JLabel strength_Label;
    // End of variables declaration//GEN-END:variables
    private final Color darkGreen = new Color(0x006400);
    private final Color darkRed = new Color(0x640000);
    private final int accountID;
    private final int deviceID;
    boolean validPass = false;
    private final ArrayList<Integer> device_ID_List = new ArrayList<>();
    private final ArrayList<String> device_Name = new ArrayList<>();
    private final ArrayList<String> device_Address = new ArrayList<>();
    private final ArrayList<String> device_Created = new ArrayList<>();
}
