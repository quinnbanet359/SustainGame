package com.quinnbanet.sustaingame;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CurrentChallenge extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference().child("teams1");

    public CurrentChallenge() {
        //required empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_challenge, container, false);

        ListView lv = (ListView) view.findViewById(R.id.currentList);
        ListAdapter la = new FirebaseListAdapter<Challenges>(getActivity(),Challenges.class, R.layout.challenges_item_layout, mRef) {
            @Override
            protected void populateView(View v, Challenges model, int position) {
                TextView tv = (TextView) v.findViewById(R.id.challengeName);
                tv.setText(model.getName());
            }
        };
        lv.setAdapter(la);
        return view;
    }
}
