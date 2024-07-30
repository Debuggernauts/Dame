package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImagePanel extends JPanel {
    private final BufferedImage image;

    private final int scale;

    public ImagePanel(String path, int startX, int startY, int scale) {
        this.scale = scale;

        try {
            InputStream imageStream = getClass().getResourceAsStream(path);
            assert imageStream != null;
            this.image = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setOpaque(false);
        this.setBounds(startX, startY, this.image.getWidth() * scale, this.image.getHeight() * scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {

            int imgWidth = this.image.getWidth() * this.scale;
            int imgHeight = this.image.getHeight() * this.scale;

            g.drawImage(this.image, 0, 0, imgWidth, imgHeight, this);
        }
    }
}
