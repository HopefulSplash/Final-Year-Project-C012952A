package Proximity_Encryption_Suite;

import Proximity_Encryption_Suite.Device_Add.Task;
import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test_Device_Thread {

    JFrame j1 = new JFrame();
    Device_Add da = new Device_Add(j1, true, 1);


    public Test_Device_Thread() {
    }

    /**
     * Testing if the check_Device method works as intended.
     */
    @Test
    public void test_Check_Device() {
        Task task = da.new Task();
        //address that is already taken.
        String address = "7E5D466572AF";
        assertEquals(true, task.check_Taken(address));
        //address that isnt already taken.
        String address2 = "7M5Y461546ED";
        assertEquals(false, task.check_Taken(address2));
    }

}
