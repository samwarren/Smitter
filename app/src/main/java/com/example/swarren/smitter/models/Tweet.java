package com.example.swarren.smitter.models;

/**
 * Created by swarren on 2/24/16.
 */
public class Tweet {
    private String username;
    private String input;

    public Tweet() {

    }
    public Tweet(String username, String input){
        this.username = username;
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public String getUsername() {
        return username;
    }
}
