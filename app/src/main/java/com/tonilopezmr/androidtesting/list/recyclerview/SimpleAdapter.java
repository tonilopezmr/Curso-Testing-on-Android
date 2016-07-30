package com.tonilopezmr.androidtesting.list.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tonilopezmr.androidtesting.list.CharacterListProvider;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter {

    private RecyclerViewActivity recyclerViewActivity;
    List<String> characterNames = CharacterListProvider.characterNames();

    public SimpleAdapter(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        StringViewHolder holder = (StringViewHolder) viewHolder;
        holder.textView.setText(characterNames.get(position));
    }

    @Override
    public int getItemCount() {
        return characterNames.size();
    }

    public List<String> getItems() {
        return new ArrayList<>(characterNames);
    }

    public class StringViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        StringViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewActivity.nameTextView.setText(textView.getText());
                }
            });
        }
    }
}
