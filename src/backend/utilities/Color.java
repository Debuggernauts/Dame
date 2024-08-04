package backend.utilities;

public enum Color {
    BLACK,
    WHITE;

    public Color not() {
        return this == BLACK ? WHITE : BLACK;
    }
}
