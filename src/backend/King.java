package backend;

import backend.utilities.Color;
import backend.utilities.Position;

import java.util.ArrayList;

public class King extends Piece {

    public King(Position pos, Color color) {
        super(pos,color);
    }

    public ArrayList<Move> getValidMoves(GameState currentGameState) {
        ArrayList<Move> moves = new ArrayList<>();

        int x = this.pos.x;
        int y = this.pos.y;

        while (0 <= x && x <= 7 && 0 <= y && y <= 7 ) {
            Piece field = currentGameState.getPieceAt(x,y);
            if (field != null) {
               break;
            }
            moves.add(new Move(
                    this.pos,
                    new Position(x,y)
            ));
            x++;
            y++;
        }



        return moves;
    }
}
