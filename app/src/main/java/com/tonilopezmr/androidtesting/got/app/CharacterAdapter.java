package com.tonilopezmr.androidtesting.got.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.got.app.activity.DetailActivity;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CharacterAdapter
        extends RecyclerView.Adapter<CharacterAdapter.GotCharacterViewHolder> {

    private final List<GoTCharacter> characterList;
    private Activity activity;

    public CharacterAdapter(Activity activity) {
        this.characterList = new ArrayList<>();
        this.activity = activity;
    }

    public void clear() {
        characterList.clear();
        notifyDataSetChanged();
    }

    public void addAll(Collection<GoTCharacter> collection) {
        characterList.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public GotCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.got_character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final GotCharacterViewHolder holder, final int position) {
        final GoTCharacter character = characterList.get(position);
        holder.render(character);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDetailActivity(holder, character);
            }
        });
    }

    private void moveToDetailActivity(GotCharacterViewHolder viewHolder, GoTCharacter character) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewHolder.itemView, "character");

        Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DESCRIPTION, character.getDescription());
        intent.putExtra(DetailActivity.NAME, character.getName());
        intent.putExtra(DetailActivity.IMAGE_URL, character.getImageUrl());
        intent.putExtra(DetailActivity.HOUSE_NAME, character.getHouseName());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTextView;

        public GotCharacterViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivBackground);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void render(final GoTCharacter character) {
            Picasso.with(imageView.getContext())
                    .load(character.getImageUrl())
                    .fit()
                    .into(imageView);
            nameTextView.setText(character.getName());
        }
    }

}