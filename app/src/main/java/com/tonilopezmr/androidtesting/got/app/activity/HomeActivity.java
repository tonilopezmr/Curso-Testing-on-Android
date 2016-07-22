package com.tonilopezmr.androidtesting.got.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private CharacterAdapter adapter;
    private TextView informationCase;
    private FloatingActionButton floatingActionButton;

    //Presenter
    private CharacterListPresenter characterListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //presenter layer
        characterListPresenter = new CharacterListPresenter(new CharacterCollection());
        characterListPresenter.setView(this);
        characterListPresenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort_by_name) {
            characterListPresenter.onSortClick();
        }
        return true;
    }

    @Override
    public void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        informationCase = (TextView) findViewById(R.id.empty_and_error_case);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.content_loading_progress_bar);

        initRecyclerView();
        initFAB();
        addFABScrollBehavior();
    }

    private void addFABScrollBehavior() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    floatingActionButton.hide();
                } else if (dy < 0) {
                    floatingActionButton.show();
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new CharacterAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initFAB() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_character);
        floatingActionButton.setVisibility(View.INVISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateCharacterActivity.start(HomeActivity.this);
            }
        });
    }

    private void showInformation(String text) {
        informationCase.setText(text);
        informationCase.setVisibility(View.VISIBLE);
    }


    /////////////////////////////////////
    //   CharacterListView contract    //
    /////////////////////////////////////


    @Override
    public void showProgressBar() {
        adapter.clear();
        progressBar.setVisibility(View.VISIBLE);
        floatingActionButton.hide();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        floatingActionButton.show();
    }

    @Override
    public void hideEmptyCase() {
        informationCase.setVisibility(View.GONE);
    }


    @Override
    public void showEmptyCase() {
        showInformation(getString(R.string.not_characters));
    }

    @Override
    public void error() {
        showInformation(getString(R.string.error_on_load_characters));
    }

    @Override
    public void show(List<GoTCharacter> list) {
        adapter.addAll(list);
    }

}
