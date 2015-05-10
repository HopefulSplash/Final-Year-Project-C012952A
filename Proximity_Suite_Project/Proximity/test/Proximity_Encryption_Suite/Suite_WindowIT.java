
package Proximity_Encryption_Suite;

import org.junit.Test;
import static org.junit.Assert.*;

public class Suite_WindowIT {
    
    public Suite_WindowIT() {
    }
    /**
     * Test of getAccountDetails method, of class Suite_Window.
     */
    @Test
    public void testGetAccountDetails() {
        Suite_Window instance = new Suite_Window(1, "Account", null, -1, null);
        instance.getAccountDetails();
    }

    /**
     * Test of removeFile method, of class Suite_Window.
     */
    @Test
    public void testRemoveFile() {
        int fileID = 1;
        Suite_Window instance = new Suite_Window(1, "Account", null, -1, null);
        instance.removeFile(fileID);

    }
    /**
     * Test of getFileSize method, of class Suite_Window.
     */
    @Test
    public void testGetFileSize() {
        double fileLength = 111111.111;
        Suite_Window instance = new Suite_Window(1, "Account", null, -1, null);
        String expResult = "108.51 KB";
        String result = instance.getFileSize(fileLength);
        assertEquals(expResult, result);
    }  
}

