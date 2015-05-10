package Proximity_Encryption_Suite;

import Proximity_Encryption_Suite.Suite_Window.Task;
import static org.junit.Assert.*;
import org.junit.Test;

public class Test_Main {

    Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
    Task task = mainGUI.new Task(mainGUI);

    public Test_Main() {
    }

    /**
     * Testing if the getAccountID method works as intended.
     */
    @Test
    public void test_Get_Account_ID() {
        mainGUI.deviceID = 1;
        task.getAccountID();
        
        assertEquals(1, mainGUI.accountID);
    }
    
       /**
     * Testing if the getFileID method works as intended.
     */
    @Test
    public void test_Get_File_ID() {
        String expected = "C:\\Users\\HopefulSplash\\Desktop\\Final-Year-Project-C012952A\\Proximity_Suite_Project\\Test Files\\arch2.dwg";
        String result = task.getFileID(2);
        
        assertEquals(expected, result);
    }

}

