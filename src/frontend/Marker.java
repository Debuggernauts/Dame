package frontend;

import backend.Move;
import backend.utilities.Position;

import java.awt.*;

public class Marker extends ImagePanel {
    public Position pos;
    private Move move;

    public Marker(Move move, Point startPosBoard) {
        super("res/marker.png", new Point(startPosBoard.x + 36 + 64 * move.getEnd().x, startPosBoard.y + 40 + 64 * move.getEnd().y), 4);
        this.pos = move.getEnd();
        this.move = move;
    }

    public Move getMove() {
        return this.move;
    }
}
