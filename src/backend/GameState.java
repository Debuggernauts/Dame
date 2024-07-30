package backend;

import backend.utilities.Tuple;

import java.util.ArrayList;

public class GameState {
    ArrayList<Piece> black;
    ArrayList<Piece> white;
    boolean blacksTurn;
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
        black = new ArrayList<>();
        white = new ArrayList<>();

        Tuple<Integer, Integer>[] blackPos = new Tuple[] {
                new Tuple<>(0,1), new Tuple<>(1,0), new Tuple<>(1,2), new Tuple<>(2,1), new Tuple<>(3,0),
                new Tuple<>(3,2), new Tuple<>(4,1), new Tuple<>(5,0), new Tuple<>(5,2), new Tuple<>(6,1),
                new Tuple<>(7,0), new Tuple<>(7,2)
        };
        Tuple<Integer, Integer>[] whitePos = new Tuple[] {
                new Tuple<>(0,7), new Tuple<>(0,5), new Tuple<>(1,6), new Tuple<>(2,7), new Tuple<>(2,5),
                new Tuple<>(3,6), new Tuple<>(4,7), new Tuple<>(4,5), new Tuple<>(5,6), new Tuple<>(6,7),
                new Tuple<>(6,5), new Tuple<>(7,6)
        };

        for (Tuple<Integer,Integer> pos : blackPos) {
            black.add(new Man(pos.x,pos.y));
        }
        for (Tuple<Integer,Integer> pos : whitePos) {
            white.add(new Man(pos.x,pos.y));
        }
    }

    /**
     * Moves a {@link Piece} in a specific turn.
     */
    void makeMove() {

    }

    boolean pieceIsGroundRow(Piece piece, boolean isBlack) {
        return ((isBlack && piece.getY() == 7) || (!isBlack && piece.getY() == 0));
    }

    /**
     * Upgrades a {@link Man} to a {@link King} {@link Piece}
     */
    void toKing(Piece piece) {

    }
}
