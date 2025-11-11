package org.hollingdale.vestasim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hollingdale.vestasim.gui.VestasimGui;

public class Main {

    public static void main(String[] args) throws IOException {
        var gui = new VestasimGui();

        var line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        gui.setMessage(line);
    }
}
