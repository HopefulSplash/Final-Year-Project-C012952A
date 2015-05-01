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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javax.swing.SwingWorker;

/**
 * The Device_Delete.Java Class implements an application that allows a users to
 * delete a device from the account.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Delete extends javax.swing.JDialog {

    //a swingwoker to do work in the background of the application.
    class Task extends SwingWorker<Void, Void> {

        private int background_Counter = 0;
        private String background_Status;
        private Object background_Objecy;

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
        public void setBackground_Objecy(Object background_Objecy) {
            this.background_Objecy = background_Objecy;
        }

        @Override
        public Void doInBackground() {
            //setup GUI
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            accept_Button.setEnabled(false);
            progressBar.setValue(0);
            progressBar.setIndeterminate(true);

            if ("Loading".equals(background_Status)) {

                /* Get bluetooth device details */
                get_Device_ID();

                if (device_ID_List.isEmpty()) {
                    //print error message
                } else {
                    for (int i = 0; i < device_ID_List.size(); i++) {

                        get_Device_Details(device_ID_List.get(i));

                        for (int u = 0; u < device_Name.size(); u++) {
                            device_ComboBox.addItem(device_Name.get(i));
                        }
                    }
                }

            }

            if ("Update".equals(background_Status)) {
                //remove the device from the account
                removeDevice();

            }

            return null;
        }

        /**
         * a method that will get the relevant device id's.
         */
        private void get_Device_ID() {
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
         * a method that will remove the device from the account.
         */
        private void removeDevice() {
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

                String sql = "DELETE FROM account_device_list WHERE device_Details_ID = ?;";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, device_ID_List.get(device_ComboBox.getSelectedIndex() - 1));
                pStmt.executeUpdate();

                sql = "DELETE FROM device_Details WHERE device_Details_ID = ?;";
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, device_ID_List.get(device_ComboBox.getSelectedIndex() - 1));
                pStmt.executeUpdate();

                pStmt.close();
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

        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor

            if ("Loading".equals(background_Status)) {
                //GUI setup
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);
                accept_Button.setEnabled(false);

            }
            if ("Update".equals(background_Status)) {
                //GUI setup
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);

                //confirmation of delete
                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) background_Objecy,
                        "Device Removed Successfully.",
                        "Device Removed Successful!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);
                //application closes.
                cancel_Button.doClick();

            }
        }
    }

    /**
     * a method that will create a new Device_Delete form
     *
     * @param parent
     * @param modal
     * @param account_ID
     * @param deviceID
     */
    public Device_Delete(java.awt.Frame parent, boolean modal, int account_ID, int deviceID) {
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
        //GUI setup
        cancel_Button.requestFocus();
        //start new task for loading devices
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
        device_Panel = new javax.swing.JPanel();
        address_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        address_Field = new javax.swing.JTextField();
        name_Field = new javax.swing.JTextField();
        created_Label = new javax.swing.JLabel();
        created_Field = new javax.swing.JTextField();
        progressBar = new javax.swing.JProgressBar();
        device_Select_Panel = new javax.swing.JPanel();
        device_ComboBox = new javax.swing.JComboBox();
        device_Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Device Delete");
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

        device_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Details"));

        address_Label.setText("Bluetooth Device Address:");

        name_Label.setText("Bluetooth Device Name:");

        address_Field.setEditable(false);
        address_Field.setBackground(new java.awt.Color(255, 255, 255));
        address_Field.setFocusable(false);

        name_Field.setEditable(false);
        name_Field.setBackground(new java.awt.Color(255, 255, 255));
        name_Field.setFocusable(false);

        created_Label.setText("Device Created:");

        created_Field.setEditable(false);
        created_Field.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout device_PanelLayout = new javax.swing.GroupLayout(device_Panel);
        device_Panel.setLayout(device_PanelLayout);
        device_PanelLayout.setHorizontalGroup(
            device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(address_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name_Label)
                    .addComponent(created_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(created_Field, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(name_Field)
                    .addComponent(address_Field))
                .addGap(6, 6, 6))
        );
        device_PanelLayout.setVerticalGroup(
            device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_PanelLayout.createSequentialGroup()
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address_Label)
                    .addComponent(address_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(device_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(created_Label)
                    .addComponent(created_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        device_Select_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Select_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Device"));

        device_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select A Device" }));
        device_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_ComboBoxActionPerformed(evt);
            }
        });

        device_Label.setText("Select Device:");

        javax.swing.GroupLayout device_Select_PanelLayout = new javax.swing.GroupLayout(device_Select_Panel);
        device_Select_Panel.setLayout(device_Select_PanelLayout);
        device_Select_PanelLayout.setHorizontalGroup(
            device_Select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Select_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(device_Label)
                .addGap(6, 6, 6)
                .addComponent(device_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        device_Select_PanelLayout.setVerticalGroup(
            device_Select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Select_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Select_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(device_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(device_Label))
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
                    .addComponent(device_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(device_Select_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(device_Select_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(device_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
     * a method that will start the deleting process.
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        Object[] options = {"Confirm", "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want to Remove This Device?",
                "Confirm Device Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {
            Task task = new Task();
            task.setBackground_Status("Update");
            task.setBackground_Objecy(this);
            task.execute();
        }
    }//GEN-LAST:event_accept_ButtonActionPerformed
    /**
     * a method that will load each devices details depending on what device is
     * selected.
     *
     * @param evt
     */
    private void device_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_ComboBoxActionPerformed

        if (device_ComboBox.getSelectedIndex() == 0) {
            name_Field.setText(null);
            address_Field.setText(null);
            created_Field.setText(null);
            accept_Button.setEnabled(false);

        } else {

            accept_Button.setEnabled(true);
            name_Field.setText(device_Name.get(device_ComboBox.getSelectedIndex() - 1));
            address_Field.setText(device_Address.get(device_ComboBox.getSelectedIndex() - 1));
            created_Field.setText(device_Created.get(device_ComboBox.getSelectedIndex() - 1));
        }
    }//GEN-LAST:event_device_ComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JTextField address_Field;
    private javax.swing.JLabel address_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField created_Field;
    private javax.swing.JLabel created_Label;
    private javax.swing.JComboBox device_ComboBox;
    private javax.swing.JLabel device_Label;
    private javax.swing.JPanel device_Panel;
    private javax.swing.JPanel device_Select_Panel;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    private final Color darkGreen = new Color(0x006400);
    private final Color darkRed = new Color(0x640000);
    private final int accountID;
    private final int deviceID;
    private final ArrayList<Integer> device_ID_List = new ArrayList<>();
    private final ArrayList<String> device_Name = new ArrayList<>();
    private final ArrayList<String> device_Address = new ArrayList<>();
    private final ArrayList<String> device_Created = new ArrayList<>();
}
