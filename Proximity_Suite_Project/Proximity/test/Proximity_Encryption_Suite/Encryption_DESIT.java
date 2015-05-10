package Proximity_Encryption_Suite;

import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

public class Encryption_DESIT {

    public Encryption_DESIT() {
    }
    /**
     * Test of isEncrypted method, of class Encryption_DES.
     */
    @Test
    public void testIsEncrypted() {
        Encryption_DES instance = new Encryption_DES();
        boolean expResult = false;
        boolean result = instance.isEncrypted();
        assertEquals(expResult, result);
    }
    /**
     * Test of setEncrypted method, of class Encryption_DES.
     */
    @Test
    public void testSetEncrypted() {
        boolean encrypted = false;
        Encryption_DES instance = new Encryption_DES();
        instance.setEncrypted(encrypted);
    }
    /**
     * Test of encrypt method, of class Encryption_DES.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEncrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = 0;
        InputStream is = null;
        OutputStream os = null;
        Encryption_DES instance = new Encryption_DES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
    /**
     * Test of decrypt method, of class Encryption_DES.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDecrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = -1;
        InputStream is = null;
        OutputStream os = null;
        Encryption_DES instance = new Encryption_DES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
}
