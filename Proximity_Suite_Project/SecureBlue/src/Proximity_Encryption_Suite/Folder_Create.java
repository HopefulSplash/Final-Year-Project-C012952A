package Proximity_Encryption_Suite;

import java.awt.Color;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c012952a
 */
public class Folder_Create extends javax.swing.JDialog {

    /**
     * Creates new form addFolder
     *
     * @param parent
     * @param modal
     * @param accountID
     */
    public Folder_Create(java.awt.Frame parent, boolean modal, int accountID) {
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(this.getParent());
        this.accountID = accountID;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        create_Button = new javax.swing.JButton();
        folder_Details_Panel = new javax.swing.JPanel();
        type_ComboBox = new javax.swing.JComboBox();
        type_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        description_Label = new javax.swing.JLabel();
        name_Field = new javax.swing.JTextField();
        description_ScrollPane = new javax.swing.JScrollPane();
        description_Area = new javax.swing.JTextArea();
        Folder_Status_Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Folder");
        setMinimumSize(new java.awt.Dimension(565, 215));
        setModal(true);
        setResizable(false);

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        cancel_Button.setText("Cancel");
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        create_Button.setText("Create");
        create_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(create_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        folder_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select Folder Type", "Work", "Home", "Personal", "Important", "Archive", "Other" }));

        type_Label.setText("Folder Type: ");

        name_Label.setText("Folder Name: ");

        description_Label.setText("Folder Description: ");

        name_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                name_FieldCaretUpdate(evt);
            }
        });

        description_Area.setColumns(20);
        description_Area.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        description_Area.setRows(5);
        description_ScrollPane.setViewportView(description_Area);

        Folder_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        Folder_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        Folder_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Folder_Status_Label.setText("Please Enter A Username");
        Folder_Status_Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout folder_Details_PanelLayout = new javax.swing.GroupLayout(folder_Details_Panel);
        folder_Details_Panel.setLayout(folder_Details_PanelLayout);
        folder_Details_PanelLayout.setHorizontalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(description_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(type_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(type_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name_Field)
                    .addComponent(description_ScrollPane)
                    .addComponent(Folder_Status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        folder_Details_PanelLayout.setVerticalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(Folder_Status_Label)
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                        .addComponent(description_Label)
                        .addGap(6, 6, 6))
                    .addComponent(description_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_Details_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(folder_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_ButtonActionPerformed
        // creares the variables that will be added to the databse.

        System.out.print(validFolder);

        String name = name_Field.getText();
        String type = (String) type_ComboBox.getSelectedItem();
        String description = description_Area.getText();

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
                "Are You Sure You Want to Create This Folder?",
                "Confirm Folder Creation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options, //the titles of buttons
                options[0]); //default button title

        // if the user has clicked confirm.
        if (n == 0) {

            //checks if the data the user has entered is correct before storing it in the database.
            if (validFolder == true) {
                try {
                    // Register JDBC driver
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

                    //creates and SQL statement and executes it.
                    stmt = conn.createStatement();

                    String sql = "INSERT INTO Folder_Details VALUES (NULL, " + accountID + ", ? , '" + type + "' , ? , DEFAULT);";

                    PreparedStatement pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1, name);
                    pStmt.setString(2, description);
                    
                    System.out.println(pStmt);

                    pStmt.executeUpdate();

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
                name_Field.setText(null);
                type_ComboBox.setSelectedIndex(0);
                description_Area.setText(null);

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
    }//GEN-LAST:event_create_ButtonActionPerformed

    private boolean checkUsernameExsists(String folderName) {
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
            String sql = "SELECT folder_Name FROM Folder_Details "
                    + "WHERE account_Details_ID = " + accountID + ";";

            /*
             * extracts the data from the results of the SQL statment
             */
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    //retrieves the information and puts it into a variable
                    String folderCheck = rs.getString("folder_Name");
                    //checks if the username is present in the database and changing the value of isTaken if it is taken.
                    if (folderCheck.equals(folderName)) {
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

    private void name_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_name_FieldCaretUpdate
        // TODO add your handling code here:

        boolean folderIsTaken = checkUsernameExsists(name_Field.getText());

        // checks if the username is taken and updates the status.
        if (folderIsTaken == true && name_Field.getText().length() > 0) {
            Folder_Status_Label.setText("Folder Name Already In Use");
            Folder_Status_Label.setForeground(darkRed);
            validFolder = false;

        } // checks if the username field is empty and updates the status.
        else if (name_Field.getText().length() == 0) {
            Folder_Status_Label.setText("Folder Name Place Holder");
            Folder_Status_Label.setForeground(Color.WHITE);
            validFolder = false;

        } // checks if the username is too long for the database and updates the status.
        else if (name_Field.getText().length() > 255) {
            Folder_Status_Label.setText("Folder Name Is Too Long");
            Folder_Status_Label.setForeground(darkRed);
            validFolder = false;
        } //if non of the above statements are met then they username is approved and the status updated.
        else {
            validFolder = true;
            Folder_Status_Label.setText("Folder Name Approved");
            Folder_Status_Label.setForeground(darkGreen);
        }
    }//GEN-LAST:event_name_FieldCaretUpdate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Folder_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Folder_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Folder_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Folder_Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Folder_Create dialog = new Folder_Create(new javax.swing.JFrame(), true, 1);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Folder_Status_Label;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton create_Button;
    private javax.swing.JTextArea description_Area;
    private javax.swing.JLabel description_Label;
    private javax.swing.JScrollPane description_ScrollPane;
    private javax.swing.JPanel folder_Details_Panel;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JComboBox type_ComboBox;
    private javax.swing.JLabel type_Label;
    // End of variables declaration//GEN-END:variables
    private int accountID;
    private boolean validFolder;
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);

}
