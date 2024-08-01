package frontend;

import javax.swing.*;
import java.awt.*;

public class NewGameDialog extends JDialog {
    private String inputData;

    public NewGameDialog(Frame parent) {
        super(parent, "New Game", true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(276, 227);

        JLayeredPane layeredPane = new JLayeredPane();
        this.setContentPane(layeredPane);

        ImagePanel boardBorder = new ImagePanel(
                "res/black_board.png",
                0,
                0,
                4
        );

        layeredPane.add(boardBorder, JLayeredPane.DEFAULT_LAYER);

        CustomButton okButton = new CustomButton(
                0,
                0,
                "res/black_king.png",
                layeredPane
        );
        okButton.addActionListener(e -> {
            inputData = "test";
            setVisible(false);
        });
    }

    public String getInputData() {
        return this.inputData;
    }
}