/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;
/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
/**
 * The Files_Add_Duplicates.Java Class implements an application that notifies the 
 * user if they are trying to add a file that is all ready in the folder they have
 * chosen.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Files_Add_Duplicates extends javax.swing.JDialog {

    /**
     * Creates new form addingFileError
     * @param parent
     * @param modal
     * @param files
     */
    public Files_Add_Duplicates(java.awt.Frame parent, boolean modal, ArrayList files) {
        super(parent, modal);
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
        
        //setup GUI
        DefaultListModel listModel = new DefaultListModel();
        file_List.setModel(listModel);
        for (int i = 0; i < files.size(); i++) {
            listModel.addElement(files.get(i));

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        info_Panel = new javax.swing.JPanel();
        status_Label = new javax.swing.JLabel();
        file_Panel = new javax.swing.JPanel();
        file_ScrollPane = new javax.swing.JScrollPane();
        file_List = new javax.swing.JList();
        button_Panel = new javax.swing.JPanel();
        ok_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | Duplicate Files");

        info_Panel.setBackground(new java.awt.Color(255, 255, 255));

        status_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Login_Info.png"))); // NOI18N
        status_Label.setText("These Files Were Already Present In The Folder So Were Not Added");

        file_Panel.setBackground(new java.awt.Color(255, 255, 255));
        file_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Duplicate Files"));

        file_List.setEnabled(false);
        file_ScrollPane.setViewportView(file_List);

        javax.swing.GroupLayout file_PanelLayout = new javax.swing.GroupLayout(file_Panel);
        file_Panel.setLayout(file_PanelLayout);
        file_PanelLayout.setHorizontalGroup(
            file_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, file_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(file_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        file_PanelLayout.setVerticalGroup(
            file_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, file_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(file_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout info_PanelLayout = new javax.swing.GroupLayout(info_Panel);
        info_Panel.setLayout(info_PanelLayout);
        info_PanelLayout.setHorizontalGroup(
            info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(status_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(file_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        info_PanelLayout.setVerticalGroup(
            info_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, info_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(status_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(file_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        ok_Button.setText("OK");
        ok_Button.setFocusPainted(false);
        ok_Button.setPreferredSize(new java.awt.Dimension(90, 22));
        ok_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_ButtonActionPerformed(evt);
            }
        });
        button_Panel.add(ok_Button);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(info_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(info_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private void ok_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_ok_ButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel button_Panel;
    private javax.swing.JList file_List;
    private javax.swing.JPanel file_Panel;
    private javax.swing.JScrollPane file_ScrollPane;
    private javax.swing.JPanel info_Panel;
    private javax.swing.JButton ok_Button;
    private javax.swing.JLabel status_Label;
    // End of variables declaration//GEN-END:variables
}
