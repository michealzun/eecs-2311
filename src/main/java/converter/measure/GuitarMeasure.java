package converter.measure;

import java.util.ArrayList;
import java.util.List;

import converter.measure_line.TabGuitarString;
import converter.measure_line.TabString;
import converter.note.TabNote;
import models.measure.attributes.Attributes;
import models.measure.attributes.Clef;
import models.measure.attributes.Key;
import models.measure.attributes.StaffDetails;
import models.measure.attributes.StaffTuning;
import models.measure.attributes.Time;
import utility.AnchoredText;
import utility.DoubleDigitStyle;
import utility.Settings;
import utility.ValidationError;

public class GuitarMeasure extends TabMeasure{
    protected int MIN_LINE_COUNT;
    protected int MAX_LINE_COUNT;
    String [][] tuning;

    public GuitarMeasure(List<AnchoredText> inputData, List<AnchoredText> inputNameData, boolean isFirstMeasure) {
        super(inputData, inputNameData, isFirstMeasure);
        allowedPadding = Settings.getInstance().guitarMeasureStartPadding;
        MIN_LINE_COUNT = 6;
        MAX_LINE_COUNT = 6;
        tuning = Settings.getInstance().getGuitarTuning();
    }

    @Override
	protected int adjustDivisionsForSpecialCases(int usefulMeasureLength) {
		for (List<List<TabNote>> chordList : getVoiceSortedChordList()) {
			int start = 0;
			// Start at 1 to ignore first chord
			// If it is longer than one digit, it has not been counted in usefulMeasureLength
			// Applies only for NOTE ON SECOND DIGIT
			if (Settings.getInstance().ddStyle == DoubleDigitStyle.NOTE_ON_SECOND_DIGIT_STRETCH)
				start = 1;
			for (int i = start; i < chordList.size(); i++) {
				List<TabNote> chord = chordList.get(i);
				usefulMeasureLength -= chordStretch(chord) - 1;
			}
		}
		return usefulMeasureLength;
	}

	@Override
	protected int adjustDurationForSpecialCases(int duration, List<TabNote> chord, List<TabNote> nextChord) {
		// Adjust duration due to double digit fret numbers
		switch (Settings.getInstance().ddStyle) {
		case NOTE_ON_FIRST_DIGIT_STRETCH: duration -= chordStretch(chord) - 1;
			break;
		case NOTE_ON_SECOND_DIGIT_NO_STRETCH:
			break;
		case NOTE_ON_SECOND_DIGIT_STRETCH: if (nextChord != null) duration -= chordStretch(nextChord) - 1;
		default:
			break;
		}
		return duration;
	}

	protected TabString newTabString(int stringNumber, AnchoredText data, AnchoredText name)
	{
		return new TabGuitarString(stringNumber, data, name);
	}
	
	protected Attributes getAttributesModel() {
        Attributes attributes = new Attributes();
        attributes.setDivisions(this.divisions);
        attributes.setKey(new Key(0));
        if (this.changesTimeSignature)
            attributes.setTime(new Time(this.beatCount, this.beatType));
        if (this.measureCount == 1) {
            attributes.setClef(new Clef("TAB", 5));
            List<StaffTuning> staffTunings = new ArrayList<>();
            for (int string = 0; string < MAX_LINE_COUNT; string++)
            	staffTunings.add(new StaffTuning(string + 1, tuning[MAX_LINE_COUNT-1-string][0], Integer.parseInt(tuning[MAX_LINE_COUNT-1-string][1])));
            attributes.setStaffDetails(new StaffDetails(MAX_LINE_COUNT, staffTunings));
        }
        return attributes;
    }
	
	/**
	 * Validates that the guitar has a supported number of strings. Validates its
	 * aggregated TabString objects TODO Can be moved to superclass?
	 */
	public List<ValidationError> validate() {
		super.validate();

		if (this.tabStringList.size() < MIN_LINE_COUNT || this.tabStringList.size() > MAX_LINE_COUNT) {
			String rangeMsg;
			if (MIN_LINE_COUNT == MAX_LINE_COUNT)
				rangeMsg = "" + MIN_LINE_COUNT;
			else
				rangeMsg = "between " + MIN_LINE_COUNT + " and " + MAX_LINE_COUNT;

			addError("A Guitar measure should have " + rangeMsg + " lines.", 2, this.getRanges());

		}

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
