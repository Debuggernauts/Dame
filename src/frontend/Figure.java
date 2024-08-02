package frontend;

import backend.King;
import backend.Move;
import backend.Piece;
import backend.utilities.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Figure {
    private final JLayeredPane layeredPane;
    private final Piece piece;
    private final ImagePanel image;
    private final ImagePanel imageActive;
    private final Point startPosBoard;
    private boolean active = false;
    private final List<ActionListener> actionListeners = new ArrayList<>();
    private final ArrayList<Marker> markers = new ArrayList<>();
    private final GUI gui; // temp

    public Figure(Piece piece, Point startPosBoard, JLayeredPane layeredPane, GUI gui) {
        this.piece = piece;
        this.gui = gui;
        this.layeredPane = layeredPane;
        this.startPosBoard = startPosBoard;

        this.image = createPieceImage(piece);
        this.imageActive = createActiveImage(piece);
        this.imageActive.setVisible(false);

        this.layeredPane.add(this.image, JLayeredPane.DRAG_LAYER);
        this.layeredPane.add(this.imageActive, JLayeredPane.DRAG_LAYER);

        this.image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActive(true);
                fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        this.imageActive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActive(false);
                fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }

    private ImagePanel createPieceImage(Piece piece) {
        String imagePath = piece instanceof King
                ? (piece.getColor() == Color.BLACK ? "res/black_king.png" : "res/white_king.png")
                : (piece.getColor() == Color.BLACK ? "res/black_man.png" : "res/white_man.png");

        int xPosition = this.startPosBoard.x + 40 + 64 * piece.getX();
        int yPosition = this.startPosBoard.y + 44 + 64 * piece.getY();

        return new ImagePanel(imagePath, xPosition, yPosition, 4);
    }

    private ImagePanel createActiveImage(Piece piece) {
        String imagePath = piece instanceof King
                ? (piece.getColor() == Color.BLACK ? "res/black_king_active.png" : "res/white_king_active.png")
                : (piece.getColor() == Color.BLACK ? "res/black_man_active.png" : "res/white_man_active.png");

        int xPosition = this.startPosBoard.x + 40 + 64 * piece.getX();
        int yPosition = this.startPosBoard.y + 44 + 64 * piece.getY();

        return new ImagePanel(imagePath, xPosition, yPosition, 4);
    }

    public void setActive(boolean active) {
        if (active == this.active) {
            return;
        }
        this.active = active;
        if (active && this.gui.gameState.whitesTurn == (this.piece.getColor() == Color.WHITE)) {
            this.image.setVisible(false);
            this.imageActive.setVisible(true);
            ArrayList<Move> moves = this.piece.getValidMoves(this.gui.gameState);
            for (Move move : moves) {
                Marker marker = new Marker(move, this.startPosBoard);
                marker.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("Marker: " + marker.pos);
                        gui.gameState.makeMove(marker.getMove());
                        gui.renderGameState(gui.gameState);
                    }
                });
                this.markers.add(marker);
                this.layeredPane.add(marker, JLayeredPane.DRAG_LAYER);
            }

        } else {
            for (Marker marker : this.markers) {
                marker.setVisible(false);
                this.layeredPane.remove(marker);
            }
            this.markers.clear();

            this.image.setVisible(true);
            this.imageActive.setVisible(false);
        }
    }

    public void removeThis() {
        this.image.setVisible(false);
        this.layeredPane.remove(this.image);
        this.imageActive.setVisible(false);
        this.layeredPane.remove(this.imageActive);
        for (Marker m : this.markers) {
            m.setVisible(false);
            this.layeredPane.remove(m);
        }
        markers.clear();
    }

    public boolean isActive() {
        return active;
    }

    private void fireActionPerformed(ActionEvent event) {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Figure other = (Figure) obj;

        if (piece == null) {
            if (other.piece != null) {
                return false;
            }
        }
        assert piece != null;
        return (piece.equals(other.piece));
    }
}
