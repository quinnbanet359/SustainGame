package com.quinnbanet.sustaingame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateChallenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //hide error fields
        final ImageView challengeError = (ImageView) findViewById(R.id.nameError);
        final ImageView endDateError = (ImageView) findViewById(R.id.endDateError);
        final TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
        challengeError.setVisibility(View.INVISIBLE);
        endDateError.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);


        //set "start date" and "created by" for current user
        EditText startDate = (EditText) findViewById(R.id.createSDContent);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        String currentDate= sdf.format(new Date());
        startDate.setText(currentDate);

        EditText userName = (EditText) findViewById(R.id.createCreatedByContent);
        Profile profile = Profile.getCurrentProfile();
        userName.setText(profile.getName());

        Button button = (Button) findViewById(R.id.createBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate fields
                EditText challenge = (EditText) findViewById(R.id.createChallengeContent);
                EditText endDate = (EditText) findViewById(R.id.createEDContent);

                String specialChars = ""; //TODO: look up and fill with java special chars in string
                String specialReturns = ""; //TODO: look up and fill with java special return chars in string

                if (challenge.getText().toString() == " " || challenge.getText().toString() == null) { //TODO: if or.contains special chars
                    challengeError.setVisibility(View.VISIBLE);
                    errorMessage.setText("Error with Challenge Name Field \n Be sure to enter text and ensure there are no special characters");
                }
                else if (endDate.getText().toString() == " " || endDate.getText().toString() == null) { //TODO: if or.contains special chars
                    endDateError.setVisibility(View.VISIBLE);
                    errorMessage.setText("Enter Date as 12/10/16 \n Error with End Date Field \n Be sure to enter text and ensure there are no special characters");
                }
                else {
                    // remove any previously activated fields
                    challengeError.setVisibility(View.INVISIBLE);
                    endDateError.setVisibility(View.INVISIBLE);
                    errorMessage.setText("");
                    errorMessage.setVisibility(View.INVISIBLE);

                    //TODO: Send info to firebase

                }
            }
        });
    }

}
