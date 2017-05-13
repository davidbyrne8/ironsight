package com.davebyrne.ironsight.activity;

import android.content.ActivityNotFoundException;
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
                String subject = userName+" would like to play "+gameName;
                String bodyText = userName+" has sent you a request to play "+gameName+" with them";

                String mailto = "mailto:"+userEmailAddress+
                        "&subject=" + Uri.encode(subject)+
                        "&body=" + Uri.encode(bodyText);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "This feature requires an email application.", Toast.LENGTH_SHORT).show();
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