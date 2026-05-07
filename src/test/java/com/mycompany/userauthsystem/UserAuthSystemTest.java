
package com.mycompany.applicationsystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationSystemTest {

    // ---------------- USERNAME TEST ----------------
    @Test
    public void testCheckUserName() {

        String username = "ab_cd"; // valid username

        boolean result = ApplicationSystem.checkUserName(username);

        assertTrue(result); // must return true
    }

    // ---------------- PASSWORD TEST ----------------
    @Test
    public void testCheckPassword() {

        String password = "Pass@123"; // valid password

        boolean result = ApplicationSystem.checkPassword(password);

        assertTrue(result); // must return true
    }

    // ---------------- LOGIN TEST ----------------
    @Test
    public void testLogin() {

        String enteredUser = "ab_cd";
        String enteredPass = "Pass@123";

        String savedUser = "ab_cd";
        String savedPass = "Pass@123";

        boolean result = ApplicationSystem.login(
                enteredUser,
                enteredPass,
                savedUser,
                savedPass
        );

        assertTrue(result); // login should pass
    }

    // ---------------- PHONE VALIDATION TEST ----------------
    @Test
    public void testValidateNumber() {

        String number = "+27123456789"; // valid number

        String result = ApplicationSystem.validateNumber(number);

        assertEquals("Valid", result); // expected result
    }

    // ---------------- HASH TEST ----------------
    @Test
    public void testCreateMessageHash() {

        String id = "1234567890";
        int number = 0;
        String message = "Hello World";

        String result = ApplicationSystem.createMessageHash(
                id,
                number,
                message
        );

        assertEquals("12:0:HELLOWORLD", result); // expected hash
    }

    // ---------------- STORE MESSAGE TEST ----------------
    @Test
    public void testStoreMessage() {

        assertDoesNotThrow(() -> {

            ApplicationSystem.storeMessage();
        });
    }

    // ---------------- SHOW MESSAGE TEST ----------------
    @Test
    public void testShowMessages() {

        assertDoesNotThrow(() -> {

            ApplicationSystem.showMessages();
        });
    }

    // ---------------- DISCARD MESSAGE TEST ----------------
    @Test
    public void testDiscardMessage() {

        assertDoesNotThrow(() -> {

            ApplicationSystem.discardMessage();
        });
    }

    // ---------------- SIMPLE ARRAY TEST ----------------
    @Test
    public void testArrayNotNull() {

        String[] data = {"user", "pass"};

        assertNotNull(data); // array must exist
    }
}

