package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.utils.LuhnAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * National Identification Number validator for Canada.
 *
 * A wikipedia source can be found in: https://en.wikipedia.org/wiki/Social_Insurance_Number
 *
 **/
class CanadaIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;

    @Override
    public boolean validate(String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !StringUtils.isNumeric(sanitizedId)
        ) {
            return false;
        }

        return validateSequence(sanitizedId);
    }

    private String sanitize(final String id) {
        return id
            .trim()
            .replace(" ","")
            .replace("-", "");
    }

    private boolean validateSequence(String sanitizedId) {
        String[] digits =  sanitizedId.split("");
        List<String> multiplyResult = new ArrayList<>();
        for (int i=0; i < digits.length; i++){
            if ((i+1) % 2 == 0){
                multiplyResult.add(String.valueOf(Integer.valueOf(digits[i])*2));
            }
            else {
                multiplyResult.add(String.valueOf(Integer.valueOf(digits[i])));
            }
        }
        return luhnValidation(multiplyResult);
    }

    private boolean luhnValidation(List<String> multiplyResult) {
        int sum = 0;
        for (String digit: multiplyResult){
            if (Integer.valueOf(digit) > 9){
                sum += Integer.valueOf(digit.substring(0,1)) + Integer.valueOf(digit.substring(1));
            }
            else {
                sum += Integer.valueOf(digit);
            }
        }
        return sum % 10 == 0;
    }
}
