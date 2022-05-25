package nl.ricoapon;

import one.util.streamex.StreamEx;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class XmlAnalyzer {
    private final TagReader tagReader;

    public XmlAnalyzer(String path) {
        this.tagReader = new TagReader(path);
    }

    public void parseTagCount(Writer writer) throws IOException {
        Map<String, Integer> tagsCount = new HashMap<>();

        for (Tag tag : StreamEx.of(tagReader.readTags())) {
            if (tag.type() != Tag.Type.CLOSE) {
                tagsCount.put(tag.name(), tagsCount.getOrDefault(tag.name(), 0) + 1);
            }
        }

        tagsCount.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry<String, Integer>::getValue).reversed())
                .forEach(e -> {
                    try {
                        writer.write(e.getValue() + " " + e.getKey() + "\n");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

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
