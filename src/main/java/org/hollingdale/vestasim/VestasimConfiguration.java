package org.hollingdale.vestasim;

import java.awt.Color;

public interface VestasimConfiguration {

    public static final int BOARD_COLUMNS = 22;
    public static final int BOARD_ROWS = 6;

    public static final double BOARD_ASPECT_RATIO = 1.87;
    public static final double BOARD_PADDING_FACTOR = 0.05;
    public static final Color[] BOARD_GRADIENT = new Color[] {
            Color.DARK_GRAY, new Color(20, 20, 20), Color.DARK_GRAY };
    public static final double BOARD_FRAME_FACTOR = 0.01;
    public static final double BOARD_MARGIN_FACTOR = 0.03;
    public static final Color BOARD_BORDER_COLOR = Color.BLACK;

    public static final String BRANDING_FONT = "Arial";
    public static final double BRANDING_FONT_FACTOR = 0.02;
    public static final double BRANDING_Y_FACTOR = 0.96;

    public static final double BIT_LINE_FACTOR = 0.1;
    public static final Color BIT_BACKGROUND_COLOR = new Color(20, 20, 20);
    public static final String BIT_FONT = "Arial";
    public static final double BIT_FONT_FACTOR = 0.8;
    public static final Color BIT_SPLIT_COLOR = Color.BLACK;
    public static final double BIT_SPLIT_POSITION = 0.44;
    public static final Color BIT_SHADOW_COLOR = Color.DARK_GRAY;

    public static final long FLIP_DELAY_MS = 100L;
}
