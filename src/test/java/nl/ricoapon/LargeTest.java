package nl.ricoapon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.function.Supplier;

/**
 * Tests in this class have no assertion. They are identical to tests in {@link XmlAnalyzer}, but now for large files.
 */
public class LargeTest {
    private final static String OUTPUT_PATH = "build/generated/large-test/";
    private final static Supplier<BufferedReader> LARGE_XML = ReaderSupplierBuilder.fromClasspathResource("/Large.xml");

    @BeforeAll
    static void createOutputDirs() {
        //noinspection ResultOfMethodCallIgnored
        new File(OUTPUT_PATH).mkdirs();
    }

    /**
     * This test can be used to check if memory settings are correct by running it. It should throw an {@link OutOfMemoryError}.
     */
    @Test
    @Disabled
    void readFullFileIntoMemory() {
        System.out.println(LARGE_XML.get().lines().toList());
    }

    @Test
    void parseTagCount() throws IOException {
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(LARGE_XML);
        Writer writer = new FileWriter(OUTPUT_PATH + "Frequency.txt");
        xmlAnalyzer.parseTagCount(writer);
    }

    @Test
    void parseIntoTree() throws IOException {
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(LARGE_XML);
        Writer writer = new FileWriter(OUTPUT_PATH + "Tree.txt");
        xmlAnalyzer.parseIntoTree(writer);
    }

    @Test
    void parseIntoTreeWithCompletePath() throws IOException {
        // Given
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(LARGE_XML);
        Writer writer = new FileWriter(OUTPUT_PATH + "TreeWithPaths.txt");
        xmlAnalyzer.parseIntoTreeWithCompletePath(writer);
    }
}
