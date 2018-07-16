package com.example.sulekha.assignment_2.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sulekha.assignment_2.MusicPreviewActivity;
import com.example.sulekha.assignment_2.MusicPreviewActivity;
import com.example.sulekha.assignment_2.R;
import com.example.sulekha.assignment_2.network.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ArtistItemAdapter extends RecyclerView.Adapter<ArtistItemAdapter.ArtistViewHolder> {

    private List<Result> artistMap;
    private Context context;
    public static final String purl = "test";
    Activity activity;
    public ArtistItemAdapter(List<Result> artistMap, Context context) {
        this.artistMap = artistMap;
        this.context = context;

    }

    public ArtistItemAdapter(List artistMap){
        this.artistMap = artistMap;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rock_row, parent, false);
        return new ArtistViewHolder(view, context, artistMap);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {

        holder.collectionName.setText(artistMap.get(position).getCollectionName());
        holder.artistName.setText(artistMap.get(position).getArtistName());
        holder.trackPrice.setText(artistMap.get(position).getTrackPrice().toString());
        Picasso.with(context).load(String.valueOf(artistMap.get(position).getArtworkUrl60())).into(holder.artistImage);

    }

    @Override
    public int getItemCount() {

        return artistMap.size();
    }


    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView artistName;
        private TextView collectionName;
        private TextView trackPrice;
        private ImageView artistImage;
        private Context context;
        List<Result> artistMaps;

        public ArtistViewHolder(@NonNull View itemView, Context ctx, List<Result> artistMaps) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.artistMaps = artistMaps;
            this.context = ctx;
            artistName = itemView.findViewById(R.id.artistName);
            collectionName = itemView.findViewById(R.id.collectionName);
            trackPrice = itemView.findViewById(R.id.trackPrice);
            artistImage = itemView.findViewById(R.id.artistimg);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Result result = this.artistMaps.get(position);
            //Snackbar.make(itemView, "URL: " + result.getPreviewUrl(), Snackbar.LENGTH_LONG).show();
            Intent intent =  new Intent(this.context, MusicPreviewActivity.class);
            intent.putExtra(purl, result.getPreviewUrl());
            this.context.startActivity(intent);

        }
    }
}
