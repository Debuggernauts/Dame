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
    private final ArrayList<Figure> figures = new ArrayList<>();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final Point startPosBoard = new Point(55, 45 + 44);
    private final Point startPosBlackBoard = new Point(702, 100 + 44);
    private final PieceStack pieceStackWhite;
    private final PieceStack pieceStackBlack;

    GameState gameState = new GameState();
    private boolean debug = false;


    public GUI() {
        this.gameState.initalize();

        // init stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1040 - 16, 871 + 5);
        this.setTitle("Dame");
        this.setResizable(false);
        this.setLayout(null);
        this.setFocusable(true);
        this.setUndecorated(true);
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

        // Decorator
        CustomDecorator decorator = new CustomDecorator(this);

        // Background Panel
        BackgroundPanel background = new BackgroundPanel(new Point(0, 44), 1);
        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Board Image Panel
        BoardPanel board = new BoardPanel(this.startPosBoard, 4);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setFiguresActive(false);
            }
        });
        this.layeredPane.add(board, JLayeredPane.PALETTE_LAYER);

        // debugPieceHolder
        DebugPieceHolder debugPieceHolder = new DebugPieceHolder(
                new Point(this.startPosBlackBoard.x, this.startPosBlackBoard.y + 210),
                4
        );
        debugPieceHolder.setVisible(false);
        this.layeredPane.add(debugPieceHolder, JLayeredPane.MODAL_LAYER);

        // stackHolder
        StackHolder stackHolder = new StackHolder(
                new Point(this.startPosBlackBoard.x, this.startPosBlackBoard.y + 417),
                4
        );
        this.layeredPane.add(stackHolder, JLayeredPane.MODAL_LAYER);

        this.playerIndicator = new PlayerIndicator();
        this.layeredPane.add(playerIndicator, JLayeredPane.MODAL_LAYER);

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
                "res/manual.png"
        );
        blackBoardManual.addActionListener(e -> openManual());
        this.layeredPane.add(blackBoardManual, JLayeredPane.DRAG_LAYER);

        // blackboard new Game
        CustomButton blackBoardNewGame = new CustomButton(
                new Point(
                        this.startPosBlackBoard.x + 24,
                        this.startPosBlackBoard.y + 73
                ),
                "res/new_game.png"
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
        this.layeredPane.add(blackBoardNewGame, JLayeredPane.DRAG_LAYER);

        // Endscreen
        ImagePanel endScreen = new ImagePanel("res/end_screen.png", this.startPosBoard, 1);
        endScreen.setVisible(false);
        this.layeredPane.add(endScreen, Integer.valueOf(JLayeredPane.DRAG_LAYER + 1));

        // Stacks
        this.pieceStackWhite = new PieceStack(this.layeredPane, Color.WHITE, new Point(720, 570 + 44));
        this.pieceStackBlack = new PieceStack(this.layeredPane, Color.BLACK, new Point(780, 540 + 44));

        ImagePanel playerIndicatorToggleBackground = new ImagePanel(
                "res/debug_overlay.png",
                new Point(249, 653 + 44),
                4
        );
        playerIndicatorToggleBackground.setVisible(false);
        this.layeredPane.add(playerIndicatorToggleBackground, Integer.valueOf(JLayeredPane.DRAG_LAYER - 1));

        CustomButton playerIndicatorToggle = new CustomButton(
                new Point(333, 658 + 44),
                "res/button_switch_turn.png"
        );
        playerIndicatorToggle.setVisible(false);
        playerIndicatorToggle.addActionListener(e -> {
            gameState.whitesTurn = !gameState.whitesTurn;
            this.playerIndicator.setIndicator(gameState.whitesTurn);
            setFiguresActive(false);
        });
        this.layeredPane.add(playerIndicatorToggle, JLayeredPane.DRAG_LAYER);

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

    private void setFiguresActive(boolean active) {
        for (Figure f : figures) {
            f.setActive(active);
        }
    }

    private void openManual() {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(
                        Objects.requireNonNull(getClass().getResource("res/manual.pdf")).toURI()
                );
                Desktop.getDesktop().open(file);
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
        for (int i = 0; i < 12; i++) {
            this.pieceStackBlack.increment();
            this.pieceStackWhite.increment();
        }

        for (Piece p : gst.pieces) {
            switch (p.getColor()) {
                case BLACK:
                    this.pieceStackBlack.decrement();
                    break;
                case WHITE:
                    this.pieceStackWhite.decrement();
                    break;
            }
            Figure figure = new Figure(p, this.startPosBoard, this);
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
        this.playerIndicator.setIndicator(gst.whitesTurn);
        /*
        int gameEnd = this.gameState.checkGameEnd();
        switch (gameEnd) {
            case Color.WHITE:
                endScreen.setVisible(true);
                this.gameState = new GameState();
                break;
            case Color.BLACK:
                endScreen.setVisible(true);
                this.gameState = new GameState();
                break;
            case Color.UNENDSCHIEDEN:
                endScreen.setVisible(false);
                break;
        }
        */
    }

    public void deleteAllPieces() {
        for (Figure c : this.figures) {
            c.removeThis();
        }
        this.figures.clear();
    }
}

