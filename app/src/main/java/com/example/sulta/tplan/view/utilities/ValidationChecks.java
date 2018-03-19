package com.example.sulta.tplan.view.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Passant on 3/18/2018.
 */

public class ValidationChecks {

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-.]+([A-Za-z0-9-_.]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$");
    private final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z[ ]{0,1}]{3,20}$");
    private Matcher patternMatcher;

    public boolean isEmptyString(String string) {
        boolean emptyString = false;
        if (string.trim().length() == 0 || string.equals(null) || "".equals(string.trim())) {
            emptyString = true;
        }
        return emptyString;
    }

    public boolean isEmail(String email) {
        patternMatcher = EMAIL_PATTERN.matcher(email);
        return !isEmptyString(email) && patternMatcher.matches();
    }

    public boolean isName(String name) {
        patternMatcher = NAME_PATTERN.matcher(name);
        return !isEmptyString(name) && patternMatcher.matches();
    }

    public boolean isValidPassword(String pass) {
        boolean validPass = false;
        if (pass.length() >= 8 && pass.length() <= 30) {
            validPass = true;
        }
        return validPass;
    }

    public boolean isMatchPassword(String pass, String confirmPass) {
        boolean matchPass = false;
        if (pass.equals(confirmPass)) {
            matchPass = true;
        }
        return matchPass;
    }
}
