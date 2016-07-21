package com.tonilopezmr.androidtesting.got.app.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.got.app.CharacterAdapter;
import com.tonilopezmr.androidtesting.got.model.CharacterCollection;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;
import com.tonilopezmr.androidtesting.got.presenter.CharacterListPresenter;
import com.tonilopezmr.androidtesting.got.view.CharacterListView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements CharacterListView {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ContentLoadingProgressBar progressBar;
    CharacterAdapter adapter;
    
    TextView informationCase;

    CharacterListPresenter characterListPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        characterListPresenter = new CharacterListPresenter(new CharacterCollection());
        characterListPresenter.setView(this);
        characterListPresenter.init();
    }

    @Override
    public void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        informationCase = (TextView) findViewById(R.id.empty_and_error_case); 
        
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.content_loading_progress_bar);

        adapter = new CharacterAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void error() {
        showInformation("Error on load characters");
    }
    
    
    @Override
    public void show(List<GoTCharacter> list) {
        if (list.isEmpty()){
            showInformation("Has not Characters");
            return;
        }
        
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        hide();
    }

    private void hide() {
        progressBar.hide();
        informationCase.setVisibility(View.GONE);
    }

    private void showInformation(String text) {
        progressBar.hide();
        informationCase.setText(text);
        informationCase.setVisibility(View.VISIBLE);
    }
}
