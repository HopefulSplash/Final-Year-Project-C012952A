package Proximity_Encryption_Suite;

import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

public class Encryption_Triple_DESIT {
    
    public Encryption_Triple_DESIT() {
    }
    
       /**
     * Test of isEncrypted method, of class Encryption_Triple_DES.
     */
    @Test
    public void testIsEncrypted() {
        Encryption_Triple_DES instance = new Encryption_Triple_DES();
        boolean expResult = false;
        boolean result = instance.isEncrypted();
        assertEquals(expResult, result);
    }
    /**
     * Test of setEncrypted method, of class Encryption_Triple_DES.
     */
    @Test
    public void testSetEncrypted() {
        boolean encrypted = false;
        Encryption_Triple_DES instance = new Encryption_Triple_DES();
        instance.setEncrypted(encrypted);
    }
    /**
     * Test of encrypt method, of class Encryption_Triple_DES.
     * @throws java.lang.Exception
     */
    @Test
    public void testEncrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = 0;
        InputStream is = null;
        OutputStream os = null;
        Encryption_Triple_DES instance = new Encryption_Triple_DES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
     /**
     * Test of decrypt method, of class Encryption_Triple_DES.
     * @throws java.lang.Exception
     */
    @Test
    public void testDecrypt() throws Exception, Throwable {
        String key = "thisisakey";
        int mode = -1;
        InputStream is = null;
        OutputStream os = null;
        Encryption_Triple_DES instance = new Encryption_Triple_DES();
        instance.encryptOrDecrypt(key, mode, is, os);
    }
}

