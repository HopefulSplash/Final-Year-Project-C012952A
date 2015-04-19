package Proximity_Encryption_Suite;

import static Proximity_Encryption_Suite.AddWindow.addFilesList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
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
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TheThoetha
 */
public class AddWindow extends javax.swing.JDialog implements ActionListener,
        PropertyChangeListener {

    private DefaultListModel listModel;
    private String Current_Folder;
    private final int Account_ID;
    boolean canClose;

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class Task extends SwingWorker<Void, Void> {

        int progress = 0;
        int fileIndex;
        boolean addedFile = false;
        Object o1;

        public void setO1(Object o1) {
            this.o1 = o1;
        }

        public int getFileIndex() {
            return fileIndex;
        }

        public void setFileIndex(int fileIndex) {
            this.fileIndex = fileIndex;
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            canClose = false;
            progress = 0;
            progressBar.setValue(0);
            progressBar.setMaximum(filelist.size());

            create_Button.setEnabled(false);
            add_Button.setEnabled(false);
            remove_Button.setEnabled(false);
            clear_Button.setEnabled(false);
            accept_Button.setEnabled(false);
            cancel_Button.setEnabled(false);
            jComboBox2.setEnabled(false);
            jComboBox1.setEnabled(false);

            //Initialize progress property.
            setProgress(0);

            while (progress < filelist.size()) {
                progressBar.setIndeterminate(true);

                //Sleep for up to one second.
                for (int i = 0; i < filelist.size(); i++) {
                    Search(filelist.get(i));
                    progress += 1;
                    setProgress(progress);
                }

            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            canClose = true;
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMaximum());
            create_Button.setEnabled(true);
            add_Button.setEnabled(true);
            remove_Button.setEnabled(true);
            clear_Button.setEnabled(true);
            accept_Button.setEnabled(true);
            cancel_Button.setEnabled(true);
            jComboBox2.setEnabled(true);
            jComboBox1.setEnabled(true);

            if (addFilesList.isEmpty()) {
                /*
                 * shows an error message due one or more fields being incorrect.
                 */
                Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
                JOptionPane.showMessageDialog((Component) o1,
                        "One Or More Fields Are Incorrect. Please Try Again.",
                        "Account Creation Error!",
                        JOptionPane.INFORMATION_MESSAGE,
                        crossIcon);
            }

        }

        public void Search(File file) {

            if (file.exists()) {

                if (file.isDirectory()) {
                    if (file.canRead()) {

                        File[] listOfFiles = file.listFiles();
                        if (listOfFiles != null) {
                            for (int i = 0; i < listOfFiles.length; i++) {
                                Search(listOfFiles[i]);

                            }
                        }
                    }
                } else if (file.isFile()) {

                    if (file.canRead()) {

                        if (addFilesList.contains(file)) {

                            addedFile = false;
                        } else {
                            addFilesList.add(file);

                            fileIndex = addFilesList.size() - 1;

                            addedFile = true;

                        }

                    }

                }
            }
        }
    }

    int temp = 0;

    public void propertyChange(PropertyChangeEvent evt) {

        if ("progress" == evt.getPropertyName()) {
            if (!addFilesList.isEmpty()) {
                for (int i = task.getFileIndex(); i >= temp; i--) {

                    listModel.addElement(addFilesList.get(i).getAbsolutePath() + "\n");

                }
            }
            temp = task.getFileIndex();
        }
    }

    /**
     *
     *
     * Creates new form AddWindow
     *
     * @param parent
     * @param Current_Folder
     * @param Account_ID
     * @param modal
     */
    public AddWindow(java.awt.Frame parent, boolean modal, int Account_ID, String Current_Folder) {
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

        listModel = new DefaultListModel();

        initComponents();

        /**
         * sets the location of the application to the middle of the screen.
         */
        this.setLocationRelativeTo(null);
        /**
         * loads the appropriate icons.
         */
        this.setIconImages(icons);

        this.getContentPane().setBackground(Color.WHITE);
        this.Current_Folder = Current_Folder;
        this.Account_ID = Account_ID;
        accept_Button.requestFocus();

        getAccountFolders();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        add_Button = new javax.swing.JButton();
        clear_Button = new javax.swing.JButton();
        remove_Button = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taskOutput = new javax.swing.JList();
        progressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        accept_Button = new javax.swing.JButton();
        cancel_Button = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        create_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Files");
        setModal(true);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("File Addition Details"));

        jLabel1.setText("Selected Files:");

        jLabel2.setText("Select Item To Add:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "File", "Folder", "Drive", "External Device" }));

        add_Button.setText("Add");
        add_Button.setFocusPainted(false);
        add_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_ButtonActionPerformed(evt);
            }
        });

        clear_Button.setText("Clear");
        clear_Button.setFocusPainted(false);
        clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_ButtonActionPerformed(evt);
            }
        });

        remove_Button.setText("Remove");
        remove_Button.setFocusPainted(false);
        remove_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remove_ButtonActionPerformed(evt);
            }
        });

        taskOutput.setModel(listModel);
        jScrollPane2.setViewportView(taskOutput);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(add_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(remove_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(add_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remove_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear_Button))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        accept_Button.setText("Accept");
        accept_Button.setFocusPainted(false);
        accept_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        accept_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        accept_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        accept_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_ButtonActionPerformed(evt);
            }
        });

        cancel_Button.setText("Cancel");
        cancel_Button.setFocusPainted(false);
        cancel_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        cancel_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        cancel_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        cancel_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(accept_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cancel_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Folder Details"));

        jLabel3.setText("Select Folder:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        create_Button.setText("Create");
        create_Button.setFocusPainted(false);
        create_Button.setMaximumSize(new java.awt.Dimension(90, 23));
        create_Button.setMinimumSize(new java.awt.Dimension(90, 23));
        create_Button.setPreferredSize(new java.awt.Dimension(90, 23));
        create_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(create_Button, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accept_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_ButtonActionPerformed
        // TODO add your handling code here:
        sendFileDetials();
    }//GEN-LAST:event_accept_ButtonActionPerformed

    private void sendFileDetials() {
        ArrayList<Boolean> fileStatusList = new ArrayList<>();

        int fileID;
        Boolean fileStatus;

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

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            for (int i = 0; i < addFilesList.size(); i++) {
                fileID = 0;
                stmt = conn.createStatement();
                String sql = "SELECT file_Details_ID, file_Directory FROM File_Details "
                        + "WHERE file_Directory = ?;";

                PreparedStatement getFolderID = conn.prepareStatement(sql);
                getFolderID.setString(1, addFilesList.get(i).getAbsolutePath());

                /*
                 * extracts the data from the results of the SQL statment
                 */
                try (ResultSet rs = getFolderID.executeQuery()) {
                    while (rs.next()) {
                        fileID = rs.getInt("file_Details_ID");

                    }
                }
                if (fileID == 0) {
                    //add new file
                    //get file ID
                    //get folderID
                    //add to Folder
                } else {
                    //get folderID
                    //add to Folder
                }
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

    static ArrayList<File> addFilesList = new ArrayList<>();

    private void add_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_ButtonActionPerformed
        // TODO add your handling code here:

        File[] files = null;
        int n = 5;

        if (jComboBox1.getSelectedIndex() == 0) {
            n = 0;
        } else if (jComboBox1.getSelectedIndex() == 1) {
            n = 1;
        } else if (jComboBox1.getSelectedIndex() == 2) {
            n = 2;
        } else if (jComboBox1.getSelectedIndex() == 3) {
            n = 3;
        }

        if (n == 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + System.getProperty("user.name") + File.separator + "Documents"));

            javax.swing.Action details = chooser.getActionMap().get("viewTypeDetails");
            details.actionPerformed(null);
            chooser.setMultiSelectionEnabled(true);
            chooser.setDragEnabled(true);
            chooser.setDialogTitle("Select A File");
            chooser.setApproveButtonText("Add");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int returnVal = chooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser.getSelectedFiles();
            }

        } else if (n == 1) {
            JFileChooser chooser1 = new JFileChooser();
            javax.swing.Action details = chooser1.getActionMap().get("viewTypeDetails");
            details.actionPerformed(null);
            chooser1.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + System.getProperty("user.name") + File.separator + "Documents"));
            chooser1.setMultiSelectionEnabled(true);

            chooser1.setDialogTitle("Select A Folder");
            chooser1.setApproveButtonText("Add");
            chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser1.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser1.getSelectedFiles();

            }
        } else if (n == 2) {
            JFileChooser chooser2 = new JFileChooser();
            javax.swing.Action details = chooser2.getActionMap().get("viewTypeDetails");
            details.actionPerformed(null);
            chooser2.setMultiSelectionEnabled(true);

            chooser2.setCurrentDirectory(chooser2.getFileSystemView().getParentDirectory(new File(System.getProperty("user.home")).getParentFile().getParentFile()));
            chooser2.setDialogTitle("Select A Drive");
            chooser2.setApproveButtonText("Add");
            chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser2.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser2.getSelectedFiles();

            }
        } else if (n == 3) {
            JFileChooser chooser3 = new JFileChooser();
            javax.swing.Action details = chooser3.getActionMap().get("viewTypeDetails");
            details.actionPerformed(null);
            chooser3.setMultiSelectionEnabled(true);

            chooser3.setCurrentDirectory(chooser3.getFileSystemView().getParentDirectory(new File(System.getProperty("user.home")).getParentFile().getParentFile()));
            chooser3.setDialogTitle("Select A External Device");
            chooser3.setApproveButtonText("Add");
            chooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser3.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                files = chooser3.getSelectedFiles();

            }
        }

        if (files != null) {

            filelist.clear();

            for (int i = 0; i < files.length; i++) {

                filelist.add(files[i]);

            }
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            task = new Task();
            task.setO1(this);

            if (listModel.isEmpty()) {
                task.setFileIndex(0);
                temp = 0;

            } else {
                task.setFileIndex(task.getFileIndex());
                temp = listModel.getSize();
            }
            task.addPropertyChangeListener(this);
            task.execute();

        }


    }//GEN-LAST:event_add_ButtonActionPerformed
    ArrayList<File> filelist = new ArrayList();

    private Task task;

    private void cancel_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_ButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancel_ButtonActionPerformed

    private void clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_ButtonActionPerformed
        // TODO add your handling code here:
        addFilesList.clear();
        filelist.clear();
        listModel.clear();
        progressBar.setValue(0);

    }//GEN-LAST:event_clear_ButtonActionPerformed

    private void remove_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remove_ButtonActionPerformed
        // TODO add your handling code here:

        DefaultListModel dlm = (DefaultListModel) taskOutput.getModel();

        if (this.taskOutput.getSelectedIndices().length > 0) {

            int[] selectedIndices = taskOutput.getSelectedIndices();

            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.removeElementAt(selectedIndices[i]);
                addFilesList.remove(selectedIndices[i]);
            }

        }


    }//GEN-LAST:event_remove_ButtonActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void create_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_ButtonActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        Folder_Create af = new Folder_Create((Frame) this.getParent(), true, Account_ID);
        af.setVisible(true);

        if (af.isCreatedFolder() == true) {
            folderIDList.clear();
            folderNameList.clear();
            getAccountFolders();
            jComboBox2.setSelectedIndex(jComboBox2.getItemCount() - 1);
        } else {
        }
    }//GEN-LAST:event_create_ButtonActionPerformed

    ArrayList<Integer> folderIDList = new ArrayList<>();
    ArrayList<String> folderNameList = new ArrayList<>();

    private void getAccountFolders() {

        int folderID;
        String folderName;

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

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            stmt = conn.createStatement();
            String sql = "SELECT folder_Details_ID, folder_Name FROM Folder_Details "
                    + "WHERE account_Details_ID = " + Account_ID + ";";

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

    private void updateFolderListGUI() {

        String tempFolder = Current_Folder;
        jComboBox2.removeAllItems();

        for (int i = 0; i < folderIDList.size(); i++) {
            if (!folderIDList.isEmpty()) {

                jComboBox2.addItem(folderNameList.get(i));

            }
        }
        jComboBox2.setSelectedItem(tempFolder);
    }

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
            java.util.logging.Logger.getLogger(AddWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddWindow dialog = new AddWindow(new javax.swing.JFrame(), true, 1, "Admin's Default Folder");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_Button;
    private javax.swing.JButton add_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel_Button;
    private javax.swing.JButton clear_Button;
    private javax.swing.JButton create_Button;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton remove_Button;
    private javax.swing.JList taskOutput;
    // End of variables declaration//GEN-END:variables
}
