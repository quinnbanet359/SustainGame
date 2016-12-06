package com.quinnbanet.sustaingame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CompletedChallenge extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference().child("teams");

    public CompletedChallenge() {
        //required empty contructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_challenge, container, false);

        ListView lv = (ListView) view.findViewById(R.id.ListView);
        ListAdapter la = new FirebaseListAdapter<Team>(getActivity(),Team.class, R.layout.team_item_layout, mRef) {
            @Override
            protected void populateView(View v, Team model, int position) {
                TextView tv = (TextView) v.findViewById(R.id.teamName);
                tv.setText(model.getName());
            }
        };
        lv.setAdapter(la);
        return view;
    }
}
