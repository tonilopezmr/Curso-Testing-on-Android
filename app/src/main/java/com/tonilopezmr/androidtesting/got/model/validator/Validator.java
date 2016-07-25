package com.tonilopezmr.androidtesting.got.model.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora de campos erroneos para manejar los datos de una validación.
 *
 * Y devuelve si es valido o no.
 */
public class Validator {

    List<WrongField> wrongFieldList;
    private String objectName;

    public Validator(String objectName) {
        this.objectName = objectName;
        wrongFieldList = new ArrayList<>();
    }

    public void addWrong(String fieldKey, String errorKey) {
        wrongFieldList.add(new WrongField(fieldKey, errorKey));
    }

    /**
     * Si no hay campos erroneos, significa que no hay errores.
     *
     * @return si es valido el conjunto de campos
     */
    public boolean isValid() {
        return wrongFieldList.isEmpty();
    }

    public String getObjectName() {
        return objectName;
    }

    public class WrongField {
        private String fieldKey;
        private String errorKey;

        public WrongField(String fieldKey, String errorKey) {
            this.fieldKey = fieldKey;
            this.errorKey = errorKey;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public String getErrorKey() {
            return errorKey;
        }
    }

}
