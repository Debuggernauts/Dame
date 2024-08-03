package frontend;

import javax.swing.*;
import java.awt.*;

public class ToggleDebug extends ToggleButton {

    public ToggleDebug(Point pos, JLayeredPane layeredPane) {
        super("res/enabled_debug.png", "res/disabled_debug.png", pos, false, layeredPane);
    }

    public boolean isDebugEnabled() {
        return this.isFirst();
    }
}
