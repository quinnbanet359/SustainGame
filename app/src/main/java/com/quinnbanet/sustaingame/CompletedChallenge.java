package com.quinnbanet.sustaingame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CompletedChallenge extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference().child("Teams");

    public CompletedChallenge() {
        //required empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_challenge, container, false);

        ListView lv = (ListView) view.findViewById(R.id.completedList);
        ListAdapter la = new FirebaseListAdapter<Challenges>(getActivity(),Challenges.class, R.layout.challenges_item_layout, mRef) {
            @Override
            protected void populateView(View v, Challenges model, int position) {
                TextView tv = (TextView) v.findViewById(R.id.challengeName);
                tv.setText(model.getName());
            }
        };
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //send user to new activity upon challenge click
               // Toast toast = Toast.makeText(this, (String)arg0.getItemAtPosition(position), Toast.LENGTH_SHORT);
                //toast.show();

                //Snackbar.make(R.layout.activity_auth_dashboard, "Had a snack at Snackbar", Snackbar.LENGTH_LONG)
                  //      .setAction("Undo", mOnClickListener)
                   //     .setActionTextColor(Color.RED)
                    //    .show();
            }
        });
        lv.setAdapter(la);
        return view;
    }

}
