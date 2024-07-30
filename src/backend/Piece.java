package backend;

import backend.utilities.Color;
import backend.utilities.Tuple;

import java.util.ArrayList;

public abstract class Piece {
    protected int x;
    protected int y;
    protected Color color;

    public Piece(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() { return color; }

    public ArrayList<Move> getValidMoves() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.color + ")" + " at (" + x + ", " + y + ")";
    }
}
