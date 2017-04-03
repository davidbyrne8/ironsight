package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    //data entry
    EditText editTextName;
    Button buttonAdd;
    Spinner spinnerGenres;

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

        //data entry
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        buttonAdd = (Button) rootView.findViewById(R.id.add);
        spinnerGenres = (Spinner) rootView.findViewById(R.id.spinnerGenre);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();

            }
        });



        //references the firebase database games node
        databaseGames = FirebaseDatabase.getInstance().getReference("games"); //for child references, add this e.g. .child("games")

        listViewGames = (ListView) rootView.findViewById(R.id.listViewGames);

         gameList = new ArrayList<>();



        //use firebase list adapter to populate the lists, crashes at when loading MainListFragment activity
//        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>((Activity) getActivity().getApplicationContext(), String.class, android.R.layout.two_line_list_item, mDatabase) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//
//            }
//        };

        //firebaselistadpater attempt
//        mAdapter = new FirebaseListAdapter<Game>(this, Game.class, android.R.layout.two_line_list_item, mDatabase) {
//            @Override
//            protected void populateView(View view, Game game, int position) {
//                ((TextView)view.findViewById(android.R.id.text1)).setText(game.getJSON));


        //populating simple DB entries to the list, just single text game titles
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                String value = dataSnapshot.getValue(String.class);
//                mGamenames.add(value);
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //testing listview entry clicks
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), "works", Toast.LENGTH_SHORT).show();
//            }
//        });

        //this is for clicking the list entries
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
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

    private void addGame() {
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id = databaseGames.push().getKey(); //creates unique string entry

            Game game = new Game(id, name, genre);

            databaseGames.child(id).setValue(game); //sets the new user input value to the unique key string
            Toast.makeText(getActivity().getApplicationContext(), "Game added", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
        }
    }

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
                //may be an issue as getActivity gets the MainActivity, maybe need to get MainListFragment activity
                GameList adapter = new GameList(getActivity(), gameList);
                listViewGames.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //handles errors

            }
        });
    }

    //temporary hard coded data
//    private void prepareGameData() {
//
//
////        Game game = new Game("Super Mario Run", "Platformer", "18/11/2016");
////        gameList.add(game);
////
////        game = new Game("Stardew Valley", "Strategy", "19/12/2016");
////        gameList.add(game);
////
////        game = new Game("Dead Rising", "Survival", "20/12/2016");
////        gameList.add(game);
////
////        game = new Game("The Last Guardian", "Adventure", "20/12/2016");
////        gameList.add(game);
////
////        game = new Game("Steep", "Sports", "21/12/2016");
////        gameList.add(game);
////
////        game = new Game("Final Fantasy 15", "RPG", "22/12/2016");
////        gameList.add(game);
////
////        game = new Game("Pokemon Sun/Moon", "RPG", "23/12/2016");
////        gameList.add(game);
////
////        game = new Game("Dishonored 2", "FPS", "24/12/2016");
////        gameList.add(game);
////
////        game = new Game("Tyranny", "RPG", "25/12/2016");
////        gameList.add(game);
////
////        game = new Game("Owlboy", "Platformer", "26/12/2016");
////        gameList.add(game);
////
////        game = new Game("Civilization 6", "Strategy", "27/12/2016");
////        gameList.add(game);
////
////        game = new Game("TitanFall 2", "Animation", "28/12/2016");
////        gameList.add(game);
////
////        game = new Game("Root Letter", "Indie", "29/11/2016");
////        gameList.add(game);
////
////        game = new Game("Watch Dogs 2", "Action", "30/12/2016");
////        gameList.add(game);
////
////        game = new Game("Minimal", "Adventure", "30/12/2016");
////        gameList.add(game);
////
////        game = new Game("Total War", "Strategy", "31/12/2016");
////        gameList.add(game);
//
////        mAdapter.notifyDataSetChanged();
//    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}