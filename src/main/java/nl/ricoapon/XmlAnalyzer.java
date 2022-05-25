package nl.ricoapon;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class XmlAnalyzer {
    private final TagReader tagReader;

    public XmlAnalyzer(String path) {
        this.tagReader = new TagReader(path);
    }

    public void parseTagCount(Writer writer) throws IOException {
        Map<String, Integer> tagsCount = new HashMap<>();

        tagReader.readTags().forEach(tag -> {
            if (tag.type() != Tag.Type.CLOSE) {
                tagsCount.put(tag.name(), tagsCount.getOrDefault(tag.name(), 0) + 1);
            }
        });

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

}
