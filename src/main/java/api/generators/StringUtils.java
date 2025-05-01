package api.generators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {}

    public static String getStringByRegex(String str, String regex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            matcher.find();
            return matcher.group(1);
    }
}
