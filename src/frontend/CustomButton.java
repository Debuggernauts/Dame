package frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomButton {
    private final ImagePanel image;
    private final List<ActionListener> actionListeners = new ArrayList<>();

    public CustomButton(int posX, int posY, String path, JLayeredPane layeredPane) {
        this.image = new ImagePanel(path, posX, posY, 4);

        layeredPane.add(this.image, JLayeredPane.DRAG_LAYER);

        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }

    private void fireActionPerformed(ActionEvent event) {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }
}
