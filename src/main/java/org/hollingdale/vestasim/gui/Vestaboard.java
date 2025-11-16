package org.hollingdale.vestasim.gui;

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
        board = new ArrayList<>();
        for (var y=0; y<VestasimConfiguration.BOARD_ROWS; y++) {
            List<VestaBit> line = new ArrayList<>();
            board.add(line);

            for (var x=0; x<VestasimConfiguration.BOARD_COLUMNS; x++) {
                var bit = new VestaBit();
                line.add(bit);
                add(bit);
            }
        }
    }

    public void setMessage(List<List<Integer>> message) {
        for (var y=0; y<VestasimConfiguration.BOARD_ROWS; y++) {
            var line = board.get(y);
            var data = message.get(y);

            for(var x=0; x<VestasimConfiguration.BOARD_COLUMNS; x++) {
                line.get(x)
                    .setTarget(VestaCharacter.fromCode(data.get(x)).orElse(VestaCharacter.BLANK))
                    .transition();
            }
        }
    }
}
