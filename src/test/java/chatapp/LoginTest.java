package chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login("John", "Doe");
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login("John", "Doe");
        assertFalse(login.checkUserName("kyle!!!!!!!"));
        String expectedMessage = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expectedMessage, login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login login = new Login("John", "Doe");
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Login login = new Login("John", "Doe");
        assertFalse(login.checkPasswordComplexity("password"));
        String expectedMessage = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expectedMessage, login.registerUser("kyl_1", "password"));
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login("John", "Doe");
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login("John", "Doe");
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login("John", "Doe");
        login.registerUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertEquals("Welcome John, Doe it is great to see you again.", login.returnLoginStatus(true));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login("John", "Doe");
        login.registerUser("kyl_1", "Ch&&sec@ke99!");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(false));
    }
}