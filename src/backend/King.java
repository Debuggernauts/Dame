package backend;

import backend.utilities.Color;
import backend.utilities.Position;
import backend.utilities.Tuple;

import java.util.ArrayList;


public class King extends Piece {

    public King(Position pos, Color color) {
        super(pos,color);
    }

    public ArrayList<Move> getValidMoves(GameState currentGameState) {
        ArrayList<Move> moves = new ArrayList<>();

        ArrayList<Tuple<Integer,Integer>> directions = new ArrayList<>();
        directions.add(new Tuple<>(1,1));
        directions.add(new Tuple<>(-1,1));
        directions.add(new Tuple<>(1,-1));
        directions.add(new Tuple<>(-1,-1));

        for (Tuple<Integer, Integer> dir : directions) {
            checkDirection(currentGameState, moves, dir.x, dir.y);
        }

        return moves;
    }

    private void checkDirection(GameState currentGameState, ArrayList<Move> moves, int dx, int dy) {
        int x = this.pos.x;
        int y = this.pos.y;

        while (0 <= x + dx && x + dx < 8 && 0 <= y + dy && y + dy < 8) {
            x += dx;
            y += dy;
            Piece field = currentGameState.getPieceAt(x,y);
            System.out.println(field);

            if (field != null) {
                filterMoves(currentGameState, moves, field, x, y, dx, dy);
                break;
            }

            moves.add(new Move(this.pos, new Position(x, y)));
        }
    }

    private void filterMoves(GameState currentGameState, ArrayList<Move> moves, Piece field, int x, int y, int dx, int dy) {
        if (field.getColor() != this.getColor() &&
                0 <= x + dx && x + dx < 8 && 0 <= y + dy && y + dy < 8 &&
                currentGameState.getPieceAt(x+dx, y+dy) == null) {
            moves.clear();
            moves.add(new Move(this.pos, new Position(x + dx, y + dy)));
        }
    }
}
