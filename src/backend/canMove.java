package backend;

import java.util.ArrayList;

public interface canMove {
    public ArrayList<Move> getValidMoves(GameState currentGameState);
}
