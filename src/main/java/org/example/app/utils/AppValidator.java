package org.example.app.utils;

import java.util.HashMap;
import java.util.Map;

public class AppValidator {

    public final static String ID_RGX = "^[1-9]$";

    public final static String EMAIL_RGX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean isIdValid(String id) {
        if (id != null)
            return id.isEmpty() || !id.matches(ID_RGX);
        return false;
    }

    public static boolean isEmailValid(String email) {
        if (email != null)
            return email.isEmpty() || !email.matches(EMAIL_RGX);
        return false;
    }

    public static Map<String, String> validateUserData(Map<String, String> data) {

        Map<String, String> errors = new HashMap<>();

        if (data.containsKey("id") & AppValidator.isIdValid(data.get("id")))
            errors.put("id", Message.WRONG_ID_MSG.getMessage());

        if (data.containsKey("first_name")) {
            if (data.get("first_name") != null & data.get("first_name").isEmpty())
                errors.put("first name", Message.INPUT_REQ_MSG.getMessage());
        }

        if (data.containsKey("last_name")) {
            if (data.get("last_name") != null & data.get("last_name").isEmpty())
                errors.put("last name", Message.INPUT_REQ_MSG.getMessage());
        }

        if (data.containsKey("email") & AppValidator.isEmailValid(data.get("email")))
            errors.put("email", Message.WRONG_EMAIL_MSG.getMessage());

        return errors;
    }
}
