package org.hollingdale.vestasim.gui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.hollingdale.vestasim.VestaCharacter;

public class VestasimGui extends JFrame {
    private static final int NUM_BITS = 22;
    private final List<VestaBit> bits = new ArrayList<>();
    
    public VestasimGui() {
        setLayout(new FlowLayout());
        for (var i = 0; i < NUM_BITS; i++) {
            var bit = new VestaBit();
            bits.add(bit);
            add(bit);
        }

        setTitle("Vestasim");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);    
    }

    public void setMessage(String message) {
        for (var i = 0; i<NUM_BITS; i++) {
            var c = message.length() >= i ? message.charAt(i) : ' ';
            var vc = VestaCharacter.fromCharacter(c);
            bits.get(i)
                .setTarget(vc.get())
                .transition();
        }
    }
}
