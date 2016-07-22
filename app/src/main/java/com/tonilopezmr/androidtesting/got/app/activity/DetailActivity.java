package com.tonilopezmr.androidtesting.got.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tonilopezmr.androidtesting.R;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

public class DetailActivity extends AppCompatActivity {

    public static final String HOUSE_NAME = "houseName";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        final String houseName = intent.getStringExtra(HOUSE_NAME);
        final String description = intent.getStringExtra(DESCRIPTION);
        final String name = intent.getStringExtra(NAME);
        final String imageUrl = intent.getStringExtra(IMAGE_URL);

        initUI(new GoTCharacter(name, imageUrl, description, houseName));
    }

    private void initUI(GoTCharacter character) {
        initToolbar(character.getName());
        showImage(character.getImageUrl());
        fillCharacter(character);
    }

    private void fillCharacter(GoTCharacter character) {
        TextView nameTextView = (TextView) findViewById(R.id.name);
        TextView descTextView = (TextView) findViewById(R.id.description);
        nameTextView.setText(character.getName());
        descTextView.setText(character.getDescription());
    }

    private void showImage(String imageUrl) {
        ImageView ivp = (ImageView) findViewById(R.id.iv_photo);
        Picasso.with(getApplicationContext()).load(imageUrl).into(ivp);
    }

    private void initToolbar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }
}
