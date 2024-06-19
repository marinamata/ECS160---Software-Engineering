package viewAndController;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiUnavailableException;

public class MidiPlayer {
        private Synthesizer synthesizer;
        private MidiChannel channel;

        public MidiPlayer() {
            try {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                // Get a channel. Channels are usually 0-indexed and the 0th channel is often a good default.
                channel = synthesizer.getChannels()[0];
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void playNote(int noteNumber, int velocity, int duration) {
            channel.noteOn(noteNumber, velocity); // Start playing the note
            try {
                Thread.sleep(duration); // Wait for the note to play for the desired duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace(); // Handle this error appropriately in your application
            } finally {
                channel.noteOff(noteNumber); // Stop playing the note
            }
        }
    }


