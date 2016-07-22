package com.tonilopezmr.androidtesting.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tonilopezmr.androidtesting.R;

import java.util.LinkedList;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = (ListView) findViewById(R.id.list_view);

        LinkedList<String> list = new LinkedList();
        list.add("Arya Stark");
        list.add("Bronn");
        list.add("Cersei Lannister");
        list.add("Daenerys Targaryen");
        list.add("Eddard Stark");
        list.add("Hodor");
        list.add("Jaime Lannister");
        list.add("Jon Snow");
        list.add("Jorah Mormont");
        list.add("Khal Drogo");
        list.add("Lord Varys");
        list.add("Margaery Tyrell");
        list.add("Oberyn Martell");
        list.add("Petyr Baelish");
        list.add("Sansa Stark");
        list.add("Stannis Baratheon");
        list.add("Theon Greyjoy");
        list.add("Tyrion Lannister");

        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

}
