package com.example.swarren.smitter.frontend;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swarren.smitter.R;
import com.example.swarren.smitter.models.Tweet;
import com.example.swarren.smitter.tasks.TweetListAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.FileOutputStream;


/**
 * Created by swarren on 2/17/16.
 */
public class TweetList_Activity extends ListActivity {
    private String FIREBASE_URL;
    private static final int TWEETS_PER_LIST = 50;

    private Firebase firebase;
    private Firebase tFirebase;
    private String userEmail;
    private TweetListAdapter mTweetListAdapter;
    private ValueEventListener mConnectedListener;
    public final String LOCALLY_STORED_TWEET_LIST= "LocallyStoredTweetList";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tweetlist_activity);
        userEmail = getUserEmail(bundle);
        FIREBASE_URL = getFB_URL(bundle);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(FIREBASE_URL);
        tFirebase = new Firebase(FIREBASE_URL).child("tweets");

        FileOutputStream fos;

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.tweetInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendTweet();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTweet();
            }
        });


    }






    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mTweetListAdapter = new TweetListAdapter(tFirebase.limit(TWEETS_PER_LIST),
                this, R.layout.tweet_layout, userEmail);
        listView.setAdapter(mTweetListAdapter);
        mTweetListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mTweetListAdapter.getCount() - 1);
            }
        });

        mConnectedListener = tFirebase.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(TweetList_Activity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TweetList_Activity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    private String getFB_URL(Bundle bundle){
        if (bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                return null;
            } else {
                return extras.getString("FIREBASE_URL");
            }
        } else {
            return (String) bundle.getSerializable("FIREBASE_URL");
        }
    }

    private String getUserEmail(Bundle bundle){
        if (bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                return null;
            } else {
                return extras.getString("userEmail");
            }
        } else {
            return (String) bundle.getSerializable("userEmail");
        }
    }
    public void sendTweet(){

        EditText inputText = (EditText) findViewById(R.id.tweetInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Tweet object
            Tweet newTweet = new Tweet(userEmail, input);
            // Create a new, auto-generated child of that tweet location, and save our tweet data there
            tFirebase.push().setValue(newTweet);



            inputText.setText("");
        }
    }

    public void logout(View view) {
        Intent intent = new Intent(TweetList_Activity.this, Login_Activity.class);
        startActivity(intent);
        firebase.unauth();
    }

    @Override
    public void onStop() {
        super.onStop();
        tFirebase.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mTweetListAdapter.cleanup();
    }
}
