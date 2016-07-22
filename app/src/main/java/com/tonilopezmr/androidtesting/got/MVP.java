package com.tonilopezmr.androidtesting.got;

public interface MVP {

    interface View {
        void initUi();
        void error();
    }

    interface Presenter<T extends View> {
        void init();
        void setView(T view);
    }

}
