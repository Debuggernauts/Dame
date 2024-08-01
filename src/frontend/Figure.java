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

    public Figure(Piece piece, Point startPosBoard, JLayeredPane layeredPane) {
        this.piece = piece;
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
        /*if (active == this.active) {
            return;
        }*/
        System.out.println(active);
        this.active = active;
        if (active) {
            this.image.setVisible(false);
            this.imageActive.setVisible(true);
            System.out.println("render all Marker");
            ArrayList<Move> moves = Piece.getValidMoves();
            for (Move move : moves) {
                Marker marker = new Marker(move, this.startPosBoard);
                this.markers.add(marker);
                this.layeredPane.add(marker, JLayeredPane.DRAG_LAYER);
            }

        } else {
            System.out.println(this.markers);
            for (Marker marker : this.markers) {
                this.layeredPane.remove(marker);
            }
            this.markers.clear();

            this.image.setVisible(true);
            this.imageActive.setVisible(false);
            System.out.println("remove all Marker");
        }
    }

    public void removeThis() {
        this.layeredPane.remove(this.image);
        this.layeredPane.remove(this.imageActive);
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
