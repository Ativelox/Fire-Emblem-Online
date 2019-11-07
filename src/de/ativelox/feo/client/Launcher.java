package de.ativelox.feo.client;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import de.ativelox.feo.client.controller.MainController;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Launcher {

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        MainController mc = new MainController();
        mc.initialize();
        mc.run();

    }
}
