package frontend;

import backend.Move;
import backend.utilities.Position;

import java.awt.*;

public class Marker extends ImagePanel {
    private final Position pos;
    private final Move move;

    public Marker(Move move, Point startPosBoard) {
        super(
                "res/marker.gif",
                new Point(
                        startPosBoard.x + 40 + 64 * move.getEnd().x,
                        startPosBoard.y + 44 + 64 * move.getEnd().y
                ),
                4
        );
        this.pos = move.getEnd();
        this.move = move;
    }

    public Move getMove() {
        return this.move;
    }

    public Position getPos() {
        return this.pos;
    }
}
