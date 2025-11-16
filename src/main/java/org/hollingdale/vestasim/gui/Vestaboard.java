package org.hollingdale.vestasim.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
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

    private static final double MARGIN_FACTOR = 0.03;
    private static final double PADDING_FACTOR = 0.05;
    private static final double FRAME_FACTOR = 0.01;

    private List<List<VestaBit>> board;

    public Vestaboard() {
        setLayout(null);

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

        var padding = (bottomRight.x - topLeft.x) * PADDING_FACTOR;
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

        var bounds = getRenderedBounds();
        var topLeft = bounds.get(0);
        var bottomRight = bounds.get(1);

        // Background
        var gradient = new LinearGradientPaint(topLeft, bottomRight,
                new float[] { 0f, 0.5f, 1f },
                new Color[] { Color.DARK_GRAY, Color.BLACK, Color.DARK_GRAY });

        g2d.setPaint(gradient);
        g2d.fillRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);

        // Frame
        var frame = Math.max(getWidth(), getHeight()) * FRAME_FACTOR;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float) frame));
        g2d.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    }

    private List<Point> getRenderedBounds() {
        var margin = (int) (Math.max(getWidth(), getHeight()) * MARGIN_FACTOR);
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
