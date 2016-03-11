package com.example.swarren.smitter.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.swarren.smitter.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends Activity {
    private static final String FIREBASE_URL = "https://brilliant-heat-7188.firebaseio.com";
    private static final String INVALID_EMAIL = "invalid email";
    private static final String ACCOUNT_CREATION_ERROR = "Error creating account";
    private static final int FIVE_MB = 5000000;
    private static final int MAX_BYTES_ALLOCATED_FOR_CACHE = FIVE_MB;
    EditText emailAddr;
    EditText password;
    PopupWindow createUserPopUp;
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseInitialization(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        firebase = new Firebase(FIREBASE_URL);
        firebase.keepSynced(true);
        emailAddr = (EditText) findViewById(R.id.email_address);
        password = (EditText) findViewById(R.id.password);
        boolean loggedIn;
        loggedIn = firebase.getAuth()!=null;
        if(loggedIn){
            goToTweetList();
        }
    }

    private boolean firebaseAlreadyInitialized(Bundle bundle){
        if (bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                return false;
            } else {
                return extras.getBoolean("FIREBASE_ALREADY_SETUP");            }
        } else {
            return (boolean) bundle.getSerializable("FIREBASE_ALREADY_SETUP");
        }
    }
    private void firebaseInitialization(Bundle bundle){
        boolean initialized=firebaseAlreadyInitialized(bundle);
        if(!initialized) {
            //store tweet data with user's device
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
            Firebase.getDefaultConfig().setPersistenceCacheSizeBytes(MAX_BYTES_ALLOCATED_FOR_CACHE);
            Firebase.setAndroidContext(this);
        }
    }

    public Boolean loggedIn(){
        return firebase.getAuth()!=null;
    }
    private void goToTweetList() {
        Intent intent = new Intent(Login_Activity.this, TweetList_Activity.class);
        intent.putExtra("FIREBASE_URL", FIREBASE_URL);
        intent.putExtra("userEmail", firebase.getAuth().getProviderData().get("email").toString());
        startActivity(intent);
    }

    /*
    method called when user presses login button;
     */
    public void authenticateUser(View view) {
        String email = emailAddr.getText().toString();
        String pass = password.getText().toString();
        if(validEmail(email)) {
            firebase.authWithPassword(email,pass, new MyAuthResultHandler(firebase));
        }else{
            emailAddr.setText("");
            emailAddr.setHint(INVALID_EMAIL);
        }

    }


    public void createAccount(View view){
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_activity, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AppBarLayout.LayoutParams.MATCH_PARENT,
                AppBarLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);

        Button btnDismiss = (Button)popupView.findViewById(R.id.enterNewAccountInfo);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText newEmail = (EditText) popupView.findViewById(R.id.new_account_email_address);
                EditText newPass = (EditText) popupView.findViewById(R.id.new_account_password);
                String newPasswordString = newPass.getText().toString();
                String newEmailString = newEmail.getText().toString();
                if(validEmail(newEmailString)) {
                    authorizeNewAccount(newEmailString, newPasswordString);
                    popupWindow.dismiss();
                }
                newEmail.setText(ACCOUNT_CREATION_ERROR);
            }
        });
        final Button createAccountButton = (Button) findViewById(R.id.createAccount);
        popupWindow.showAtLocation(view, 0, 0, 1);

    }

    private void authorizeNewAccount(String email, String password){

            firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {

                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    System.out.println(ACCOUNT_CREATION_ERROR);
                }
            });
    }

    public Boolean validEmail(String email) {
        if(email==null){
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    class MyAuthResultHandler implements Firebase.AuthResultHandler {
        Firebase firebase;
            public MyAuthResultHandler(Firebase fb) {
                firebase = fb;

            }

            /*
            callback function for after login authentication over
            if login successful
             */
            public void onAuthenticated(AuthData authData){
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")) {
                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                }
                firebase.child("users").child(authData.getUid()).setValue(map);
                goToTweetList();
            }

            /*
            callback function for after login authentication over
            if login unsuccessful
            */
            public void onAuthenticationError(FirebaseError error){
                emailAddr.setText("");
                emailAddr.setHint(R.string.invalid_login);
            }
    }
}
