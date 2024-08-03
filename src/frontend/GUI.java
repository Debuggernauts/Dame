package frontend;

import backend.*;
import backend.utilities.Color;

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
    private final PlayerIndicator playerIndicator;
    private final ArrayList<Figure> figures = new ArrayList<Figure>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final Point startPosBoard = new Point(55, 45);
    private final Point startPosBlackBoard = new Point(702, 100);
    private final ArrayList<ImagePanel> markers = new ArrayList<>();
    private final Piecestack pieceStackWhite;
    private final Piecestack pieceStackBlack;

    GameState gameState = new GameState();
    private boolean debug = false;


    public GUI() {
        this.gameState.initalize();

        // init stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040, 871);
        this.setTitle("Dame");
        this.setResizable(false);
        this.setLayout(null);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
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
                new Point(0, 0),
                1
        );
        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Border Image Panel
        ImagePanel boardBorder = new ImagePanel(
                "res/board_border.png",
                this.startPosBoard,
                4
        );
        this.layeredPane.add(boardBorder, JLayeredPane.PALETTE_LAYER);

        // bg grid
        ImagePanel boardBackground = new ImagePanel(
                "res/board_bg.png",
                new Point(this.startPosBoard.x + 40, this.startPosBoard.y + 44),
                4
        );
        boardBackground.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                for (Figure f : figures) {
                    f.setActive(false);
                }
            }
        });
        this.layeredPane.add(boardBackground, JLayeredPane.MODAL_LAYER);

        // debugPieceHolder
        DebugPieceHolder debugPieceHolder = new DebugPieceHolder(
                new Point(this.startPosBlackBoard.x, this.startPosBlackBoard.y + 210),
                4
        );
        debugPieceHolder.setVisible(false);
        this.layeredPane.add(debugPieceHolder, JLayeredPane.MODAL_LAYER);

        // stackHolder
        ImagePanel stackHolder = new ImagePanel(
                "res/stack_holder.png",
                new Point(this.startPosBlackBoard.x, this.startPosBlackBoard.y + 417),
                4
        );
        this.layeredPane.add(stackHolder, JLayeredPane.MODAL_LAYER);

        this.playerIndicator = new PlayerIndicator(this.layeredPane);

        // blackboard background
        ImagePanel blackBoardBackground = new ImagePanel(
                "res/black_board.png",
                this.startPosBlackBoard,
                4
        );
        this.layeredPane.add(blackBoardBackground, JLayeredPane.MODAL_LAYER);

        // blackboard manual
        CustomButton blackBoardManual = new CustomButton(
                new Point(
                        this.startPosBlackBoard.x + 24,
                        this.startPosBlackBoard.y + 25
                ),
                "res/manual.png",
                this.layeredPane
        );
        blackBoardManual.addActionListener(e -> {
            openManual();
        });

        CustomButton blackBoardNewGame = new CustomButton(
                new Point(
                        this.startPosBlackBoard.x + 24,
                        this.startPosBlackBoard.y + 73
                ),
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
        this.pieceStackWhite = new Piecestack(this.layeredPane, Color.WHITE, 720, 570);
        this.pieceStackBlack = new Piecestack(this.layeredPane, Color.BLACK, 780, 540);

        ImagePanel playerIndicatorToggleBackground = new ImagePanel("res/debug_overlay.png", new Point(249, 653), 4);
        playerIndicatorToggleBackground.setVisible(false);
        this.layeredPane.add(playerIndicatorToggleBackground, Integer.valueOf(JLayeredPane.DRAG_LAYER - 1));

        CustomButton playerIndicatorToggle = new CustomButton(new Point(333, 658), "res/button_switch_turn.png", this.layeredPane);
        playerIndicatorToggle.setVisible(false);
        playerIndicatorToggle.addActionListener(e -> {
            gameState.whitesTurn = !gameState.whitesTurn;
            this.playerIndicator.setIndicator(gameState);
        });

        // blackboard debug blob
        ToggleDebug toggleDebug = new ToggleDebug(
                new Point(
                        this.startPosBlackBoard.x + 24,
                        this.startPosBlackBoard.y + 121
                ),
                this.layeredPane
        );
        toggleDebug.addActionListener(e -> {
            boolean debugState = toggleDebug.isDebugEnabled();
            this.debug = debugState;
            System.out.println("DebugMode: " + (debugState ? "\33[32m" : "\33[31m") + debugState + "\33[0m");

            debugPieceHolder.setVisible(debugState);
            playerIndicatorToggle.setVisible(debugState);
            playerIndicatorToggleBackground.setVisible(debugState);
        });

        this.renderGameState(gameState);

        this.setVisible(true);
    }

    public boolean isDebug() {
        return this.debug;
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
        System.out.println("render GameState");
        this.deleteAllPieces();
        for (int i = 0; i < 12; i++) { // TODO: refactor
            this.pieceStackBlack.increment();
            this.pieceStackWhite.increment();
        }

        for (Piece p : gst.pieces) {
            if (p.getColor() == Color.BLACK) {
                this.pieceStackBlack.decrement();
            } else {
                this.pieceStackWhite.decrement();
            }
            Figure figure = new Figure(p, this.startPosBoard, this.layeredPane, this);
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
        this.playerIndicator.setIndicator(gst);
    }

    public void deleteAllPieces() {
        for (Figure c : this.figures) {
            c.removeThis();
        }
        this.figures.clear();
    }
}

