package frontend;

import backend.Move;

import java.awt.*;

public class Marker extends ImagePanel {

    public Marker(Move move, Point startPosBoard) {
        super("res/marker.png", startPosBoard.x + 40 + 64 * move.getEnd().x, startPosBoard.y + 40 + 64 * move.getEnd().y, 4);
    }
}
