package backend;

import backend.utilities.Color;
import backend.utilities.Position;

import java.util.ArrayList;

public class Man extends Piece {
    public Man(Position position, Color color) {
        super(position, color);
    }

    public ArrayList<Move> getValidMoves(GameState currentGameState) {
        ArrayList<Move> moves = new ArrayList<>();

        int y = this.color == Color.BLACK ? this.pos.y+1 : this.pos.y-1;
        int xLeft = this.color == Color.BLACK ? this.pos.x+1 : this.pos.x-1;
        int xRight = this.color == Color.BLACK ? this.pos.x-1 : this.pos.x+1;

        Piece leftField = currentGameState.getPieceAt(xLeft, y);
        getMove(currentGameState, moves, leftField, true);
            Piece rightField = currentGameState.getPieceAt(xRight, y);
            getMove(currentGameState, moves, rightField, false);

        if (moves.isEmpty()) {
            if (leftField == null) {
                moves.add(new Move(
                        new Position(this.pos.x, this.pos.y),
                        new Position(xLeft, y)
                ));
            }
            if (rightField == null) {
                moves.add(new Move(
                        new Position(this.pos.x, this.pos.y),
                        new Position(xRight, y)
                ));
            }
        }

        for (int i = moves.size()-1; i >= 0; i--) {
            Position moveEnd = moves.get(i).getEnd();
            if (moveEnd.x < 0 || moveEnd.x > 7 || moveEnd.y < 0 || moveEnd.y > 7) {
                moves.remove(moves.get(i));
            }
        }

        return moves;
    }

    private void getMove(GameState currentGameState, ArrayList<Move> moves, Piece field, boolean isLeft) {
        // TODO: [Max] This fuckery should not be final, pls fix u stupid cunt
        //int x = (this.getColor() == Color.BLACK && !isLeft) || (this.getColor() == Color.WHITE && isLeft) ? this.getX()-2 : field.getX()+2;
        //int y = this.color == Color.BLACK ? this.getY()-2 : this.getY()+2;
        if (field != null && field.color != this.color) {
            int x = 0;
            int y = 0;

            if (isLeft) {
                x = this.color == Color.BLACK ? field.getX() + 1 : field.getX() - 1;
                y = this.color == Color.BLACK ? field.getY() + 1 : field.getY() - 1;
            }
            if (!isLeft) {
                x = this.color == Color.BLACK ? field.getX() - 1 : field.getX() + 1;
                y = this.color == Color.BLACK ? field.getY() + 1 : field.getY() - 1;
            }
            if (currentGameState.getPieceAt(x,y) == null) {
                moves.add(new Move(
                        this.pos,
                        new Position(x, y)
                ));
            }
            System.out.println("Sup");
        }
    }
}
