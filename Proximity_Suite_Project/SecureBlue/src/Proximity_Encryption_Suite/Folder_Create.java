package Proximity_Encryption_Suite;

import java.awt.Color;
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

        this.accountID = accountID;
        description_Area.setText(null);
        description_Area.setEditable(false);
        type_ComboBox.setSelectedIndex(0);
        type_ComboBox.setEnabled(false);
        description_Area.setBackground(darkGrey);
        accept_Button.setEnabled(false);

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
        accept_Button = new javax.swing.JButton();
        folder_Details_Panel = new javax.swing.JPanel();
        type_ComboBox = new javax.swing.JComboBox();
        type_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        description_Label = new javax.swing.JLabel();
        name_Field = new javax.swing.JTextField();
        description_ScrollPane = new javax.swing.JScrollPane();
        description_Area = new javax.swing.JTextArea();
        folder_Status_Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Create Folder");
        setMinimumSize(new java.awt.Dimension(565, 215));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
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
                .addContainerGap(383, Short.MAX_VALUE)
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

        folder_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        folder_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Please Select Folder Type", "Work", "Home", "Personal", "Important", "Archive", "Other" }));
        type_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type_ComboBoxActionPerformed(evt);
            }
        });

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

        folder_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        folder_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        folder_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        folder_Status_Label.setText("Please Enter A Username");
        folder_Status_Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
                    .addComponent(folder_Status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
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
                .addComponent(folder_Status_Label)
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(description_Label)
                    .addComponent(description_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(folder_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    boolean createdFolder = true;

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        createdFolder = false;
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // creares the variables that will be added to the databse.

        String name = name_Field.getText().trim();
        String type = (String) type_ComboBox.getSelectedItem();
        String description = description_Area.getText().trim();

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
                    Statement stmt = conn.createStatement();

                    String sql = "INSERT INTO Folder_Details VALUES (NULL, " + accountID + ", ? , '" + type + "' , ? , DEFAULT);";

                    PreparedStatement pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1, name);
                    pStmt.setString(2, description);

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
                        "Your Folder Has Been Created Successfully.",
                        "Folder Created!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                //clears all the data a user has entered.
                name_Field.setText(null);
                type_ComboBox.setSelectedIndex(0);
                description_Area.setText(null);

                this.dispose();

            } else {

                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Folder Creation Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }
        }
    }//GEN-LAST:event_accept_ButtonActionPerformed

    private boolean checkFolderNameExsists(String folderName) {
        // creates a variable call isTaken and sets it to false.
        boolean isTaken = false;
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
                if (conn != null) {
                     conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            
        }
        //returns isTaken value so it can be used.
        return isTaken;
    }

    private void name_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_name_FieldCaretUpdate
        // TODO add your handling code here:

        boolean folderIsTaken = checkFolderNameExsists(name_Field.getText());

        // checks if the username is taken and updates the status.
        if (folderIsTaken == true && name_Field.getText().length() > 0 && !name_Field.getText().startsWith(" ")) {
            folder_Status_Label.setText("Folder Name Already In Use");
            folder_Status_Label.setForeground(darkRed);
            description_Area.setText(null);
            description_Area.setEditable(false);
            description_Area.setBackground(darkGrey);
            type_ComboBox.setSelectedIndex(0);
            type_ComboBox.setEnabled(false);
            validFolder = false;

        } // checks if the username field is empty and updates the status.
        else if (name_Field.getText().length() == 0 && !name_Field.getText().startsWith(" ")) {
            folder_Status_Label.setText("Folder Name Place Holder");
            folder_Status_Label.setForeground(Color.WHITE);
            description_Area.setText(null);
            description_Area.setEditable(false);
            description_Area.setBackground(darkGrey);
            type_ComboBox.setSelectedIndex(0);
            type_ComboBox.setEnabled(false);
            validFolder = false;

        } // checks if the username is too long for the database and updates the status.
        else if (name_Field.getText().length() > 255 && !name_Field.getText().startsWith(" ")) {
            folder_Status_Label.setText("Folder Name Is Too Long");
            folder_Status_Label.setForeground(darkRed);
            description_Area.setText(null);
            description_Area.setEditable(false);
            description_Area.setBackground(darkGrey);
            type_ComboBox.setSelectedIndex(0);
            type_ComboBox.setEnabled(false);
            validFolder = false;
        } //if non of the above statements are met then they username is approved and the status updated.
        else if (!name_Field.getText().startsWith(" ")) {
            validFolder = true;
            type_ComboBox.setEnabled(true);
            folder_Status_Label.setText("Folder Name Approved");
            folder_Status_Label.setForeground(darkGreen);
        } else {
            folder_Status_Label.setText("Folder Name Cannot Start With A Space");
            folder_Status_Label.setForeground(darkRed);
            description_Area.setEditable(false);
            description_Area.setText(null);
            type_ComboBox.setSelectedIndex(0);
            type_ComboBox.setEnabled(false);
            validFolder = false;
        }
    }//GEN-LAST:event_name_FieldCaretUpdate

    private void type_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type_ComboBoxActionPerformed
        // TODO add your handling code here:
        if (type_ComboBox.getSelectedIndex() != 0) {
            description_Area.setEditable(true);
            description_Area.setBackground(Color.WHITE);
            accept_Button.setEnabled(true);
        } //resets the fields if the user have change the combo box to index 0.
        else {
            description_Area.setText(null);
            description_Area.setEditable(false);
            accept_Button.setEnabled(false);
            description_Area.setBackground(darkGrey);
        }
    }//GEN-LAST:event_type_ComboBoxActionPerformed

    public boolean isCreatedFolder() {
        return createdFolder;
    }

    public void setCreatedFolder(boolean createdFolder) {
        this.createdFolder = createdFolder;
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        createdFolder = false;
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextArea description_Area;
    private javax.swing.JLabel description_Label;
    private javax.swing.JScrollPane description_ScrollPane;
    private javax.swing.JPanel folder_Details_Panel;
    private javax.swing.JLabel folder_Status_Label;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JComboBox type_ComboBox;
    private javax.swing.JLabel type_Label;
    // End of variables declaration//GEN-END:variables
    private final int accountID;
    private boolean validFolder;
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);
    private Color darkGrey = new Color(240, 240, 240);

}
