package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImagePanel extends JPanel {
    private final BufferedImage image;
    private final ImageIcon imageIcon;
    private final int scale;
    private final boolean isGif;

    public ImagePanel(String path, Point pos, int scale) {
        this.scale = scale;
        BufferedImage img = null;
        ImageIcon icon;
        boolean gifFlag = false;

        try {
            InputStream imageStream = getClass().getResourceAsStream(path);
            assert imageStream != null;

            icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
            if (icon.getIconWidth() > 0) {
                gifFlag = true;
            } else {
                img = ImageIO.read(imageStream);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.image = img;
        this.imageIcon = icon;
        this.isGif = gifFlag;

        int width, height;

        if (this.isGif) {
            width = this.imageIcon.getIconWidth() * scale;
            height = this.imageIcon.getIconHeight() * scale;
        } else {
            width = this.image.getWidth() * scale;
            height = this.image.getHeight() * scale;
        }

        this.setOpaque(false);
        this.setBounds(pos.x, pos.y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imgWidth, imgHeight;

        if (this.isGif && this.imageIcon != null) {
            imgWidth = this.imageIcon.getIconWidth() * this.scale;
            imgHeight = this.imageIcon.getIconHeight() * this.scale;
            g.drawImage(this.imageIcon.getImage(), 0, 0, imgWidth, imgHeight, this);
        } else if (this.image != null) {
            imgWidth = this.image.getWidth() * this.scale;
            imgHeight = this.image.getHeight() * this.scale;
            g.drawImage(this.image, 0, 0, imgWidth, imgHeight, this);
        }
    }
}
