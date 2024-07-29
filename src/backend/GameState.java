package backend;

import backend.utilities.Tuple;

import java.util.ArrayList;

public class GameState {
    ArrayList<Piece> white;
    ArrayList<Piece> black;
    int turn;
    boolean whitesTurn;
    boolean playing;

    /**
     * Starts a new game by initializing the game state
     * and maintaining the main game loop.
     */
    public void startGame() {
        initalize();
        playing = true;

        while (playing) {

        }
    }

    /**
     * Initializes the game state with the starting positions of all {@link Piece} objects
     */
    void initalize() {
        white = new ArrayList<>();
        black = new ArrayList<>();

        Tuple<Integer, Integer>[] whitePos = new Tuple[] {
                new Tuple<>(1,1), new Tuple<>(1,3), new Tuple<>(2,2), new Tuple<>(3,1), new Tuple<>(3,3),
                new Tuple<>(4,2), new Tuple<>(5,1), new Tuple<>(5,3), new Tuple<>(6,2), new Tuple<>(7,1),
                new Tuple<>(7,3), new Tuple<>(8,2)
        };
        Tuple<Integer, Integer>[] blackPos = new Tuple[] {
                new Tuple<>(1,2), new Tuple<>(2,1), new Tuple<>(2,3), new Tuple<>(3,2), new Tuple<>(4,1),
                new Tuple<>(4,3), new Tuple<>(5,2), new Tuple<>(6,1), new Tuple<>(6,3), new Tuple<>(7,2),
                new Tuple<>(8,1), new Tuple<>(8,3)
        };

        for (Tuple<Integer,Integer> pos : whitePos) {
            white.add(new Piece(pos.x,pos.y));
        }
        for (Tuple<Integer,Integer> pos : blackPos) {
            black.add(new Piece(pos.x,pos.y));
        }
    }

    void makeMove() {

    }

    boolean pieceIsGroundRow(Piece piece, boolean isWhite) {
        if ((isWhite && piece.getY() == 8) || (!isWhite && piece.getY() == 1)) {
            return true;
        }
        return false;
    }

    /**
     * Upgrades a {@link Man} to a {@link King} {@link Piece}
     */
    void toKing(Piece piece) {

    }
}