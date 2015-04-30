/**
 * Defines to package to class belongs to.
 */
package Proximity_Encryption_Suite;
/**
 * Import all of the necessary libraries.
 */
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
/**
 * The Support_About.Java Class implements an application that allows a users to
 * see various information aboutProximity Encryption Suite.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Support_About extends javax.swing.JDialog {

    /**
     * Creates new form AboutWindow
     *
     * @param parent
     * @param modal
     */
    public Support_About(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

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
        /**
         * loads the appropriate icons.
         */
        this.setIconImages(icons);
        /**
         * sets the location of the application to the middle of the screen.
         */
        this.setLocationRelativeTo(null);
        /**
         *  setups up all the GUI components 
         */
        initComponents();
        
        this.getContentPane().setBackground(Color.WHITE);
        
        // put the customised text into the relavant components
        setupTextArea();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        about_Logo_Panel = new javax.swing.JPanel();
        about_Logo_Image = new javax.swing.JLabel();
        about_Details_Panel = new javax.swing.JPanel();
        details_Project__Scroll_Pane = new javax.swing.JScrollPane();
        details_Text_Pane1 = new javax.swing.JTextPane();
        details_System_Scroll_Pane = new javax.swing.JScrollPane();
        details_Text_Pane2 = new javax.swing.JTextPane();
        button_Panel = new javax.swing.JPanel();
        close_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proximity Suite | About");
        setMaximumSize(null);
        setMinimumSize(null);
        setResizable(false);

        about_Logo_Panel.setBackground(new java.awt.Color(255, 255, 255));

        about_Logo_Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        about_Logo_Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Support_About/about_Logo.png"))); // NOI18N

        javax.swing.GroupLayout about_Logo_PanelLayout = new javax.swing.GroupLayout(about_Logo_Panel);
        about_Logo_Panel.setLayout(about_Logo_PanelLayout);
        about_Logo_PanelLayout.setHorizontalGroup(
            about_Logo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(about_Logo_Image, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        about_Logo_PanelLayout.setVerticalGroup(
            about_Logo_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, about_Logo_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(about_Logo_Image)
                .addGap(0, 0, 0))
        );

        about_Details_Panel.setBackground(new java.awt.Color(255, 255, 255));
        about_Details_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Software Details"));

        details_Project__Scroll_Pane.setBackground(new java.awt.Color(255, 255, 255));
        details_Project__Scroll_Pane.setBorder(null);
        details_Project__Scroll_Pane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        details_Project__Scroll_Pane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        details_Project__Scroll_Pane.setFocusable(false);

        details_Text_Pane1.setEditable(false);
        details_Text_Pane1.setContentType("text/html"); // NOI18N
        details_Text_Pane1.setText("");
        details_Text_Pane1.setHighlighter(null);
        details_Text_Pane1.setMaximumSize(null);
        details_Text_Pane1.setMinimumSize(null);
        details_Project__Scroll_Pane.setViewportView(details_Text_Pane1);
        details_Text_Pane1.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if(Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException | URISyntaxException ex) {
                        }
                    }
                }
            }
        });

        details_System_Scroll_Pane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        details_System_Scroll_Pane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        details_System_Scroll_Pane.setFocusable(false);

        details_Text_Pane2.setEditable(false);
        details_Text_Pane2.setContentType("text/html"); // NOI18N
        details_System_Scroll_Pane.setViewportView(details_Text_Pane2);
        details_Text_Pane2.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if(Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException | URISyntaxException ex) {
                        }
                    }
                }
            }
        });

        javax.swing.GroupLayout about_Details_PanelLayout = new javax.swing.GroupLayout(about_Details_Panel);
        about_Details_Panel.setLayout(about_Details_PanelLayout);
        about_Details_PanelLayout.setHorizontalGroup(
            about_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(about_Details_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(about_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(details_Project__Scroll_Pane)
                    .addGroup(about_Details_PanelLayout.createSequentialGroup()
                        .addComponent(details_System_Scroll_Pane)
                        .addGap(6, 6, 6))))
        );
        about_Details_PanelLayout.setVerticalGroup(
            about_Details_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(about_Details_PanelLayout.createSequentialGroup()
                .addComponent(details_Project__Scroll_Pane, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(details_System_Scroll_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        button_Panel.setBackground(new java.awt.Color(255, 255, 255));

        close_Button.setText("Close");
        close_Button.setFocusPainted(false);
        close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_PanelLayout = new javax.swing.GroupLayout(button_Panel);
        button_Panel.setLayout(button_PanelLayout);
        button_PanelLayout.setHorizontalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(close_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(326, Short.MAX_VALUE))
        );
        button_PanelLayout.setVerticalGroup(
            button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(close_Button)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(about_Details_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(about_Logo_Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(about_Logo_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(about_Details_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
       /**
     * a method that creates a new instance of the Support_About class
     *
     * @param evt
     */
    private void close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_close_ButtonActionPerformed

    /**
     * a method that will put text into GUI components and get system information for the user to see
     */
    private void setupTextArea() {

        
        
        aboutJava = System.getProperty("java.version") + " " + System.getProperty("java.vm.name")
                + " " + System.getProperty("java.vm.version");

        aboutRuntime = System.getProperty("java.runtime.name") + " " + System.getProperty("java.runtime.version");

        aboutSystem = System.getProperty("os.name") + " version " + System.getProperty("os.version") + " running on " + System.getProperty("os.arch");

        details_Text_Pane2.setText("<html>\n"
                + "	<head>\n"
                + "	</head>\n"
                + "	<body>\n"
                + "		<div>\n"
                + "			<strong>Product Version:</strong> Proximity Encryption Suite Version 1.1.0&nbsp;</div>\n"
                + "		<div>\n"
                + "			<strong>Updates:</strong> Updates available from&nbsp;<a href=\"http://www.proximitysuite.wix.com/proximitysuite\">www.proximitysuite.wix.com/proximitysuite</a></div>\n"
                + "		<div>\n"
                + "			<strong>Java:</strong>&nbsp;" + aboutJava + "</div>\n"
                + "		<div>\n"
                + "			<strong>Runtime:</strong>&nbsp;" + aboutRuntime + "</div>\n"
                + "		<div>\n"
                + "			<strong>System:</strong>&nbsp;" + aboutSystem + "</div>\n"
                + "	</body>\n"
                + "</html>\n"
                + "");

        details_Text_Pane1.setText("<html>\n"
                + "	<head>\n"
                + "	</head>\n"
                + "	<body>\n"
                + "The Proximity Encryption Suite offers its users flexible encryption and decryption of sensitive documents with a variety of industry standard algorithms with Bluetooth functionality."
                + " For more information, please visit&nbsp;<a href=\"http://www.proximitysuite.wix.com/proximitysuite\">www.proximitysuite.wix.com/proximitysuite</a></div>\n"
                + "	</body>\n"
                + "</html>\n"
                + "");
        
        
    }

    private String aboutJava;
    private String aboutRuntime;
    private String aboutSystem;
    private String aboutUser;
    private String aboutCache;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel about_Details_Panel;
    private javax.swing.JLabel about_Logo_Image;
    private javax.swing.JPanel about_Logo_Panel;
    private javax.swing.JPanel button_Panel;
    private javax.swing.JButton close_Button;
    private javax.swing.JScrollPane details_Project__Scroll_Pane;
    private javax.swing.JScrollPane details_System_Scroll_Pane;
    private javax.swing.JTextPane details_Text_Pane1;
    private javax.swing.JTextPane details_Text_Pane2;
    // End of variables declaration//GEN-END:variables
}
