package frontend;

import backend.Man;
import backend.Piece;
import backend.utilities.Color;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
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
        if (stackSize >= 12) {
            return;
        }
        stackSize++;
        ImagePanel figure = new ImagePanel(
                this.color == Color.BLACK ? "res/black_man.png" : "res/white_man.png",
                this.startX + (stackSize > 6 ? 35 : 0),
                this.startY - (stackSize - 1 - (stackSize > 6 ? 8 : 0)) * 15,
                4
        );
        this.figures.push(figure);
        this.layeredPane.add(
                figure,
                Integer.valueOf(
                        JLayeredPane.MODAL_LAYER + stackSize*2 + (stackSize > 6 ? 1 : 0)
                )
        );
    }

    public void decrement() {
        if (stackSize <= 0) {
            return;
        }
        stackSize--;
        ImagePanel topFigure = figures.pop();
        layeredPane.remove(topFigure);
        layeredPane.revalidate();
        layeredPane.repaint();
    }
}
