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
    private final ArrayList<Tuple<ImagePanel, Piece>> figures = new ArrayList<>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final Point startPosBoard = new Point(55, 45);
    private final Point startPosBlackBoard = new Point(702, 100);
    private final ArrayList<ImagePanel> markers = new ArrayList<>();
    private final Piecestack piecestackWhite;
    private final Piecestack piecestackBlack;

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
        ImagePanel blackBoardManual = new ImagePanel(
                "res/manual.png",
                this.startPosBlackBoard.x + 24,
                this.startPosBlackBoard.y + 25,
                4
        );
        blackBoardManual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openManual();
            }
        });
        this.layeredPane.add(blackBoardManual, JLayeredPane.DRAG_LAYER);

        // blackboard manual
        ImagePanel blackBoardNewGame = new ImagePanel(
                "res/new_game.png",
                this.startPosBlackBoard.x + 24,
                this.startPosBlackBoard.y + 73,
                4
        );
        blackBoardNewGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewGameDialog dialog = new NewGameDialog(GUI.this);
                dialog.setVisible(true);

                String data = dialog.getInputData();
                if (!data.isEmpty()) {
                    System.out.println(data);
                } else {
                    System.out.println("No data found");
                }
            }
        });

        this.layeredPane.add(blackBoardNewGame, JLayeredPane.DRAG_LAYER);

        // Stacks
        this.piecestackWhite = new Piecestack(this.layeredPane, Color.WHITE, 700, 640);
        this.piecestackBlack = new Piecestack(this.layeredPane, Color.BLACK, 750, 610);

        // begin temp
        JButton button = new JButton("(temp): black +");
        button.setBounds(700, 100 + 188 + 70, 130, 30);
        button.addActionListener(e -> {
            piecestackBlack.increment();
        });
        button.setVisible(false);
        this.layeredPane.add(button, JLayeredPane.MODAL_LAYER);

        JButton button2 = new JButton("(temp): black -");
        button2.setBounds(700, 100 + 188 + 40, 130, 30);
        button2.addActionListener(e -> {
            piecestackBlack.decrement();
        });
        button2.setVisible(false);
        this.layeredPane.add(button2, JLayeredPane.MODAL_LAYER);
        JButton buttona = new JButton("(temp): white +");
        buttona.setBounds(830, 100 + 188 + 70, 130, 30);
        buttona.addActionListener(e -> {
            piecestackWhite.increment();
        });
        buttona.setVisible(false);
        this.layeredPane.add(buttona, JLayeredPane.MODAL_LAYER);

        JButton button2a = new JButton("(temp): white -");
        button2a.setBounds(830, 100 + 188 + 40, 130, 30);
        button2a.addActionListener(e -> {
            piecestackWhite.decrement();
        });
        button2a.setVisible(false);
        this.layeredPane.add(button2a, JLayeredPane.MODAL_LAYER);


        test_gameState.blacksTurn = true;
        this.setPlayerIndicator(test_gameState);
        JButton button3 = new JButton("(temp): Toggle current player");
        button3.setBounds(700, 100 + 188 + 10, 260, 30);
        button3.addActionListener(e -> {
            test_gameState.blacksTurn = !test_gameState.blacksTurn;
            setPlayerIndicator(test_gameState);
        });
        button3.setVisible(false);
        this.layeredPane.add(button3, JLayeredPane.MODAL_LAYER);
        // end temp


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
            buttona.setVisible(toggleDebug.isDebugEnabled());
            button2a.setVisible(toggleDebug.isDebugEnabled());
            button3.setVisible(toggleDebug.isDebugEnabled());

        });

        this.renderGamestate(test_gameState);

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
            InputStream imageStream = getClass().getResourceAsStream("res/black_king.png");
            assert imageStream != null;
            BufferedImage icon = ImageIO.read(imageStream);
            this.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void placeFigure(Piece piece) {
        if (piece instanceof King) {
            ImagePanel figure = new ImagePanel(
                    piece.getColor() == Color.BLACK ? "res/black_king.png" : "res/white_king.png",
                    this.startPosBoard.x + 40 + 64 * piece.getX(),
                    this.startPosBoard.y + 44 + 64 * piece.getY(),
                    4
            );
            this.figures.add(new Tuple<>(figure, piece));
            this.layeredPane.add(figure, JLayeredPane.DRAG_LAYER);

        } else if (piece instanceof Man) {
            ImagePanel figure = new ImagePanel(
                    piece.getColor() == Color.BLACK ? "res/black_man.png" : "res/white_man.png",
                    this.startPosBoard.x + 40 + 64 * piece.getX(),
                    this.startPosBoard.y + 44 + 64 * piece.getY(),
                    4);
            this.figures.add(new Tuple<>(figure, piece));
            this.layeredPane.add(figure, JLayeredPane.DRAG_LAYER);
        }
    }

    public void renderGamestate(GameState gst) {
        this.deleteAllPieces();
        gst.initalize();

        for (Piece p : gst.pieces) {
            this.placeFigure(p);
        }
        this.setPlayerIndicator(gst);
    }

    public void deleteAllPieces() {
        for (Tuple<ImagePanel, Piece> c : this.figures) {
            this.layeredPane.remove(c.x);
        }
        this.figures.clear();
    }

    public void deleteAllMarkers() {
        for (ImagePanel c : this.markers) {
            this.layeredPane.remove(c);
        }
        this.markers.clear();
    }

    private void placePossibleMoveButton(Move move) {
        Tuple<Integer, Integer> endPos = move.getEnd();
        ImagePanel marker = new ImagePanel(
                "res/white_king.png",
                this.startPosBoard.x + 40 + 64 * endPos.x,
                this.startPosBoard.y + 44 + 64 * endPos.x,
                4
        );
        this.markers.add(marker);
        this.layeredPane.add(marker, JLayeredPane.DRAG_LAYER);
    }
}