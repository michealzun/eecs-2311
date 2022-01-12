package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.instruction.Instruction;
import converter.instruction.InvalidRepeat;
import converter.instruction.Repeat;
import converter.instruction.TimeSignature;
import converter.instruction.Timing;
import utility.AnchoredText;
import utility.Patterns;
import utility.Range;
import utility.Settings;
import utility.ValidationError;

public class TabSection extends ScoreComponent {

	//                           a measure line at start of line(with name)          zero or more middle measure lines       (optional |'s and spaces then what's ahead is end of line)
    private static String LINE_PATTERN = "(" + Patterns.START_OF_LINE          +          Patterns.MIDDLE_OF_LINE + "*"    +   "("+ Patterns.DIVIDER+"*"+Patterns.SPACEORTAB+"*)"     +  ")";

    int endIndex;
    private TabRow tabRow;
    boolean isFirstTabSection;
    private List<Instruction> instructionList = new ArrayList<>();
    private boolean repeatsFound = false;

    public TabSection(AnchoredText at, boolean isFirstTabSection) {
        this.at = at;
        this.endIndex = at.positionInScore + at.text.length();
        this.isFirstTabSection = isFirstTabSection;
        createTabRowAndInstructionList();
        for (Instruction instruction : this.instructionList)
            instruction.applyTo(this);
    }

	/**
	 * Separates the text of this TabSection into
	 * Instructions, and a TabRow
	 */
	private void createTabRowAndInstructionList() {

		List<String> lines = new ArrayList<>();
		List<Integer> starts = new ArrayList<>();
		List<AnchoredText> tabRowData = new ArrayList<>();
		Matcher lineMatcher = Pattern.compile("(?<=^|\\n)[^\\n]+(?=$|\\n)").matcher(at.text);
		while (lineMatcher.find()) { // go through each line
			String l = lineMatcher.group();
			lines.add(l);
			starts.add(lineMatcher.start());
		}
		boolean isTop = true;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			int start = starts.get(i);
			Matcher topInstructionMatcher = Pattern.compile(Instruction.LINE_PATTERN).matcher(line);
			if (topInstructionMatcher.find()) { // no need for loop as only one line
				AnchoredText instructionAT = new AnchoredText(topInstructionMatcher.group(),
						at.positionInScore + start + topInstructionMatcher.start(), topInstructionMatcher.start());
				this.instructionList.addAll(extractInstructions(instructionAT, isTop));
				continue;
			}
			String pattern = tabRowLinePattern();
			Matcher tabRowMatcher = Pattern.compile(pattern).matcher(line);
			if (tabRowMatcher.find()) {
				isTop = false;
				assert line.charAt(0) != '\n';
				AnchoredText tabRowAT = new AnchoredText(line, at.positionInScore + start, 0);
				tabRowData.add(tabRowAT);
			}
		}

		if (isFirstTabSection && Settings.getInstance().getInstrument() == Instrument.GUITAR && tabRowData.size() < 6)
			Settings.getInstance().setDetectedInstrument(Instrument.BASS);
		this.tabRow = new TabRow(tabRowData);
	}
	
    private List<Instruction> extractInstructions(AnchoredText instrAT, boolean isTop) {
        List<Instruction> instructionList = new ArrayList<>();
        // Matches the repeat text including any barlines
        Matcher repeatMatcher = Pattern.compile(Repeat.PATTERN).matcher(instrAT.text);
        boolean repeatsAdded = false;
        
        while((repeatMatcher.find())) {
        	AnchoredText repeatAT = new AnchoredText(repeatMatcher.group(), instrAT.positionInScore + repeatMatcher.start(), instrAT.positionInLine + repeatMatcher.start());
        	if (repeatsFound)
        		instructionList.add(new InvalidRepeat(repeatAT, isTop));
        	else {
        		repeatsAdded = true;
        		instructionList.add(new Repeat(repeatAT, isTop));
        	}
        }
        if (repeatsAdded) repeatsFound = true;
        
        Matcher timeSigMatcher = Pattern.compile(TimeSignature.PATTERN).matcher(instrAT.text);
        while(timeSigMatcher.find()) {
        	AnchoredText timeSigAT = new AnchoredText(timeSigMatcher.group(), instrAT.positionInScore + timeSigMatcher.start(), instrAT.positionInLine + timeSigMatcher.start());   	
            instructionList.add(new TimeSignature(timeSigAT, isTop));
        }

        Matcher timingMatcher = Pattern.compile(Timing.PATTERN).matcher(instrAT.text);
        while(timingMatcher.find()) {
        	AnchoredText timingAT = new AnchoredText(timingMatcher.group(), instrAT.positionInScore + timingMatcher.start(), instrAT.positionInLine + timingMatcher.start());
            instructionList.add(new Timing(timingAT, isTop));
        }
        
        return instructionList;
    }

    public static String tabRowLinePattern() {
        return "(^"+ Patterns.SPACEORTAB + "*(" + LINE_PATTERN + Patterns.SPACEORTAB + "*)+)";
    }

    /**
     * Creates the regex pattern for detecting a tab section (i.e a tab row and corresponding instructions)
     * @return a String regex pattern enclosed in brackets that identifies a tab section pattern (the pattern also captures the newline right before the tab row collection)
     */
    public static String getRegexPattern() {
        // Zero or more instructions, followed by one or more tab rows, followed by zero or more instructions
        return "((^|\\n)"+ Instruction.LINE_PATTERN+")*"          // 0 or more lines separated by newlines, each containing a group of instructions
                + "("                                                                   // then the tab row, which is made of...
                +       "(^|\\n)"                                                           // a start of line or a new line
                +       TabSection.tabRowLinePattern()                               // a tab row followed by whitespace, all repeated one or more times
                + ")+"                                                                  // the tab row just described is repeated one or more times.
                + "(\\n" + Instruction.LINE_PATTERN+")*";
    }
	    
	public TabRow getTabRow() {
		return this.tabRow;
	}

	@Override
	public List<Range> getRanges() {
		List<Range> ranges = new ArrayList<>();
		ranges.add(new Range(at.positionInScore, at.positionInScore + at.text.length()));
		return null;
	}

	/**
	 * Validates the aggregated TabRow objects of this class.
	 */
	public List<ValidationError> validate() {
		errors.addAll(tabRow.validate());
		for (Instruction instruction : this.instructionList) {
			errors.addAll(instruction.validate());
		}
		return errors;
	}

}
