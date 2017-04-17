package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;

import java.util.zip.InflaterOutputStream;

public class GameActivity extends AppCompatActivity {

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

        final TextView tv1 = (TextView) findViewById(R.id.textView);
        final TextView tv2 = (TextView) findViewById(R.id.textView2);
        final TextView tv3 = (TextView) findViewById(R.id.textView3);
        final TextView tv5 = (TextView) findViewById(R.id.textView5);

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
                Toast.makeText(getApplicationContext(), title + " added to your list.",Toast.LENGTH_SHORT).show();
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ign.com"));
                startActivity(iBrowser);
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.steampowered.com"));
                startActivity(iBrowser);
            }
        });
    }
}
