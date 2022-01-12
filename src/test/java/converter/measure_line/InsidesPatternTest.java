package converter.measure_line;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utility.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
public class InsidesPatternTest {
    Pattern pattern = Pattern.compile(Patterns.insidesPattern());

    @Test
    void multipleDividersValidTest() {
        String[] samples = {
                "||12---8--3|",
                "|12---8--3||",

                "||xo---o--x|",
                "|x---o--xo||",

                "|xxxxooxoxxo-||",
                "||oxxooxooxxx-|",
                "|-xxxxooxoxxo||",
                "||-oxxooxooxxx|",
        };
        String expected;
        String actual;

        Matcher matcher;
        for (String sample : samples) {
            //remove all measure names
            expected = sample.replaceAll("^[^|]+", "");
            //if there is only one | at either extreme, it removes it. it there are multiple |'s, it keeps only one. this is how the measure insides pattern is meant to behave (useful when we want to detect repeats notated as such ||*----*||).
            expected = expected.replaceAll("^\\|(\\|(?=\\|))*|((?<=\\|)\\|(?!$))*\\|$", "").strip();
            matcher = pattern.matcher(sample);
            assertTrue(matcher.find(), "no measure insides was detected in the following sample:\n\t\"" + sample + "\"\n");
            actual = matcher.group();
            assertEquals(expected, actual, "the measure insides in the following sample was not accurately detected:\n\t\"" + sample + "\"\n");
        }
    }

    @Test
    void invalidInsidesTest() {
        String[] samples = {
                "(1tl2tl3tl4tl5tl6tl|1tl2tl3tl4tl5tl6tl|1tl2tl3tl4tl5tl6tl|1tl2tl3tl4tl5tl6tl)",
                "|1&a2&a3&a4&a56&a|1&a2&a3&a4&a5&a6&a|",
                "(1 + 2 + 3 + 4 + |1 + 2 + 3 + 4 + |1 + 2 + 3 + 4 + )",
                "| the user places text unintended to be detected as a measure inside here",
                "there is a measure divider | placed unexpectedly",
                "multiple | measure dividers | placed unexpectedly",
                "measure dividers |placed right next to text| unexpectedly"
        };

        Matcher matcher;
        for (String sample : samples) {
            matcher = pattern.matcher(sample);
            assertFalse(matcher.find(), "the following sample is not supposed to be detected as having a valid measure inside:\n\t\"" + sample + "\"\n");
        }
    }
}
