package GUI;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.xml.parsers.ParserConfigurationException;

import org.jfugue.integration.MusicXmlParser;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.player.Player;

import converter.Instrument;
import nu.xom.ParsingException;
import utility.Settings;

public class MusicPlayer extends ApplicationController {
	public Player manager;
	private Sequencer sequencer;
	private Sequence sequence;
	private String musicXMLString;

	public MusicPlayer(String XMLString) throws ParserConfigurationException, MidiUnavailableException {
		manager = new Player();
		sequencer = MidiSystem.getSequencer();
		musicXMLString = XMLString;
	}

	public void run() {
		try {
			// MusicXmlParser Parses XMLString
			MusicXmlParser parser = new MusicXmlParser();		
			MidiParserListener listener = new MidiParserListener();
			parser.addParserListener(listener);
			parser.parse(musicXMLString);

			// Retrieve default Sequencer
			if (sequencer == null) {
				System.err.println("Sequencer device not supported");
				return;
			} 
			else {
				// Open device
				sequencer.open();
			}

			// Create Sequence
			sequence = listener.getSequence();
			Track track = sequence.createTrack();
			if (Settings.getInstance().getInstrument().equals(Instrument.GUITAR)) {
				ShortMessage instrument = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, 24, 0);
				track.add(new MidiEvent(instrument, 0));
			}
			else if (Settings.getInstance().getInstrument().equals(Instrument.BASS)) {
				ShortMessage instrument = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, 32, 0);
				track.add(new MidiEvent(instrument, 0));
			}
			else if (Settings.getInstance().getInstrument().equals(Instrument.DRUMS)) { // WIP
				ShortMessage instrument = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 118, 0);
				track.add(new MidiEvent(instrument, 0));
			}
			else {
				System.err.println("Instrument not recognized");
			} 
			// Load Sequence into Sequencer
			sequencer.setSequence(sequence);
			// Start Playback

		} catch (MidiUnavailableException | InvalidMidiDataException | IOException | ParserConfigurationException | ParsingException ex) {
			ex.printStackTrace();
		}
	}

	// Play Player
	public void play() throws InvalidMidiDataException, MidiUnavailableException {
		sequencer.open();
		manager.getManagedPlayer().start(sequence);
	}

	// Pause Player
	public void pause() throws MidiUnavailableException, InvalidMidiDataException {
		if (!manager.getManagedPlayer().isFinished()) {
			manager.getManagedPlayer().pause();
			sequencer.close();
		}
		else if (manager.getManagedPlayer().isFinished()) {
			manager.getManagedPlayer().start(sequence);
		}
	}

	// Rewind Player
	public void rewind() {
		manager.getManagedPlayer().seek(0);
	}

	// Stop Player
	public void stop() throws MidiUnavailableException {
		manager.getManagedPlayer().reset();
		manager.getManagedPlayer().finish();
	}

	// Resume Player
	public void resume() throws InvalidMidiDataException {
		try {
			if (manager.getManagedPlayer().isPaused()) {
				sequencer.open();
				manager.getManagedPlayer().resume();
			}
			else {
				play();
			}
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

}
