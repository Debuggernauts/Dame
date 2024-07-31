package frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ToggleDebug {
    private final ImagePanel debugBlobEnable;
    private final ImagePanel debugBlobDisable;
    private boolean debugEnabled = false;
    private final List<ActionListener> actionListeners = new ArrayList<>();

    public ToggleDebug(int posX, int posY, JLayeredPane layeredPane) {
        this.debugBlobEnable = new ImagePanel("res/enabled_debug.png", posX, posY, 4);
        this.debugBlobDisable = new ImagePanel("res/disabled_debug.png", posX, posY, 4);

        this.debugBlobEnable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDebugEnabled(false);
            }
        });

        this.debugBlobDisable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDebugEnabled(true);
            }
        });

        this.debugBlobEnable.setVisible(false);
        layeredPane.add(this.debugBlobEnable, JLayeredPane.DRAG_LAYER);
        layeredPane.add(this.debugBlobDisable, JLayeredPane.DRAG_LAYER);
    }

    private void setDebugEnabled(boolean enabled) {
        this.debugEnabled = enabled;
        // Fire action event when state changes
        fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        // Update visibility based on the new state
        debugBlobEnable.setVisible(enabled);
        debugBlobDisable.setVisible(!enabled);
    }

    private void fireActionPerformed(ActionEvent event) {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugState(boolean enabled) {
        setDebugEnabled(enabled);
    }
}
