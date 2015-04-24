package Proximity_Encryption_Suite;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
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
public class Suite_Window extends javax.swing.JFrame {

    class Task extends SwingWorker<Void, Void> {

        int counter = 0;

        public void setKey(String key) {
            this.key = key;
        }
        String status;
        String key;

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public Void doInBackground() {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            counter = 0;
            progressBar.setValue(0);

            //Initialize progress property.
            setProgress(0);

            while (counter != 1) {
                progressBar.setIndeterminate(true);

                if ("Login".equals(status)) {

                    switch (loginType) {
                        case "Account":
                            getAccountDetails();
                            counter++;
                            break;
                        case "Device":
                            table_Add_Button.setEnabled(false);
                            table_Remove_Button.setEnabled(false);
                            table_Select_Button.setEnabled(false);
                            table_Deselect_Button.setEnabled(false);
                            table_Encrypt_Button.setEnabled(false);
                            table_Decrypt_Button.setEnabled(false);

                            //getID and password where device is... 
                           // Encryption_Script es = new Encryption_Script(key, "Decrypt", accountID);

                            
                            //start connection thread
                            getAccountDetails();
                            counter++;
                            break;
                    }

                } else if ("Logout".equals(status)) {
                    //add logout script
                    counter++;
                }
            }

            return null;
        }

        public int getCounter() {
            return counter;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            setCursor(null); //turn off the wait cursor

            progressBar.setIndeterminate(false);
            progressBar.setValue(0);

        }

        public void getkey(int accountID) {
 // get password for encryption
        }
    }

    /**
     * Creates new form MainWindowSample
     *
     * @param account_ID
     */
    public Suite_Window(int account_ID, String loginType, String DeviceName) {
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

        java.util.Date now = new java.util.Date();
        String ss = DateFormat.getDateTimeInstance().format(now);
        statusbar_Date_Label.setText("System Date & Time: " + ss);
        status_Session_Label.setText("Session Time: 00:00:00 ");

//        if (loginType.equals("Account")) {
//            accountID = 1; //accountID
//            this.loginType = "Account"; //loginType
//        }
//        else if (loginType.equals("Device")){
//            // accountID = getAccountID(DeviceName); //accountID
//            this.loginType = "Account"; //loginType
//        }
        accountID = 1;

        Task task = new Task();
        task.setStatus("Login");
        this.loginType = "Account";
        task.execute();

    }
    String loginType;

    public void getAccountDetails() {
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
        Statement stmt = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            stmt = conn.createStatement();
            String sql = "SELECT account_Username, account_Password, account_Email, account_Question, account_Answer FROM Account_Details "
                    + "WHERE account_Details_ID = '" + accountID + "';";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                accountName = rs.getString("account_Username");
                accountPass = rs.getString("account_Password");
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
        account_Current.setText(accountName);

        getAccountFolders();
    }

    ArrayList<Integer> folderIDList = new ArrayList<>();
    ArrayList<String> folderNameList = new ArrayList<>();

    private void getAccountFolders() {

        int folderID;
        String folderName;

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
        Statement stmt = null;

        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            stmt = conn.createStatement();
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

    private void updateFolderListGUI() {

        table_Folder_ComboBox.removeAllItems();

        for (int i = 0; i < folderIDList.size(); i++) {
            if (!folderIDList.isEmpty()) {

                table_Folder_ComboBox.addItem(folderNameList.get(i));
                folder_Current.setText(table_Folder_ComboBox.getSelectedItem().toString());
            }
        }

    }

    private void getFolderFiles(String folderName) {

        ArrayList<Integer> fileIDList = new ArrayList<>();

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
        Statement stmt = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            String sql = "SELECT folder_Details_ID FROM Folder_Details WHERE account_Details_ID = " + accountID + " AND folder_Name = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, folderName);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                folderID = rs.getInt("folder_Details_ID");
            }

            stmt = conn.createStatement();
            sql = "SELECT file_Details_ID FROM Folder_File_List "
                    + "WHERE folder_Details_ID = " + folderID + ";";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                fileID = rs.getInt("file_Details_ID");
                fileIDList.add(fileID);
            }

            getFolderFiles1(fileIDList);
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

    public void getFolderFiles1(ArrayList<Integer> fileIDList) {
        ArrayList<String> fileDirList = new ArrayList<>();
        ArrayList<String> fileStatusList = new ArrayList<>();

        String fileDir;
        String fileType;


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
        Statement stmt = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            for (int i = 0; i < fileIDList.size(); i++) {

                stmt = conn.createStatement();
                String sql = "SELECT file_Directory,  file_EType FROM File_Details "
                        + "WHERE file_Details_ID = " + fileIDList.get(i) + ";";

                ResultSet rs = stmt.executeQuery(sql);
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    fileDir = rs.getString("file_Directory");
                    fileType = rs.getString("file_EType");

                    if (new File(fileDir).exists()) {
                        fileDirList.add(fileDir);
                        fileStatusList.add(fileType);
                    } else {
                        removeFile(fileIDList.get(i));
                    }
                }
            }

            updateTableContents(fileDirList, fileStatusList);

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

