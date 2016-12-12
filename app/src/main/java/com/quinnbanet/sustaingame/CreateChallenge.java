package com.quinnbanet.sustaingame;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.Profile;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CreateChallenge extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Challenges"); //TODO: implement location in

    static long idSubTracker = 129; //129 is current id number, we will increase each new creation
    static long idTracker = 903; //903 is current id number, we will increase each new creation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set "start date" and "created by" for current user
        TextView startDate = (TextView) findViewById(R.id.createSDContent);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        final String currentDate= sdf.format(new Date());
        startDate.setText(currentDate);
        Log.d("setLogs","currentDate: "+currentDate);

        //convert current date from dd/mm/yy to ddmmyy
        final java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH); // zero based so +1
            month = month +1;
        int year = cal.get(Calendar.YEAR);
        year = year - 2000; // turn 2016 into 16, should work for the next 84 years
        final String enteredCurrentDate = ""+month+day+year;

        TextView userName = (TextView) findViewById(R.id.createCreatedByContent);
        final Profile profile = Profile.getCurrentProfile();
        userName.setText(profile.getName());
        Log.d("setLogs",profile.getName());

        Button button = (Button) findViewById(R.id.createBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // declare error fields
                ImageView challengeError = (ImageView) findViewById(R.id.nameError);
                ImageView endDateError = (ImageView) findViewById(R.id.endDateError);
                ImageView challengeErrorCover = (ImageView) findViewById(R.id.nameErrorCover);
                ImageView endDateErrorCover = (ImageView) findViewById(R.id.endDateErrorCover);
                TextView errorMessage = (TextView) findViewById(R.id.errorMessage);

                //reset errors
                challengeErrorCover.setVisibility(View.VISIBLE);
                endDateErrorCover.setVisibility(View.VISIBLE);

                //validate fields
                EditText challenge = (EditText) findViewById(R.id.createChallengeContent);
                EditText endDateDay = (EditText) findViewById(R.id.createEDContent1);
                EditText endDateMonth = (EditText) findViewById(R.id.createEDContent2);
                EditText endDateYear = (EditText) findViewById(R.id.createEDContent3);
                String enteredChallenge = challenge.getText().toString();
                String enteredEndDate = endDateDay.getText().toString()+
                                        endDateMonth.getText().toString()+
                                        endDateYear.getText().toString();
                Log.d("getLogs",enteredChallenge);
                Log.d("getLogs",enteredEndDate);


                String specialChars = ""; //TODO: look up and fill with java special chars in string
                String specialReturns = ""; //TODO: look up and fill with java special return chars in string

                if (enteredChallenge.isEmpty()) { //TODO: if or.contains special chars
                    challengeErrorCover.setVisibility(View.INVISIBLE);
                    errorMessage.setText("Error with Challenge Name Field \n Be sure to enter text and ensure there are no special characters");
                }
                else if (enteredEndDate.isEmpty()) { //TODO: if or.contains special chars
                    endDateErrorCover.setVisibility(View.INVISIBLE);
                    errorMessage.setText("Enter Date as 12/10/16 \n Error with End Date Field \n Be sure to enter text and ensure there are no special characters");
                }
                else {
                    long utcStart = System.currentTimeMillis();
                    String utcEndString;
                    long utcEnd = 0;
                    
                    //user entered date comes in as 121116 || change to 12/11/16
                    int yearConvert = Integer.parseInt(endDateYear.getText().toString());
                    yearConvert = yearConvert+2000; // should work for 84 more years
                    String convertedEnteredEndDate = endDateMonth.getText().toString() + "/" +
                                                    endDateDay.getText().toString() + "/" + yearConvert;

                    //convert string to Date
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                    try {
                        Date date = formatter.parse(convertedEnteredEndDate);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String dateFormattedinUTC;
                    SimpleDateFormat lv_formatter = new SimpleDateFormat();
                    lv_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    dateFormattedinUTC = lv_formatter.format(date);
                    String dateFormattedinPOSTUTC = convertedEnteredEndDate + " 01:00:00";
                    Log.d("firebaseTestLog1","TestUTCDATE:    "+dateFormattedinPOSTUTC);

                    SimpleDateFormat utcFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    try {
                        utcEnd = utcFormat.parse(dateFormattedinPOSTUTC).getTime() / 1000;
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                    Log.d("firebaseTestLog","utc start: "+ utcStart + " utcEnd: "+ utcEnd);

                    Challenges challenges = new Challenges(idSubTracker +1,enteredCurrentDate,"progress",enteredChallenge,"picture", profile.getName(), enteredEndDate, utcStart, utcEnd);
                    ref.child(""+idTracker).setValue(challenges);
                    idTracker++;
                    idSubTracker++;

                    //after entry, send user back to AuthDashboard
                    Intent intent = new Intent(CreateChallenge.this, AuthDashboard.class);
                    startActivity(intent);

                    /* FOR REFERENCE:
                    private long id;
                    private String startDate;
                    private String progress;
                    private String name;
                    private String picture;
                    private String createdBy;
                    private String endDate;
                    private double utcStartDate;
                    private double utcEndDate; */
                }
            }
        });
    }
}
