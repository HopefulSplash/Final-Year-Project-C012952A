package Proximity_Encryption_Suite;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class Files_EncryptionIT {

    public Files_EncryptionIT() {
    }

    /**
     * Test of isDid_Encrypt method, of class Files_Encryption.
     */
    @Test
    public void testIsDid_Encrypt() {
         ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Encryption instance = new Files_Encryption(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        boolean expResult = false;
        boolean result = instance.isDidEncrypt();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDid_Encrypt method, of class Files_Encryption.
     */
    @Test
    public void testSetDid_Encrypt() {
        boolean did_Encrypt = false;
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Encryption instance = new Files_Encryption(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        instance.setDidEncrypt(did_Encrypt);
    }
}

