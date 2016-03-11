package com.example.swarren.smitter.tasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.swarren.smitter.R;
import com.example.swarren.smitter.models.Tweet;
import com.firebase.client.Query;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by swarren on 2/24/16.
 */
public class TweetListAdapter extends FirebaseListAdapter<Tweet> {
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    private Context con;
    private Activity activity;
    public final String LOCALLY_STORED_TWEET_LIST= "LocallyStoredTweetList";
    public TweetListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Tweet.class, layout, activity);
        this.mUsername = mUsername;
        System.out.println("XXXX i need to ifnish this app");
        this.activity =activity;
    }

    /**
     * Bind an instance of the <code>Tweet</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Tweet</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param Tweet An instance representing the current state of a tweet
     */
    @Override
    protected void populateView(View view, Tweet tweet) {

        // Map a Tweet object to an entry in our listview
        String username = tweet.getUsername();
        TextView authorText = (TextView) view.findViewById(R.id.username);
        authorText.setText(username + ": ");
        // If the message was sent by this user, color it differently
        if (username != null && username.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.tweetContent)).setText(tweet.getInput());

    }



}
