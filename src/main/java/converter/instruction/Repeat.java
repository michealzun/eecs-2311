package converter.instruction;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.ScoreComponent;
import converter.TabRow;
import converter.TabSection;
import converter.measure.TabMeasure;
import utility.AnchoredText;
import utility.Patterns;
import utility.Range;
import utility.ValidationError;

public class Repeat extends Instruction {
    
	public static String PATTERN = getPattern();
    private int repeatCount;
    private boolean startApplied = false;
    private boolean endApplied = false;
    
    public Repeat(AnchoredText inputAT, boolean isTop) {
        super(inputAT, isTop);
        Matcher matcher = Pattern.compile("[0-9]+").matcher(at.text);
        if (matcher.find())
            this.repeatCount = Integer.parseInt(matcher.group());
    }

	public <E extends ScoreComponent> void applyTo(E scoreComponent) {
		if ((!isTop) || this.getHasBeenApplied() || this.repeatCount == 0) {
			this.setHasBeenApplied(true);
			return;
		}

		if (scoreComponent instanceof TabSection) {
			TabSection tabSection = (TabSection) scoreComponent;
			TabMeasure firstMeasure = null;
			TabMeasure lastMeasure = null;
			TabRow tabRow = tabSection.getTabRow();
			for (TabMeasure measure : tabRow.getMeasureList()) {
				Range measureRange = measure.getRelativeRange();
				if (measureRange == null || !this.getRange().overlaps(measureRange))
					continue;
				if (firstMeasure == null && !this.startApplied)
					firstMeasure = measure;
				if (!this.endApplied)
					lastMeasure = measure;
			}

			if (firstMeasure != null)
				this.startApplied = firstMeasure.setRepeat(this.repeatCount, RepeatType.START);
			if (lastMeasure != null)
				this.endApplied = lastMeasure.setRepeat(this.repeatCount, RepeatType.END);
		}
		this.setHasBeenApplied(this.startApplied && this.endApplied);
	}

    private static String getPattern() {
        String times = "[xX]";
        String timesLong = "[Tt][Ii][Mm][Ee][Ss]";
        String count = "[0-9]{1,2}";
        String repeatTextPattern = "[Rr][Ee][Pp][Ee][Aa][Tt]" + "([ -]{0,7}|[ \t]{0,2})"  +  "(" +"("+times+count+")|("+ count+times +")|("+ count + "([ -]{0,7}|[ \t]{0,3})"  + timesLong + ")" + ")";
        //     | or sol or whitespace   optional space or -                     optional space or -     | or eol or whitespace
        return "("+"(((?<=\\|)|\\||^|"+ Patterns.SPACEORTAB + ")|(?<=\\n))"  +        "[ -]*"       +   repeatTextPattern   +   "[ -]*"     +     "(($|\\s)|\\|)" + ")";
    }

	public List<ValidationError> validate() {
	    super.validate();
	    if (!(isTop)) {
	        addError(
	                "Repeats should only be applied to the top of measures.",
	                3,
	                getRanges());
	    }
	    if ((!this.startApplied && this.endApplied)) {
	        addError(
	                "This repeat was not fully applied.",
	                1,
	                getRanges());
	    }
	    return errors;
	}
}
