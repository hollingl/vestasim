package org.hollingdale.vestasim.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.hollingdale.vestasim.VestaCharacter;
import org.hollingdale.vestasim.VestasimConfiguration;

public class VestaBit extends JComponent {
    private static final double LINE_FACTOR = 0.1;
    private static final Color BACKGROUND = new Color(20, 20, 20);

    private VestaCharacter target = VestaCharacter.BLANK;
    private VestaCharacter current = VestaCharacter.X;

    public VestaBit() {
        setPreferredSize(new Dimension(20, 40));
    }

    public VestaBit setTarget(VestaCharacter target) {
        this.target = target;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;

        var width = getWidth();
        var height = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BACKGROUND);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(current.getColor());
        var character = current.getCharacter();
        if (character == (char) 0) {
            // Draw block
        } else {
            // Draw character
            g2d.setFont(new Font("Arial", Font.PLAIN, (int) (getWidth() * 0.8)));
            drawStringC(g2d, String.valueOf(character), (int) (getWidth() * 0.48), (int) (getHeight() * 0.62));
        }

        // Split
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float) (getWidth() * LINE_FACTOR * 0.5)));
        g2d.drawLine(0, (int) (height * 0.44), width, (int) (height * 0.44));

        // Shadow
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke((float) (getWidth() * LINE_FACTOR)));
        g2d.drawLine(0, height, width, height);
        g2d.drawLine(width, 0, width, height);
    }

    private static void drawStringC(Graphics2D g2d, String s, int x, int y) {
        var stringWidth = g2d.getFontMetrics().stringWidth(s);
        g2d.drawString(s, x - (stringWidth / 2), y);
    }

    public void transition() {
        new Thread(() -> {
            while (current != target) {
                current = current.next();
                repaint();
                try {
                    Thread.sleep(VestasimConfiguration.FLIP_DELAY_MS);
                } catch (InterruptedException ix) {
                    break;
                }
            }
        }).start();
    }
}
