package converter.measure;

import java.util.List;

import converter.measure_line.TabDrumString;
import converter.measure_line.TabString;
import converter.note.TabNote;
import models.measure.attributes.Attributes;
import models.measure.attributes.Clef;
import models.measure.attributes.Key;
import models.measure.attributes.Time;
import utility.AnchoredText;
import utility.Settings;
import utility.ValidationError;

public class DrumMeasure extends TabMeasure {

    public DrumMeasure(List<AnchoredText> inputData, List<AnchoredText> inputNameData, boolean isFirstMeasureInGroup) {
        super(inputData, inputNameData, isFirstMeasureInGroup);
        allowedPadding = Settings.getInstance().drumsMeasureStartPadding;
    }
    
    @Override
	protected int adjustDivisionsForSpecialCases(int usefulMeasureLength) {
		return usefulMeasureLength;
	}
    
	@Override
	protected int adjustDurationForSpecialCases(int duration, List<TabNote> chord, List<TabNote> nextChord) {
		// Duration should be 1 for choked cymbals
//		boolean choke = false;
//		for (TabNote note : chord) {
//			if (note.origin.equals("#")) {
//				choke = true;
//				break;
//			}
//		}
//		if (choke)
//			duration = 1;
		return duration;
	}

	protected TabString newTabString(int stringNumber, AnchoredText data, AnchoredText name)
	{
		return new TabDrumString(stringNumber, data, name);
	}
	
	protected Attributes getAttributesModel() {
        Attributes attributes = new Attributes();
        attributes.setDivisions(this.divisions);
        
        if (this.changesTimeSignature)
            attributes.setTime(new Time(this.beatCount, this.beatType));

        if (this.measureCount == 1) {
        	attributes.setKey(new Key(0));
            attributes.setClef(new Clef("percussion", 2));  
        }
        return attributes;
    }

	/**
     * Validates its aggregated TabString objects
     * TODO Can be moved to superclass?
     */
	@Override
	public List<ValidationError> validate() {
	    super.validate();
	    // Validate Aggregates unless there are already critical errors	
	    for (ValidationError error : errors) {
	        if (error.getPriority() <= Settings.getInstance().criticalErrorCutoff) {
	            return errors;
	        }
	    }
	    for (TabString measureLine : this.tabStringList) {
	        errors.addAll(measureLine.validate());
	    }
	
	    return errors;
	}
}
