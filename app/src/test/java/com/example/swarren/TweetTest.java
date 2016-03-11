package com.example.swarren;

import com.example.swarren.smitter.models.Tweet;

import junit.framework.TestCase;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TweetTest extends TestCase {
    /**
     * Created by swarren on 3/11/16.
     */
    private static final String username = "clintdempsey";
    private static final String tweetInput = "i wish I had scored vs skc";
    public void testUserEmail(){
        Tweet aTweet = new Tweet(username, tweetInput);
        assertEquals(aTweet.getUsername(), username);
    }

    public void testTweetInput(){
        Tweet aTweet = new Tweet(username, tweetInput);
        assertEquals(aTweet.getInput(), tweetInput);
    }
}