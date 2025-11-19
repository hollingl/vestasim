package org.hollingdale.vestasim.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.hollingdale.vestasim.VestaCharacter;
import org.hollingdale.vestasim.VestasimConfiguration;

public class VestaBit extends JComponent {

    private VestaCharacter target = VestaCharacter.BLANK;
    private VestaCharacter current = VestaCharacter.random();

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
        g2d.setColor(VestasimConfiguration.BIT_BACKGROUND_COLOR);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(current.getColor());
        var character = current.getCharacter();
        if (character == (char) 0) {
            // Draw block
            g2d.fillRect((int) (width * 0.2), (int) (height * 0.25), (int) (width * 0.6), (int) (height * 0.4));
        } else {
            // Draw character
            g2d.setFont(new Font(VestasimConfiguration.BIT_FONT, Font.PLAIN,
                    (int) (getWidth() * VestasimConfiguration.BIT_FONT_FACTOR)));
            Utils.drawStringC(g2d, String.valueOf(character), (int) (getWidth() * 0.48), (int) (getHeight() * 0.62));
        }

        // Split
        g2d.setColor(VestasimConfiguration.BIT_SPLIT_COLOR);
        g2d.setStroke(new BasicStroke((float) (getWidth() * VestasimConfiguration.BIT_LINE_FACTOR * 0.5)));
        g2d.drawLine(
                0, (int) (height * VestasimConfiguration.BIT_SPLIT_POSITION),
                width, (int) (height * VestasimConfiguration.BIT_SPLIT_POSITION));

        // Shadow
        g2d.setColor(VestasimConfiguration.BIT_SHADOW_COLOR);
        g2d.setStroke(new BasicStroke((float) (getWidth() * VestasimConfiguration.BIT_LINE_FACTOR)));
        g2d.drawLine(0, height, width, height);
        g2d.drawLine(width, 0, width, height);
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
