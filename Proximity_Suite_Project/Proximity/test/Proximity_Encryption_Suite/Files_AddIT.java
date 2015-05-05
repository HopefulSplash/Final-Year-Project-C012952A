package Proximity_Encryption_Suite;

import org.junit.Test;
import static org.junit.Assert.*;

public class Files_AddIT {

    public Files_AddIT() {
    }
    /**
     * Test of isDidAdd method, of class Files_Add.
     */
    @Test
    public void testIsDidAdd() {
         Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Add instance = new Files_Add(mainGUI, true, 1, "admin's Default Folder");
        boolean expResult = false;
        boolean result = instance.isDidAdd();
        assertEquals(expResult, result);
    }
    /**
     * Test of setDidAdd method, of class Files_Add.
     */
    @Test
    public void testSetDidAdd() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Add instance = new Files_Add(mainGUI, true, 1, "admin's Default Folder");
        boolean didAdd = false;
        instance.setDidAdd(didAdd);
    }
    /**
     * Test of getSelectedFolder method, of class Files_Add.
     */
    @Test
    public void testGetSelectedFolder() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Add instance = new Files_Add(mainGUI, true, 1, "admin's Default Folder");
        String folder_Name = "";
        int expResult = 0;
        int result = instance.getSelectedFolder(folder_Name);
        assertEquals(expResult, result);
    }
    /**
     * Test of getCurrent_Folder method, of class Files_Add.
     */
    @Test
    public void testGetCurrent_Folder() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Files_Add instance = new Files_Add(mainGUI, true, 1, "admin's Default Folder");
        String expResult = "admin's Default Folder";
        String result = instance.getCurrent_Folder();
        assertEquals(expResult, result);
    }
}

