package com.quinnbanet.sustaingame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChallengesDetails extends AppCompatActivity {

    public String idDetails;
    public String startDateDetails;
    public String progressDetails;
    public String nameDetails;
    public String pictureDetails;
    public String createdByDetails;
    public String endDateDetails;

    CompletedChallenge completedChallenge = new CompletedChallenge();
    CurrentChallenge currentChallenge = new CurrentChallenge();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize TextView objects
        TextView startDateField = (TextView) findViewById(R.id.sdContent);
        TextView progressField = (TextView) findViewById(R.id.progressContent);
        TextView nameField = (TextView) findViewById(R.id.nameContent);
        TextView createdByField = (TextView) findViewById(R.id.createdByContent);
        TextView endDateField = (TextView) findViewById(R.id.edContent);
        if (completedChallenge.getCompletedClick().equals( "yes")) {
            //relate fields to other class
            idDetails = completedChallenge.getIdDetails();
            startDateDetails = completedChallenge.getStartDateDetails();
            progressDetails = completedChallenge.getProgressDetails();
            nameDetails = completedChallenge.getNameDetails();
            pictureDetails = completedChallenge.getPictureDetails();
            createdByDetails = completedChallenge.getCreatedByDetails();
            endDateDetails = completedChallenge.getEndDateDetails();

            //set TextView Values
            startDateField.setText(startDateDetails);
            progressField.setText(progressDetails);
            nameField.setText(nameDetails);
            createdByField.setText(createdByDetails);
            endDateField.setText(endDateDetails);

            completedChallenge.setCompletedClick("");
        }
        else {
            //relate fields to other class
            idDetails = currentChallenge.getIdDetails();
            startDateDetails = currentChallenge.getStartDateDetails();
            progressDetails = currentChallenge.getProgressDetails();
            nameDetails = currentChallenge.getNameDetails();
            pictureDetails = currentChallenge.getPictureDetails();
            createdByDetails = currentChallenge.getCreatedByDetails();
            endDateDetails = currentChallenge.getEndDateDetails();

            //set TextView Values
            startDateField.setText(startDateDetails);
            progressField.setText(progressDetails);
            nameField.setText(nameDetails);
            createdByField.setText(createdByDetails);
            endDateField.setText(endDateDetails);
            
            currentChallenge.setCurrentClick("");
        }
    }

}
