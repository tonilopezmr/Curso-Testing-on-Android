package com.tonilopezmr.androidtesting.got.model.api.exceptions;

public class UnknownErrorException extends Exception {
    private int code;

    public UnknownErrorException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
