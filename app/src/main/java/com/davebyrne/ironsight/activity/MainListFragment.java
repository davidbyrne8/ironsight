package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.davebyrne.ironsight.R;
import com.davebyrne.ironsight.adapter.GamesAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.davebyrne.ironsight.R.id.inputSearch;


public class MainListFragment extends Fragment {

    private List<Game> gameList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GamesAdapter mAdapter;
    EditText inputSearch;

    private int mData;

    public MainListFragment() {
        // Required empty public constructor
    }

//    //Parcelable implementation
//    protected MainListFragment(Parcel in) {
//        mData = in.readInt();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(mData);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Parcelable.Creator<MainListFragment> CREATOR = new Parcelable.Creator<MainListFragment>() {
//        public MainListFragment createFromParcel(Parcel in) {
//            return new MainListFragment(in);
//        }
//
//        public MainListFragment[] newArray(int size) {
//            return new MainListFragment[size];
//        }
//    };

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


        // Inflate the layout for this fragment
        inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mAdapter = new GamesAdapter(gameList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        //this is for clicking the list entries
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Game game = gameList.get(position);
                String title = game.getTitle();
                String genre = game.getGenre();
                String date = game.getDate();

                Intent i = new Intent(getActivity().getApplicationContext(), GameActivity.class);
                i.putExtra("gameTitle", title);
                i.putExtra("gameGenre", genre);
                i.putExtra("gameDate", date);
                startActivity(i);
                //Toast.makeText(getActivity().getApplicationContext(), game.getTitle() + " is selected!", Toast.LENGTH_SHORT).show(); //tests if working
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //text search at top
//        inputSearch.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                // When user changes Text
//                MainActivity.this.adapter.getFilter().filter(cs);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//            }
//        });

        prepareGameData();
        return rootView;
    }

    //temporary hard coded data
    private void prepareGameData() {
        Game game = new Game("Super Mario Run", "Platformer", "18/11/2016");
        gameList.add(game);

        game = new Game("Stardew Valley", "Strategy", "19/12/2016");
        gameList.add(game);

        game = new Game("Dead Rising", "Survival", "20/12/2016");
        gameList.add(game);

        game = new Game("The Last Guardian", "Adventure", "20/12/2016");
        gameList.add(game);

        game = new Game("Steep", "Sports", "21/12/2016");
        gameList.add(game);

        game = new Game("Final Fantasy 15", "RPG", "22/12/2016");
        gameList.add(game);

        game = new Game("Pokemon Sun/Moon", "RPG", "23/12/2016");
        gameList.add(game);

        game = new Game("Dishonored 2", "FPS", "24/12/2016");
        gameList.add(game);

        game = new Game("Tyranny", "RPG", "25/12/2016");
        gameList.add(game);

        game = new Game("Owlboy", "Platformer", "26/12/2016");
        gameList.add(game);

        game = new Game("Civilization 6", "Strategy", "27/12/2016");
        gameList.add(game);

        game = new Game("TitanFall 2", "Animation", "28/12/2016");
        gameList.add(game);

        game = new Game("Root Letter", "Indie", "29/11/2016");
        gameList.add(game);

        game = new Game("Watch Dogs 2", "Action", "30/12/2016");
        gameList.add(game);

        game = new Game("Minimal", "Adventure", "30/12/2016");
        gameList.add(game);

        game = new Game("Total War", "Strategy", "31/12/2016");
        gameList.add(game);

        mAdapter.notifyDataSetChanged();
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