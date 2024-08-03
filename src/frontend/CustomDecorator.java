package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CustomDecorator extends JComponent {
    private final JLayeredPane layeredPane;
    private Point initialClick;

    public CustomDecorator(GUI gui) {
        this.layeredPane = gui.getLayeredPane();

        ImagePanel background = new ImagePanel(
                "res/decorator_background.png",
                new Point(0, 0),
                1
        );
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                initialClick = e.getPoint();
                background.getComponentAt(initialClick);
            }
        });
        background.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX;
                thisX = gui.getLocation().x;
                int thisY = gui.getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int newX = thisX + xMoved;
                int newY = thisY + yMoved;
                gui.setLocation(newX, newY);

            }
        });
        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        ImagePanel exitButton = new ImagePanel("res/button_exit.png", new Point(988, 12), 4);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });
        this.layeredPane.add(exitButton, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 1));

        ImagePanel minimizeButton = new ImagePanel("res/button_minimize.png", new Point(952, 12), 4);
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gui.setState(JFrame.ICONIFIED);
            }
        });
        this.layeredPane.add(minimizeButton, Integer.valueOf(JLayeredPane.DEFAULT_LAYER + 1));
    }
}
