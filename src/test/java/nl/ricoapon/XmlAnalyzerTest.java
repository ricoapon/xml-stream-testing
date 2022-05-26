package nl.ricoapon;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Writer;

import static nl.ricoapon.TagReaderTest.TEST_XML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class XmlAnalyzerTest {
    @Test
    void parseTagCount() throws IOException {
        // Given
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(TEST_XML);
        MockWriter mockWriter = new MockWriter();

        // When
        xmlAnalyzer.parseTagCount(mockWriter);

        // Then
        assertThat(mockWriter.value(), equalTo("""
                3 identifier
                2 item
                1 root
                1 emptyValue1
                1 itemList
                """));
    }

    @Test
    void parseIntoTree() throws IOException {
        // Given
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(TEST_XML);
        MockWriter mockWriter = new MockWriter();

        // When
        xmlAnalyzer.parseIntoTree(mockWriter);

        // Then
        assertThat(mockWriter.value(), equalTo("""
                root
                 identifier
                 itemList
                  item
                   identifier
                  item
                   identifier
                """));
    }

    @Test
    void parseIntoTreeWithCompletePath() throws IOException {
        // Given
        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer(TEST_XML);
        MockWriter mockWriter = new MockWriter();

        // When
        xmlAnalyzer.parseIntoTreeWithCompletePath(mockWriter);

        // Then
        assertThat(mockWriter.value(), equalTo("""
                root
                root/identifier
                root/itemList
                root/itemList/item
                root/itemList/item/identifier
                root/itemList/item
                root/itemList/item/identifier
                """));

    }

    /**
     * Mock for {@link Writer}. Get the written string using {@link MockWriter#value()}.
     */
    private static class MockWriter extends Writer {
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void write(@SuppressWarnings("NullableProblems") char[] cbuf, int off, int len) {
            for (int i = 0; i < len; i++) {
                stringBuilder.append(cbuf[i + off]);
            }
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }

        public String value() {
            return stringBuilder.toString();
        }
    }
}
