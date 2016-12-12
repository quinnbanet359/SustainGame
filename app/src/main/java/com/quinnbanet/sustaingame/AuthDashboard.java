package com.quinnbanet.sustaingame;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_dashboard);
        final String whichButton = getIntent().getExtras().getString("WHICH_BUTTON");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBase);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthDashboard.this, CreateChallenge.class);
                intent.putExtra("WHICH_BUTTON", whichButton);
                startActivity(intent);
            }
        });


    }

}
