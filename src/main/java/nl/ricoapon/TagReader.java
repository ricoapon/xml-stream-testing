package nl.ricoapon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("ClassCanBeRecord")
public class TagReader {
    private final String resourcePath;

    public TagReader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Stream<Tag> readTags() throws IOException {
        //noinspection ConstantConditions
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(resourcePath)));
        // We skip the first line which contains the XML declaration (<?xml ...>).
        reader.readLine();

        Iterator<Character> characterIterator = createCharacterIterator(reader);
        Iterator<Tag> tagIterator = createTagIterator(characterIterator);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(tagIterator,
                        Spliterator.ORDERED | Spliterator.NONNULL), false)
                // The iterator last element can be null, so we need to filter this.
                .filter(Objects::nonNull)
                .onClose(() -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Creates an {@link Iterator} of tags by reading a {@link Iterator} of characters.
     */
    private Iterator<Tag> createTagIterator(Iterator<Character> characterIterator) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return characterIterator.hasNext();
            }

            @Override
            public Tag next() {
                // Indicates if we are currently reading characters inside '<' and '/>' tags.
                boolean isInTag = false;
                StringBuilder tagNameBuilder = new StringBuilder();

                while (hasNext()) {
                    char c = characterIterator.next();

                    // If we are inside the tag, and we find the closing tag, we can finish.
                    if (isInTag && c == '>') {
                        String tagName = tagNameBuilder.toString();
                        Tag.Type type;
                        if (tagName.endsWith("/")) {
                            type = Tag.Type.EMPTY;
                            tagName = removeLastChar(tagName);
                        } else if (tagName.startsWith("/")) {
                            type = Tag.Type.CLOSE;
                            tagName = removeFirstChar(tagName);
                        } else {
                            type = Tag.Type.OPEN;
                        }

                        // Sometimes we have stuff like 'xsi:nil="true"' in the tag. Remove all these things.
                        if (tagName.contains(" ")) {
                            tagName = removeAfterSpace(tagName);
                        }

                        // We found our tag, so we return this.
                        return new Tag(tagName, type);
                    } else if (isInTag) {
                        // We are inside a tag, but not the closing part. So we just keep adding the letters.
                        tagNameBuilder.append(c);
                    } else {
                        // We are not inside a tag, so we try to detect the opening tag.
                        if (c == '<') {
                            isInTag = true;
                        }
                    }
                }

                if (tagNameBuilder.length() > 0) {
                    throw new RuntimeException("Finished reading but have leftover text that is not a tag" + tagNameBuilder);
                }

                // We do not want to stop, but we must return something. We need to filter out this element later on.
                return null;
            }
        };
    }

    /**
     * Creates an {@link Iterator} of all the characters in the reader.
     */
    private Iterator<Character> createCharacterIterator(Reader reader) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                try {
                    return reader.ready();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Character next() {
                try {
                    return (char) reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private static String removeLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    private static String removeFirstChar(String s) {
        return s.substring(1);
    }

    private static String removeAfterSpace(String s) {
        return s.substring(0, s.indexOf(" "));
    }
}
