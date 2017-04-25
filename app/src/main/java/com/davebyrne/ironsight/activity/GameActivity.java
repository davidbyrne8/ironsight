package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GameActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        final String title = getIntent().getStringExtra("gameName");
        final String genre = getIntent().getStringExtra("gameGenre");
        final String date = getIntent().getStringExtra("gameDate");
        final String plat = getIntent().getStringExtra("gamePlat");
        final String identification = getIntent().getStringExtra("gameId");
        ImageView iv= (ImageView)findViewById(R.id.imageView3);
        iv.setImageResource(R.mipmap.ic_game_back);

        final Game game = new Game(identification, title, genre, date, plat);

        final TextView tv1 = (TextView) findViewById(R.id.textView);
        final TextView tv2 = (TextView) findViewById(R.id.textView2);
        final TextView tv3 = (TextView) findViewById(R.id.textView3);
        final TextView tv5 = (TextView) findViewById(R.id.textView5);

        databaseUserList = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/list");

        tv1.setText(title);
        tv2.setText(genre);
        tv3.setText(date);
        tv5.setText(plat);

        Button addBtn = (Button) findViewById(R.id.button2);
        Button infoBtn = (Button) findViewById(R.id.button3);
        Button orderBtn = (Button) findViewById(R.id.button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewGame(game);
                Toast.makeText(getApplicationContext(), title + " added to your list.", Toast.LENGTH_SHORT).show(); //breaking it somehow
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/#q="+title));
                startActivity(iBrowser);

            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://store.steampowered.com/search/?term="+title));
                startActivity(iBrowser);
            }
        });
    }

    private void writeNewGame(Game game) {
        databaseUserList.child(game.getGameId()).setValue(game);
    }
}
