package frontend;

import javax.swing.*;
import java.awt.*;

public class ToggleDebug extends ToggleButton {

    public ToggleDebug(int posX, int posY, JLayeredPane layeredPane) {
        super("res/enabled_debug.png", "res/disabled_debug.png", new Point(posX, posY), false, layeredPane);
    }

    public boolean isDebugEnabled() {
        return this.isFirst();
    }
}
