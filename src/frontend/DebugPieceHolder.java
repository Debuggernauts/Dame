package frontend;

import java.awt.*;

public class DebugPieceHolder extends ImagePanel {
    private Point pos;

    public DebugPieceHolder(Point pos, int scale) {
        super("res/debug_piece_holder.png", pos, scale);
        this.pos = pos;
    }
}
