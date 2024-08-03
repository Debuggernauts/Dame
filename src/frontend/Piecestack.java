package frontend;

import backend.utilities.Color;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Piecestack {
    private int stackSize = 0;
    private final int startX;
    private final int startY;
    private final Stack<ImagePanel> figures = new Stack<>();
    private final JLayeredPane layeredPane;
    private final Color color;

    public Piecestack(JLayeredPane layeredPane, Color color, int startX, int startY) {
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.layeredPane = layeredPane;
    }

    public void increment() {
        if (this.stackSize >= 12) {
            return;
        }
        this.stackSize++;
        this.addFigure();
    }

    private void addFigure() {
        ImagePanel figure = new ImagePanel(
                this.color == Color.BLACK ? "res/black_man.png" : "res/white_man.png",
                new Point(
                        this.startX + (this.stackSize > 6 ? 35 : 0),
                        this.startY - (this.stackSize - 1 - (this.stackSize > 6 ? 8 : 0)) * 15
                ),
                4
        );
        this.figures.push(figure);
        this.layeredPane.add(
                figure,
                Integer.valueOf(
                        JLayeredPane.MODAL_LAYER + this.stackSize*2 + (this.stackSize > 6 ? 1 : 0)
                )
        );
    }

    private void removeFigure() {
        ImagePanel topFigure = this.figures.pop();
        this.layeredPane.remove(topFigure);
        this.layeredPane.revalidate();
        this.layeredPane.repaint();
    }

    public void decrement() {
        if (this.stackSize <= 0) {
            return;
        }
        this.stackSize--;
        this.removeFigure();
    }
}
