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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The Folder_Management.Java Class implements an application that allows a user
 * to modify a folder on the system.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Folder_Management extends javax.swing.JDialog {

    /**
     * *
     * Creates new form Folder_Management
     *
     * @param parent
     * @param modal
     * @param AccountID
     * @param FolderName
     */
    public Folder_Management(java.awt.Frame parent, boolean modal, int AccountID, String FolderName) {

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

        accept_Button.requestFocus();
        //setup variables
        this.currentFolder = FolderName;
        this.accountID = AccountID;
        //update the GUI with all the folder information
        getAccountFolders();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        folder_Details_Panel = new javax.swing.JPanel();
        type_Label = new javax.swing.JLabel();
        description_Label = new javax.swing.JLabel();
        created_Label = new javax.swing.JLabel();
        created_Field = new javax.swing.JTextField();
        folder_ComboBox = new javax.swing.JComboBox();
        select_Label = new javax.swing.JLabel();
        delete_Button = new javax.swing.JButton();
        type_ComboBox = new javax.swing.JComboBox();
        name_Field = new javax.swing.JTextField();
        Folder_Status_Label = new javax.swing.JLabel();
        name_Label = new javax.swing.JLabel();
        description_Scroll_Pane = new javax.swing.JScrollPane();
        description_Area = new javax.swing.JTextArea();
        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        accept_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Modify Folder");
        setIconImage(null);
        setIconImages(null);
        setMinimumSize(null);
        setModal(true);
        setResizable(false);

        folder_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        folder_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        type_Label.setText("Folder Type: ");

        description_Label.setText("Folder Description: ");

        created_Label.setText("Folder Created: ");

        created_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        created_Field.setFocusable(false);

        folder_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_ComboBoxActionPerformed(evt);
            }
        });

        select_Label.setText("Selected Folder: ");

        delete_Button.setText("Delete");
        delete_Button.setFocusPainted(false);
        delete_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_ButtonActionPerformed(evt);
            }
        });

        type_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Work", "Home", "Personal", "Important", "Archive", "Other" }));

        name_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                name_FieldCaretUpdate(evt);
            }
        });

        Folder_Status_Label.setBackground(new java.awt.Color(255, 255, 255));
        Folder_Status_Label.setForeground(new java.awt.Color(255, 255, 255));
        Folder_Status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Folder_Status_Label.setText("Please Enter A Username");
        Folder_Status_Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        name_Label.setText("Folder Name: ");

        description_Area.setColumns(20);
        description_Area.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        description_Area.setRows(5);
        description_Scroll_Pane.setViewportView(description_Area);

        javax.swing.GroupLayout folder_Details_PanelLayout = new javax.swing.GroupLayout(folder_Details_Panel);
        folder_Details_Panel.setLayout(folder_Details_PanelLayout);
        folder_Details_PanelLayout.setHorizontalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(name_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(select_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name_Field)
                            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                                .addComponent(folder_ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Folder_Status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(created_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(description_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addComponent(type_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(description_Scroll_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                            .addComponent(type_ComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(created_Field))))
                .addGap(6, 6, 6))
        );
        folder_Details_PanelLayout.setVerticalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select_Label)
                    .addComponent(delete_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(Folder_Status_Label)
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_Label)
                    .addComponent(type_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(description_Label)
                    .addComponent(description_Scroll_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(created_Label)
                    .addComponent(created_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

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
                .addContainerGap(400, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(accept_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(folder_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(folder_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * a method that will open the delete form
     * 
     * @param evt 
     */
    private void delete_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_ButtonActionPerformed
        Folder_Delete sw = new Folder_Delete((Frame) this.getParent(), true, accountID);
        this.dispose();
        sw.setVisible(true);
    }//GEN-LAST:event_delete_ButtonActionPerformed
    /**
     * a method that will close the form
     *
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will start the folder modification
     *
     * @param evt
     */
    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        sendFolderDetails("Accept");

    }//GEN-LAST:event_accept_ButtonActionPerformed
    /**
     * a method that will check if the folder name is not taken and is valid
     *
     * @param evt
     */
    private void name_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_name_FieldCaretUpdate

        boolean folderIsTaken = checkFolderNameExsists(name_Field.getText());

        // checks if the username is taken and updates the status.
        if (folderIsTaken == true && name_Field.getText().length() > 0 && (!name_Field.getText().equals(currentFolder)) && !name_Field.getText().startsWith(" ")) {
            Folder_Status_Label.setText("Folder Name Already In Use");
            Folder_Status_Label.setForeground(darkRed);
            validFolder = false;

        } // checks if the username field is empty and updates the status.
        else if (name_Field.getText().length() == 0 && (!name_Field.getText().equals(currentFolder)) && !name_Field.getText().startsWith(" ")) {
            Folder_Status_Label.setText("Folder Name Place Holder");
            Folder_Status_Label.setForeground(Color.WHITE);
            validFolder = false;

        } // checks if the username is too long for the database and updates the status.
        else if (name_Field.getText().length() > 255 && (!name_Field.getText().equals(currentFolder)) && !name_Field.getText().startsWith(" ")) {
            Folder_Status_Label.setText("Folder Name Is Too Long");
            Folder_Status_Label.setForeground(darkRed);
            validFolder = false;
        } //if non of the above statements are met then they username is approved and the status updated.
        else if (name_Field.getText().equals(currentFolder) && !name_Field.getText().startsWith(" ")) {
            validFolder = true;
            Folder_Status_Label.setText("Folder Name Place Holder");
            Folder_Status_Label.setForeground(Color.WHITE);
        } else if (!name_Field.getText().startsWith(" ")) {
            validFolder = true;
            Folder_Status_Label.setText("Folder Name Approved");
            Folder_Status_Label.setForeground(darkGreen);
        } else {
            Folder_Status_Label.setText("Folder Name Cannot Start With A Space");
            Folder_Status_Label.setForeground(darkRed);
        }
    }//GEN-LAST:event_name_FieldCaretUpdate
    /**
     * a method that will clear the data and reset it for the new folder that
     * has been selected
     *
     * @param evt
     */
    private void folder_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_ComboBoxActionPerformed

        fileIDList.clear();
        currentFolder = (String) folder_ComboBox.getSelectedItem();
        getFolderDetails();


    }//GEN-LAST:event_folder_ComboBoxActionPerformed
    /**
     * a method that will return if the folder has been modifed
     *
     * @return
     */
    public boolean isModifyFolder() {
        return modifyFolder;
    }

    /**
     * a method that will set if the folder has been modified
     *
     * @param modifyFolder
     */
    public void setModifyFolder(boolean modifyFolder) {
        this.modifyFolder = modifyFolder;
    }

    /**
     * a method that will send all the information to the database
     *
     * @param buttonPressed
     */
    private void sendFolderDetails(String buttonPressed) {

        int folderID = 0;
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
                "Are You Sure You Want to Modify This Folder?",
                "Confirm Folder Modification",
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

                    Statement stmt = conn.createStatement();
                    String sql = "SELECT folder_Details_ID FROM Folder_Details "
                            + "WHERE account_Details_ID = " + accountID + " AND folder_Name = ?;";
                    PreparedStatement getFolderID = conn.prepareStatement(sql);
                    getFolderID.setString(1, currentFolder);

                    /*
                     * extracts the data from the results of the SQL statment
                     */
                    try (ResultSet rs = getFolderID.executeQuery()) {
                        while (rs.next()) {
                            folderID = rs.getInt("folder_Details_ID");
                        }
                    }

                    //creates and SQL statement and executes it.
                    String aSpql = "UPDATE Folder_Details SET folder_Name = ? , folder_Type = ?, folder_Description = ? WHERE folder_Details_ID = ? AND account_Details_ID = ? ;";

                    PreparedStatement updateFolder = conn.prepareStatement(aSpql);
                    updateFolder.setString(1, name_Field.getText());
                    updateFolder.setString(2, (String) type_ComboBox.getSelectedItem());
                    updateFolder.setString(3, description_Area.getText());
                    updateFolder.setInt(4, folderID);
                    updateFolder.setInt(5, accountID);
                    updateFolder.executeUpdate();

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
                        "Your Folder Has Been Modified Successfully.",
                        "Folder Modified!",
                        JOptionPane.INFORMATION_MESSAGE,
                        tickIcon);

                //when the window closes it opens the relevant screen so that a user can login with thier new details.
                if (buttonPressed.equals("Apply")) {
                    folderIDList.clear();
                    folderNameList.clear();
                    currentFolder = name_Field.getText();
                    getAccountFolders();
                    modifyFolder = true;
                } else if (buttonPressed.equals("Accept")) {
                    //clears all the data a user has entered.
                    folderIDList.clear();
                    folderNameList.clear();
                    fileIDList.clear();
                    modifyFolder = true;
                    this.dispose();
                }

            } else {

                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog(this,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Folder Modification Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }
        }
    }

    /**
     * a method that will check if the folder already exists on the system
     *
     * @param folderName
     * @return
     */
    private boolean checkFolderNameExsists(String folderName) {
        // creates a variable call isTaken and sets it to false.
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

    /**
     * a method that will get all the folders related to the account
     *
     */
    private void getAccountFolders() {

        int folderID;
        String folderName;

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

            Statement stmt = conn.createStatement();
            String sql = "SELECT folder_Details_ID, folder_Name FROM Folder_Details "
                    + "WHERE account_Details_ID = " + accountID + ";";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                folderName = rs.getString("folder_Name");
                folderIDList.add(folderID);
                folderNameList.add(folderName);
            }
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }

        updateFolderListGUI();
    }

    /**
     * a method that will update the GUI
     *
     */
    private void updateFolderListGUI() {

        String tempFolder = currentFolder;
        folder_ComboBox.removeAllItems();

        for (int i = 0; i < folderIDList.size(); i++) {
            if (!folderIDList.isEmpty()) {

                folder_ComboBox.addItem(folderNameList.get(i));

            }
        }
        folder_ComboBox.setSelectedItem(tempFolder);
    }

    /**
     * a method that will get a folders information
     *
     */
    private void getFolderDetails() {

        int folderID = 0;
        int fileID = 0;


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

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            String sql = "SELECT folder_Details_ID, folder_Name, folder_Type, folder_Description, folder_Created FROM Folder_Details WHERE account_Details_ID = " + accountID + " AND folder_Name = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, currentFolder);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                name_Field.setText(rs.getString("folder_Name"));
                tempName = name_Field.getText();
                type_ComboBox.setSelectedItem(rs.getString("folder_Type"));
                tempType = (String) type_ComboBox.getSelectedItem();
                description_Area.setText(rs.getString("folder_Description"));
                tempDesc = description_Area.getText();
                created_Field.setText(rs.getString("folder_Created"));
            }

            Statement stmt = conn.createStatement();
            sql = "SELECT file_Details_ID FROM Folder_File_List "
                    + "WHERE folder_Details_ID = " + folderID + ";";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                fileID = rs.getInt("file_Details_ID");
                fileIDList.add(fileID);
            }

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
     * a method that gets the current folder
     *
     * @return
     */
    public String getCurrentFolder() {
        return currentFolder;
    }

    /**
     * a method that sets the current folder
     *
     * @param currentFolder
     */
    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Folder_Status_Label;
    private javax.swing.JButton accept_Button;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField created_Field;
    private javax.swing.JLabel created_Label;
    private javax.swing.JButton delete_Button;
    private javax.swing.JTextArea description_Area;
    private javax.swing.JLabel description_Label;
    private javax.swing.JScrollPane description_Scroll_Pane;
    private javax.swing.JComboBox folder_ComboBox;
    private javax.swing.JPanel folder_Details_Panel;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JLabel select_Label;
    private javax.swing.JComboBox type_ComboBox;
    private javax.swing.JLabel type_Label;
    // End of variables declaration//GEN-END:variables
    boolean validFolder;
    private Color darkGreen = new Color(0x006400);
    private Color darkRed = new Color(0x640000);
    private ArrayList<Integer> fileIDList = new ArrayList<>();
    private TableModel model;
    private TableRowSorter<TableModel> sorter;
    private String tempName;
    private String tempType;
    private String tempDesc;
    private String currentFolder;
    private final int accountID;
    private ArrayList<Integer> folderIDList = new ArrayList<>();
    private ArrayList<String> folderNameList = new ArrayList<>();
    private boolean modifyFolder = false;

}
