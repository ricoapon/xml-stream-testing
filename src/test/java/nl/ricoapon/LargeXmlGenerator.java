package nl.ricoapon;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Generates large XML that can be used as input for {@link LargeTest}.
 */
public class LargeXmlGenerator {
    public static void generateLargeXml(Writer writer) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<root>\n");
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 1000000; i++) {
            writer.write(String.format("""
                                <Person>
                                    <Name>%s</Name>
                                    <StartDate>%s</StartDate>
                                    <EndDate>%s</EndDate>
                                    <Location>%s</Location>
                                </Person>
                            """,
                    randomName(random),
                    randomDate(),
                    randomDate(),
                    randomLocation(random)));
        }
        writer.close();
    }

    private static String randomName(SecureRandom random) {
        return new BigInteger(20, random).toString(32) + " " + new BigInteger(20, random).toString(32);
    }

    private static String randomLocation(SecureRandom random) {
        return new BigInteger(50, random).toString(32);
    }

    private static String randomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.get(Calendar.YEAR) + "-" + gc.get(Calendar.MONTH) + "-" + gc.get(Calendar.DAY_OF_MONTH);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
