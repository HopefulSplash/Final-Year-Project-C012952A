/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;

/**
 * Import all of the necessary libraries.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The Encryption_Script.Java Class implements an method to encrypt files when
 * the user changes there password.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Encryption_Script {

    private final String key;
    private final String function;
    private final int account_ID;
    ArrayList<String> fileDirList = new ArrayList<>();
    ArrayList<Boolean> fileStatusList = new ArrayList<>();

    /**
     * a method that will start the process of changing the key used to encrypt
     * and decrypt files.
     *
     * @param key
     * @param function
     * @param account_ID
     */
    public Encryption_Script(String key, String function, int account_ID) {
        //setup variables
        this.key = key;
        this.function = function;
        this.account_ID = account_ID;
        //do the encryption and decryption
        changeKey(get_Account_Files());
    }

    /**
     * a method that will get all the files in each folder.
     *
     * @return
     */
    private ArrayList<File> get_Account_Files() {
        ArrayList<File> result = new ArrayList<>();

        get_Folder_Files();

        for (int i = 0; i < fileDirList.size(); i++) {
            if (fileStatusList.get(i).equals(true)) {
                result.add(new File(fileDirList.get(i)));
            }

        }

        return result;
    }

    /**
     * a method that will get ever file that is inside a folder
     */
    private void get_Folder_Files() {

        ArrayList<Integer> fileIDList = new ArrayList<>();
        ArrayList<Integer> folderID = new ArrayList<>();
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
        Statement stmt = null;
        try {

            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(d.getCONNECT_DB_URL(), d.getUSER(), d.getPASS());

            String sql = "SELECT folder_Details_ID FROM Folder_Details WHERE account_Details_ID = " + account_ID + ";";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                folderID.add(rs.getInt("folder_Details_ID"));
            }

            for (int i = 0; i < folderID.size(); i++) {
                stmt = conn.createStatement();
                sql = "SELECT file_Details_ID FROM Folder_File_List "
                        + "WHERE folder_Details_ID = ?;";

                PreparedStatement pStmt1 = conn.prepareStatement(sql);
                pStmt1.setInt(1, folderID.get(i));
                rs = pStmt1.executeQuery();

                while (rs.next()) {
                    fileID = rs.getInt("file_Details_ID");
                    if (fileIDList.contains(fileID)) {
                        // file already present
                    } else {
                        fileIDList.add(fileID);
                    }
                }
            }

            get_File_Details(fileIDList);
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
     * a method that will get the details of each file
     *
     * @param fileIDList
     */
    public void get_File_Details(ArrayList<Integer> fileIDList) {

        String fileDir;
        Boolean fileStatus;

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
                String sql = "SELECT file_Directory,  file_EStatus FROM File_Details "
                        + "WHERE file_Details_ID = " + fileIDList.get(i) + ";";

                ResultSet rs = stmt.executeQuery(sql);
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    fileDir = rs.getString("file_Directory");
                    fileStatus = rs.getBoolean("file_EStatus");
                    fileDirList.add(fileDir);
                    fileStatusList.add(fileStatus);
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

    /**
     * a method that will decide whether to encrypt or decrypt files.
     *
     * @param filelist
     */
    private void changeKey(ArrayList<File> filelist) {
        switch (function) {
            case "Decrypt":
                for (int i = 0; i < filelist.size(); i++) {
                    decryptFiles(filelist.get(i));
                }
                break;
            case "Encrypt":
                for (int i = 0; i < filelist.size(); i++) {
                    encryptFiles(filelist.get(i));
                }
                break;
        }
    }

    /**
     * a method that will check if a files needs to be encrypted.
     *
     * @param file
     */
    private void encryptFiles(File file) {
        boolean didEncrypt = false;
        int fileID = 0;

        if (get_File_Encryption_Status(file.getAbsolutePath()).equals("AES Encryption")) {
            didEncrypt = encryptAES(file);

        } else if (get_File_Encryption_Status(file.getAbsolutePath()).equals("DES Encryption")) {
            didEncrypt = encryptDES(file);

        } else if (get_File_Encryption_Status(file.getAbsolutePath()).equals("Triple DES Encryption")) {
            didEncrypt = encryptTripleDES(file);
        } else {
            didEncrypt = false;
        }

    }

    /**
     * a method that will encrypt files that are meant to be encrypted
     *
     * @param file
     * @return
     */
    private boolean encryptAES(File file) {
        boolean encrypted = false;

        if (file.canRead() && file.canWrite() && file.canExecute()) {

            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                int length = key.length();
                String eKey = generateKey(key);

                if (length > 16 && length != 16) {
                    eKey = eKey.substring(0, 16);
                }
                if (length < 16 && length != 16) {
                    for (int i = 0; i < 16 - length; i++) {
                        eKey = eKey + "0";
                    }
                }

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                Encryption_AES aes = new Encryption_AES();
                aes.encrypt(eKey, originalInput, encryptedOutput);

                if (aes.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        aes.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        encrypted = true;
                    } catch (IOException e) {
                        encrypted = false;
                    }
                } else {
                    encrypted = false;
                }

            } catch (Throwable e) {
            }
        }
        return encrypted;
    }

    /**
     * a method that will encrypt files that are meant to be encrypted
     *
     * @param file
     * @return
     */
    private boolean encryptDES(File file) {

        boolean encrypted = false;
        if (file.canRead() && file.canWrite() && file.canExecute()) {
            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                String eKey = generateKey(key);

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                Encryption_DES des = new Encryption_DES();
                des.encrypt(eKey, originalInput, encryptedOutput);

                if (des.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        des.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        encrypted = true;
                    } catch (IOException e) {
                        encrypted = false;
                    }

                } else {
                    encrypted = false;
                }

            } catch (Throwable e) {
            }
        }
        return encrypted;

    }

    /**
     * a method that will encrypt files that are meant to be encrypted
     *
     * @param file
     * @return
     */
    private boolean encryptTripleDES(File file) {
        boolean encrypted = false;
        if (file.canRead() && file.canWrite() && file.canExecute()) {
            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                String eKey = generateKey(key);

                int length = eKey.length();
                if (length > 16 && length != 16) {
                    eKey = eKey.substring(0, 15);
                }
                if (length < 16 && length != 16) {
                    for (int i = 0; i < 16 - length; i++) {
                        eKey = eKey + "0";
                    }
                }

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                Encryption_Triple_DES tdes = new Encryption_Triple_DES();
                tdes.encrypt(eKey, originalInput, encryptedOutput);

                if (tdes.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        tdes.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        encrypted = true;
                    } catch (IOException e) {
                        encrypted = false;
                    }

                } else {
                    encrypted = false;
                }
            } catch (Throwable e) {
            }
        }
        return encrypted;

    }

    /**
     * a method that will check what files are encrypted and with which
     * encryption standard.
     *
     * @param file
     */
    private void decryptFiles(File file) {
        boolean didDecrypt = false;
        int fileID = 0;

        if (get_File_Encryption_Status(file.getAbsolutePath()).equals("AES Encryption")) {
            didDecrypt = decryptAES(file);

        } else if (get_File_Encryption_Status(file.getAbsolutePath()).equals("DES Encryption")) {
            didDecrypt = decryptDES(file);

        } else if (get_File_Encryption_Status(file.getAbsolutePath()).equals("Triple DES Encryption")) {
            didDecrypt = decryptTripleDES(file);
        } else {
            didDecrypt = false;
        }

    }

    /**
     * a method that will generate a key for the encryption.
     *
     * @param key
     * @return
     */
    private String generateKey(String key) {
        String toString = new StringBuilder(key).reverse().toString();
        return toString;
    }

    /**
     * a method that will decrypt files that are encrypted
     *
     * @param file
     * @return
     */
    private boolean decryptAES(File file) {
        boolean decrypted = false;

        if (file.canRead() && file.canWrite() && file.canExecute()) {

            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                String eKey = generateKey(key);
                int length = eKey.length();

                if (length > 16 && length != 16) {
                    eKey = eKey.substring(0, 16);
                }
                if (length < 16 && length != 16) {
                    for (int i = 0; i < 16 - length; i++) {
                        eKey = eKey + "0";
                    }
                }

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                Encryption_AES aes = new Encryption_AES();
                aes.decrypt(eKey, originalInput, encryptedOutput);

                if (aes.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        aes.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        decrypted = true;
                    } catch (IOException e) {
                        decrypted = false;
                    }
                } else {
                    decrypted = false;
                }

            } catch (Throwable e) {
            }
        }
        return decrypted;
    }

    /**
     * a method that will decrypt files that are encrypted
     *
     * @param file
     * @return
     */
    private boolean decryptDES(File file) {

        boolean encrypted = false;
        if (file.canRead() && file.canWrite() && file.canExecute()) {
            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                String eKey = generateKey(key);

                Encryption_DES des = new Encryption_DES();
                des.decrypt(eKey, originalInput, encryptedOutput);

                if (des.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        des.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        encrypted = true;
                    } catch (IOException e) {
                        encrypted = false;
                    }

                } else {
                    encrypted = false;
                }

            } catch (Throwable e) {
            }
        }
        return encrypted;

    }

    /**
     * a method that will decrypt files that are encrypted
     *
     * @param file
     * @return
     */
    private boolean decryptTripleDES(File file) {
        boolean encrypted = false;
        if (file.canRead() && file.canWrite() && file.canExecute()) {
            try {

                File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

                String eKey = generateKey(key);

                int length = eKey.length();
                if (length > 16 && length != 16) {
                    eKey = eKey.substring(0, 15);
                }
                if (length < 16 && length != 16) {
                    for (int i = 0; i < 16 - length; i++) {
                        eKey = eKey + "0";
                    }
                }

                FileInputStream originalInput = new FileInputStream(file);
                FileOutputStream encryptedOutput = new FileOutputStream(temp);

                Encryption_Triple_DES tdes = new Encryption_Triple_DES();
                tdes.decrypt(eKey, originalInput, encryptedOutput);

                if (tdes.isEncrypted()) {
                    FileInputStream encryptedInput = new FileInputStream(temp);
                    try {
                        FileOutputStream originalOutput = new FileOutputStream(file);
                        tdes.doCopy(encryptedInput, originalOutput);
                        temp.delete();
                        encrypted = true;
                    } catch (IOException e) {
                        encrypted = false;
                    }

                } else {
                    encrypted = false;
                }
            } catch (Throwable e) {
            }
        }
        return encrypted;

    }

    /**
     * a method that will check what type of encryption a file has
     *
     * @param file_Path
     * @return
     */
    public String get_File_Encryption_Status(String file_Path) {

        String fileID = null;

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

            String sql = "SELECT file_EType FROM File_Details WHERE file_Directory = ?;";

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, file_Path);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                fileID = rs.getString("file_EType");
            }

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
        return fileID;
    }
}
