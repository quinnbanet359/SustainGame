package com.quinnbanet.sustaingame;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
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


public class MainActivity extends AppCompatActivity{
    //permsions vaiables
    public static final int locationFeedback = 0;
    public String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
    public String granted = "granted";
    public String denied = "denied";
    public String permissionStatus = granted;
    public int timesLaunched = 0;

    // FB variables
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    // Firebase variables
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth;



    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Every time app is Launched or Resumed Check Permissions
        checkPermission();
        //logout user on resume
        FirebaseAuth.getInstance().signOut();
        if (permissionStatus == granted) {
            Log.d("testabc123", "resume granted");
            //do app things
            setContentView(R.layout.activity_main);
        } else {
            Log.d("testabc123", "resume denied");
            //stop app things
            setContentView(R.layout.permission_denied);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        askPermission(); //ask user for location permissions upon first app launch

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // ...
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Log.d("FBSignInTAG", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("FBSignInTAG", "onAuthStateChanged:signed_in:" + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("FBSignInTAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...

        super.onCreate(savedInstanceState);


        callbackManager = CallbackManager.Factory.create();

        //info = (TextView)findViewById(R.id.info);

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                Intent intent = new Intent(MainActivity.this, AuthDashboard.class);
                startActivity(intent);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
               // info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
               // info.setText("Login attempt failed.");
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */


    public void askPermission() {
        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                showPreAlert(); //tell user to accept permission dialog upon first app launch

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, locationFeedback);

                // locationFeedback is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case locationFeedback: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    Log.d("test123", "granted");

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("test123", "denied");
                        // while permission denied, bring up "permission denied" view
                        Log.d("test123","got to while loop");
                        showAlert();
                        //setContentView(R.layout.permission_denied);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void showAlert() {
        //TODO:     If user has not granted location access, block them from the app
        //TODO:     If user has enabled sharing, let them use app
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String message = "We need your device's location. Please go to Settings >" +
                " Apps >  SustainGame > Permissions and enable location sharing " +
                "Are you sure you want to deny this permission?";
        // set title
        alertDialogBuilder.setTitle("Sad Face ):");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Go away",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Stop",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void showPreAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // allow is enclosed in quotes, done in Java with the use of "\\"
        String message = "Please hit \"Allow\" when you are promoted";
        // set title
        alertDialogBuilder.setTitle("We need your location");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Will Do",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void checkPermission() {

        if (timesLaunched > 0) {
            Log.d("testabc123","times launched > 0");
            // user has launched before, start checking
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // permission denied
                permissionStatus = denied;

            } else {
                // permission granted
                permissionStatus = granted;
            }
        }
        else {
            //user never launched app, increase counter
            timesLaunched++;
            Log.d("testabc123","timesLauchedCount:" + timesLaunched);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("accessTokenTAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("accessTokenTAG", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("accessTokenTAG", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
public void iwasclicked(View view) {
    Intent intent = new Intent(MainActivity.this, AuthDashboard.class);
    startActivity(intent);
}
public void LoginWatcher() {
    // call with:         LoginWatcher(); //get current user state and send user to proper activity
        Profile currentUser = Profile.getCurrentProfile();

        //if user is logged out, send them to MainActivity for FB Login
        if(currentUser.getName().length() == 0)
        {
            Log.d("blahblah", "first if");
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }else
        //user is logged in, send them to AuthDashboard
        {
            Log.d("blahblah", "else");
            Intent intent = new Intent(MainActivity.this,AuthDashboard.class);
            startActivity(intent);
        }
    }
}
