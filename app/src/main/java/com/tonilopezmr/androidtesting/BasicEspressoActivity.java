package com.tonilopezmr.androidtesting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class BasicEspressoActivity extends AppCompatActivity {

    private EditText welcomeEditText;
    private String undoText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_espresso);

        initToolbar();

        welcomeEditText = (EditText)findViewById(R.id.welcome_edittext);
        welcomeEditText.setInputType(InputType.TYPE_NULL);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isToEditWelcome()){
                    //go to edit te welcome edit text
                    fab.setImageResource(R.drawable.ic_done);
                    welcomeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    welcomeEditText.setSelection(welcomeEditText.getText().length());
                    undoText = welcomeEditText.getText().toString();
                    showSoftKeyboard(welcomeEditText);
                } else {
                    //disable welcome EditText
                    fab.setImageResource(R.drawable.ic_edit);
                    welcomeEditText.setInputType(InputType.TYPE_NULL);
                    hideSoftKeyboard();
                    //show update message
                    Snackbar.make(view, "Updated", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    welcomeEditText.setText(undoText);
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private boolean isToEditWelcome() {
        return welcomeEditText.getInputType() == InputType.TYPE_NULL;
    }


    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

}
