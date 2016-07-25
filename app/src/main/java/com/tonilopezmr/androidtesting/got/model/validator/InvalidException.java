package com.tonilopezmr.androidtesting.got.model.validator;

public class InvalidException extends Exception {

    private Validator validator;

    public InvalidException(Validator validator) {
        super("Invalid "+ validator.getObjectName());
        this.validator = validator;
    }

    public Validator getValidator() {
        return validator;
    }
}
