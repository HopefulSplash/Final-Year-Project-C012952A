package Proximity_Encryption_Suite;

import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

public class Encryption_AESIT {
    
    public Encryption_AESIT() {
    }
    /**
     * Test of isEncrypted method, of class Encryption_AES.
     */
    @Test
    public void testIsEncrypted() {
        Encryption_AES instance = new Encryption_AES();
        boolean expResult = false;
        boolean result = instance.isEncrypted();
        assertEquals(expResult, result);
    }
    /**
     * Test of setEncrypted method, of class Encryption_AES.
     */
    @Test
    public void testSetEncrypted() {
        boolean encrypted = false;
        Encryption_AES instance = new Encryption_AES();
        instance.setEncrypted(encrypted);
    }
    /**
     * Test of encrypt method, of class Encryption_AES.
     * @throws java.lang.Exception
     */
    @Test
    public void testEncrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = 0;
        InputStream is = null;
        OutputStream os = null;
        Encryption_AES instance = new Encryption_AES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
     /**
     * Test of decrypt method, of class Encryption_AES.
     * @throws java.lang.Exception
     */
    @Test
    public void testDecrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = -1;
        InputStream is = null;
        OutputStream os = null;
        Encryption_AES instance = new Encryption_AES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
}
