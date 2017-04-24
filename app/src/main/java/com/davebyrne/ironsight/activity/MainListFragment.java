package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainListFragment extends Fragment {

    List<Game> gameList;
    ListView listViewGames;
    private DatabaseReference databaseGames;

    public MainListFragment() {
        // Required empty public constructor
    }

    //unused onCreate in fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //onCreateView is used instead of onCreate and returns View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainlist, container, false);



        //references the firebase database games node
        databaseGames = FirebaseDatabase.getInstance().getReference("games"); //for child references, add this e.g. .child("games")

        listViewGames = (ListView) rootView.findViewById(R.id.listViewGames);

        gameList = new ArrayList<>();

        //clicking a list item, sends that instance of the game to the game activity for full game details
        listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game gameInstance = gameList.get(position);
                String title = gameInstance.getGameName();
                String genre = gameInstance.getGameGenre();
                String date = gameInstance.getGameDate();
                String plat = gameInstance.getGamePlat();
                String identification = gameInstance.getGameId();


                Intent i = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                i.putExtra("gameName", title);
                i.putExtra("gameGenre", genre);
                i.putExtra("gameDate", date);
                i.putExtra("gamePlat", plat);
                i.putExtra("gameId", identification);
                startActivity(i);
            }
        });

        //this is for clicking the list entries
//        listViewGames.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Game game = gameList.get(position);
//                String title = game.getTitle();
//                String genre = game.getGenre();
//                String date = game.getDate();
//
//                Intent i = new Intent(getActivity().getApplicationContext(), GameActivity.class);
//                i.putExtra("gameTitle", title);
//                i.putExtra("gameGenre", genre);
//                i.putExtra("gameDate", date);
//                startActivity(i);
//                //Toast.makeText(getActivity().getApplicationContext(), game.getTitle() + " is selected!", Toast.LENGTH_SHORT).show(); //tests if working
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));



        return rootView;
    }

    //this just populates the screen with DB entries from the specified DB location set above
    //adds each game object from DB to an array (gameList) and sets the array to listViewGames (ListView) using the adapter (GameList)
    @Override
    public void onStart() {
        super.onStart();

        databaseGames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                gameList.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){ //iterate through all values of the database (games)

                    Game game = gameSnapshot.getValue(Game.class);

                    gameList.add(game);

                }

                //getActivity() in a Fragment returns the Activity the Fragment is currently associated with
                GameList adapter = new GameList(getActivity(), gameList);
                listViewGames.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //handles errors

            }
        });
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}