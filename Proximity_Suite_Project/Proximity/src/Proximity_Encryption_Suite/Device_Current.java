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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The Device_Current.Java Class implements an application that allows a users
 * to view the device they currently have connected to the suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Device_Current extends javax.swing.JDialog {

    /**
     * Creates new form Device_Current
     *
     * @param parent
     * @param modal
     * @param deviceID
     * @param accountID
     */
    public Device_Current(java.awt.Frame parent, boolean modal, int deviceID, int accountID) {
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
        this.deviceID = deviceID;
        this.accountID = accountID;
        //gets the devices details
        setupDeviceDetails();
    }

    private void setupDeviceDetails() {
        //define variables
        String name = null;
        String address = null;
        String created = null;

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

            String sql = "SELECT device_Name, device_Address, device_Created FROM device_Details WHERE device_Details_ID = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, deviceID);

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {

                name = rs.getString("device_Name");
                address = rs.getString("device_Address");
                created = rs.getString("device_Created");

            }

        } catch (SQLException | ClassNotFoundException se) {
        } finally {

            name_Field.setText(name);
            address_Field.setText(address);
            created_Field.setText(created);

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        ok_Button = new javax.swing.JButton();
        device_Details_Panel = new javax.swing.JPanel();
        address_Label = new javax.swing.JLabel();
        service_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        address_Field = new javax.swing.JTextField();
        name_Field = new javax.swing.JTextField();
        created_Field = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Current Device");
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
                .addContainerGap(320, Short.MAX_VALUE)
                .addComponent(ok_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(ok_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        device_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        device_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Device Details"));

        address_Label.setText("Bluetooth Device Address:");

        service_Label.setText("Bluetooth Device Created:");

        name_Label.setText("Bluetooth Device Name:");

        address_Field.setEditable(false);
        address_Field.setBackground(new java.awt.Color(255, 255, 255));
        address_Field.setFocusable(false);

        name_Field.setEditable(false);
        name_Field.setBackground(new java.awt.Color(255, 255, 255));
        name_Field.setFocusable(false);

        created_Field.setEditable(false);
        created_Field.setBackground(new java.awt.Color(255, 255, 255));
        created_Field.setFocusable(false);

        javax.swing.GroupLayout device_Details_PanelLayout = new javax.swing.GroupLayout(device_Details_Panel);
        device_Details_Panel.setLayout(device_Details_PanelLayout);
        device_Details_PanelLayout.setHorizontalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(address_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(service_Label)
                    .addComponent(name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_Field, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                    .addComponent(address_Field)
                    .addComponent(created_Field))
                .addGap(6, 6, 6))
        );
        device_Details_PanelLayout.setVerticalGroup(
            device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(device_Details_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address_Label)
                    .addComponent(address_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(device_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(service_Label)
                    .addComponent(created_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(device_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(device_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
     * a method that will close the form
     *
     * @param evt
     */
    private void ok_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_ok_ButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address_Field;
    private javax.swing.JLabel address_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField created_Field;
    private javax.swing.JPanel device_Details_Panel;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JButton ok_Button;
    private javax.swing.JLabel service_Label;
    // End of variables declaration//GEN-END:variables
    private final int deviceID;
    private final int accountID;

}
