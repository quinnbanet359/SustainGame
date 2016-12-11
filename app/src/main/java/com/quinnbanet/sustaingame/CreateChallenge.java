package com.quinnbanet.sustaingame;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateChallenge extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Challenges");
    DatabaseReference usersRef = ref.child("users");
    Map<String, Challenges> createChallenge = new HashMap<String, Challenges>();
    final long idTracker = 129; //129 is current id number, we will increase each new creation

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
                EditText endDate = (EditText) findViewById(R.id.createEDContent);
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
                    createChallenge.put("",new Challenges(idTracker+1,currentDate,"",enteredChallenge,"",profile.getName(), enteredEndDate));
                    usersRef.setValue(createChallenge);
                    //idTracker++;          increment idTracker upn each creation
                }
            }
        });
    }

}
