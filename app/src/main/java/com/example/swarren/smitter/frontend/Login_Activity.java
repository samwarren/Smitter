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
    private final String FIREBASE_URL = "https://brilliant-heat-7188.firebaseio.com";
    private final String INVALID_EMAIL = "invalid email";
    private final String ACCOUNT_CREATION_ERROR = "Error creating account";
    EditText emailAddr;
    EditText password;
    PopupWindow createUserPopUp;

    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        firebase = new Firebase(FIREBASE_URL);
        emailAddr = (EditText) findViewById(R.id.email_address);
        password = (EditText) findViewById(R.id.password);
        boolean loggedIn;
        loggedIn = firebase.getAuth()!=null;
        //remember to add if the login is expired
        if(loggedIn){
            goToTweetList();
        }

    }

    public Boolean loggedIn(){

        return firebase.getAuth()!=null;
    }
    private void goToTweetList() {
        Intent intent = new Intent(Login_Activity.this, TweetList.class);
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

    private Boolean validEmail(String email) {
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
