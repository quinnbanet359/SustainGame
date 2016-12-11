package com.quinnbanet.sustaingame;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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
import com.google.firebase.database.Query;


public class CurrentChallenge extends Fragment {
    View view;
    double utc_timestamp = System.currentTimeMillis();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference().child("Challenges");
    Query currentChallQuery = mRef.orderByChild("utcEndDate").startAt(utc_timestamp);

    public static String idDetails;
    public static String startDateDetails;
    public static String progressDetails;
    public static String nameDetails;
    public static String pictureDetails;
    public static String createdByDetails;
    public static String endDateDetails;

    public static String currentClick = "";

    public CurrentChallenge() {
        //required empty constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_challenge, container, false);

        final ListView lv = (ListView) view.findViewById(R.id.currentList);
        final ListAdapter la = new FirebaseListAdapter<Challenges>(getActivity(), Challenges.class, R.layout.challenges_item_layout, currentChallQuery) {
            @Override
            protected void populateView(View v, final Challenges model, int position) {
                TextView tv = (TextView) v.findViewById(R.id.challengeName);
                tv.setText(model.getName());
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        //set completed as being clicked
                        currentClick = "yes";

                        //get current data field
                        String currentPosition;
                        Object lvRawData = (Object) lv.getItemAtPosition(position);
                        currentPosition = lvRawData.toString();

                        //ID
                        String idBefore[] = currentPosition.split("id=");
                        String idAfter[] = idBefore[1].split(",");
                        idDetails = idAfter[0];
                        //startDate
                        String sdBefore[] = currentPosition.split("startDate=");
                        String sdAfter[] = sdBefore[1].split(",");
                        startDateDetails = sdAfter[0];
                        //progress
                        String progressBefore[] = currentPosition.split("progress=");
                        String progressAfter[] = progressBefore[1].split(",");
                        progressDetails = progressAfter[0];
                        //name
                        String nameBefore[] = currentPosition.split("name='");
                        String nameAfter[] = nameBefore[1].split("',");
                        nameDetails = nameAfter[0];
                        //picture
                        String pictureBefore[] = currentPosition.split("picture=");
                        String pictureAfter[] = pictureBefore[1].split(",");
                        pictureDetails = pictureAfter[0];
                        //createdBy
                        String createdByBefore[] = currentPosition.split("createdBy=");
                        String createdByAfter[] = createdByBefore[1].split(",");
                        createdByDetails = createdByAfter[0];
                        //createdBy
                        String endDateBefore[] = currentPosition.split("endDate=");
                        String endDateAfter[] = endDateBefore[1].split("\\}"); // chars "\\" specify that i am looking for char "}"
                        endDateDetails = endDateAfter[0];

                        Log.d("testListAdapterLog","id: "+idDetails);
                        Log.d("testListAdapterLog","startDate: "+ startDateDetails);
                        Log.d("testListAdapterLog","progress: "+progressDetails);
                        Log.d("testListAdapterLog","name: "+nameDetails);
                        Log.d("testListAdapterLog","picture:"+pictureDetails);
                        Log.d("testListAdapterLog","createdBy: "+createdByDetails);
                        Log.d("testListAdapterLog","endDate: "+endDateDetails);

                        Intent intent = new Intent(CurrentChallenge.this.getActivity(), ChallengesDetails.class);
                        startActivity(intent);
                    }
                });

            }
        };
        lv.setAdapter(la);
        return view;
    }

    public static String getIdDetails() {
        return idDetails;
    }

    public static void setIdDetails(String idDetails) {
        CurrentChallenge.idDetails = idDetails;
    }

    public static String getStartDateDetails() {
        return startDateDetails;
    }

    public static void setStartDateDetails(String startDateDetails) {
        CurrentChallenge.startDateDetails = startDateDetails;
    }

    public static String getProgressDetails() {
        return progressDetails;
    }

    public static void setProgressDetails(String progressDetails) {
        CurrentChallenge.progressDetails = progressDetails;
    }

    public static String getNameDetails() {
        return nameDetails;
    }

    public static void setNameDetails(String nameDetails) {
        CurrentChallenge.nameDetails = nameDetails;
    }

    public static String getPictureDetails() {
        return pictureDetails;
    }

    public static void setPictureDetails(String pictureDetails) {
        CurrentChallenge.pictureDetails = pictureDetails;
    }

    public static String getCreatedByDetails() {
        return createdByDetails;
    }

    public static void setCreatedByDetails(String createdByDetails) {
        CurrentChallenge.createdByDetails = createdByDetails;
    }

    public static String getEndDateDetails() {
        return endDateDetails;
    }

    public static void setEndDateDetails(String endDateDetails) {
        CurrentChallenge.endDateDetails = endDateDetails;
    }

    public String getCurrentClick() {
        return currentClick;
    }

    public void setCurrentClick(String currentClick) {
        this.currentClick = currentClick;
    }
}
