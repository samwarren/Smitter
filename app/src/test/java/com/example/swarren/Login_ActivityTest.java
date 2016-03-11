package com.example.swarren;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.swarren.smitter.frontend.Login_Activity;
import com.firebase.client.Firebase;

import junit.framework.TestCase;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class Login_ActivityTest extends TestCase {
    Login_Activity login;
    private final String FIREBASE_URL = "https://brilliant-heat-7188.firebaseio.com";
    Firebase testFirebase;

    public Login_ActivityTest(){
        login = new Login_Activity();
        testFirebase = new Firebase(FIREBASE_URL);
    }

    @SmallTest
    public void testInValidEmail() {
        boolean invalidBool = login.validEmail("noatsign");
        assertEquals("invalid email", false, invalidBool);
    }

    @SmallTest
    public void testValidEmail() {
        boolean validBool = login.validEmail("money@bank.com");
        assertEquals("invalid email", true, validBool);
    }

    public void testAccountCreation(){
    }
}