package com.tonilopezmr.androidtesting.got.model.validator;

import android.support.annotation.NonNull;
import com.tonilopezmr.androidtesting.got.model.GoTCharacter;

public class CharacterValidator {

    //Fields
    public static final String NAME_FIELD = "_name";
    public static final String HOUSE_NAME_FIELD = "_house_name";
    public static final String DESCRIPTION_FIELD = "_description";

    //Errors
    public static final String EMPTY_ERROR = "_empty_";
    public static final String ONLY_SPACES_ERROR = "_only_spaces_";
    public static final String ONLY_UPPER_CASE_ERROR = "_only_upper_case_";

    private Validator validator;

    /**
     * No es valido:
     *  - Vacio
     *  - Solo espacios
     *  - Solo en mayusculas
     *
     * @param goTCharacter
     * @return {@link Validator} con el resultado de la validación
     */
    public Validator valid(GoTCharacter goTCharacter) {
        validator = new Validator(GoTCharacter.class.getSimpleName());
        
        isValid(goTCharacter.getName(), NAME_FIELD);
        isValid(goTCharacter.getHouseName(), HOUSE_NAME_FIELD);
        isValid(goTCharacter.getDescription(), DESCRIPTION_FIELD);
        
        return validator;
    }

    private void isValid(String fieldText, String fieldKey) {
        if (isNotEmpty(fieldText)) {
            validator.addWrong(fieldKey, EMPTY_ERROR);
        }else if (isOnlySpaces(fieldText)) {
            validator.addWrong(fieldKey, ONLY_SPACES_ERROR);
        } else if (isOnlyUpperCase(fieldText)) {
            validator.addWrong(fieldKey, ONLY_UPPER_CASE_ERROR);
        }
    }

    private boolean isOnlyUpperCase(String fieldText) {
        String upperCase = withoutFirstCharacter(fieldText.toUpperCase());
        String realFieldText = withoutFirstCharacter(fieldText);
        return realFieldText.equals(upperCase);
    }

    private boolean isOnlySpaces(String fieldText) {
        return fieldText.trim().isEmpty();
    }

    private boolean isNotEmpty(String fieldText) {
        return fieldText.isEmpty();
    }

    @NonNull
    private String withoutFirstCharacter(String text) {
        return text.substring(1, text.length());
    }

}
