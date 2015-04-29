package Proximity_Encryption_Suite;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
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

public class Device_Delete extends javax.swing.JDialog {

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

            if ("Update".equals(status)) {

                removeDevice();

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
                pStmt.setInt(1, deviceIDList.get(jComboBox1.getSelectedIndex() - 1));
                pStmt.executeUpdate();

                 

                sql = "DELETE FROM device_Details WHERE device_Details_ID = ?;";
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, deviceIDList.get(jComboBox1.getSelectedIndex() - 1));
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

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor

            if ("Loading".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);

                jButton2.setEnabled(false);

            }
            if ("Update".equals(status)) {
                progressBar.setValue(0);
                progressBar.setIndeterminate(false);

                Icon tickIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Tick_Icon.png"));
                JOptionPane.showMessageDialog((Component) o1,
                        "Device Removed Successfully.",
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
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        progressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

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

        jLabel4.setText("Device Created:");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDeviceAddress)
                            .addComponent(lblDeviceName)
                            .addComponent(jLabel4))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                            .addComponent(lblRuntimeDeviceName)
                            .addComponent(lblRuntimeDeviceAddress))))
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceName)
                    .addComponent(lblRuntimeDeviceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeviceAddress)
                    .addComponent(lblRuntimeDeviceAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            task.setStatus("Update");
            task.setO1(this);
            task.execute();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        if (jComboBox1.getSelectedIndex() == 0) {
            lblRuntimeDeviceName.setText(null);
            lblRuntimeDeviceAddress.setText(null);
            jTextField1.setText(null);
            jButton2.setEnabled(false);

        } else {

            jButton2.setEnabled(true);
            lblRuntimeDeviceName.setText(deviceName.get(jComboBox1.getSelectedIndex() - 1));
            lblRuntimeDeviceAddress.setText(deviceAddress.get(jComboBox1.getSelectedIndex() - 1));
            jTextField1.setText(deviceCreated.get(jComboBox1.getSelectedIndex() - 1));
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
