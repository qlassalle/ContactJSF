package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qlassalle on 04/01/2017.
 */
public class Validator {

    private static final String NAME_REGEX = "[a-zA-z]+([ '-.][a-zA-Z]+)*";
    private static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String STREET_NAME_REGEX = "[0-9a-zA-z]+([ '-.][a-zA-Z]+)*";

    public static boolean isMailCorrect(String inputString) {
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(inputString);
        return m.find() ? true : false;
    }

    public static boolean isCorrectString(String inputString) {
        return inputString.matches(NAME_REGEX);
    }

    public static boolean isCorrectNumber(String inputString) {
        return inputString.matches(NUMBER_REGEX);
    }

    public static boolean isCorrectStreetName(String inputString) {
        return inputString.matches(STREET_NAME_REGEX);
    }
}
