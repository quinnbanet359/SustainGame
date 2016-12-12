package com.quinnbanet.sustaingame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationChallenges extends AppCompatActivity {
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view to be the activity_navigation_challenges xml
        setContentView(R.layout.activity_navigation_challenges);

        // Locate the buttons in activity_navigation_challenges.xml
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        // Set onClickListerner to capture button clicks
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(NavigationChallenges.this,
                        AuthDashboard.class);
                myIntent.putExtra("WHICH_BUTTON","my_challenges");
                startActivity(myIntent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(NavigationChallenges.this,
                        AuthDashboard.class);
                myIntent.putExtra("WHICH_BUTTON","friends_challenges");
                startActivity(myIntent);
            }
        });





    }


}
