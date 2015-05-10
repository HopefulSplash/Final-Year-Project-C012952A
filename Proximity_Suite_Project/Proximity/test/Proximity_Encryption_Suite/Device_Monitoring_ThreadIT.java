
package Proximity_Encryption_Suite;

import org.junit.Test;
import static org.junit.Assert.*;

public class Device_Monitoring_ThreadIT {
    
    public Device_Monitoring_ThreadIT() {
    }
    /**
     * Test of run method, of class Device_Monitoring_Thread.
     */
    @Test
    public void test_Device_Thread() {
        Device_Monitoring_Thread dmt = new Device_Monitoring_Thread();
        dmt.start();
        assertEquals(true, dmt.isAlive());
    }
}
