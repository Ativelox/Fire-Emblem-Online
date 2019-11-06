package de.ativelox.feo.client;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import de.ativelox.feo.client.controller.MainController;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class Launcher {

    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {

//        Sequencer sequencer = MidiSystem.getSequencer(false);
//
//        Sequence sequence = MidiSystem.getSequence(Paths.get("res", "fe6", "music", "midi", "song001.mid").toFile());
//
//        sequencer.open();
//        sequencer.setSequence(sequence);
//
//        Synthesizer s = MidiSystem.getSynthesizer();
//        s.open();
//
//        s.loadAllInstruments(
//                MidiSystem.getSoundbank(Paths.get("res", "fe6", "music", "soundbank", "soundbank_073.sf2").toFile()));
//
//        sequencer.getTransmitter().setReceiver(s.getReceiver());
//
//        sequencer.start();

        MainController mc = new MainController();
        mc.initialize();
        mc.run();

    }

}
