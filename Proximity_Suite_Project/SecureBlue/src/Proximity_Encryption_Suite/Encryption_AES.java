/**
 * Defines the package to class belongs to.
 */
package Proximity_Encryption_Suite;
/**
 * Import all of the necessary libraries.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * The Encryption_AES.Java Class implements an method to encrypt files using the 
 * encryption standard of AES.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Encryption_AES {
    private boolean encrypted = false;
    /**
     * a method that will check if the class did encrypt
     * 
     * @return 
     */
    public boolean isEncrypted() {
        return encrypted;
    }
    /**
     * a method to set if the file did encrypt
     * 
     * @param encrypted 
     */
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
    /**
     * a method that will encrypt files
     * 
     * @param key
     * @param is
     * @param os
     * @throws Throwable 
     */
    public void encrypt(String key, InputStream is, OutputStream os) throws Throwable {
        encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
    }
    /**
     * a method that will decrypt files
     * 
     * @param key
     * @param is
     * @param os
     * @throws Throwable 
     */
    public void decrypt(String key, InputStream is, OutputStream os) throws Throwable {
        encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }
    /**
     * a method that will process if the user is encrypting or decrypting files.
     * 
     * @param key
     * @param mode
     * @param is
     * @param os
     * @throws Throwable 
     */
    public void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable {
        // setup variables
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); 

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            CipherInputStream cis = new CipherInputStream(is, cipher);
         
            doCopy(cis, os);
            encrypted = true;
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
            encrypted = true;
        }
    }
    /**
     * a method that will copy the contents of one file into another.
     * 
     * @param is
     * @param os
     * @throws IOException 
     */
    public void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }

}
