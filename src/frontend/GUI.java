package frontend;

import backend.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GUI extends JFrame {
    public GUI() {
        // init stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 832);
        this.setTitle("Dame");
        this.setResizable(false);
        this.setLayout(null);
        // TODO: this.setFocusable();

        // icon
        this.setIcon();

        // layer
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        this.setContentPane(layeredPane);

        // Background Panel
        BackgroundImage background = new BackgroundImage();
        background.setBounds(0, 0, 800, 600);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Border Image Panel
        BorderImage boardBorder = new BorderImage(50, 50);
        boardBorder.setBounds(0, 0, 800, 600);
        layeredPane.add(boardBorder, JLayeredPane.PALETTE_LAYER);

        this.pack();


        // menubar
        JMenuBar menuBar = this.generateMenuBar();
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu spielMenu = new JMenu("Spiel");
        menuBar.add(spielMenu);

        JMenuItem newGameItem = new JMenuItem("neues Spiel");
        JMenuItem onlineConnectItem = new JMenuItem("online spielen");

        JMenu playAgainstMenu = new JMenu("wähle Gegner");

        JRadioButtonMenuItem opponentItem = new JRadioButtonMenuItem("Gegner local");
        JRadioButtonMenuItem aiEzzItem = new JRadioButtonMenuItem("KI leicht");
        JRadioButtonMenuItem aiMiddlItem = new JRadioButtonMenuItem("KI mittel");
        JRadioButtonMenuItem aiHardItem = new JRadioButtonMenuItem("KI schwer");

        ButtonGroup aiGroup = new ButtonGroup();
        aiGroup.add(opponentItem);
        aiGroup.add(aiEzzItem);
        aiGroup.add(aiMiddlItem);
        aiGroup.add(aiHardItem);

        aiEzzItem.setSelected(true);

        playAgainstMenu.add(opponentItem);
        playAgainstMenu.add(aiEzzItem);
        playAgainstMenu.add(aiMiddlItem);
        playAgainstMenu.add(aiHardItem);

        spielMenu.add(newGameItem);
        spielMenu.add(onlineConnectItem);
        spielMenu.add(playAgainstMenu);

        JMenu hilfeMenu = new JMenu("Hilfe");
        menuBar.add(hilfeMenu);

        JCheckBoxMenuItem toggleDebugItem = new JCheckBoxMenuItem("Debug umschalten");
        JMenuItem instructionItem = new JMenuItem("Anleitung");

        hilfeMenu.add(toggleDebugItem);
        hilfeMenu.add(instructionItem);
        return menuBar;
    }

    private void setIcon() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("images/black_king.png");
            assert imageStream != null;
            BufferedImage icon = ImageIO.read(imageStream);
            this.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void renderGamestate(GameState gst) {
        // delete
        // create

    }

    private void placePossibleMoveButton() {

    }
}