package backend;

import backend.utilities.Tuple;

import java.util.ArrayList;

public class Piece {
    protected int x;
    protected int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
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

    ArrayList<Tuple<Integer,Integer>> getValidMoves() {
        return new ArrayList<>();
    }
}
