package com.davebyrne.ironsight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davebyrne.ironsight.R;
import com.davebyrne.ironsight.activity.Game;

import java.util.List;

/**
 * Created by Dave on 16/12/2016.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {

    private List<Game> gamesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public GamesAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Game game = gamesList.get(position);
        holder.title.setText(game.getTitle());
        holder.genre.setText(game.getGenre());
        holder.year.setText(game.getDate());
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
