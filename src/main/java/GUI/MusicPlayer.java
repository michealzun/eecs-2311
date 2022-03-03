package GUI;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.xml.parsers.ParserConfigurationException;

import org.jfugue.integration.MusicXmlParser;
import org.jfugue.midi.MidiParserListener;

public class MusicPlayer {
	MusicXmlParser parser;	
	MidiParserListener midilistener;
	Sequencer sequencer;
	
	public MusicPlayer() throws ParserConfigurationException, MidiUnavailableException {
		parser = new MusicXmlParser();
		midilistener = new MidiParserListener();
		sequencer = MidiSystem.getSequencer();
	}

}
