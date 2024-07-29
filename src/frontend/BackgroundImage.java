package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

class BackgroundImage extends JPanel {
    private BufferedImage image;

    public BackgroundImage() {
        InputStream imageStream = getClass().getResourceAsStream("images/debug_background.png");
        assert imageStream != null;
        try {
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
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            for (int x = 0; x < panelWidth; x += imgWidth) {
                for (int y = 0; y < panelHeight; y += imgHeight) {
                    g.drawImage(this.image, x, y, this);
                }
            }
        }
    }
}