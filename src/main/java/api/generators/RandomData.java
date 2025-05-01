package api.generators;

import com.github.curiousoddman.rgxgen.RgxGen;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private RandomData() {}

    public static String getUsername() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getPassword() {
        return RandomStringUtils.randomAlphabetic(3).toUpperCase() +
                RandomStringUtils.randomAlphabetic(5).toLowerCase() +
                RandomStringUtils.randomNumeric(3) + "$" ;
    }

    public static String getStringWithRegex(String regex) {
        RgxGen rgxGen = new RgxGen(regex);
        return rgxGen.generate();
    }
}
