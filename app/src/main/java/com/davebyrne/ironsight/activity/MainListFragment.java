package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.davebyrne.ironsight.R.id.listView1;


public class MainListFragment extends Fragment {

    private List<Game> gameList = new ArrayList<>();
    private ListView listView;
    private DatabaseReference mDatabase;
    private ArrayList<String> mGamenames = new ArrayList<>();
    private FirebaseListAdapter mAdapter;

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

        //references the firebase database root node
        mDatabase = FirebaseDatabase.getInstance().getReference().child("games"); //for child references, add this e.g. .child("games")

        // Inflate the layout for this fragment
        listView = (ListView) rootView.findViewById(listView1);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "works", Toast.LENGTH_SHORT).show();
            }
        });

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