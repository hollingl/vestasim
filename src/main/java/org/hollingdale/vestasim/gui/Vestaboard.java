package org.hollingdale.vestasim.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.hollingdale.vestasim.VestaCharacter;
import org.hollingdale.vestasim.VestasimConfiguration;
import org.springframework.stereotype.Component;

@Component
public class Vestaboard extends JPanel {

    private List<List<VestaBit>> board;

    public Vestaboard() {
        // Do our own layout management
        setLayout(null);

        // Add Bits to the board
        board = new ArrayList<>();
        for (var y = 0; y < VestasimConfiguration.BOARD_ROWS; y++) {
            List<VestaBit> line = new ArrayList<>();
            board.add(line);

            for (var x = 0; x < VestasimConfiguration.BOARD_COLUMNS; x++) {
                var bit = new VestaBit();
                line.add(bit);
                add(bit);
            }
        }

        // Resize and position Bits upon component resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutBits();
            }
        });
    }

    private void layoutBits() {
        var bounds = getRenderedBounds();
        var topLeft = bounds.get(0);
        var bottomRight = bounds.get(1);

        var padding = (bottomRight.x - topLeft.x) * VestasimConfiguration.BOARD_PADDING_FACTOR;
        var dx = (bottomRight.x - topLeft.x - (2 * padding)) / VestasimConfiguration.BOARD_COLUMNS;
        var dy = 1.02 * (bottomRight.y - topLeft.y - (2 * padding)) / VestasimConfiguration.BOARD_ROWS;

        for (var y = 0; y < VestasimConfiguration.BOARD_ROWS; y++) {
            List<VestaBit> line = board.get(y);
            for (var x = 0; x < VestasimConfiguration.BOARD_COLUMNS; x++) {
                var bit = line.get(x);
                bit.setBounds(
                        (int) (topLeft.x + padding + (x * dx)),
                        (int) (topLeft.y + padding + (y * dy)),
                        (int) (dx * 0.8),
                        (int) (dy * 0.8));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;

        var bounds = getRenderedRectangle();

        // Background
        var gradient = new LinearGradientPaint(
                new Point(bounds.x, bounds.y),
                new Point(bounds.x + bounds.width, bounds.y + bounds.height),
                new float[] { 0f, 0.5f, 1f },
                VestasimConfiguration.BOARD_GRADIENT);

        g2d.setPaint(gradient);
        g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Frame
        var frameWidth = Math.max(getWidth(), getHeight()) * VestasimConfiguration.BOARD_FRAME_FACTOR;
        g2d.setColor(VestasimConfiguration.BOARD_BORDER_COLOR);
        g2d.setStroke(new BasicStroke((float) frameWidth));
        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Branding
        g2d.setFont(new Font(VestasimConfiguration.BRANDING_FONT, Font.BOLD,
                (int) (getWidth() * VestasimConfiguration.BRANDING_FONT_FACTOR)));
        Utils.drawStringC(g2d, "V E S T A B O A R D", bounds.x + bounds.width / 2,
                (int) (bounds.y + bounds.height * VestasimConfiguration.BRANDING_Y_FACTOR));
    }

    private Rectangle getRenderedRectangle() {
        var bounds = getRenderedBounds();
        var topLeft = bounds.get(0);
        var bottomRight = bounds.get(1);

        return new Rectangle(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    }

    private List<Point> getRenderedBounds() {
        var margin = (int) (Math.max(getWidth(), getHeight()) * VestasimConfiguration.BOARD_MARGIN_FACTOR);
        var width = getWidth() - (2 * margin);
        var height = getHeight() - (2 * margin);
        var aspectRatio = width / height;

        if (aspectRatio > VestasimConfiguration.BOARD_ASPECT_RATIO) {
            var desiredWidth = (int) (height * VestasimConfiguration.BOARD_ASPECT_RATIO);
            var hpadding = (width - desiredWidth) / 2;
            return List.of(
                    new Point(hpadding + margin, margin),
                    new Point(margin + width - hpadding, margin + height));
        } else {
            var desiredHeight = (int) (width / VestasimConfiguration.BOARD_ASPECT_RATIO);
            var vpadding = (height - desiredHeight) / 2;
            return List.of(
                    new Point(margin, vpadding + margin),
                    new Point(margin + width, margin + height - vpadding));
        }
    }

    public void setMessage(List<List<Integer>> message) {
        for (var y = 0; y < VestasimConfiguration.BOARD_ROWS; y++) {
            var line = board.get(y);
            var data = message.get(y);

            for (var x = 0; x < VestasimConfiguration.BOARD_COLUMNS; x++) {
                line.get(x)
                        .setTarget(VestaCharacter.fromCode(data.get(x)).orElse(VestaCharacter.BLANK))
                        .transition();
            }
        }
    }
}
