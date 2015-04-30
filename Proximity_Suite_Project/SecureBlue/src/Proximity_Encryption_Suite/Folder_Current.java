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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The Folder_Current.Java Class implements an application that allows a user to
 * view the details of the current folder.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Folder_Current extends javax.swing.JDialog {

    int accountID;
    String currentName;

    /**
     * Creates new form viewCurrentlyFolder
     *
     * @param parent
     * @param modal
     * @param account_ID
     * @param folderName
     */
    public Folder_Current(java.awt.Frame parent, boolean modal, int account_ID, String folderName) {

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

        ok_Button.requestFocus();

        //setup variables
        this.accountID = account_ID;
        this.currentName = folderName;

        getFolderDetails();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        folder_Details_Panel = new javax.swing.JPanel();
        name_Label = new javax.swing.JLabel();
        type_Label = new javax.swing.JLabel();
        description_Label = new javax.swing.JLabel();
        created_Label = new javax.swing.JLabel();
        name_Field = new javax.swing.JTextField();
        created_Field = new javax.swing.JTextField();
        type_Field = new javax.swing.JTextField();
        description_Scroll_Pane = new javax.swing.JScrollPane();
        description_Area = new javax.swing.JTextArea();
        button_Panel = new javax.swing.JPanel();
        cancel_Button = new javax.swing.JButton();
        modify_Button = new javax.swing.JButton();
        ok_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Current Folder");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(565, 224));
        setModal(true);
        setResizable(false);

        folder_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        folder_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        name_Label.setText("Folder Name: ");

        type_Label.setText("Folder Type: ");

        description_Label.setText("Folder Description: ");

        created_Label.setText("Folder Created: ");

        name_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        name_Field.setFocusable(false);
        name_Field.setRequestFocusEnabled(false);

        created_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        created_Field.setFocusable(false);

        type_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        type_Field.setFocusable(false);

        description_Scroll_Pane.setBackground(new java.awt.Color(255, 255, 255));

        description_Area.setColumns(20);
        description_Area.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        description_Area.setRows(5);
        description_Area.setBorder(null);
        description_Area.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        description_Area.setFocusable(false);
        description_Scroll_Pane.setViewportView(description_Area);

        javax.swing.GroupLayout folder_Details_PanelLayout = new javax.swing.GroupLayout(folder_Details_Panel);
        folder_Details_Panel.setLayout(folder_Details_PanelLayout);
        folder_Details_PanelLayout.setHorizontalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                        .addComponent(name_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name_Field))
                    .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(created_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(description_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(type_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(type_Field)
                            .addComponent(description_Scroll_Pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                            .addComponent(created_Field, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(6, 6, 6))
        );
        folder_Details_PanelLayout.setVerticalGroup(
            folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(folder_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_Label)
                    .addComponent(name_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(folder_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(description_Label)
                    .addComponent(description_Scroll_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
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

        modify_Button.setText("Modify");
        modify_Button.setFocusPainted(false);
        modify_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_ButtonActionPerformed(evt);
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
                .addContainerGap(308, Short.MAX_VALUE)
                .addComponent(ok_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modify_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGroup(button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_Button)
                    .addComponent(modify_Button)
                    .addComponent(ok_Button))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
     * a method that will close the form
     *
     * @param evt
     */
    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed
    /**
     * a method that will open the modification form
     *
     * @param evt
     */
    private void modify_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_ButtonActionPerformed
        Folder_Management j1 = new Folder_Management((Frame) this.getParent(), true, accountID, name_Field.getText());
        this.dispose();
        j1.setVisible(true);
    }//GEN-LAST:event_modify_ButtonActionPerformed
    /**
     * a method that will close the form
     *
     * @param evt
     */
    private void ok_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_ButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_ok_ButtonActionPerformed
    /**
     * a method that will get a folder information
     *
     */
    private void getFolderDetails() {

        ArrayList<Integer> fileIDList = new ArrayList<>();

        int folderID = 0;
        int fileID = 0;


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

            String sql = "SELECT folder_Details_ID, folder_Name, folder_Type, folder_Description, folder_Created FROM Folder_Details WHERE account_Details_ID = " + accountID + " AND folder_Name = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, currentName);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
                name_Field.setText(rs.getString("folder_Name"));
                type_Field.setText(rs.getString("folder_Type"));
                description_Area.setText(rs.getString("folder_Description"));
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JTextField created_Field;
    private javax.swing.JLabel created_Label;
    private javax.swing.JTextArea description_Area;
    private javax.swing.JLabel description_Label;
    private javax.swing.JScrollPane description_Scroll_Pane;
    private javax.swing.JPanel folder_Details_Panel;
    private javax.swing.JButton modify_Button;
    private javax.swing.JTextField name_Field;
    private javax.swing.JLabel name_Label;
    private javax.swing.JButton ok_Button;
    private javax.swing.JTextField type_Field;
    private javax.swing.JLabel type_Label;
    // End of variables declaration//GEN-END:variables
    private TableModel model;
    private TableRowSorter<TableModel> sorter;

}
