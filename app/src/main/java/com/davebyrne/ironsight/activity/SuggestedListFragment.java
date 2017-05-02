package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.davebyrne.ironsight.R;
import com.davebyrne.ironsight.adapter.GamesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static com.davebyrne.ironsight.R.id.listViewGames;


public class SuggestedListFragment extends Fragment{

    List<Game> gameList;
    List<Game> gameList1;
    ListView listViewGames;
    private DatabaseReference databaseUserGames;
    private DatabaseReference databaseGames;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public SuggestedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sugglist, container, false);

        databaseUserGames = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/list"); //reference to game list for current user
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

        listViewGames = (ListView) rootView.findViewById(R.id.listViewGames);

        gameList = new ArrayList<>();


        return rootView;
    }
    public void onStart() {
        super.onStart();

        databaseGames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                gameList.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){ //iterate through all values of the database (games)

                    Game game = gameSnapshot.getValue(Game.class);

                    //add in here the check for the winning genre
                    String favGenre = favouriteGenre();
                    String thisGameGenre = game.getGameGenre();
                    if(thisGameGenre.equalsIgnoreCase(favGenre)) {
                        gameList.add(game);
                    }

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

    public String favouriteGenre() {

        String favouriteGenreString = "Strategy";

        gameList1 = new ArrayList<>();

        databaseUserGames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //any time database is changed

                gameList1.clear(); //need to clear if it contains any game previously as the dataSnapshot will contain every game each time it is executed

                for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) { //iterate through all values of the database (games)

                    Game game = gameSnapshot.getValue(Game.class);

                    gameList1.add(game);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //handles errors

            }
        });

        int strategy = 0;
        int rpg = 0;
        int fps = 0;
        int sport = 0;
        int action = 0;
        int horror = 0;
        int adventure = 0;
        int platformer = 0;
        int fighting = 0;

        for(int i = 0; i < gameList1.size(); i++) {
            Game game = gameList1.get(i);
            String gameGenre = game.getGameGenre();

            switch (gameGenre) {
                case "Strategy":
                    strategy++;
                    break;
                case "RPG":
                    rpg++;
                    break;
                case "FPS":
                    fps++;
                    break;
                case "Sport":
                    sport++;
                    break;
                case "Action":
                    action++;
                    break;
                case "Adventure":
                    horror++;
                    break;
                case "Horror":
                    adventure++;
                    break;
                case "Platformer":
                    platformer++;
                    break;
                case "Fighting":
                    fighting++;
                    break;
            }
        }
        if(strategy > rpg && strategy > fps && strategy > sport && strategy > action && strategy > horror && strategy > adventure && strategy > platformer && strategy > fighting){
            favouriteGenreString = "Strategy";
        }
        else if(rpg > strategy && rpg > fps && rpg > sport && rpg > action && rpg > horror && rpg > adventure && rpg > platformer && rpg > fighting){
            favouriteGenreString = "RPG";
        }
        else if(fps > strategy && fps > rpg && fps > sport && fps > action && fps > horror && fps > adventure && fps > platformer && fps > fighting){
            favouriteGenreString = "FPS";
        }
        else if(sport > strategy && sport > fps && sport > rpg && sport > action && sport > horror && sport > adventure && sport > platformer && sport > fighting){
            favouriteGenreString = "Sport";
        }
        else if(action > strategy && action > fps && action > sport && action > rpg && action > horror && action > adventure && action > platformer && action > fighting){
            favouriteGenreString = "Action";
        }
        else if(adventure > strategy && adventure > fps && adventure > sport && adventure > action && adventure > horror && adventure > rpg && adventure > platformer && adventure > fighting){
            favouriteGenreString = "Adventure";
        }
        else if(horror > strategy && horror > fps && horror > sport && horror > action && horror > rpg && horror > adventure && horror > platformer && horror > fighting){
            favouriteGenreString = "Horror";
        }
        else if(platformer > strategy && platformer > fps && platformer > sport && platformer > action && platformer > horror && platformer > adventure && platformer > rpg && platformer > fighting){
            favouriteGenreString = "Platformer";
        }
        else if(fighting > strategy && fighting > fps && fighting > sport && fighting > action && fighting > horror && fighting > adventure && fighting > platformer && fighting > rpg){
            favouriteGenreString = "Fighting";
        }


        return favouriteGenreString;
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