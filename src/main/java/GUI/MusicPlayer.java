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

public class MusicPlayer {
	//	MusicXmlParser parser;	
	//	MidiParserListener midilistener;
	//	Sequencer sequencer;
	String musicXMLString;
	Sequencer sequencer;
	boolean isPlaying = false;
	Player manager = new Player();

	public MusicPlayer(String XMLString) throws ParserConfigurationException, MidiUnavailableException {
		//		parser = new MusicXmlParser();
		//		midilistener = new MidiParserListener();
		//		sequencer = MidiSystem.getSequencer();
		this.musicXMLString = XMLString;
		sequencer = MidiSystem.getSequencer();

	}

	public void run() {
		try {
			// MusicXmlParser parses a MusicXML String
			MusicXmlParser parser = new MusicXmlParser();		
			MidiParserListener listener = new MidiParserListener();
			parser.addParserListener(listener);
			parser.parse(musicXMLString);

			// Get the default Sequencer
			if (sequencer == null) {
				System.err.println("Sequencer device not supported");
				return;
			} 
			else {
				// Open device
				sequencer.open();
			}

			// Create Sequence
			Sequence sequence = listener.getSequence();
			Track track = sequence.createTrack();
			if (Settings.getInstance().getInstrument().equals(Instrument.GUITAR)) {
				/*
					MidiEvent instrumentChange = new MidiEvent(ShortMessage.PROGRAM_CHANGE,drumPatch.getBank(),drumPatch.getProgram());
					Synthesizer synthesizer = MidiSystem.getSynthesizer();
					synthesizer.open();
					javax.sound.midi.Instrument[] instruments = synthesizer.getDefaultSoundbank().getInstruments();
					for (javax.sound.midi.Instrument i : instruments) {
						System.out.println(i);
					}
					javax.sound.midi.Instrument[] instruments = synthesizer.getDefaultSoundbank().getInstruments();
					MidiChannel guitarChannel = synthesizer.getChannels()[0];
					guitarChannel.programChange(instruments[25].getPatch().getProgram());
				 */
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
			// Load sequence into sequencer
			sequencer.setSequence(sequence);
			// Start playback
			//				sequencer.start();
			manager.play(sequence);
		} catch (MidiUnavailableException | InvalidMidiDataException | IOException | ParserConfigurationException | ParsingException ex) {
			ex.printStackTrace();
		}
	}

}
