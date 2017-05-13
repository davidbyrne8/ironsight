package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 16/12/2016.
 *
 * Reference: Firebase Realtime Database Tutorial for Android, Simplified Coding. Accessed 20/04/17. https://www.youtube.com/watch?v=EM2x33g4syY
 * */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("");
        spinnerArray.add("Strategy");
        spinnerArray.add("RPG");
        spinnerArray.add("FPS");
        spinnerArray.add("Platformer");
        spinnerArray.add("Action");
        spinnerArray.add("Sport");
        spinnerArray.add("Horror");
        spinnerArray.add("Fighting");
        spinnerArray.add("Adventure");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String searchString = queryET.getText().toString();
                databaseGames.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                        gameList.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                        for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){ //iterate through all values of the database (games)
                            String inputName = queryET.getText().toString();
                            String selectedSpinner = sItems.getSelectedItem().toString();

                            Game game = gameSnapshot.getValue(Game.class);
                            String thisGameName = game.getGameName();
                            String thisGameGenre = game.getGameGenre();
                            if(thisGameName.toLowerCase().contains(inputName.toLowerCase()) && thisGameGenre.equalsIgnoreCase(selectedSpinner) //name and genre match up
                                    || inputName.equalsIgnoreCase("") && thisGameGenre.equalsIgnoreCase(selectedSpinner)
                                    || thisGameName.toLowerCase().contains(inputName.toLowerCase()) && selectedSpinner.equalsIgnoreCase("")) { //no name, but genre is set
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
