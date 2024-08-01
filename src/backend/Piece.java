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

    public Color getColor() {
        return color;
    }

    public ArrayList<Move> getValidMoves(GameState currentGameState) {
        ArrayList<Move> moves = new ArrayList<>();

        int y = this.color == Color.BLACK ? this.y-1 : this.y+1;
        int xLeft = this.color == Color.BLACK ? this.x+1 : this.x-1;
        int xRight = this.color == Color.BLACK ? this.x-1 : this.x+1;

        Piece leftField = currentGameState.getPieceAt(xLeft,y);
        Piece rightField = currentGameState.getPieceAt(xRight,y);

        getMove(currentGameState, moves, leftField);
        getMove(currentGameState, moves, rightField);

        if (moves.isEmpty()) {
            if (leftField == null) {
                moves.add(new Move(
                        new Tuple<Integer, Integer>(this.x, this.y),
                        new Tuple<Integer, Integer>(xRight, y)
                ));
            }
            if (rightField == null) {
                moves.add(new Move(
                        new Tuple<Integer, Integer>(this.x, this.y),
                        new Tuple<Integer, Integer>(xRight, y)
                ));
            }
        }

        return moves;
    }

    private void getMove(GameState currentGameState, ArrayList<Move> moves, Piece field) {
        // TODO: [Max] This fuckery should not be final, pls fix u stupid cunt
        if (field != null && currentGameState.getPieceAt(field.getX()-1, this.color == Color.BLACK ? field.getY()-1 : field.getY()+1) == null && field.getColor() != this.color) {
            moves.add(new Move(
                    new Tuple<Integer,Integer>(this.x, this.y),
                    new Tuple<Integer,Integer>(field.getX()-1, field.getY())
            ));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.color + ")" + " at (" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Piece other = (Piece) obj;

        if (color != other.color) {
            return false;
        }

        return (x == other.x && y == other.y);
    }
}
