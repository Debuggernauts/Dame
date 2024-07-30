package frontend;

import backend.GameState;
import backend.Piece;
import backend.King;
import backend.Man;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class GUI extends JFrame {
    private final ImagePanel indicatorWhite;
    private final ImagePanel indicatorBlack;
    GameState test_gameState = new GameState(); // temp
    private ArrayList<ImagePanel> figures = new ArrayList<>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final Point startPosBoard = new Point(55, 45);


    public GUI() {
        // init stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040, 894);
        this.setTitle("Dame");
        this.setResizable(false);
        this.setLayout(null);
        // TODO: this.setFocusable();

        // icon
        this.setIcon();

        // layer
        this.setContentPane(this.layeredPane);

        // Background Panel
        ImagePanel background = new ImagePanel("images/background.png", 0, 0, 1);
        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // menubar
        JMenuBar menuBar = this.generateMenuBar();
        this.setJMenuBar(menuBar);

        // Border Image Panel
        ImagePanel boardBorder = new ImagePanel("images/board_border.png", this.startPosBoard.x, this.startPosBoard.y, 4);
        this.layeredPane.add(boardBorder, JLayeredPane.PALETTE_LAYER);

        // bg grid
        ImagePanel boardBackground = new ImagePanel("images/board_bg.png", this.startPosBoard.x + 40, this.startPosBoard.y + 44, 1);
        this.layeredPane.add(boardBackground, JLayeredPane.MODAL_LAYER);

        // Player Indicator
        this.indicatorWhite = new ImagePanel("images/current_player_indicator.png", 132, 600, 4);
        this.indicatorBlack = new ImagePanel("images/current_player_indicator.png", 452, 600, 4);
        this.indicatorBlack.setVisible(false);
        this.layeredPane.add(this.indicatorWhite, JLayeredPane.MODAL_LAYER);
        this.layeredPane.add(this.indicatorBlack, JLayeredPane.MODAL_LAYER);


        // temp
        test_gameState.whitesTurn = false;
        this.setPlayerIndicator(test_gameState);
        JButton button = new JButton("(temp): Toggle current player");
        button.setBounds(800, 200, 200, 30);
        button.addActionListener(e -> {
            test_gameState.whitesTurn = !test_gameState.whitesTurn;
            setPlayerIndicator(test_gameState);
        });
        this.layeredPane.add(button, JLayeredPane.MODAL_LAYER);

        this.renderGamestate(test_gameState);

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

    public void setPlayerIndicator(GameState gst) {
        if (gst.whitesTurn) {
            this.indicatorWhite.setVisible(true);
            this.indicatorBlack.setVisible(false);
        } else {
            this.indicatorWhite.setVisible(false);
            this.indicatorBlack.setVisible(true);
        }
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

    private void placeFigure(Piece piece, boolean isWhite) {

        if (piece instanceof King || true) {
            ImagePanel figure = new ImagePanel(isWhite ? "images/black_king.png" : "images/white_king.png", this.startPosBoard.x + 40 + 64 * piece.getX(), this.startPosBoard.y + 44 + 64 * piece.getY(), 4);
            this.figures.add(figure);
            this.layeredPane.add(figure, JLayeredPane.DRAG_LAYER);

        } else if (piece instanceof Man) {
            ImagePanel figure = new ImagePanel(isWhite ? "images/black_man.png" : "images/white_man.png", this.startPosBoard.x + 40 + 64 * piece.getX(), this.startPosBoard.y + 44 + 64 * piece.getY(), 4);
            this.figures.add(figure);
            this.layeredPane.add(figure, JLayeredPane.DRAG_LAYER);
        }


    }

    public void renderGamestate(GameState gst) {
        this.deleteAllPieces();
        test_gameState.startGame();

        for (Piece p : test_gameState.white) {
            this.placeFigure(p, true);
        }

        for (Piece p : test_gameState.black) {
            this.placeFigure(p, false);
        }

    }

    public void deleteAllPieces() {
        for(ImagePanel c : this.figures){
            this.layeredPane.remove(c);
        }
    }

    private void placePossibleMoveButton() {

    }
}