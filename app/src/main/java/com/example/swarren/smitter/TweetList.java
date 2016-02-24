package com.example.swarren.smitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.Firebase;


/**
 * Created by swarren on 2/17/16.
 */
public class TweetList extends Activity {
    private String FIREBASE_URL;
    private Firebase firebase;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tweetlist_activity);

        FIREBASE_URL = getFB_URL(bundle);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(FIREBASE_URL);
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

    public void logout(View view) {
        Intent intent = new Intent(TweetList.this, Login.class);
        startActivity(intent);
        firebase.unauth();
    }

}
