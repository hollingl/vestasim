package org.hollingdale.vestasim.server;

import java.util.List;

import org.hollingdale.vestasim.VestasimConfiguration;
import org.hollingdale.vestasim.gui.Vestaboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VestaboardController {

    @Autowired
    private Vestaboard board;

    @PostMapping("/local-api/message")
    public ResponseEntity<Void> setMessage(
            @RequestHeader(value = "X-Vestaboard-Local-Api-Key", required = false) String key,
            @RequestBody List<List<Integer>> payload) {

        if (key == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (!isValid(payload)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            board.setMessage(payload);
            return ResponseEntity.ok().build();
        }
    }

    private static boolean isValid(List<List<Integer>> payload) {
        return payload != null &&
                payload.size() == VestasimConfiguration.BOARD_ROWS &&
                payload.stream().allMatch(line -> line.size() == VestasimConfiguration.BOARD_COLUMNS);
    }
}
