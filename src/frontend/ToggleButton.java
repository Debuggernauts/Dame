package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ToggleButton {
    private final ImagePanel firstStateImage;
    private final ImagePanel secondStateImage;
    private boolean isFirstState = true;
    private final List<ActionListener> actionListeners = new ArrayList<>();

    public ToggleButton(String firstStatePath, String secondStatePath, Point pos, boolean defaultState, JLayeredPane layeredPane) {
        this.firstStateImage = new ImagePanel(firstStatePath, pos, 4);
        this.secondStateImage = new ImagePanel(secondStatePath, pos, 4);

        this.firstStateImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setFirstState(false); // TODO
            }
        });

        this.secondStateImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setFirstState(true); // TODO
            }
        });

        this.firstStateImage.setVisible(defaultState);
        this.secondStateImage.setVisible(!defaultState);
        layeredPane.add(this.firstStateImage, JLayeredPane.DRAG_LAYER);
        layeredPane.add(this.secondStateImage, JLayeredPane.DRAG_LAYER);
    }

    public boolean isFirst() {
        return isFirstState;
    }

    private void fireActionPerformed(ActionEvent event) {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    private void setFirstState(boolean enabled) {
        this.isFirstState = enabled;

        fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        firstStateImage.setVisible(enabled);
        secondStateImage.setVisible(!enabled);
    }
}









