package frontend;

import backend.utilities.Color;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class PieceStack {
    private int stackSize = 0;
    private final Point pos;
    private final Stack<ImagePanel> figures = new Stack<>();
    private final JLayeredPane layeredPane;
    private final Color color;

    public PieceStack(JLayeredPane layeredPane, Color color, Point pos) {
        this.color = color;
        this.pos = pos;
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
                        this.pos.x + (this.stackSize > 6 ? 35 : 0),
                        this.pos.y - (this.stackSize - 1 - (this.stackSize > 6 ? 8 : 0)) * 15
                ),
                4
        );
        this.figures.push(figure);
        this.layeredPane.add(
                figure,
                Integer.valueOf(
                        JLayeredPane.MODAL_LAYER + this.stackSize * 2 + (this.stackSize > 6 ? 1 : 0)
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
