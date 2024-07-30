package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        ImagePanel okButton = new ImagePanel(
                "res/black_king.png",
                0,
                0,
                4
        );
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputData = "test";
                setVisible(false);
            }
        });
        layeredPane.add(okButton, JLayeredPane.PALETTE_LAYER);



    }

    public String getInputData() {
        return this.inputData;
    }
}