package frontend;

import backend.GameState;

import javax.swing.*;
import java.awt.*;

public class PlayerIndicator {
    private final ImagePanel indicatorWhite;
    private final ImagePanel indicatorBlack;

    public PlayerIndicator(JLayeredPane layeredPane) {
        this.indicatorWhite = new ImagePanel(
                "res/current_player_indicator.png",
                new Point(163, 649 + 44),
                4
        );
        this.indicatorBlack = new ImagePanel(
                "res/current_player_indicator.png",
                new Point(483, 649 + 44),
                4
        );

        this.indicatorBlack.setVisible(false);
        layeredPane.add(this.indicatorWhite, JLayeredPane.MODAL_LAYER);
        layeredPane.add(this.indicatorBlack, JLayeredPane.MODAL_LAYER);
    }

    public void setIndicator(GameState gst) {
        if (gst.whitesTurn) {
            this.indicatorWhite.setVisible(false);
            this.indicatorBlack.setVisible(true);
        } else {
            this.indicatorWhite.setVisible(true);
            this.indicatorBlack.setVisible(false);

        }
    }
}
