package com.quinnbanet.sustaingame;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CreateChallenge extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Challenges");
    DatabaseReference usersRef = ref.child("users");
    Map<String, Challenges> createChallenge = new HashMap<String, Challenges>();
    static long idSubTracker = 129; //129 is current id number, we will increase each new creation
    static long idTracker = 903;

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
        Log.d("setLogs",currentDate);

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
                EditText endDate = (EditText) findViewById(R.id.createEDContent1);
                String enteredChallenge = challenge.getText().toString();
                String enteredEndDate = endDate.getText().toString();
                Log.d("getLogs",enteredChallenge);
                Log.d("getLogs",enteredEndDate);


                String specialChars = ""; //TODO: look up and fill with java special chars in string
                String specialReturns = ""; //TODO: look up and fill with java special return chars in string

                if (enteredChallenge.isEmpty()) { //TODO: if or.contains special chars
                    Log.d("ifLog","got to if");
                    challengeErrorCover.setVisibility(View.INVISIBLE);
                    errorMessage.setText("Error with Challenge Name Field \n Be sure to enter text and ensure there are no special characters");
                }
                else if (enteredEndDate.isEmpty()) { //TODO: if or.contains special chars
                    Log.d("ifLog","got to else if");
                    endDateErrorCover.setVisibility(View.INVISIBLE);
                    errorMessage.setText("Enter Date as 12/10/16 \n Error with End Date Field \n Be sure to enter text and ensure there are no special characters");
                }
                else {
                    //TODO: Send info to firebase
                    double utcStart = System.currentTimeMillis();
                    String utcEndString;
                    double utcEnd = 0;

                    //convert string to Date
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    Date endingDate;
                    try {
                        endingDate = df.parse(enteredEndDate);
                        //convert date to string utc
                        SimpleDateFormat formatter = new SimpleDateFormat();
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        utcEndString = formatter.format(endingDate);
                        //convert string utc to double
                        utcEnd = Double.parseDouble(utcEndString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    //utc start and end throwing error bc of decimal . in the double
                    // round to nearest whole number
                    double roundedUTCStart = (double) Math.round(utcStart);
                    double roundedUTCEnd = (double) Math.round(utcEnd); //round does not remove .0 from double
                    long fake1 = 0;
                    long fake2= 0;

                    Log.d("firebaseTestLog","utc start: "+ fake1 + " utcEnd: "+ fake2);
                    Log.d("firebaseTestLog", idSubTracker +1+""+""+enteredChallenge+"121016"+ profile.getName()+ enteredEndDate+ fake1+ fake2);


                    Challenges challenges = new Challenges(idSubTracker +1,"121116","progress",enteredChallenge,"picture", profile.getName(), enteredEndDate, fake1, fake2);
                    ref.child(""+idTracker).setValue(challenges);
                    idTracker++;
                    idSubTracker++;

                    //TODO: it puts data in the db as challanges vs as an id, we need to assign it in the db as an id

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
                    private double utcEndDate;
                 */

                }
            }
        });
    }
}
