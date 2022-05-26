package nl.ricoapon;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class retrieves certain data from XML files and writes results to disk. This is done specifically to work with
 * large XML files and prevent memory issues.
 */
public class XmlAnalyzer {
    private final TagReader tagReader;

    public XmlAnalyzer(Supplier<BufferedReader> readerSupplier) {
        this.tagReader = new TagReader(readerSupplier);
    }

    /**
     * Writes a summary of all tags with their count to the writer.
     */
    public void parseTagCount(Writer writer) throws IOException {
        Map<String, Long> tagsCount = tagReader.readTags()
                .filter(tag -> Tag.Type.CLOSE != tag.type())
                .collect(Collectors.groupingBy(Tag::name, Collectors.counting()));

        tagsCount.entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
                .forEach(e -> {
                    try {
                        writer.write(e.getValue() + " " + e.getKey() + "\n");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        writer.close();
    }

    /**
     * Writes the full tree with spaces showing the structure of the XML. Only non-empty tags are written to the file.
     */
    public void parseIntoTree(Writer writer) throws IOException {
        int indentation = 0;

        for (Tag tag : StreamEx.of(tagReader.readTags())) {
            if (tag.type() == Tag.Type.OPEN) {
                try {
                    writer.write(spaces(indentation) + tag.name() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                indentation++;
            } else if (tag.type() == Tag.Type.CLOSE) {
                indentation--;
            }
        }

        writer.close();
    }

    private static String spaces(int indentation) {
        return new String(new char[indentation]).replace('\0', ' ');
    }

    /**
     * Writes every tag to a new line with the complete path to that tag prepended. Only non-empty tags are written to the file.
     */
    public void parseIntoTreeWithCompletePath(Writer writer) throws IOException {
        List<String> paths = new ArrayList<>();


        for (Tag tag : StreamEx.of(tagReader.readTags())) {
            if (tag.type() == Tag.Type.OPEN) {
                paths.add(tag.name());
                try {
                    writer.write(concat(paths) + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (tag.type() == Tag.Type.CLOSE) {
                paths.remove(paths.size() - 1);
            }
        }

        writer.close();
    }

    private String concat(List<String> list) {
        return String.join("/", list);
    }
}
