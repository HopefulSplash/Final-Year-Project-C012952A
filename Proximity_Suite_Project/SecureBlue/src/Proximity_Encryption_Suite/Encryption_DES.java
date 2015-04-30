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
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * The Encryption_DES.Java Class implements an method to encrypt files using the
 * encryption standard of DES.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Encryption_DES {

    boolean encrypted = false;

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
        byte[] iv = {
            (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2,
            (byte) 0x44, (byte) 0x21, (byte) 0xC3, (byte) 0xC3
        };
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey, paramSpec);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
            encrypted = true;
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey, paramSpec);
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
