package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private TextView test;
    //DB testing
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //DB testing
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Name"); //for child references, add this e.g. .child("game_01")

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        /*API testing*/


//        HttpResponse response;
//        try {
//            response = Unirest.get("https://igdbcom-internet-game-database-v1.p.mashape.com/games/?fields=name&limit=10&offset=0&order=release_dates.date%3Adesc&search=zelda").header("X-Mashape-Key", "AFXAvMmPFomsh0uI1lI2rSPBuW6Up1dajCMjsnD6zANBvkP0Ny").header("Accept", "application/json").asJson();
//
////            final TextView test = (TextView) findViewById(R.id.textView4);
////            test.setText((CharSequence) response);
//
//
//
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }


        /*end testing*/

        /*SIGN OUT*/

        final Button logoutBtn = (Button) findViewById(R.id.button4);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                signOut();

            }
        });

        final Button deleteAccountBtn = (Button) findViewById(R.id.button5);

        /*code for delete user not working, crashes on button click*/

//        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                if (user != null) {
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(SettingsActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(SettingsActivity.this, SignupActivity.class));
//                                        finish();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(SettingsActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                }
//
//            }
//        });

        //DB testing
        test = (TextView) findViewById(R.id.textView4);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                test.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
