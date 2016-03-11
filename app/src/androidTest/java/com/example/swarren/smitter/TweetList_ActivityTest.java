package com.example.swarren.smitter;

import android.test.AndroidTestCase;

import com.example.swarren.smitter.frontend.Login_Activity;
import com.firebase.client.Firebase;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TweetList_ActivityTest extends AndroidTestCase {

    public TweetList_ActivityTest(){

    }
    Login_Activity login;
    private final String FIREBASE_URL = "https://brilliant-heat-7188.firebaseio.com";
    Firebase testFirebase;

    public void testSomething() throws Throwable {
        assertEquals("hi", "hi");
    }
}