package com.tonilopezmr.androidtesting.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tonilopezmr.androidtesting.R;

import java.util.List;

public class RecyclerViewActivity  extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SimpleAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.StringViewHolder> {

        List<String> characterNames = CharacterListProvider.characterNames();

        @Override
        public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StringViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(StringViewHolder holder, int position) {
            holder.textView.setText(characterNames.get(position));
        }

        @Override
        public int getItemCount() {
            return characterNames.size();
        }

        class StringViewHolder extends RecyclerView.ViewHolder{

            TextView textView;

            public StringViewHolder(View itemView) {
                super(itemView);
                textView = ((TextView) itemView);
            }
        }
    }
}
