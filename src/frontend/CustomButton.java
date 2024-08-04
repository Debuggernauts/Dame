package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomButton extends ImagePanel {
    private final List<ActionListener> actionListeners = new ArrayList<>();

    public CustomButton(Point pos, String path) {
        super(path, pos, 4);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fireActionPerformed(new ActionEvent(CustomButton.this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }

    private void fireActionPerformed(ActionEvent event) {
        for (ActionListener listener : this.actionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }
}
