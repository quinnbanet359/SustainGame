package com.quinnbanet.sustaingame;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    public static final int locationFeedback = 0;
    public String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
    public String granted = "granted";
    public String denied = "denied";
    public String permissionStatus = granted;
    public int timesLaunched = 0;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Every time app is Launched or Resumed Check Permissions
        checkPermission();
        if (permissionStatus == granted) {
            Log.d("testabc123","resume granted");
            //do app things
            setContentView(R.layout.activity_main);
        }
        else {
            Log.d("testabc123","resume denied");
            //stop app things
            setContentView(R.layout.permission_denied);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    // Ask User Permission for Device Location
    askPermission();
    }



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
    }


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
                        setContentView(R.layout.permission_denied);
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

}
