package Proximity_Encryption_Suite;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class Files_DecryptionIT {

    public Files_DecryptionIT() {
    }

    /**
     * Test of getFileEncryptionStatus method, of class Files_Decryption.
     */
    @Test
    public void testGetFileEncryptionStatus() {
        String file_Path = "C:\\Users\\HopefulSplash\\Desktop\\Final-Year-Project-C012952A\\Proximity_Suite_Project\\Test Files\\Capture.PNG";
         ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Decryption instance = new Files_Decryption(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        String result = instance.getFileEncryptionStatus(file_Path);
        String expected = "0";
        assertEquals(expected, result);
    }

    /**
     * Test of isDid_Decrypt method, of class Files_Decryption.
     */
    @Test
    public void testIsDid_Decrypt() {
         ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Decryption instance = new Files_Decryption(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        boolean expResult = false;
        boolean result = instance.isDid_Decrypt();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDid_Decrypt method, of class Files_Decryption.
     */
    @Test
    public void testSetDid_Decrypt() {
        boolean did_Decrypt = false;
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Decryption instance = new Files_Decryption(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        instance.setDid_Decrypt(did_Decrypt);
    }
}

