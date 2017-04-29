package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeetUsersActivity extends AppCompatActivity{

    private DatabaseReference databaseMeetUsers;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<User> userList;
    ListView listViewMeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_users);

        databaseMeetUsers = FirebaseDatabase.getInstance().getReference("users"); //for child references, add this e.g. .child("games")

        listViewMeet = (ListView) findViewById(R.id.listViewMeet);

        userList = new ArrayList<>();

        Intent intent = getIntent();
        final String gameName = intent.getExtras().getString("gameName");

        listViewMeet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User userInstance = userList.get(position);
                String userEmailAddress = userInstance.getEmail();
                String userName = userInstance.getName();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{userEmailAddress});
                i.putExtra(Intent.EXTRA_SUBJECT, userName+" has invited you to play!");
                i.putExtra(Intent.EXTRA_TEXT   , userName+" wants to play "+ gameName+" with you!" );
                try {
                    startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MeetUsersActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onStart() {
        super.onStart();

        databaseMeetUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                userList.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){ //iterate through all values of the database (users)

                    User user = userSnapshot.getValue(User.class);
                    //probably add in if statement here to check if the user has the game
                    userList.add(user);
                }

                //getActivity() in a Fragment returns the Activity the Fragment is currently associated with
                UserList adapter = new UserList(MeetUsersActivity.this, userList);
                listViewMeet.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //handles errors

            }
        });
    }
}