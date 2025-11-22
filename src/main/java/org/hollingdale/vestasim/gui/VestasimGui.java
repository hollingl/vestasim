package org.hollingdale.vestasim.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

@Component
public class VestasimGui extends JFrame {

    public VestasimGui(Vestaboard board) {
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);

        setTitle("Vestasim");
        setIconImage(new ImageIcon(this.getClass().getResource("/favicon.ico")).getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 480));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
