package nl.ricoapon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * Tests in this class have no assertion. They are identical to tests in {@link XmlAnalyzer}, but now for large files.
 * This test
 */
public class LargeTest {
    private final static String OUTPUT_PATH = "build/generated/large-test/";

    @BeforeAll
    static void createOutputDirs() {
        if (!new File(OUTPUT_PATH).mkdirs()) {
            throw new RuntimeException("Could not create output path");
        }
    }

    /**
     * This test can be used to check if memory settings are correct by running it. It should throw an {@link OutOfMemoryError}.
     */
    @Test
    @Disabled
    void readFullFileIntoMemory() {
        //noinspection ConstantConditions
        System.out.println(
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/Large.xml")))
                        .lines().toList()
        );
    }

    @Test
    void parseTagCount() throws IOException {
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer("/Large.xml");
        Writer writer = new FileWriter(OUTPUT_PATH + "Frequency.txt");
        xmlAnalyzer.parseTagCount(writer);
    }

    @Test
    void parseIntoTree() throws IOException {
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer("/Large.xml");
        Writer writer = new FileWriter(OUTPUT_PATH + "Tree.txt");
        xmlAnalyzer.parseIntoTree(writer);
    }

    @Test
    void parseIntoTreeWithCompletePath() throws IOException {
        // Given
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer("/Large.xml");
        Writer writer = new FileWriter(OUTPUT_PATH + "TreeWithPaths.txt");
        xmlAnalyzer.parseIntoTreeWithCompletePath(writer);
    }
}
