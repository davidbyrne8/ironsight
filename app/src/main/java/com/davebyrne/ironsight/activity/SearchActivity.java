package com.davebyrne.ironsight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<Game> gameList;
    ListView listViewGames;
    private DatabaseReference databaseGames;
    EditText queryET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //references the firebase database games node
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

        listViewGames = (ListView) findViewById(R.id.listViewGames);

        gameList = new ArrayList<>();

        queryET = (EditText) findViewById(R.id.editText2);

        Button searchBtn = (Button) findViewById(R.id.button8);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String searchString = queryET.getText().toString();
                databaseGames.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                        gameList.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                        for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){ //iterate through all values of the database (games)
                            String searchString = queryET.getText().toString();

                            Game game = gameSnapshot.getValue(Game.class);
                            String thisGame = game.getGameName();
                            if(thisGame.equalsIgnoreCase(searchString)) {
                                gameList.add(game);
                            }

                        }


                        //getActivity() in a Fragment returns the Activity the Fragment is currently associated with
                        GameList adapter = new GameList(SearchActivity.this, gameList);
                        listViewGames.setAdapter(adapter);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { //handles errors

                    }
                });

            }
        });

        listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game gameInstance = gameList.get(position);
                String title = gameInstance.getGameName();
                String genre = gameInstance.getGameGenre();
                String date = gameInstance.getGameDate();
                String plat = gameInstance.getGamePlat();
                String identification = gameInstance.getGameId();


                Intent i = new Intent(SearchActivity.this, GameActivity.class);
                i.putExtra("gameName", title);
                i.putExtra("gameGenre", genre);
                i.putExtra("gameDate", date);
                i.putExtra("gamePlat", plat);
                i.putExtra("gameId", identification);
                startActivity(i);
            }
        });




    }
}
