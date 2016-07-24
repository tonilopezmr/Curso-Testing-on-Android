package com.tonilopezmr.androidtesting;

import android.app.Application;
import com.tonilopezmr.androidtesting.got.di.CharacterInjector;

public class AndroidTestingApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //Inyección de dependencias para GoT App
        CharacterInjector.load(new CharacterInjector());
    }
}