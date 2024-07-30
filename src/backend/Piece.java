package backend;

import backend.utilities.Tuple;

import java.util.ArrayList;

enum Color {
    BLACK,
    WHITE
}

public abstract class Piece {
    protected int x;
    protected int y;
    protected Color color;

    public Piece(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    Color getColor() { return color; }

    ArrayList<Tuple<Integer,Integer>> getValidMoves() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.color + ")" + " at (" + x + ", " + y + ")";
    }
}
