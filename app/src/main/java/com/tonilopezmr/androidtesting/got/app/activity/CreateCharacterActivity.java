package com.tonilopezmr.androidtesting.got.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.tonilopezmr.androidtesting.R;

public class CreateCharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateCharacterActivity.class);
        context.startActivity(intent);
    }
}
