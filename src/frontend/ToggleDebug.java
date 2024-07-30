package frontend;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToggleDebug {
    private final ImagePanel debugBlobEnable;
    private final ImagePanel debugBlobDisable;
    private boolean debug = false;

    public ToggleDebug(int posX, int posY, JLayeredPane layeredPane) {
        // blackboard debug blob
        this.debugBlobEnable = new ImagePanel(
                "res/enabled_debug.png",
                posX,
                posY,
                4
        );
        this.debugBlobEnable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                debug = false;
                debugBlobEnable.setVisible(false);
                debugBlobDisable.setVisible(true);
            }
        });
        this.debugBlobDisable = new ImagePanel(
                "res/disabled_debug.png",
                posX,
                posY,
                4
        );
        this.debugBlobDisable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                debug = true;
                debugBlobEnable.setVisible(true);
                debugBlobDisable.setVisible(false);
            }
        });
        this.debugBlobEnable.setVisible(false);
        layeredPane.add(this.debugBlobEnable, JLayeredPane.DRAG_LAYER);
        layeredPane.add(this.debugBlobDisable, JLayeredPane.DRAG_LAYER);
    }

    public boolean isDebug() {
        return debug;
    }
}
