
package Proximity_Encryption_Suite;

import org.junit.Test;
import static org.junit.Assert.*;

public class Suite_DatabaseIT {
    
    public Suite_DatabaseIT() {
    }
    /**
     * Test of getJDBC_DRIVER method, of class Suite_Database.
     */
    @Test
    public void testGetJDBC_DRIVER() {
        Suite_Database instance = new Suite_Database();
        String expResult = "com.mysql.jdbc.Driver";
        String result = instance.getJDBC_DRIVER();
        assertEquals(expResult, result);
    }
    /**
     * Test of getDB_URL method, of class Suite_Database.
     */
    @Test
    public void testGetDB_URL() {
        Suite_Database instance = new Suite_Database();
        String expResult = "jdbc:mysql://localhost:8888";
        String result = instance.getDB_URL();
        assertEquals(expResult, result);
    }

    /**
     * Test of startDatabase method, of class Suite_Database.
     */
    @Test
    public void testStartDatabase() {
        Suite_Database instance = new Suite_Database();
        instance.startDatabase();
    }
    /**
     * Test of getCONNECT_DB_URL method, of class Suite_Database.
     */
    @Test
    public void testGetCONNECT_DB_URL() {
        Suite_Database instance = new Suite_Database();
        String expResult = "jdbc:mysql://localhost:8888/Proximity_Suite_DB";
        String result = instance.getCONNECT_DB_URL();
        assertEquals(expResult, result);
    }
    
}

