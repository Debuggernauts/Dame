package backend;

import backend.utilities.Color;
import backend.utilities.Position;

import java.util.ArrayList;

public class GameState {
    public ArrayList<Piece> pieces;
    public boolean whitesTurn;

    /**
     * Starts a new game by initializing the game state
     * and maintaining the main game loop.
     */
    /*public void startGame() {
        initalize();
    }*/

    /**
     * Initializes the game state with the starting positions of all {@link Piece} objects
     */
    public void initalize() {
        whitesTurn = true;
        pieces = new ArrayList<>();

        Position[] whitePos = new Position[] {
                new Position(0,7), new Position(2,7), new Position(4,7), new Position(6,7),
                new Position(1,6), new Position(3,6), new Position(5,6), new Position(7,6),
                new Position(0,5), new Position(2,5), new Position(4,5), new Position(6,5)
        };
        Position[] blackPos = new Position[] {
                new Position(1,2), new Position(3,2), new Position(7,2), new Position(0,1),
                new Position(2,1), new Position(4,1), new Position(6,1), new Position(1,0),
                new Position(3,0), new Position(5,0), new Position(7,0)};//, new Position(5,2)
        //};

        for (Position pos : blackPos) {
            pieces.add(new Man(pos, Color.BLACK));
        }

        for (Position pos : whitePos) {
            pieces.add(new Man(pos, Color.WHITE));
        }
    }

    /**
     * Moves a {@link Piece} in a specific turn.
     */
    public void makeMove(Move move) {
        Piece piece = getPieceAt(move.getStart().x, move.getStart().y);
        piece.setPos(move.getEnd());
        System.out.println(move);
        consecutiveMove(piece, move);
        // TODO: Philipp, pls giv method
        // test if game finished
        promotionCheck();
        whitesTurn = !whitesTurn;
    }

    public void consecutiveMove(Piece piece, Move move) {
        if (Math.abs(move.getEnd().x -move.getStart().x) > 1) {
            ArrayList<Move> moves = piece.getValidMoves(this);
            if (!moves.isEmpty() && Math.abs(moves.get(0).getEnd().x-moves.get(0).getStart().x) > 1) {
                makeMove(moves.get(0));
            }
        }
    }

    // TODO: [Max] make sure only the moves with respect to the turn are calculated... Important for stalemate question
    ArrayList<Move> getAllValidMoves() {
        ArrayList<Move> allMoves = new ArrayList<>();
        for (Piece piece : pieces) {
            ArrayList<Move> pieceMoves = piece.getValidMoves(this);
            allMoves.addAll(pieceMoves);
        }
        return allMoves;
    }

    /**
     * Removes a {@link Piece} at the given position
     */
    void removePieceAt(Position pos) {
        pieces.remove(getPieceAt(pos.x, pos.y));
    }

    /**
     * Returns the {@link Piece} at the given position
     */
    public Piece getPieceAt(int x, int y) {
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
        pieces.set(pieces.indexOf(piece), new King(new Position(x,y), color));
    }

    public ArrayList<Position> getAllEmptyPositions() {
        // TODO [Max]
        return new ArrayList<>();
    }
}
