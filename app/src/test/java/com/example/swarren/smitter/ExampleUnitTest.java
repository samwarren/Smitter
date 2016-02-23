package com.example.swarren.smitter;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void tweetListCorrectFBUrl() throws Exception {
        assertEquals(TweetList.class.getResource("FIREBASE_URL"),
                "https://brilliant-heat-7188.firebaseio.com");
    }
}