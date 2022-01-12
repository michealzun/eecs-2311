package utility;

import java.util.List;

public class DrumPieceInfo {

	private List<String> nickNames;
	private String midiID;
	private String fullName;
	private String step;
	private int octave;
	
	public DrumPieceInfo (List<String> nickNames, String midiID, String fullName, String step, int octave) {
		this.nickNames = nickNames;
		this.midiID = midiID;
		this.fullName = fullName;
		this.step = step;
		this.octave = octave;
	}
	
	public List<String> getNickNames() {
		return nickNames;
	}
	public void setNickNames(List<String> nickNames) {
		this.nickNames = nickNames;
	}
	public String getMidiID() {
		return midiID;
	}
	public void setMidiID(String midiID) {
		this.midiID = midiID;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public int getOctave() {
		return octave;
	}
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
}
