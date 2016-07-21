package com.tonilopezmr.androidtesting.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.tonilopezmr.androidtesting.R;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        listView = (ListView)findViewById(R.id.list_view);
    }

}
