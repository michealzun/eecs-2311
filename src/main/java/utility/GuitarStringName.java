package utility;

public class GuitarStringName {

	private String step;
	private String octave;
	
	public GuitarStringName (String st, String oc) {
		step = st;
		octave = oc;
	}
	
	public String getStep() {
		return step;
	}
	
	public String getOctave() {
		return octave;
	}
}
