package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NewGameDialog extends JDialog {
    private String inputData;
    private final ArrayList<String> values = new ArrayList<>(Arrays.asList("lokales Spiel", "online Spiel", "KI (leicht)", "KI (mittel)", "KI (schwer)"));
    private final ArrayList<String> imagePaths = new ArrayList<>(Arrays.asList("res/black_king.png", "res/black_man.png", "res/white_king.png", "res/white_man.png", "res/current_player_indicator.png"));
    private final ArrayList<CustomButton> buttons = new ArrayList<>();

    public NewGameDialog(Frame parent) {
        super(parent, "Neues Spiel", true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setSize(276, 227);
        this.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        this.setContentPane(layeredPane);

        ImagePanel boardBorder = new ImagePanel(
                "res/black_board.png",
                new Point(0, 0),
                4
        );
        layeredPane.add(boardBorder, JLayeredPane.DEFAULT_LAYER);

        for (int i = 0; i < values.size(); i++) {
            CustomButton button = new CustomButton(
                    new Point(20, 15 + i * 30),
                    this.imagePaths.get(i),
                    layeredPane
            );
            int finalI = i;
            button.addActionListener(e -> {
                    inputData = values.get(finalI);
                    setVisible(false);
            });

            this.buttons.add(button);
        }

    }

    public String getInputData() {
        return this.inputData;
    }
}