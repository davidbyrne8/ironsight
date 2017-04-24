package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davebyrne.ironsight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//this whole class is to create a profile for the newly registered user and redirect immediately to the main page
//the reason for this is to avoid running the create profile every time the mainactivity is run, which removes all added userlist games

public class CreateProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseUsers;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        writeNewUser(user.getUid(), user.getEmail());
        startActivity(new Intent(CreateProfileActivity.this, MainActivity.class));

    }

    private void writeNewUser(String userId, String email) {
        User user = new User(email);

        databaseUsers.child(userId).setValue(user);
    }
}
