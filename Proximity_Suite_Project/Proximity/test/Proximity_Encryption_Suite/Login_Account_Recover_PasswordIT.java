package Proximity_Encryption_Suite;

import org.junit.Test;
import static org.junit.Assert.*;

public class Login_Account_Recover_PasswordIT {

    public Login_Account_Recover_PasswordIT() {
    }

    void tearDown() {
    }
    /**
     * Test of setUsername method, of class Login_Account_Recover_Password.
     */
    @Test
    public void testSetUsername() {
        String username = "";
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Login_Account_Recover_Password instance = new Login_Account_Recover_Password(mainGUI, true);
        instance.setUsername(username);

    }
    /**
     * Test of setQuestion method, of class Login_Account_Recover_Password.
     */
    @Test
    public void testSetQuestion() {
        String question = "";
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Login_Account_Recover_Password instance = new Login_Account_Recover_Password(mainGUI, true);
        instance.setQuestion(question);

    }
    /**
     * Test of setAnswer method, of class Login_Account_Recover_Password.
     */
    @Test
    public void testSetAnswer() {
        String answer = "";
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Login_Account_Recover_Password instance = new Login_Account_Recover_Password(mainGUI, true);
        instance.setAnswer(answer);

    }
    /**
     * Test of isShouldAdd method, of class Login_Account_Recover_Password.
     */
    @Test
    public void testIsShouldAdd() {
        Suite_Window mainGUI = new Suite_Window(1, "Account", null, -1, null);
        Login_Account_Recover_Password instance = new Login_Account_Recover_Password(mainGUI, true);
        boolean expResult = false;
        boolean result = instance.isShouldAdd();
        assertEquals(expResult, result);

    }
}

