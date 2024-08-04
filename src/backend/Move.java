package backend;

import backend.utilities.Position;

public class Move {
    private final Position start;
    private final Position end;
    private Piece capturedPiece = null;
    // TODO [Max]: ich hät da gern nen parameter, der sagt, ob der Move eine Piece schlägt

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getStart() { return start; }
    public Position getEnd() { return end; }

    @Override
    public String toString() {
        return "[Move " + start + " -> " + end + "]";
    }
}
