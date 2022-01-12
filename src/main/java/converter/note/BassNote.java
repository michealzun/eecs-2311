package converter.note;

import converter.Instrument;

public class BassNote extends GuitarNote {

    public BassNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart) {
        super(stringNumber, origin, position, lineName, distanceFromMeasureStart);
        this.instrument = Instrument.BASS;
        this.fret = Integer.parseInt(origin);
        String noteDetails = this.noteDetails(this.lineName, this.fret);
        this.step = this.step(noteDetails);
        this.alter = this.alter(noteDetails);
        this.octave = this.octave(noteDetails);
        this.sign = this.fret+"";
    }

    @Override
    protected int getDefaultOctave(int stringNumber) {
    	int result;
    	switch (stringNumber) {
	    	case 1: result = 2; break;
	    	case 2: result = 2; break;
	    	case 3: result = 1; break;
	    	case 4: result = 1; break;
	    	case 5: result = 1; break;
	    	case 6: result = 1; break;
	    	default: result = 0; break;
    	}
        return result;
    }
}
