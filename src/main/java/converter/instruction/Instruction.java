package converter.instruction;

import java.util.ArrayList;
import java.util.List;

import converter.ScoreComponent;
import utility.AnchoredText;
import utility.Patterns;
import utility.Range;
import utility.ValidationError;

public abstract class Instruction extends ScoreComponent{
	protected boolean isTop;
    
    public static String LINE_PATTERN = getLinePattern();

    protected Range rangeInLine;
    protected boolean hasBeenApplied;

    Instruction(AnchoredText inputAT, boolean isTop) {
        this.at = inputAT;
        this.isTop = isTop;
        //int relStartPos = position - Score.tabText.substring(0,position).lastIndexOf("\n");
        int relStartPos = at.positionInLine;
        int relEndPos = relStartPos + at.text.length() - 1;
        setRange(new Range(relStartPos, relEndPos));
    }

    public abstract <E extends ScoreComponent> void applyTo(E scoreComponent);

    void setHasBeenApplied(boolean bool) {
        this.hasBeenApplied = bool;
    }
    boolean getHasBeenApplied() {
        return this.hasBeenApplied;
    }

	@Override
	public List<Range> getRanges() {
		List<Range> ranges = new ArrayList<>();
		ranges.add(new Range(at.positionInScore, at.positionInScore + at.text.length()));
		return ranges;
	}
	
    public List<ValidationError> validate() {
        
        if (!this.hasBeenApplied) {
            addError(
                    "This instruction could not be applied to any measure or note.",
                    3,
                    getRanges());
        }
        return errors;
    }

	private static String getLinePattern() {
		String instruction = "((" + TimeSignature.PATTERN + ")|(" + Repeat.PATTERN + ")|(" + Timing.PATTERN + "))";
		return "(" + Patterns.SPACEORTAB + "*" + instruction + Patterns.SPACEORTAB + "*" + ")+";
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public Range getRange() {
		return rangeInLine;
	}

	public void setRange(Range range) {
		this.rangeInLine = range;
	}
}


