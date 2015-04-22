package Proximity_Encryption_Suite;

//STEP 1. Import required packages
import java.sql.*;

public class Suite_Database {

    // JDBC driver name and database URL
    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:8888";
    final String CONNECT_DB_URL = "jdbc:mysql://localhost:8888/Proximity_Suite_DB";

    //  Suite_Database credentials
    final String USER = "root";
    final String PASS = "Password123$";

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }

    public void startDatabase() {

        Connection conn = null;
        Statement stmt = null;

        try {
            if (checkDBExists("Proximity_Suite_DB") == true) {

            } else {
                createDatabase();
            }
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public String getCONNECT_DB_URL() {
        return CONNECT_DB_URL;
    }

    private void createDatabase() {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            stmt = conn.createStatement();

            String createDatabase = "CREATE DATABASE Proximity_Suite_DB";
            stmt.executeUpdate(createDatabase);

            conn = DriverManager.getConnection(CONNECT_DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String createAccount = "CREATE TABLE Account_Details\n"
                    + "(\n"
                    + "account_Details_ID int NOT NULL AUTO_INCREMENT,\n"
                    + "account_Username varchar (255) NOT NULL,\n"
                    + "account_Password varchar (40) NOT NULL,\n"
                    + "account_Email varchar (255)NOT NULL,\n"
                    + "account_Question varchar (80) NOT NULL,\n"
                    + "account_Answer varchar (255) NOT NULL,\n"
                    + "\n"
                    + "CONSTRAINT pk_Account_ID PRIMARY KEY (account_Details_ID),\n"
                    + "CONSTRAINT uc_Account_Email UNIQUE (account_Email),\n"
                    + "CONSTRAINT uc_Account_Username UNIQUE (account_Username),\n"
                    + "CONSTRAINT chk_Account_ID CHECK (account_Details_ID > 0),\n"
                    + "CONSTRAINT chk_Account_Question CHECK (account_Question = \"What was your childhood nickname?\"\n"
                    + "					OR account_Question = \"In what city did you meet your spouse/significant other?\"\n"
                    + "					OR account_Question = \"What is the name of your favorite childhood friend? \"\n"
                    + "					OR account_Question = \"What street did you live on in third grade?\"\n"
                    + "					OR account_Question = \"What is the middle name of your oldest child?\"\n"
                    + "					OR account_Question = \"What school did you attend for sixth grade?\"\n"
                    + "					OR account_Question = \"What the name of your first pet?\"\n"
                    + "					OR account_Question = \"What was the name of your first stuffed animal?\"\n"
                    + "					OR account_Question = \"In what city or town did your mother and father meet?\"\n"
                    + "					OR account_Question = \"Where were you when you had your first kiss?\"\n"
                    + "					OR account_Question = \"What is the first name of the boy or girl that you first kissed?\"\n"
                    + "					OR account_Question = \"What was the last name of your third grade teacher?\"\n"
                    + "					OR account_Question = \"In what city does your nearest sibling live?\"\n"
                    + "					OR account_Question = \"In what city or town was your first job?\"\n"
                    + "					OR account_Question = \"What is the name of the place your wedding reception was held?\")\n"
                    + ");";

            stmt.executeUpdate(createAccount);

            String createDevice = "CREATE TABLE Device_Details\n"
                    + "(\n"
                    + "device_Details_ID int  NOT NULL AUTO_INCREMENT,\n"
                    + "device_Name varchar (80) NOT NULL,\n"
                    + "device_Address varchar (12) NOT NULL,\n"
                    + "device_Type varchar (40) NOT NULL,\n"
                    + "device_Description varchar (255) NOT NULL,\n"
                    + "device_Created datetime NOT NULL DEFAULT NOW(),\n"
                    + "\n"
                    + "CONSTRAINT pk_Device_ID PRIMARY KEY (device_Details_ID),\n"
                    + "CONSTRAINT chk_Device_ID CHECK (device_Details_ID > 0),\n"
                    + "CONSTRAINT chk_Device_Type CHECK (device_Type = \"Smartphone\"\n"
                    + "                                 OR device_Type = \"Smartwatch\"\n"
                    + "					OR device_Type = \"Smartband\"\n"
                    + "					OR device_Type = \"Tablet\"\n"
                    + "					OR device_Type = \"Laptop\"\n"
                    + "					OR device_Type = \"Other\"\n"
                    + "					OR device_Type = \"Default\"))\n;";
            stmt.executeUpdate(createDevice);

            String createAccountDevice = "CREATE TABLE Account_Device_List\n"
                    + "(\n"
                    + "account_Device_List_ID int  NOT NULL AUTO_INCREMENT,\n"
                    + "account_Details_ID int NOT NULL,\n"
                    + "device_Details_ID int NOT NULL,\n"
                    + "\n"
                    + "CONSTRAINT pk_Account_Device_List_ID PRIMARY KEY (account_Device_List_ID),\n"
                    + "CONSTRAINT chk_Account_Device_List_ID CHECK (account_Device_List_ID > 0),\n"
                    + "CONSTRAINT fk_Account_Device_List FOREIGN KEY (account_Details_ID) REFERENCES Account_Details (account_Details_ID),\n"
                    + "CONSTRAINT fk_Device_Account_List FOREIGN KEY (device_Details_ID) REFERENCES Device_Details (device_Details_ID)\n"
                    + ");";

            stmt.executeUpdate(createAccountDevice);

            String createFolder = "CREATE TABLE Folder_Details\n"
                    + "(\n"
                    + "folder_Details_ID int  NOT NULL AUTO_INCREMENT,\n"
                    + "account_Details_ID int  NOT NULL,\n"
                    + "folder_Name varchar (80) NOT NULL,\n"
                    + "folder_Type varchar (40) NOT NULL,\n"
                    + "folder_Description varchar (255) NOT NULL,\n"
                    + "folder_Created datetime NOT NULL DEFAULT NOW(),\n"
                    + "\n"
                    + "CONSTRAINT pk_Folder_ID PRIMARY KEY (folder_Details_ID),\n"
                    + "CONSTRAINT chk_folder_Details_ID CHECK (folder_Details_ID > 0),\n"
                    + "CONSTRAINT fk_Account_Folder_List FOREIGN KEY (account_Details_ID) REFERENCES Account_Details (account_Details_ID),\n"
                    + "CONSTRAINT chk_Folder_Type CHECK (folder_Type = \"Work\"\n"
                    + "					OR folder_Type = \"Home\"\n"
                    + "					OR folder_Type = \"Personal\"\n"
                    + "					OR folder_Type = \"Important\"\n"
                    + "					OR folder_Type = \"Archive\"\n"
                    + "					OR folder_Type = \"Other\")\n"
                    + ");";

            stmt.executeUpdate(createFolder);

            String createFile = "CREATE TABLE File_Details\n"
                    + "(\n"
                    + "file_Details_ID int  NOT NULL AUTO_INCREMENT,\n"
                    + "file_Directory varchar (255) NOT NULL,\n"
                    + "file_EStatus boolean NOT NULL DEFAULT 0,\n"
                    + "file_EType varchar (40),\n"
                    + "file_Added datetime NOT NULL DEFAULT NOW(),\n"
                    + "\n"
                    + "CONSTRAINT pk_File_ID PRIMARY KEY (file_Details_ID),\n"
                    + "CONSTRAINT chk_File_Details_ID CHECK (file_Details_ID > 0),\n"
                    + "CONSTRAINT chk_File_EType CHECK (file_EType = \"AES Encryption\"\n"
                    + "                                OR file_EType = \"DES Encryption\"\n"
                    + "                                OR file_EType = \"Triple DES Encryption\"\n"
                    + "                                OR file_EType = \"Custom\")\n"
                    + ");";

            stmt.executeUpdate(createFile);

            String createFolderFile = "CREATE TABLE Folder_File_List\n"
                    + "(\n"
                    + "folder_File_List_ID int  NOT NULL AUTO_INCREMENT,\n"
                    + "folder_Details_ID int NOT NULL,\n"
                    + "file_Details_ID int NOT NULL,\n"
                    + "\n"
                    + "CONSTRAINT pk_Folder_File_List_ID PRIMARY KEY (folder_File_List_ID),\n"
                    + "CONSTRAINT chk_Folder_File_List_ID CHECK (folder_File_List_ID > 0),\n"
                    + "CONSTRAINT fk_Folder_Device_List FOREIGN KEY (folder_Details_ID) REFERENCES Folder_Details (folder_Details_ID),\n"
                    + "CONSTRAINT fk_File_Account_List FOREIGN KEY (file_Details_ID) REFERENCES File_Details (file_Details_ID)\n"
                    + ");";

            stmt.executeUpdate(createFolderFile);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private boolean checkDBExists(String dbName) {

        Connection conn = null;

        try {
            Class.forName(JDBC_DRIVER); //Register JDBC Driver

            conn = DriverManager.getConnection(DB_URL, USER, PASS); //Open a connection
            try (ResultSet resultSet = conn.getMetaData().getCatalogs()) {
                while (resultSet.next()) {

                    String databaseName = resultSet.getString(1);
                    if (databaseName.equalsIgnoreCase(dbName)) {
                        return true;
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}//end JDBCExample
