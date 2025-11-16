package org.hollingdale.vestasim.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

@Component
public class VestasimGui extends JFrame {

    public VestasimGui(Vestaboard board) {
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);

        setTitle("Vestasim");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);    
    }
}
