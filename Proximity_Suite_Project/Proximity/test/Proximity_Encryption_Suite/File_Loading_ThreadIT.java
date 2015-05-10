package Proximity_Encryption_Suite;

import java.io.File;
import org.junit.Test;

public class File_Loading_ThreadIT {

    public File_Loading_ThreadIT() {
    }

    /**
     * Test of setSpeficDir method, of class File_Loading_Thread.
     */
    @Test
    public void testSetSpeficDir() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        File speficDir = new File("C:\\Users\\HopefulSplash\\Desktop\\Final-Year-Project-C012952A\\Proximity_Suite_Project\\Test Files");
        File_Loading_Thread instance = new File_Loading_Thread(mainGUI, speficDir);
        instance.setSpeficDir(speficDir);

    }

    /**
     * Test of run method, of class File_Loading_Thread.
     */
    @Test
    public void testRun() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        File speficDir = new File("C:\\Users\\HopefulSplash\\Desktop\\Final-Year-Project-C012952A\\Proximity_Suite_Project\\Test Files");
        File_Loading_Thread instance = new File_Loading_Thread(mainGUI, speficDir);
    }
}

