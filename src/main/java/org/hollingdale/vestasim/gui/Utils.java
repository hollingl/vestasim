package org.hollingdale.vestasim.gui;

import java.awt.Graphics2D;

public class Utils {

  public static void drawStringC(Graphics2D g2d, String s, int x, int y) {
    var stringWidth = g2d.getFontMetrics().stringWidth(s);
    g2d.drawString(s, x - (stringWidth / 2), y);
  }
}
