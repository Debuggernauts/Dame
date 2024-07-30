package backend;

import backend.utilities.Color;
import backend.utilities.Tuple;

import java.util.ArrayList;

public class GameState {
    public ArrayList<Piece> pieces;
    public boolean blacksTurn;

    /**
     * Starts a new game by initializing the game state
     * and maintaining the main game loop.
     */
    public void startGame() {
        initalize();
    }

    /**
     * Initializes the game state with the starting positions of all {@link Piece} objects
     */
    void initalize() {
        blacksTurn = true;
        pieces = new ArrayList<>();

        Tuple<Integer, Integer>[] whitePos = new Tuple[] {
                new Tuple<>(0,0), new Tuple<>(2,0), new Tuple<>(4,0), new Tuple<>(6,0),
                new Tuple<>(1,1), new Tuple<>(3,1), new Tuple<>(5,1), new Tuple<>(7,1),
                new Tuple<>(0,2), new Tuple<>(2,2), new Tuple<>(4,2), new Tuple<>(6,2)
        };
        Tuple<Integer, Integer>[] blackPos = new Tuple[] {
                new Tuple<>(1,5), new Tuple<>(3,5), new Tuple<>(7,5), new Tuple<>(0,6),
                new Tuple<>(2,6), new Tuple<>(4,6), new Tuple<>(6,6), new Tuple<>(1,7),
                new Tuple<>(3,7), new Tuple<>(5,7), new Tuple<>(7,7), new Tuple<>(5,5)
        };

        for (Tuple<Integer,Integer> pos : blackPos) {
            pieces.add(new Man(pos.x,pos.y, Color.BLACK));
        }

        for (Tuple<Integer,Integer> pos : whitePos) {
            pieces.add(new Man(pos.x,pos.y, Color.WHITE));
        }
    }

    /**
     * Moves a {@link Piece} in a specific turn.
     */
    void makeMove() {
        // Wait for click on piece
        // TODO: Philipp, pls giv method
        // Get validMoves for piece
        // Change position of selected piece with selected move
        promotionCheck();
        blacksTurn = !blacksTurn;
    }

    /**
     * Removes a {@link Piece} at the given position
     */
    void removePiece(int x, int y) {
        pieces.remove(getPieceAt(x, y));
    }

    /**
     * Returns the {@link Piece} at the given position
     */
    Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) { return piece; }
        }
        return null;
    }

    void promotionCheck() {
        for(Piece piece : pieces) {
            if (pieceIsGroundRow(piece)) { toKing(piece); }
        }
    }

    boolean pieceIsGroundRow(Piece piece) {
        return ((piece.color == Color.BLACK && piece.getY() == 7) || (piece.color == Color.WHITE && piece.getY() == 0));
    }

    /**
     * Upgrades a {@link Man} to a {@link King} {@link Piece}
     */
    void toKing(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();
        Color color = piece.getColor();
        pieces.set(pieces.indexOf(piece), new King(x, y, color));
    }
}
