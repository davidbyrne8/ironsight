package com.davebyrne.ironsight.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.davebyrne.ironsight.R;

import java.util.List;

/**
 * Created by Dave on 03/04/2017.
 */

public class GameList extends ArrayAdapter<Game> {

    private Activity context;
    private List<Game> gameList;

    public GameList(Activity context, List<Game> gameList){
        super(context, R.layout.list_layout, gameList);
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        Game game = gameList.get(position);

        textViewName.setText(game.getGameName());
        textViewGenre.setText(game.getGameGenre());
        textViewDate.setText(game.getGameDate());

        return listViewItem;
    }
}
