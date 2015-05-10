package Proximity_Encryption_Suite;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class Files_RemoveIT {

    public Files_RemoveIT() {
    }

    /**
     * Test of propertyChange method, of class Files_Remove.
     */
    @Test
    public void testPropertyChange() {
        PropertyChangeEvent evt = null;
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Remove instance = new Files_Remove(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        instance.propertyChange(evt);

    }
    /**
     * Test of isDidRemove method, of class Files_Remove.
     */
    @Test
    public void testIsDidRemove() {
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Remove instance = new Files_Remove(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        boolean expResult = false;
        boolean result = instance.isDidRemove();
        assertEquals(expResult, result);

    }
    /**
     * Test of setDidRemove method, of class Files_Remove.
     */
    @Test
    public void testSetDidRemove() {
        boolean didRemove = false;
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Remove instance = new Files_Remove(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        instance.setDidRemove(didRemove);

    }
    /**
     * Test of getSelectedFolder method, of class Files_Remove.
     */
    @Test
    public void testGetSelectedFolder() {
        String folder_Name = "";
        ArrayList<File> filesEncrypt = new ArrayList();
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Remove instance = new Files_Remove(mainGUI, true, 1, filesEncrypt, "theaccountpassword");
        int expResult = 0;
        int result = instance.getSelectedFolder(folder_Name);
        assertEquals(expResult, result);
    }
}
