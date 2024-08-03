package frontend;

public class DebugPieceHolder extends ImagePanel {
    private int startX;
    private int startY;

    public DebugPieceHolder(int startX, int startY, int scale) {
        super("res/debug_piece_holder.png", startX, startY, scale);
        this.startX = startX;
        this.startY = startY;
    }
}