    public void removeFile(int fileID) {

        /*
         * declares and new instance of the Suite_Database class and then checks if the
         * the database exists and if is does not then creates it for the system.
         */
        Suite_Database d = new Suite_Database();

        /*
         * declares the variables for use in connecting and checking the database.
         */
        Connection conn = null;
        Statement stmt = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            String sql = "DELETE FROM folder_file_list WHERE file_Details_ID = ?;";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, fileID);
            pStmt.executeUpdate();
            sql = "DELETE FROM file_details WHERE file_Details_ID = ?;";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, fileID);
            pStmt.executeUpdate();
            pStmt.close();
            conn.close();

            pStmt.close();
            conn.close();

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

    public void updateTableContents(ArrayList<String> fileDirList, ArrayList<String> fileStatusList) {

        File_Loading_Thread myRunnable = null;

        File file;
        Boolean select;
        String name;
        String fname;
        String date;
        int pos;
        String type;
        String fileSize;
        String status;

        if (!fileDirList.isEmpty()) {
            Task task = new Task();
            task.execute();

            for (String fileDirList1 : fileDirList) {

                myRunnable = new File_Loading_Thread((Suite_Window) this, new File(fileDirList1));
                myRunnable.setResultFiles(filelists);
                Thread t = new Thread(myRunnable);
                t.start();
                while (t.isAlive()) {
                    task.setCounter(0);
                }

            }
            task.setCounter(1);

            DefaultTableModel dw = (DefaultTableModel) table_View.getModel();

            for (int i = 0; i < filelists.size(); i++) {

                file = filelists.get(i);

                //file name
                fname = file.getName();
                //date
                date = DateFormat.getDateTimeInstance().format(file.lastModified());

                //file extension
                pos = fname.lastIndexOf('.');
                if (pos > 0) {
                    type = fname.substring(pos);
                    name = fname.substring(0, pos);

                } else {
                    type = fname;
                    name = file.getName();
                }

                // size
                fileSize = getFileSize(file.length());

                if (!fileStatusList.get(i).equals("AES Encryption") && !fileStatusList.get(i).equals("DES Encryption") && !fileStatusList.get(i).equals("Triple DES Encryption")) {
                    status = "Decrypted";
                } else {
                    status = "Encrypted";
                }

                dw.addRow(new Object[]{false, " " + name, date, type, fileSize, status, "Open"});
            }
        }

    }

    public String getFileSize(double fileLength) {
        int unitSize = 1024;
        if (fileLength < unitSize) {
            return fileLength + " B";
        }
        int exp = (int) (Math.log(fileLength) / Math.log(unitSize));
        char pre = "KMGTPE".charAt(exp - 1);

        String s = String.format(" %sB", pre);

        DecimalFormat df = new DecimalFormat("#.##");
        double we = fileLength / Math.pow(unitSize, exp);

        String ss = df.format(we) + s;

        return ss;
    }

    ArrayList<File> filelists = new ArrayList();

    private int accountID;
    private String accountName;
    private String accountPass;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proximity_Table_Button_Panel = new javax.swing.JPanel();
        table_Add_Button = new javax.swing.JButton();
        table_Remove_Button = new javax.swing.JButton();
        table_Select_Button = new javax.swing.JButton();
        table_Encrypt_Button = new javax.swing.JButton();
        table_Decrypt_Button = new javax.swing.JButton();
        table_Deselect_Button = new javax.swing.JButton();
        proximity_Table_Scroll_Pane = new javax.swing.JScrollPane();
        table_View = new javax.swing.JTable();
        proximity_Table_Feature_Panel = new javax.swing.JPanel();
        table_Search_Field = new javax.swing.JTextField();
        table_Search_Label = new javax.swing.JLabel();
        table_Search_Clear_Button = new javax.swing.JButton();
        table_Folder_Label = new javax.swing.JLabel();
        table_Folder_ComboBox = new javax.swing.JComboBox();
        proximity_Separator1 = new javax.swing.JSeparator();
        proximity_Statusbar_Panel = new javax.swing.JPanel();
        statusbar_Date_Label = new javax.swing.JLabel();
        status_Session_Label = new javax.swing.JLabel();
        status_Device_Label = new javax.swing.JLabel();
        proximity_System_Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        proximity_Menu = new javax.swing.JMenuBar();
        menu_Home = new javax.swing.JMenu();
        home_Login = new javax.swing.JMenuItem();
        home_Logout = new javax.swing.JMenuItem();
        home_Separator = new javax.swing.JPopupMenu.Separator();
        home_Exit = new javax.swing.JMenuItem();
        menu_Device = new javax.swing.JMenu();
        device_Current = new javax.swing.JMenuItem();
        device_Separator3 = new javax.swing.JPopupMenu.Separator();
        device_Add = new javax.swing.JMenuItem();
        device_Disconnect = new javax.swing.JMenuItem();
        device_Separator2 = new javax.swing.JPopupMenu.Separator();
        device_Manage = new javax.swing.JMenuItem();
        menu_Folder = new javax.swing.JMenu();
        folder_Current = new javax.swing.JMenuItem();
        folder_Separator1 = new javax.swing.JPopupMenu.Separator();
        folder_Create = new javax.swing.JMenuItem();
        folder_Delete = new javax.swing.JMenuItem();
        folder_Separator2 = new javax.swing.JPopupMenu.Separator();
        folder_Manage = new javax.swing.JMenuItem();
        menu_Account = new javax.swing.JMenu();
        account_Current = new javax.swing.JMenuItem();
        account_Separator1 = new javax.swing.JPopupMenu.Separator();
        account_Create = new javax.swing.JMenuItem();
        account_Delete = new javax.swing.JMenuItem();
        account_Separator2 = new javax.swing.JPopupMenu.Separator();
        account_Modify = new javax.swing.JMenuItem();
        menu_Support = new javax.swing.JMenu();
        support_Guide = new javax.swing.JMenuItem();
        support_Separator = new javax.swing.JPopupMenu.Separator();
        support_About = new javax.swing.JMenuItem();
        menu_Social_Media = new javax.swing.JMenu();
        social_Website = new javax.swing.JMenuItem();
        social_Separator1 = new javax.swing.JPopupMenu.Separator();
        social_Facebook = new javax.swing.JMenuItem();
        social_Twitter = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Proximity Encryption Suite");
        setMinimumSize(new java.awt.Dimension(1300, 550));
        setName("MainWindowFrame"); // NOI18N
        setResizable(false);

        proximity_Table_Button_Panel.setBackground(new java.awt.Color(255, 255, 255));
        proximity_Table_Button_Panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        table_Add_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Add_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Add_Button.png"))); // NOI18N
        table_Add_Button.setText("Add Files      ");
        table_Add_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_Add_Button.setFocusPainted(false);
        table_Add_Button.setIconTextGap(6);
        table_Add_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Add_ButtonActionPerformed(evt);
            }
        });

        table_Remove_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Remove_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Delete_Button.png"))); // NOI18N
        table_Remove_Button.setText("Remove Files");
        table_Remove_Button.setFocusPainted(false);
        table_Remove_Button.setIconTextGap(6);
        table_Remove_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Remove_ButtonActionPerformed(evt);
            }
        });

        table_Select_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Select_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Select_Button.png"))); // NOI18N
        table_Select_Button.setText("Select All      ");
        table_Select_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_Select_Button.setFocusPainted(false);
        table_Select_Button.setIconTextGap(6);
        table_Select_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Select_ButtonActionPerformed(evt);
            }
        });

        table_Encrypt_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Encrypt_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Encrypt_Button.png"))); // NOI18N
        table_Encrypt_Button.setText("Encrypt Files ");
        table_Encrypt_Button.setFocusPainted(false);
        table_Encrypt_Button.setIconTextGap(6);
        table_Encrypt_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Encrypt_ButtonActionPerformed(evt);
            }
        });

        table_Decrypt_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Decrypt_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Decrypt_Button.png"))); // NOI18N
        table_Decrypt_Button.setText("Decrypt Files ");
        table_Decrypt_Button.setFocusPainted(false);
        table_Decrypt_Button.setIconTextGap(6);
        table_Decrypt_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Decrypt_ButtonActionPerformed(evt);
            }
        });

        table_Deselect_Button.setBackground(new java.awt.Color(255, 255, 255));
        table_Deselect_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Buttons/table_Deselect_Button.png"))); // NOI18N
        table_Deselect_Button.setText("Deselect All  ");
        table_Deselect_Button.setFocusPainted(false);
        table_Deselect_Button.setIconTextGap(6);
        table_Deselect_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Deselect_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout proximity_Table_Button_PanelLayout = new javax.swing.GroupLayout(proximity_Table_Button_Panel);
        proximity_Table_Button_Panel.setLayout(proximity_Table_Button_PanelLayout);
        proximity_Table_Button_PanelLayout.setHorizontalGroup(
            proximity_Table_Button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proximity_Table_Button_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(proximity_Table_Button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(table_Decrypt_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_Encrypt_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_Select_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_Deselect_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_Remove_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_Add_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        proximity_Table_Button_PanelLayout.setVerticalGroup(
            proximity_Table_Button_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proximity_Table_Button_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(table_Add_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Remove_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Select_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Deselect_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Encrypt_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Decrypt_Button)
                .addGap(6, 6, 6))
        );

        table_Add_Button.getAccessibleContext().setAccessibleName("");

        proximity_Table_Scroll_Pane.setBackground(new java.awt.Color(255, 255, 255));
        proximity_Table_Scroll_Pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        proximity_Table_Scroll_Pane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table_View.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "Name", "Date modified", "Type", "Size", "Status", " "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_View.getTableHeader().setReorderingAllowed(false);
        table_View.setFillsViewportHeight(true);
        table_View.setFocusable(false);
        table_View.setRowHeight(23);
        table_View.getColumn(" ").setCellRenderer(new ButtonRenderer());
        table_View.getColumn(" ").setCellEditor(
            new ButtonEditor(new JCheckBox()));

        table_View.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
        table_View.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());

        table_View.getColumnModel().getColumn(0).setMinWidth(40);
        table_View.getColumnModel().getColumn(0).setMaxWidth(40);

        table_View.getColumnModel().getColumn(2).setMinWidth(120);
        table_View.getColumnModel().getColumn(2).setMaxWidth(120);

        table_View.getColumnModel().getColumn(3).setMinWidth(55);
        table_View.getColumnModel().getColumn(3).setMaxWidth(55);

        table_View.getColumnModel().getColumn(4).setMinWidth(60);
        table_View.getColumnModel().getColumn(4).setMaxWidth(60);

        table_View.getColumnModel().getColumn(5).setMaxWidth(60);
        table_View.getColumnModel().getColumn(5).setMinWidth(60);

        table_View.getColumnModel().getColumn(6).setMaxWidth(85);
        table_View.getColumnModel().getColumn(6).setMinWidth(85);

        table_View.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    String fname;
                    int pos;
                    //date

                    for (int i = 0; i < filelists.size(); i++) {

                        fname = filelists.get(i).getName();
                        pos = fname.lastIndexOf('.');

                        if (pos > 0) {
                            fname = fname.substring(0, pos);

                            if (fname.equals(table_View.getValueAt(table_View.getSelectedRow(), 1).toString().trim())) {
                                openFile(filelists.get(i));

                            }

                        } else {

                            if (fname.equals(table_View.getValueAt(table_View.getSelectedRow(), 1).toString().trim())) {
                                openFile(filelists.get(i));

                            }
                        }

                    }
                }
            }
        });

        TableCellRenderer rendererFromHeader = table_View.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table_View.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table_View.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );

        for (int a = 0; a < table_View.getColumnCount(); a++){

            table_View.getColumnModel().getColumn(a).setResizable(false);

        }
        table_View.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                table_ViewFocusLost(evt);
            }
        });
        proximity_Table_Scroll_Pane.setViewportView(table_View);
        model = table_View.getModel();
        sorter = new TableRowSorter<>(model);
        table_View.setRowSorter(sorter);
        sorter.setSortable(6, false);

        proximity_Table_Feature_Panel.setBackground(new java.awt.Color(255, 255, 255));

        table_Search_Field.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                table_Search_FieldCaretUpdate(evt);
            }
        });

        table_Search_Label.setText("Search: ");

        table_Search_Clear_Button.setText("Clear");
        table_Search_Clear_Button.setFocusPainted(false);
        table_Search_Clear_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Search_Clear_ButtonActionPerformed(evt);
            }
        });

        table_Folder_Label.setText("Current Folder: ");

        table_Folder_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_Folder_ComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout proximity_Table_Feature_PanelLayout = new javax.swing.GroupLayout(proximity_Table_Feature_Panel);
        proximity_Table_Feature_Panel.setLayout(proximity_Table_Feature_PanelLayout);
        proximity_Table_Feature_PanelLayout.setHorizontalGroup(
            proximity_Table_Feature_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proximity_Table_Feature_PanelLayout.createSequentialGroup()
                .addComponent(table_Search_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Search_Field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Search_Clear_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(table_Folder_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_Folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        proximity_Table_Feature_PanelLayout.setVerticalGroup(
            proximity_Table_Feature_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proximity_Table_Feature_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(table_Search_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(table_Search_Label)
                .addComponent(table_Search_Clear_Button)
                .addComponent(table_Folder_Label)
                .addComponent(table_Folder_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        table_Search_Field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                searchAll();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                searchAll();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                searchAll();
            }
        });

        proximity_Statusbar_Panel.setBackground(new java.awt.Color(255, 255, 255));
        proximity_Statusbar_Panel.setPreferredSize(new java.awt.Dimension(1150, 20));

        statusbar_Date_Label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusbar_Date_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Statusbar/statusbar_Date.png"))); // NOI18N
        statusbar_Date_Label.setText("Time Timer");

        status_Session_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        status_Session_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Statusbar/statusbar_Session.png"))); // NOI18N
        status_Session_Label.setText("Time Elapsed: ");

        status_Device_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Statusbar/statusbar_Device.png"))); // NOI18N
        status_Device_Label.setText("Bluetooth Device: ");

        javax.swing.GroupLayout proximity_Statusbar_PanelLayout = new javax.swing.GroupLayout(proximity_Statusbar_Panel);
        proximity_Statusbar_Panel.setLayout(proximity_Statusbar_PanelLayout);
        proximity_Statusbar_PanelLayout.setHorizontalGroup(
            proximity_Statusbar_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proximity_Statusbar_PanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(status_Device_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status_Session_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statusbar_Date_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        proximity_Statusbar_PanelLayout.setVerticalGroup(
            proximity_Statusbar_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proximity_Statusbar_PanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(proximity_Statusbar_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status_Session_Label)
                    .addComponent(status_Device_Label)
                    .addComponent(statusbar_Date_Label))
                .addGap(2, 2, 2))
        );

        Timer timee = new javax.swing.Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date now = new java.util.Date();
                String ss = DateFormat.getDateTimeInstance().format(now);
                statusbar_Date_Label.setText("System Date & Time: " + ss);
                statusbar_Date_Label.setToolTipText("System Date & Time: " + ss);

            }
        });
        timee.start();
        Timer timee1 = new javax.swing.Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long end = System.currentTimeMillis( );
                    long diff = end - start;
                    long second = (diff / 1000) % 60;
                    long minute = (diff / (1000 * 60)) % 60;
                    long hour = (diff / (1000 * 60 * 60)) % 24;

                    String time = String.format("Session Time: %02d:%02d:%02d", hour, minute, second);
                    status_Session_Label.setText(time);
                } catch (Exception ex) {
                    System.out.println("Got an exception!");
                }

            }
        });
        timee1.start();

        proximity_System_Panel.setBackground(new java.awt.Color(255, 255, 255));
        proximity_System_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Folder Details"));

        jLabel1.setText(" ");

        jLabel2.setForeground(new java.awt.Color(51, 153, 0));
        jLabel2.setText(" ");

        jLabel3.setForeground(new java.awt.Color(153, 51, 0));
        jLabel3.setText(" ");

        javax.swing.GroupLayout proximity_System_PanelLayout = new javax.swing.GroupLayout(proximity_System_Panel);
        proximity_System_Panel.setLayout(proximity_System_PanelLayout);
        proximity_System_PanelLayout.setHorizontalGroup(
            proximity_System_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proximity_System_PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(proximity_System_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        proximity_System_PanelLayout.setVerticalGroup(
            proximity_System_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proximity_System_PanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(6, 6, 6))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        proximity_Menu.setBorder(null);

        menu_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Home/home_Menu_Icon.png"))); // NOI18N
        menu_Home.setText("Home");

        home_Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Home/home_Login.png"))); // NOI18N
        home_Login.setText("Login");
        home_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_LoginActionPerformed(evt);
            }
        });
        menu_Home.add(home_Login);

        home_Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Home/home_Logout.png"))); // NOI18N
        home_Logout.setText("Logout");
        home_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_LogoutActionPerformed(evt);
            }
        });
        menu_Home.add(home_Logout);
        menu_Home.add(home_Separator);

        home_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        home_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Home/home_Exit.png"))); // NOI18N
        home_Exit.setText("Exit");
        home_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_ExitActionPerformed(evt);
            }
        });
        menu_Home.add(home_Exit);

        proximity_Menu.add(menu_Home);

        menu_Device.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Menu_Icon.png"))); // NOI18N
        menu_Device.setText("Device Management");

        device_Current.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        device_Current.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Current.png"))); // NOI18N
        device_Current.setText("No Device Connected");
        device_Current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_CurrentActionPerformed(evt);
            }
        });
        menu_Device.add(device_Current);
        menu_Device.add(device_Separator3);

        device_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Add.png"))); // NOI18N
        device_Add.setText("Add Device");
        device_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_AddActionPerformed(evt);
            }
        });
        menu_Device.add(device_Add);

        device_Disconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Disconnect.png"))); // NOI18N
        device_Disconnect.setText("Delete Device");
        device_Disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_DisconnectActionPerformed(evt);
            }
        });
        menu_Device.add(device_Disconnect);
        menu_Device.add(device_Separator2);

        device_Manage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Device/device_Manage.png"))); // NOI18N
        device_Manage.setText("Manage Devices");
        device_Manage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                device_ManageActionPerformed(evt);
            }
        });
        menu_Device.add(device_Manage);

        proximity_Menu.add(menu_Device);

        menu_Folder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Folder/folder_Menu_Icon.png"))); // NOI18N
        menu_Folder.setText("Folder Management");

        folder_Current.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        folder_Current.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Folder/folder_Current.png"))); // NOI18N
        folder_Current.setText("No Folder Selected");
        folder_Current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_CurrentActionPerformed(evt);
            }
        });
        menu_Folder.add(folder_Current);
        menu_Folder.add(folder_Separator1);

        folder_Create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Folder/folder_Create.png"))); // NOI18N
        folder_Create.setText("Create Folder");
        folder_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_CreateActionPerformed(evt);
            }
        });
        menu_Folder.add(folder_Create);

        folder_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Folder/folder_Delete.png"))); // NOI18N
        folder_Delete.setText("Delete Folder");
        folder_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_DeleteActionPerformed(evt);
            }
        });
        menu_Folder.add(folder_Delete);
        menu_Folder.add(folder_Separator2);

        folder_Manage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Folder/folder_Manage.png"))); // NOI18N
        folder_Manage.setText("Manage Folders");
        folder_Manage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folder_ManageActionPerformed(evt);
            }
        });
        menu_Folder.add(folder_Manage);

        proximity_Menu.add(menu_Folder);

        menu_Account.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Account/account_User.png"))); // NOI18N
        menu_Account.setText("Account Management");

        account_Current.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        account_Current.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Account/account_User.png"))); // NOI18N
        account_Current.setText("Current Account");
        account_Current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_CurrentActionPerformed(evt);
            }
        });
        menu_Account.add(account_Current);
        menu_Account.add(account_Separator1);

        account_Create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Account/account_Create.png"))); // NOI18N
        account_Create.setText("Create Account");
        account_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_CreateActionPerformed(evt);
            }
        });
        menu_Account.add(account_Create);

        account_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Account/account_Delete.png"))); // NOI18N
        account_Delete.setText("Delete Account");
        account_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_DeleteActionPerformed(evt);
            }
        });
        menu_Account.add(account_Delete);
        menu_Account.add(account_Separator2);

        account_Modify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Account/account_Modify.png"))); // NOI18N
        account_Modify.setText("Modify Account");
        account_Modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_ModifyActionPerformed(evt);
            }
        });
        menu_Account.add(account_Modify);

        proximity_Menu.add(menu_Account);

        menu_Support.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Support/support_Menu_Icon.png"))); // NOI18N
        menu_Support.setText("Support");

        support_Guide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        support_Guide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Support/support_Guide.png"))); // NOI18N
        support_Guide.setText("User Guide");
        support_Guide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                support_GuideActionPerformed(evt);
            }
        });
        menu_Support.add(support_Guide);
        menu_Support.add(support_Separator);

        support_About.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Support/support_About.png"))); // NOI18N
        support_About.setText("About");
        support_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                support_AboutActionPerformed(evt);
            }
        });
        menu_Support.add(support_About);

        proximity_Menu.add(menu_Support);
        proximity_Menu.add(Box.createHorizontalGlue());

        menu_Social_Media.setBorder(null);
        menu_Social_Media.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_SocialMedia/social_Menu_Icon.png"))); // NOI18N
        menu_Social_Media.setText("Social Media");
        menu_Social_Media.setToolTipText("");

        social_Website.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_SocialMedia/social_Website.png"))); // NOI18N
        social_Website.setText("Proximity Webiste");
        social_Website.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                social_WebsiteActionPerformed(evt);
            }
        });
        menu_Social_Media.add(social_Website);
        menu_Social_Media.add(social_Separator1);

        social_Facebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_SocialMedia/social_Facebook.png"))); // NOI18N
        social_Facebook.setText("Proximity Facebook");
        social_Facebook.setToolTipText("Like Us On Facebook");
        social_Facebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                social_FacebookActionPerformed(evt);
            }
        });
        menu_Social_Media.add(social_Facebook);

        social_Twitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_SocialMedia/social_Twitter.png"))); // NOI18N
        social_Twitter.setText("Proximity Twitter");
        social_Twitter.setToolTipText("Follow Us On Twitter");
        social_Twitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                social_TwitterActionPerformed(evt);
            }
        });
        menu_Social_Media.add(social_Twitter);

        proximity_Menu.add(menu_Social_Media);

        setJMenuBar(proximity_Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(proximity_Table_Button_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proximity_System_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proximity_Table_Scroll_Pane)
                    .addComponent(proximity_Table_Feature_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(proximity_Separator1, javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proximity_Statusbar_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(proximity_Table_Button_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximity_System_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(proximity_Table_Feature_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximity_Table_Scroll_Pane, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)))
                .addGap(3, 3, 3)
                .addComponent(proximity_Separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proximity_Statusbar_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().setBackground(Color.WHITE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ArrayList<File> addFiles = new ArrayList();

    private void table_Add_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Add_ButtonActionPerformed
        // TODO add your handling code here:
        Files_Add aw = new Files_Add(this, true, accountID, table_Folder_ComboBox.getSelectedItem().toString());
        aw.setVisible(true);

        String fodler = aw.getCurrent_Folder();

        folderIDList.clear();
        folderNameList.clear();
        clearTableFiles();
        getAccountFolders();
        table_Folder_ComboBox.setSelectedItem(aw.getCurrent_Folder());

    }//GEN-LAST:event_table_Add_ButtonActionPerformed

    ArrayList<File> filesRemove = new ArrayList();

    private void table_Remove_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Remove_ButtonActionPerformed
        // TODO add your handling code here:

        int tempFolder = table_Folder_ComboBox.getSelectedIndex();
        filesRemove.clear();

        DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            if (tableModel.getValueAt(i, 0).equals(true)) {

                filesRemove.add(filelists.get(i));
            }

        }

        if (!filesRemove.isEmpty()) {

            Files_Remove aw = new Files_Remove(this, true, accountID, filesRemove, (String) table_Folder_ComboBox.getSelectedItem());
            aw.setVisible(true);

            if (aw.isDidAdd()) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.setValueAt(false, i, 0);

                }
                folderIDList.clear();
                folderNameList.clear();
                clearTableFiles();
                getAccountFolders();
                table_Folder_ComboBox.setSelectedIndex(tempFolder);

            }

        } else if (filesRemove.isEmpty()) {

            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog((Component) this,
                    "No Files Selected. Please Try Again.",
                    "Account Creation Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }


    }//GEN-LAST:event_table_Remove_ButtonActionPerformed

    private void table_Select_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Select_ButtonActionPerformed
        // TODO add your handling code here:

        DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            tableModel.setValueAt(true, i, 0);

        }

    }//GEN-LAST:event_table_Select_ButtonActionPerformed

    private void support_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_support_AboutActionPerformed
        // TODO add your handling code here:
        //  AboutSample accountSampleWindow = new AboutSample();
        //       accountSampleWindow.setVisible(true);
        //      accountSampleWindow.setLocationRelativeTo(this);

        JDialog Register = new Support_About(this, true);
        Register.setLocationRelativeTo(this);
        Register.setVisible(true);


    }//GEN-LAST:event_support_AboutActionPerformed

    private void home_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_LogoutActionPerformed
        // TODO add your handling code here:
        //close script
        this.dispose();
        Login_Account als = new Login_Account();
        als.setVisible(true);

        Task task = new Task();
        task.setStatus("Logout");

    }//GEN-LAST:event_home_LogoutActionPerformed

    private void home_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_ExitActionPerformed
        // TODO add your handling code here:

        Task task = new Task();
        task.setStatus("Logout");
        System.exit(0);
    }//GEN-LAST:event_home_ExitActionPerformed

    private void device_CurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_CurrentActionPerformed
        // TODO add your handling code here:
        currentDEV vcd = new currentDEV(this, true);
        vcd.setVisible(true);
    }//GEN-LAST:event_device_CurrentActionPerformed

    private void social_TwitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_social_TwitterActionPerformed
        // TODO add your handling code here:
        openWebpage("www.twitter.com/ProximitySuite");
    }//GEN-LAST:event_social_TwitterActionPerformed

    private void social_FacebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_social_FacebookActionPerformed
        // TODO add your handling code here:
        openWebpage("www.facebook.com/ProximityEncryptionSuite");
    }//GEN-LAST:event_social_FacebookActionPerformed

    private void social_WebsiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_social_WebsiteActionPerformed
        // TODO add your handling code here:
        openWebpage("www.proximitysuite.wix.com/proximitysuite");
    }//GEN-LAST:event_social_WebsiteActionPerformed

    private void table_Deselect_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Deselect_ButtonActionPerformed
        // TODO add your handling code here:

        DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(false, i, 0);

        }

    }//GEN-LAST:event_table_Deselect_ButtonActionPerformed

    private void table_Search_Clear_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Search_Clear_ButtonActionPerformed
        // TODO add your handling code here:
        table_Search_Field.setText("");
    }//GEN-LAST:event_table_Search_Clear_ButtonActionPerformed

    private void support_GuideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_support_GuideActionPerformed
        // TODO add your handling code here:
        String pathPDF = this.getClass().getResource("/Proximity/user_Guide/Proximity_User_Guide.pdf").getPath();
        File filePDF = new File(pathPDF);
        openFile(filePDF);
    }//GEN-LAST:event_support_GuideActionPerformed

    private void folder_CurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_CurrentActionPerformed
        // TODO add your handling code here:

        int tempFolder = table_Folder_ComboBox.getSelectedIndex();

        Folder_Current vcf = new Folder_Current(this, true, accountID, table_Folder_ComboBox.getSelectedItem().toString());
        vcf.setVisible(true);

        folderIDList.clear();
        folderNameList.clear();
        clearTableFiles();
        getAccountFolders();
        table_Folder_ComboBox.setSelectedIndex(tempFolder);


    }//GEN-LAST:event_folder_CurrentActionPerformed

    private void folder_CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_CreateActionPerformed
        // TODO add your handling code here:
        Folder_Create af = new Folder_Create(this, true, accountID);
        af.setVisible(true);

        if (af.isCreatedFolder() == true) {
            folderIDList.clear();
            folderNameList.clear();
            clearTableFiles();
            getAccountFolders();
            table_Folder_ComboBox.setSelectedIndex(table_Folder_ComboBox.getItemCount() - 1);

        } else {
        }
    }//GEN-LAST:event_folder_CreateActionPerformed

    private void clearTableFiles() {
        filelists.clear();
        DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();

        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);

        }
    }

    private void folder_ManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_ManageActionPerformed
        // TODO add your handling code here:
        Folder_Management mf = new Folder_Management(this, true, accountID, table_Folder_ComboBox.getSelectedItem().toString());
        mf.setVisible(true);

        if (mf.isModifyFolder() == true) {
            folderIDList.clear();
            folderNameList.clear();
            clearTableFiles();
            getAccountFolders();
            table_Folder_ComboBox.setSelectedItem(mf.getCurrentFolder());

        } else {
        }
    }//GEN-LAST:event_folder_ManageActionPerformed

    private void device_DisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_DisconnectActionPerformed
        // TODO add your handling code here:
        Object[] options = {"Yes",
            "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "Are You Sure You Want To Disconnect This Device ",
                "Disconnect Device",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0) {

            // delete folder
        }
    }//GEN-LAST:event_device_DisconnectActionPerformed

    private void device_ManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_ManageActionPerformed
        // TODO add your handling code here:
        manageDEV md = new manageDEV(this, true);
        md.setVisible(true);
    }//GEN-LAST:event_device_ManageActionPerformed

    private void folder_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folder_DeleteActionPerformed
        // TODO add your handling code here
        Delete_Folder md = new Delete_Folder(this, true, accountID);
        md.setVisible(true);

        if (md.isDidDelete() == true) {

            folderIDList.clear();
            folderNameList.clear();
            clearTableFiles();
            getAccountFolders();
            table_Folder_ComboBox.setSelectedIndex(0);

        }

    }//GEN-LAST:event_folder_DeleteActionPerformed


    private void home_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_LoginActionPerformed
        // TODO add your handling code here:
        Login_Account mWSameple = new Login_Account();
        mWSameple.setVisible(true);

        Task task = new Task();
        task.setStatus("Logout");

        //pop dialog lock screen
        this.dispose();

    }//GEN-LAST:event_home_LoginActionPerformed

    ArrayList<File> filesEncrypt = new ArrayList();
    ArrayList<File> filesAlreadyEncrypt = new ArrayList();

    private void table_Encrypt_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Encrypt_ButtonActionPerformed
        // TODO add your handling code here:

        int tempFolder = table_Folder_ComboBox.getSelectedIndex();
        filesEncrypt.clear();
        filesAlreadyEncrypt.clear();

        DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();

        String fname;
        int pos;

        for (int i = 0; i < filelists.size(); i++) {

            fname = filelists.get(i).getName();
            pos = fname.lastIndexOf('.');

            if (pos > 0) {
                fname = fname.substring(0, pos);

                if (fname.equals(tableModel.getValueAt(i, 1).toString().trim())) {

                    if (tableModel.getValueAt(i, 0).equals(true) && tableModel.getValueAt(i, 5).equals("Decrypted")) {

                        filesEncrypt.add(filelists.get(i));
                    } else if (tableModel.getValueAt(i, 0).equals(true) && tableModel.getValueAt(i, 5).equals("Encrypted")) {
                        filesAlreadyEncrypt.add(filelists.get(i));
                    }
                }

            } else {

                if (fname.equals(tableModel.getValueAt(i, 1).toString().trim())) {
                    if (tableModel.getValueAt(i, 0).equals(true) && tableModel.getValueAt(i, 5).equals("Decrypted")) {

                        filesEncrypt.add(filelists.get(i));
                    } else if (tableModel.getValueAt(i, 0).equals(true) && tableModel.getValueAt(i, 5).equals("Encrypted")) {
                        filesAlreadyEncrypt.add(filelists.get(i));
                    }
                }
            }

        }

        if (filesAlreadyEncrypt.isEmpty() && !filesEncrypt.isEmpty()) {

            Files_Encryption ew = new Files_Encryption(this, true, accountID, filesEncrypt, accountPass);
            ew.setVisible(true);
            if (ew.isDidAdd()) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.setValueAt(false, i, 0);

                }
                folderIDList.clear();
                folderNameList.clear();
                clearTableFiles();
                getAccountFolders();
                table_Folder_ComboBox.setSelectedIndex(tempFolder);

            }

        } else if (!filesAlreadyEncrypt.isEmpty() && !filesEncrypt.isEmpty()) {
            Object[] options = {"Encrypted Files (Deselect Encrypted Files)", "Canel"};
            int n = JOptionPane.showOptionDialog(this,
                    "One or More Files were Already Encrypted. Would you like To Conitue Without Encryption Them.",
                    "Decrypt Files",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (n == 0) {

                Files_Encryption ew = new Files_Encryption(this, true, accountID, filesEncrypt, accountPass);
                ew.setVisible(true);
                if (ew.isDidAdd()) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        tableModel.setValueAt(false, i, 0);

                    }
                    folderIDList.clear();
                    folderNameList.clear();
                    clearTableFiles();
                    getAccountFolders();
                    table_Folder_ComboBox.setSelectedIndex(tempFolder);

                }
            }
        } else if (filesAlreadyEncrypt.isEmpty() && filesEncrypt.isEmpty()) {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog((Component) this,
                    "No Files Selected. Please Try Again.",
                    "Account Creation Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        } else if (!filesAlreadyEncrypt.isEmpty() && filesEncrypt.isEmpty()) {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog((Component) this,
                    "Only Selected Encrypted Files. Please Try Again.",
                    "Account Creation Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);

        }


    }//GEN-LAST:event_table_Encrypt_ButtonActionPerformed

    //
    ArrayList<File> filesdecrypt = new ArrayList();
    ArrayList<File> filesAlreadydecrypt = new ArrayList();

    private void table_Decrypt_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Decrypt_ButtonActionPerformed
        // TODO add your handling code here:
        table_Search_Field.setText("");
        int tempFolder = table_Folder_ComboBox.getSelectedIndex();
        filesEncrypt.clear();
        filesAlreadyEncrypt.clear();

        String fname;
        int pos;
        //date

        for (int i = 0; i < filelists.size(); i++) {

            fname = filelists.get(i).getName();
            pos = fname.lastIndexOf('.');

            if (pos > 0) {
                fname = fname.substring(0, pos);

                if (fname.equals(table_View.getValueAt(i, 1).toString().trim())) {

                    if (table_View.getValueAt(i, 0).equals(true) && table_View.getValueAt(i, 5).equals("Encrypted")) {

                        filesEncrypt.add(filelists.get(i));
                    } else if (table_View.getValueAt(i, 0).equals(true) && table_View.getValueAt(i, 5).equals("Decrypted")) {
                        filesAlreadyEncrypt.add(filelists.get(i));
                    }
                }

            } else {

                if (fname.equals(table_View.getValueAt(i, 1).toString().trim())) {
                    if (table_View.getValueAt(i, 0).equals(true) && table_View.getValueAt(i, 5).equals("Encrypted")) {

                        filesEncrypt.add(filelists.get(i));
                    } else if (table_View.getValueAt(i, 0).equals(true) && table_View.getValueAt(i, 5).equals("Decrypted")) {
                        filesAlreadyEncrypt.add(filelists.get(i));
                    }
                }
            }

        }

        if (filesAlreadyEncrypt.isEmpty() && !filesEncrypt.isEmpty()) {

            Files_Decryption ew = new Files_Decryption(this, true, accountID, filesEncrypt, accountPass);
            ew.setVisible(true);
            if (ew.isDidAdd()) {
                for (int i = 0; i < table_View.getRowCount(); i++) {
                    table_View.setValueAt(false, i, 0);

                }
                folderIDList.clear();
                folderNameList.clear();
                clearTableFiles();
                getAccountFolders();
                table_Folder_ComboBox.setSelectedIndex(tempFolder);

            }

        } else if (!filesAlreadyEncrypt.isEmpty() && !filesEncrypt.isEmpty()) {
            Object[] options = {"Encrypted Files (Deselect Encrypted Files)", "Canel"};
            int n = JOptionPane.showOptionDialog(this,
                    "One or More Files are not Encrypted. Would you like To Conitue Without decryption Them.",
                    "Decrypt Files",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (n == 0) {

                Files_Decryption ew = new Files_Decryption(this, true, accountID, filesEncrypt, accountPass);
                ew.setVisible(true);
                if (ew.isDidAdd()) {
                    for (int i = 0; i < table_View.getRowCount(); i++) {
                        table_View.setValueAt(false, i, 0);

                    }

                    folderIDList.clear();
                    folderNameList.clear();
                    clearTableFiles();
                    getAccountFolders();
                    table_Folder_ComboBox.setSelectedIndex(tempFolder);
                    ;
                }
            }
        } else if (filesAlreadyEncrypt.isEmpty() && filesEncrypt.isEmpty()) {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog((Component) this,
                    "No Files Selected. Please Try Again.",
                    "Account Creation Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        } else if (!filesAlreadyEncrypt.isEmpty() && filesEncrypt.isEmpty()) {
            Icon crossIcon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Login/graphic_Cross_Icon.png"));
            JOptionPane.showMessageDialog((Component) this,
                    "Only Selected Encrypted Files. Please Try Again.",
                    "Account Creation Error!",
                    JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);

        }


    }//GEN-LAST:event_table_Decrypt_ButtonActionPerformed

    int selectedIndex = -1;
    private void table_Folder_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_Folder_ComboBoxActionPerformed
        // TODO add your handling code here:

        int encryptCounter = 0;
        int decryptCounter = 0;

        if (table_Folder_ComboBox.getSelectedIndex() != selectedIndex) {
            table_Search_Field.setText("");

            DefaultTableModel tableModel = (DefaultTableModel) table_View.getModel();
            sorter = new TableRowSorter<>(model);
            table_View.setRowSorter(sorter);
            sorter.setSortable(6, false);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(false, i, 0);

            }
            filelists.clear();
            clearTableFiles();

            getFolderFiles((String) table_Folder_ComboBox.getSelectedItem());

            for (int i = 0; i < tableModel.getRowCount(); i++) {

                if (tableModel.getValueAt(i, 5).equals("Encrypted")) {
                    encryptCounter++;
                } else if (tableModel.getValueAt(i, 5).equals("Decrypted")) {
                    {
                        decryptCounter++;
                    }
                }
            }

            jLabel2.setText("Encrypted Files: " + encryptCounter);
            jLabel3.setText("Decrypted Files: " + decryptCounter);
            jLabel1.setText("Total Files: " + tableModel.getRowCount());
        }
        selectedIndex = table_Folder_ComboBox.getSelectedIndex();
    }//GEN-LAST:event_table_Folder_ComboBoxActionPerformed

    private void table_Search_FieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_table_Search_FieldCaretUpdate
        // TODO add your handling code here:

        if (table_Search_Field.getText().length() != 0) {

            searchAll();
        }

    }//GEN-LAST:event_table_Search_FieldCaretUpdate

    private void account_ModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_ModifyActionPerformed
        // TODO add your handling code here:

        int tempFolder = table_Folder_ComboBox.getSelectedIndex();

        Acccount_Management md = new Acccount_Management(this, true, accountID);
        md.setVisible(true);

        folderIDList.clear();
        folderNameList.clear();
        clearTableFiles();
        getAccountDetails();
        table_Folder_ComboBox.setSelectedIndex(tempFolder);


    }//GEN-LAST:event_account_ModifyActionPerformed

    private void account_CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_CreateActionPerformed
        // TODO add your handling code here:
        new Login_Account_Create("Main").setVisible(true);
    }//GEN-LAST:event_account_CreateActionPerformed

    private void account_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_DeleteActionPerformed
        // TODO add your handling code here:
        Delete_Account md = new Delete_Account(this, true, accountID);
        md.setVisible(true);
        Task task = new Task();
        task.setStatus("Logout");
        // logout account

    }//GEN-LAST:event_account_DeleteActionPerformed

    private void account_CurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_CurrentActionPerformed
        // TODO add your handling code here:

        int tempFolder = table_Folder_ComboBox.getSelectedIndex();

        Account_Current md = new Account_Current(this, true, accountID);
        md.setVisible(true);

        folderIDList.clear();
        folderNameList.clear();
        clearTableFiles();
        getAccountDetails();
        table_Folder_ComboBox.setSelectedIndex(tempFolder);


    }//GEN-LAST:event_account_CurrentActionPerformed

    private void table_ViewFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_table_ViewFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_table_ViewFocusLost

    private void device_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_device_AddActionPerformed
        // TODO add your handling code here:
        Device_Add da = new Device_Add (accountID);
        da.setVisible(true);
    }//GEN-LAST:event_device_AddActionPerformed

    private void searchAll() {

        String expr = table_Search_Field.getText();
        //stop feilds from filtering

        if (expr.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + expr, 1));
        }

    }

    private void openWebpage(String url) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
            }
        }
    }

    void openFile(File file1) {

        boolean isWindows = false;
        boolean isLinux = false;
        boolean isMac = false;
        boolean couldOpenWithDesktop = false;
        boolean couldOpenWithDesktop1 = false;

        String os = System.getProperty("os.name").toLowerCase();
        isWindows = os.contains("win");
        isLinux = os.contains("nux") || os.contains("nix");
        isMac = os.contains("mac");

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file1);
                couldOpenWithDesktop = true;
            } else {
                couldOpenWithDesktop = false;
            }
        } catch (IOException ioe) {
            couldOpenWithDesktop = false;
        }
        if (couldOpenWithDesktop == false) {

            if (isLinux == true || isMac == true) {
                try {
                    Process process = Runtime.getRuntime().exec("/usr/bin/open" + file1.toURI());
                    couldOpenWithDesktop1 = true;
                } catch (IOException ex) {
                    couldOpenWithDesktop1 = false;
                }
            } else if (isWindows == true) {
                try {
                    Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file1);

                    couldOpenWithDesktop1 = true;
                } catch (Exception exception) {
                    System.err.println("Exception occured: " + exception);
                    couldOpenWithDesktop1 = false;
                }
            } else {
                ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Error/page_error.png"));

                String filePath = file1.getAbsolutePath();

                JOptionPane.showMessageDialog(this.getRootPane(),
                        "File Located: \n" + filePath + "\nCannot Be Opened",
                        "File Canot Be Opened UNSUPPORTED OPERATING SYSTEM",
                        JOptionPane.INFORMATION_MESSAGE,
                        icon);
            }

            //check if it opened file
        }

        if (couldOpenWithDesktop1 == false && couldOpenWithDesktop == false) {

            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Error/page_error.png"));

            String filePath = file1.getAbsolutePath();

            JOptionPane.showMessageDialog(this.getRootPane(),
                    "File Located: \n" + filePath + "\nCannot Be Opened",
                    "File Canot Be Opened",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon);
        }
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
            java.util.logging.Logger.getLogger(Suite_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Suite_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Suite_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Suite_Window.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        Suite_Window p = new Suite_Window(30, "asdas", null);
        p.setVisible(true);
    }

    private TableModel model;
    TableRowSorter<TableModel> sorter;
    long start = System.currentTimeMillis();
    private boolean selected = false;
    private javax.swing.JPopupMenu popup;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem account_Create;
    private javax.swing.JMenuItem account_Current;
    private javax.swing.JMenuItem account_Delete;
    private javax.swing.JMenuItem account_Modify;
    private javax.swing.JPopupMenu.Separator account_Separator1;
    private javax.swing.JPopupMenu.Separator account_Separator2;
    private javax.swing.JMenuItem device_Add;
    private javax.swing.JMenuItem device_Current;
    private javax.swing.JMenuItem device_Disconnect;
    private javax.swing.JMenuItem device_Manage;
    private javax.swing.JPopupMenu.Separator device_Separator2;
    private javax.swing.JPopupMenu.Separator device_Separator3;
    private javax.swing.JMenuItem folder_Create;
    private javax.swing.JMenuItem folder_Current;
    private javax.swing.JMenuItem folder_Delete;
    private javax.swing.JMenuItem folder_Manage;
    private javax.swing.JPopupMenu.Separator folder_Separator1;
    private javax.swing.JPopupMenu.Separator folder_Separator2;
    private javax.swing.JMenuItem home_Exit;
    private javax.swing.JMenuItem home_Login;
    private javax.swing.JMenuItem home_Logout;
    private javax.swing.JPopupMenu.Separator home_Separator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu menu_Account;
    private javax.swing.JMenu menu_Device;
    private javax.swing.JMenu menu_Folder;
    private javax.swing.JMenu menu_Home;
    private javax.swing.JMenu menu_Social_Media;
    private javax.swing.JMenu menu_Support;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuBar proximity_Menu;
    private javax.swing.JSeparator proximity_Separator1;
    private javax.swing.JPanel proximity_Statusbar_Panel;
    private javax.swing.JPanel proximity_System_Panel;
    private javax.swing.JPanel proximity_Table_Button_Panel;
    private javax.swing.JPanel proximity_Table_Feature_Panel;
    private javax.swing.JScrollPane proximity_Table_Scroll_Pane;
    private javax.swing.JMenuItem social_Facebook;
    private javax.swing.JPopupMenu.Separator social_Separator1;
    private javax.swing.JMenuItem social_Twitter;
    private javax.swing.JMenuItem social_Website;
    private javax.swing.JLabel status_Device_Label;
    private javax.swing.JLabel status_Session_Label;
    private javax.swing.JLabel statusbar_Date_Label;
    private javax.swing.JMenuItem support_About;
    private javax.swing.JMenuItem support_Guide;
    private javax.swing.JPopupMenu.Separator support_Separator;
    private javax.swing.JButton table_Add_Button;
    private javax.swing.JButton table_Decrypt_Button;
    private javax.swing.JButton table_Deselect_Button;
    private javax.swing.JButton table_Encrypt_Button;
    private javax.swing.JComboBox table_Folder_ComboBox;
    private javax.swing.JLabel table_Folder_Label;
    private javax.swing.JButton table_Remove_Button;
    private javax.swing.JButton table_Search_Clear_Button;
    private javax.swing.JTextField table_Search_Field;
    private javax.swing.JLabel table_Search_Label;
    private javax.swing.JButton table_Select_Button;
    private javax.swing.JTable table_View;
    // End of variables declaration//GEN-END:variables

    class ImageRenderer extends DefaultTableCellRenderer {

        ImageIcon icon;

        public ImageRenderer() {
            setVerticalAlignment(CENTER);
            setHorizontalAlignment(CENTER);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            String fileType = value.toString();

            icon = switchIcon(fileType);

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor(Color.WHITE));
            }

            String s = switchToolTip(fileType);
            setToolTipText(s);
            setIcon(icon);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);

            return this;
        }

        private String switchToolTip(String fileType) {
            String toolTip = null;

            switch (fileType.toLowerCase()) {

                case "not supported":
                    toolTip = "Encryption Not Supported";
                    break;
                case "decrypted":
                    toolTip = "File Not Encrypted";
                    break;
                case "encrypted":
                    toolTip = "File Encrypted";
                    break;

                default:
                    toolTip = fileType;
                    break;
            }
            return toolTip;
        }

        private ImageIcon switchIcon(String fileType) {
            ImageIcon file_Type_Icon;

            switch (fileType.toLowerCase()) {
                case ".3pg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_3pg.png"));
                    break;

                case ".7z":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_7z.png"));
                    break;
                case ".ace":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ace.png"));
                    break;
                case ".ai":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ai.png"));
                    break;
                case ".aif":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_aif.png"));
                    break;
                case "aiff":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_aiff.png"));
                    break;
                case ".amr":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_amr.png"));
                    break;
                case ".asf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_asf.png"));
                    break;
                case ".asx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_asx.png"));
                    break;
                case ".bat":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_bat.png"));
                    break;
                case ".bin":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_bin.png"));
                    break;
                case ".bmp":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_bmp.png"));
                    break;
                case ".bup":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_bup.png"));
                    break;
                case ".cab":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_cab.png"));
                    break;

                case ".cbr":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_cbr.png"));
                    break;

                case ".cda":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_cda.png"));
                    break;
                case ".cdl":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_cdl.png"));
                    break;
                case ".cdr":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_cdr.png"));
                    break;
                case ".chm":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_chm.png"));
                    break;
                case "dat":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dat.png"));
                    break;
                case ".divx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_divx.png"));
                    break;
                case ".dll":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dll.png"));
                    break;
                case ".dmg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dmg.png"));
                    break;
                case ".docx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_doc.png"));
                    break;
                case ".doc":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_doc.png"));
                    break;
                case ".dss":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dss.png"));
                    break;
                case ".dvf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dvf.png"));
                    break;
                case ".dwg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_dwg.png"));
                    break;
                case ".eml":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_eml.png"));
                    break;
                case ".eps":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_eps.png"));
                    break;
                case ".exe":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_exe.png"));
                    break;
                case ".fla":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_fla.png"));
                    break;
                case ".flv":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_flv.png"));
                    break;
                case ".gif":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_gif.png"));
                    break;
                case ".gz":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_gz.png"));
                    break;
                case ".hqx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_hqx.png"));
                    break;
                case ".htm":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_htm.png"));
                    break;
                case ".html":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_html.png"));
                    break;
                case ".xml":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_html.png"));
                    break;
                case ".ifo":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ifo.png"));
                    break;

                case ".indd":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_indd.png"));
                    break;

                case ".iso":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_iso.png"));
                    break;

                case ".jar":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_jar.png"));
                    break;

                case ".jpeg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_jpeg.png"));
                    break;

                case ".jpg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_jpg.png"));
                    break;

                case ".lnk":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_lnk.png"));
                    break;
                case ".log":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_log.png"));
                    break;
                case ".m4a":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_m4a.png"));
                    break;
                case ".m4b":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_m4b.png"));
                    break;
                case "m4p":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_m4p.png"));
                    break;
                case ".m4v":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_m4v.png"));
                    break;
                case ".mcd":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mcd.png"));
                    break;
                case ".mpp":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mpp.png"));
                    break;
                case ".accdb":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mdb.png"));
                    break;
                case ".mid":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mid.png"));
                    break;
                case ".mov":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mov.png"));
                    break;
                case ".mp3":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mp2.png"));
                    break;
                case ".mp2":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mp2.png"));
                    break;
                case ".mp4":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mp4.png"));
                    break;
                case ".mpeg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mpeg.png"));
                    break;

                case ".mpg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mpg.png"));
                    break;

                case ".msi":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_msi.png"));
                    break;
                case ".mswmm":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_mswmm.png"));
                    break;
                case ".ogg":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ogg.png"));
                    break;
                case ".pdf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_pdf.png"));
                    break;
                case ".png":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_png.png"));
                    break;
                case ".pptx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_pps.png"));
                    break;
                case ".ps":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ps.png"));
                    break;
                case ".psd":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_psd.png"));
                    break;
                case ".pst":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_pst.png"));
                    break;
                case ".ptb":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ptb.png"));
                    break;
                case ".pub":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_pub.png"));
                    break;
                case ".qbb":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_qbb.png"));
                    break;
                case ".qbw":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_qbw.png"));
                    break;
                case ".qxd":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_qxd.png"));
                    break;
                case ".ram":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ram.png"));
                    break;
                case ".rar":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_rar.png"));
                    break;
                case ".rm":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_rm.png"));
                    break;
                case ".rmvb":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_rmvb.png"));
                    break;
                case ".rtf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_rtf.png"));
                    break;
                case ".sea":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_sea.png"));
                    break;
                case ".ses":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ses.png"));
                    break;
                case ".sit":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_sit.png"));
                    break;
                case ".sitx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_sitx.png"));
                    break;
                case ".ss":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ss.png"));
                    break;
                case ".swf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_swf.png"));
                    break;
                case ".tgz":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_tgz.png"));
                    break;
                case ".thm":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_thm.png"));
                    break;
                case ".tif":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_tif.png"));
                    break;

                case ".tmp":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_tmp.png"));
                    break;

                case ".torrent":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_torrent.png"));
                    break;
                case ".ttf":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_ttf.png"));
                    break;
                case ".txt":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_txt.png"));
                    break;
                case ".ini":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_txt.png"));
                    break;
                case ".nfo":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_txt.png"));
                    break;
                case ".jnt":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_txt.png"));
                    break;
                case ".vcd":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_vcd.png"));
                    break;
                case "vob":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_vob.png"));
                    break;
                case ".wav":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_wav.png"));
                    break;
                case ".wma":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_wma.png"));
                    break;
                case ".wmv":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_wmv.png"));
                    break;
                case ".wps":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_wps.wps"));
                    break;
                case ".xlsx":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_xls.png"));
                    break;
                case ".xpi":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_xpi.png"));
                    break;
                case ".zip":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_zip.png"));
                    break;
                case ".m3u":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_wmv.png"));
                    break;

                case "not supported":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Status/lock_break.png"));
                    break;
                case "decrypted":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Status/lock_open.png"));
                    break;
                case "encrypted":
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Status/lock.png"));
                    break;

                default:
                    file_Type_Icon = new javax.swing.ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_File/file_extension_blank.png"));
                    break;
            }

            return file_Type_Icon;
        }

    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        ImageIcon deleteButtonIcon = new ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Buttons/table_Delete.png"));
        ImageIcon openButtonIcon = new ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Buttons/table_Open.png"));

        public ButtonRenderer() {

            setVerticalAlignment(CENTER);
            setHorizontalAlignment(CENTER);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                setFocusPainted(false);
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setFocusPainted(false);
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            if (value == "Delete") {
                setIcon(deleteButtonIcon);
            } else if (value == "Open") {
                setIcon(openButtonIcon);
            }
            return this;
        }
    }

    /**
     * @version 1.0 11/09/98
     */
    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;

        private String label;

        ImageIcon deleteButtonIcon = new ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Buttons/table_Delete.png"));

        ImageIcon openButtonIcon = new ImageIcon(getClass().getResource("/Proximity/graphic_Table/graphic_Buttons/table_Open.png"));

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            ;

            if (isSelected) {
                button.setFocusPainted(false);
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setFocusPainted(false);
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            if (value == "Delete") {
                button.setIcon(deleteButtonIcon);
            } else if (value == "Open") {
                button.setIcon(openButtonIcon);
            }
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {

                if (button.getText().equalsIgnoreCase("Open")) {

                    String fname;
                    int pos;
                    //date

                    for (int i = 0; i < filelists.size(); i++) {

                        fname = filelists.get(i).getName();
                        pos = fname.lastIndexOf('.');

                        if (pos > 0) {
                            fname = fname.substring(0, pos);

                            if (fname.equals(table_View.getValueAt(table_View.getSelectedRow(), 1).toString().trim())) {
                                openFile(filelists.get(i));

                            }

                        } else {

                            if (fname.equals(table_View.getValueAt(table_View.getSelectedRow(), 1).toString().trim())) {
                                openFile(filelists.get(i));

                            }
                        }

                    }

                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
