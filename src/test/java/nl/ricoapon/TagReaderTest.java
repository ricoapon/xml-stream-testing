package nl.ricoapon;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class TagReaderTest {
    @Test
    void streamOfTagsWorks() throws IOException {
        // Given
        TagReader tagReader = new TagReader("/Test.xml");

        // When
        var result = tagReader.readTags().toList();

        // Then
        assertThat(result, contains(
                new Tag("root", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.CLOSE),
                new Tag("emptyValue1", Tag.Type.EMPTY),
                new Tag("itemList", Tag.Type.OPEN),
                new Tag("item", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.CLOSE),
                new Tag("item", Tag.Type.CLOSE),
                new Tag("item", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.OPEN),
                new Tag("identifier", Tag.Type.CLOSE),
                new Tag("item", Tag.Type.CLOSE),
                new Tag("itemList", Tag.Type.CLOSE),
                new Tag("root", Tag.Type.CLOSE)
        ));

    }
}
