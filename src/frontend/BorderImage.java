package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BorderImage extends JPanel {
    private BufferedImage image;
    private int startX;
    private int startY;

    public BorderImage(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        try {
            InputStream imageStream = getClass().getResourceAsStream("images/board_border.png");
            assert imageStream != null;
            this.image = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {

            int imgWidth = this.image.getWidth();
            int imgHeight = this.image.getHeight();

            g.drawImage(this.image, this.startX, this.startY, imgWidth, imgHeight, this);
        }
    }
}
