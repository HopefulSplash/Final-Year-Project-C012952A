package Proximity_Encryption_Suite;

import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test_Account {

    JFrame j1 = new JFrame();
    Login_Account_Create lac = new Login_Account_Create(j1, true, "Account");

    public Test_Account() {
    }

    /**
     * Testing if the check_Username method works as intended.
     */
    @Test
    public void test_Check_Username() {
        //username that is already taken.
        String username = "admin";
        assertEquals(true, lac.checkUsernameExsists(username));
        //username that isnt already taken.
        String username2 = "test";
        assertEquals(false, lac.checkUsernameExsists(username2));
    }

    /**
     * Testing that the check_Email method works as intended.
     */
    @Test
    public void test_Check_Email() {
        //email address that is already taken.
        String email = "admin@admin.com";
        assertEquals(true, lac.checkEmailExsists(email));
        //email address that isnt already taken.
        String email2 = "test@test.com";
        assertEquals(false, lac.checkEmailExsists(email2));
    }

    /**
     * Testing that the valid_Email method works as intended.
     */
    @Test
    public void test_Valid_Email() {
        //email address that is valid.
        String email = "admin@admin.com";
        assertEquals(true, lac.validateEmail(email));
        //email address that is not valid.
        String email2 = "test@test.com12";
        assertEquals(false, lac.validateEmail(email2));
    }

    /**
     * Testing that the convert_SHA1 method works as intended.
     */
    @Test
    public void test_Convert_SHA1() {
        //email address that is valid.
        String password = "thisisapassword";
        try {
            assertEquals(40, lac.convertToSha1(password).length());
        } catch (NoSuchAlgorithmException ex) {
        }

    }

    /**
     * Testing that the password_Strength method works as intended.
     */
    @Test
    public void test_Password_Strength() {
        //several different passwords and thier strengths
        String password = "pass";
        assertEquals(20, lac.passwordStrength(password));
        String password1 = "pass1";
        assertEquals(40, lac.passwordStrength(password1));
        String password2 = "pass1@";
        assertEquals(60, lac.passwordStrength(password2));
        String password3 = "pass@1F";
        assertEquals(80, lac.passwordStrength(password3));
        String password4 = "pass@1Fed";
        assertEquals(100, lac.passwordStrength(password4));

    }
}
