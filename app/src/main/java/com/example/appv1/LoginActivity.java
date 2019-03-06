package com.example.appv1;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String PERMISSIONS = "email";
    private static final String TAG = "authStatus";

    CallbackManager callbackManager; //manages callbacks into the FacebookSdk from Activity's onActivityResult()
    LoginButton loginButton; //login button
    private FirebaseAuth mAuth; //entry point of the Firebase Authentication SDK

    boolean isLoggedIn = false;

    private TextView loggedTextView;
    private LinearLayout logoutLayout;
    private LinearLayout loginLayout;
    private Button continueButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //create callback manager
        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();

        initializeViews();

        registerLoginCallback();

        loginButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);


        //determine what can be accessed
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    //computes the value of the logged in flag
    public void computeLoggedInStatus()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
    }

    //initializes the views
    public void initializeViews()
    {
        loggedTextView = findViewById(R.id.text_view);
        continueButton = findViewById(R.id.continue_button);
        loginLayout = findViewById(R.id.layout_login);
        logoutLayout = findViewById(R.id.layout_logout);
        logoutButton = findViewById(R.id.logout_button);
        loginButton = findViewById(R.id.login_button);

        //set what permissions it can access
        loginButton.setReadPermissions(Arrays.asList(PERMISSIONS));
    }

    //register the callback to respond to a login result
    public void registerLoginCallback()
    {
        //callback function after logging in with facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(getApplicationContext(), "User cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error)
            {
                Log.e(TAG, "errorLoggingIn: " + error);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void handleFacebookAccessToken(AccessToken token)
    {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            computeLoggedInStatus();
                            updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            computeLoggedInStatus();
                            updateUI(null);
                        }
                    }
                });
    }

    //updates UI accordingly
    private void updateUI(FirebaseUser user)
    {
        if(isLoggedIn && user != null)
        {
            loggedTextView.setVisibility(View.VISIBLE);
            continueButton.setVisibility(View.VISIBLE);

            loggedTextView.setText("Already logged in");
            continueButton.setText("Continue as " + user.getDisplayName());

            logoutLayout.setVisibility(View.VISIBLE);

            loginLayout.setVisibility(View.GONE);
        }
        else
        {
            loginLayout.setVisibility(View.VISIBLE);

            logoutLayout.setVisibility(View.GONE);

            loggedTextView.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
        }
    }

    private void launchMainActivity()
    {
        if(isLoggedIn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void disconnectFromFacebook()
    {
        if(AccessToken.getCurrentAccessToken() == null)
        {
            return; //already logged out
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response)
            {
                LoginManager.getInstance().logOut();
            }
        }).executeAndWait();
        Toast.makeText(LoginActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.login_button:
                updateUI(mAuth.getCurrentUser());
                break;

            case R.id.continue_button:
                launchMainActivity();
                break;

            case R.id.logout_button:
                mAuth.signOut();
                disconnectFromFacebook();
                computeLoggedInStatus();
                AccessToken.setCurrentAccessToken(null);
                updateUI(null);
        }

    }
}
