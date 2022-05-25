package nl.ricoapon;

public record Tag(String name, Tag.Type type) {
    public enum Type {
        OPEN, CLOSE, EMPTY
    }
}
