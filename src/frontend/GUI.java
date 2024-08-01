package frontend;

import backend.*;
import backend.utilities.Color;
import backend.utilities.Tuple;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;


public class GUI extends JFrame {
    private final ImagePanel indicatorWhite;
    private final ImagePanel indicatorBlack;
    private final ArrayList<Figure> figures = new ArrayList<Figure>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final Point startPosBoard = new Point(55, 45);
    private final Point startPosBlackBoard = new Point(702, 100);
    private final ArrayList<ImagePanel> markers = new ArrayList<>();
    private final Piecestack pieceStackWhite;
    private final Piecestack pieceStackBlack;

    GameState test_gameState = new GameState(); // TODO: temp


    public GUI() {
        // init stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040, 871);
        this.setTitle("Dame");
        this.setResizable(false);
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    openManual();
                }
            }
        });

        // icon
        this.setIcon();

        // layer
        this.setContentPane(this.layeredPane);

        // Background Panel
        ImagePanel background = new ImagePanel(
                "res/background.png",
                0,
                0,
                1
        );
        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Border Image Panel
        ImagePanel boardBorder = new ImagePanel(
                "res/board_border.png",
                this.startPosBoard.x,
                this.startPosBoard.y,
                4
        );
        this.layeredPane.add(boardBorder, JLayeredPane.PALETTE_LAYER);

        // bg grid
        ImagePanel boardBackground = new ImagePanel(
                "res/board_bg.png",
                this.startPosBoard.x + 40,
                this.startPosBoard.y + 44,
                1
        );
        this.layeredPane.add(boardBackground, JLayeredPane.MODAL_LAYER);

        // Player Indicator
        this.indicatorWhite = new ImagePanel(
                "res/current_player_indicator.png",
                163,
                649,
                4
        );
        this.indicatorBlack = new ImagePanel(
                "res/current_player_indicator.png",
                483,
                649,
                4
        );
        this.indicatorBlack.setVisible(false);
        this.layeredPane.add(this.indicatorWhite, JLayeredPane.MODAL_LAYER);
        this.layeredPane.add(this.indicatorBlack, JLayeredPane.MODAL_LAYER);

        // blackboard background
        ImagePanel blackBoardBackground = new ImagePanel(
                "res/black_board.png",
                this.startPosBlackBoard.x,
                this.startPosBlackBoard.y,
                4
        );
        this.layeredPane.add(blackBoardBackground, JLayeredPane.MODAL_LAYER);

        // blackboard manual
        CustomButton blackBoardManual = new CustomButton(
                this.startPosBlackBoard.x + 24,
                this.startPosBlackBoard.y + 25,
                "res/manual.png",
                this.layeredPane
        );
        blackBoardManual.addActionListener(e -> {
            openManual();
        });

        CustomButton blackBoardNewGame = new CustomButton(
                this.startPosBlackBoard.x + 24,
                this.startPosBlackBoard.y + 73,
                "res/new_game.png",
                this.layeredPane
        );
        blackBoardNewGame.addActionListener(e -> {
            NewGameDialog dialog = new NewGameDialog(GUI.this);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(this);
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            String data = dialog.getInputData();
            if (!data.isEmpty()) {
                System.out.println(data);
            } else {
                System.out.println("No data found");
            }
        });

        // Stacks
        this.pieceStackWhite = new Piecestack(this.layeredPane, Color.WHITE, 700, 640);
        this.pieceStackBlack = new Piecestack(this.layeredPane, Color.BLACK, 750, 610);

        // begin debug
            JButton button = new JButton("(temp): black +");
            button.setBounds(700, 288 + 70, 130, 30);
            button.addActionListener(e -> {
                pieceStackBlack.increment();
            });
            button.setVisible(false);
            this.layeredPane.add(button, JLayeredPane.MODAL_LAYER);

            JButton button2 = new JButton("(temp): black -");
            button2.setBounds(700, 328, 130, 30);
            button2.addActionListener(e -> {
                pieceStackBlack.decrement();
            });
            button2.setVisible(false);
            this.layeredPane.add(button2, JLayeredPane.MODAL_LAYER);

            JButton button3 = new JButton("(temp): white +");
            button3.setBounds(830, 358, 130, 30);
            button3.addActionListener(e -> {
                pieceStackWhite.increment();
            });
            button3.setVisible(false);
            this.layeredPane.add(button3, JLayeredPane.MODAL_LAYER);

            JButton button4 = new JButton("(temp): white -");
            button4.setBounds(830, 328, 130, 30);
            button4.addActionListener(e -> {
                pieceStackWhite.decrement();
            });
            button4.setVisible(false);
            this.layeredPane.add(button4, JLayeredPane.MODAL_LAYER);

            test_gameState.blacksTurn = true;
            this.setPlayerIndicator(test_gameState);
            JButton button5 = new JButton("(temp): Toggle current player");
            button5.setBounds(700, 298, 260, 30);
            button5.addActionListener(e -> {
                test_gameState.blacksTurn = !test_gameState.blacksTurn;
                setPlayerIndicator(test_gameState);
            });
            button5.setVisible(false);
            this.layeredPane.add(button5, JLayeredPane.MODAL_LAYER);
        // end debug


        // blackboard debug blob
        ToggleDebug toggleDebug = new ToggleDebug(
                this.startPosBlackBoard.x + 24,
                this.startPosBlackBoard.y + 121,
                this.layeredPane
        );
        toggleDebug.addActionListener(e -> {
            // System.out.println("DebugMode: " + toggleDebug.isSelected());
            button.setVisible(toggleDebug.isDebugEnabled());
            button2.setVisible(toggleDebug.isDebugEnabled());
            button3.setVisible(toggleDebug.isDebugEnabled());
            button4.setVisible(toggleDebug.isDebugEnabled());
            button5.setVisible(toggleDebug.isDebugEnabled());

        });

        this.renderGameState(test_gameState);

        this.setVisible(true);
    }

    public void setPlayerIndicator(GameState gst) {
        if (gst.blacksTurn) {
            this.indicatorWhite.setVisible(true);
            this.indicatorBlack.setVisible(false);
        } else {
            this.indicatorWhite.setVisible(false);
            this.indicatorBlack.setVisible(true);
        }
    }

    private void openManual() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(
                        Objects.requireNonNull(getClass().getResource("res/manual.pdf")).toURI()
                );
                Desktop.getDesktop().open(myFile);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setIcon() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("res/icon.png");
            assert imageStream != null;
            BufferedImage icon = ImageIO.read(imageStream);
            this.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void renderGameState(GameState gst) {
        this.deleteAllPieces();
        gst.initalize();

        for (Piece p : gst.pieces) {
            Figure figure = new Figure(p, this.startPosBoard, this.layeredPane);
            figure.addActionListener(e -> {
                for (Figure f : this.figures) {
                    if (figure.equals(f)) {
                        continue;
                    }
                    f.setActive(false);
                }
            });
            this.figures.add(figure);
        }
        this.setPlayerIndicator(gst);
    }

    public void deleteAllPieces() {
        for (Figure c : this.figures) {
            c.removeThis();
        }
        this.figures.clear();
    }
}

