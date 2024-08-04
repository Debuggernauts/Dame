package frontend;

import java.awt.*;
import java.util.ArrayList;

public class PlayerIndicator extends ImagePanel {
    private final ArrayList<Point> positions = new ArrayList<>() {
        {
            this.add(new Point(483, 649 + 44));
            this.add(new Point(163, 649 + 44));
        }
    };

    public PlayerIndicator() {
        super("res/current_player_indicator.png",
                new Point(483, 649 + 44), // siehe Kommentar drunter
                4
        );
        /*
            Das ist so nen Feature von Java 11,
            dass die super als erstes stehen muss
            und man auf keine Klasseneigenschaften
            zugreifen kann. lol
        */
    }

    public void setIndicator(boolean whitesTurn) {
        this.setLocation(this.positions.get(whitesTurn ? 1 : 0));
    }
}