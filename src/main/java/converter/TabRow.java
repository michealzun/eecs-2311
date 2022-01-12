package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.instruction.RepeatType;
import converter.measure.BassMeasure;
import converter.measure.DrumMeasure;
import converter.measure.GuitarMeasure;
import converter.measure.TabMeasure;
import utility.AnchoredText;
import utility.Patterns;
import utility.Range;
import utility.Settings;
import utility.ValidationError;

public class TabRow extends ScoreComponent {

    public List<TabMeasure> tabMeasures = new ArrayList<>();
	public List<AnchoredText> data = new ArrayList<>();
    public TabRow() {}
    
    /**
     * Creates a TabRow object from a List of AnchoredText which represent the lines in the tablature row
     * @param data a List<AnchoredText> containing the lines which are meant to represent a tablature row. 
     */
    public TabRow(List<AnchoredText> inputData) {
    	data = inputData;
    	List<List<AnchoredText>> measureData = new ArrayList<>();
    	List<List<AnchoredText>> measureNameData = new ArrayList<>();

        //Iterate over all the lines of the tabRow, e.g. all six for guitar
        for (int i=0; i < data.size(); i++) {
            String currentLine = data.get(i).text;//  tabRowLines.get(i);
            int currentLineStartPos = data.get(i).positionInScore;
            assert data.get(i).positionInLine == 0;
            //Find the name at the beginning of a text line
            AnchoredText currentNameData = nameOf(currentLine, currentLineStartPos);
            int nameOffset = currentNameData.positionInLine + currentNameData.text.length();
            if (Settings.getInstance().getInstrument() == Instrument.GUITAR && i < 6) {
            	if (currentNameData.text == "") currentNameData.text = Settings.getInstance().getGuitarTuning()[i][0];    // Keep using what ever tuning was previously set if this is guitar
            	Settings.getInstance().setGuitarTuning(i, currentNameData.text.toUpperCase());  // Update tuning. Only likely to make a difference for the first measure
            }
            if (Settings.getInstance().getInstrument() == Instrument.BASS && i < 4) {
            	if (currentNameData.text == "") currentNameData.text = Settings.getInstance().getBassTuning()[i][0];    // Keep using what ever tuning was previously set if this is bass
            	Settings.getInstance().setBassTuning(i, currentNameData.text.toUpperCase());  // Update tuning. Only likely to make a difference for the first measure
            }
            
            int measureCount = 0;
            Matcher measureInsidesMatcher = Pattern.compile(Patterns.insidesPattern()).matcher(currentLine.substring(nameOffset));
            while (measureInsidesMatcher.find()) {
                measureCount++;
                String measureText = measureInsidesMatcher.group();
                int measurePositionInLine = nameOffset + measureInsidesMatcher.start();
                int measurePositionInScore = currentLineStartPos + measurePositionInLine;
                if (measureText.charAt(0) == '|') {
                	measureText = measureText.substring(1);
                	measurePositionInLine++;
                	measurePositionInScore++;
                }
                if (measureText.charAt(measureText.length()-1) == '|')
                	measureText = measureText.substring(0, measureText.length() - 1);

                // If we are starting a new measure, measureCount is larger by 1
                if (measureData.size() < measureCount) {
                    measureData.add(new ArrayList<>());
                    measureNameData.add(new ArrayList<>());
                }

                // Get the particular measure we are interested in and add this line to its list of lines
                AnchoredText currentLineData = new AnchoredText(measureText, measurePositionInScore, measurePositionInLine);
                measureData.get(measureCount - 1).add(currentLineData);
                measureNameData.get(measureCount - 1).add(currentNameData);
            }
        }
        boolean isFirstMeasureInGroup = true;
        //Iterate over all the extracted measures
        for (int i = 0; i < measureData.size(); i++) {
            tabMeasures.add(from(measureData.get(i), measureNameData.get(i), isFirstMeasureInGroup));
            isFirstMeasureInGroup = false;
        }
    }

    /**
     * Creates an instance of the abstract Measure class whose concrete type depends on the instrument setting
     * Also deals with in-measure repeat markings 
     */
    private TabMeasure from(List<AnchoredText> measureAT, List<AnchoredText> measureNameAT, boolean isFirstMeasureInGroup) {
    	
        boolean repeatStart = checkRepeatStart(measureAT);
        boolean repeatEnd = checkRepeatEnd(measureAT);
        AnchoredText countAT = extractRepeatCount(measureAT);
        removeRepeatMarkings(measureNameAT, repeatStart, repeatEnd, countAT);
//        int repeatCount = 1;
//        if (!repeatCountStr.isEmpty()) {
//            Matcher numMatcher = Pattern.compile("(?<=\\])[0-9]+").matcher(repeatCountStr);
//            numMatcher.find();
//            repeatCountStr = numMatcher.group();
//            repeatCount = Integer.parseInt(repeatCountStr);
//        }

		TabMeasure measure = switch (Settings.getInstance().getInstrument()) {
		case GUITAR -> new GuitarMeasure(measureAT, measureNameAT, isFirstMeasureInGroup);
		case BASS -> new BassMeasure(measureAT, measureNameAT, isFirstMeasureInGroup);
		case DRUMS -> new DrumMeasure(measureAT, measureNameAT, isFirstMeasureInGroup);
		case NONE -> null;
		};
		assert measure != null: "Instrument must be set or detected before creating a TabRow";
    
    int repeatCount = 2;
    if (!countAT.text.isEmpty()) repeatCount = Integer.parseInt(countAT.text);
        if (repeatStart)
            measure.setRepeat(repeatCount, RepeatType.START);
        if (repeatEnd)
            measure.setRepeat(repeatCount, RepeatType.END);
        return measure;
    }

	private boolean checkRepeatStart(List<AnchoredText> atList) {
        boolean repeatStart = true;
        int repeatStartMarkCount = 0;
        for (AnchoredText at : atList) {
            repeatStart &= at.text.strip().startsWith("|");
            if (at.text.strip().startsWith("|*")) repeatStartMarkCount++;
        }
        repeatStart &= repeatStartMarkCount>=2;
        return repeatStart;
    }
	
    private boolean checkRepeatEnd(List<AnchoredText> atList) {
        boolean repeatEnd = true;
        int repeatEndMarkCount = 0;
        for (int i=1; i<atList.size(); i++) {
            String line = atList.get(i).text;
            repeatEnd &= line.strip().endsWith("|");
            if (line.strip().endsWith("*|")) repeatEndMarkCount++;
        }
        repeatEnd &= repeatEndMarkCount>=2;
        return repeatEnd;
    }
    
    private AnchoredText extractRepeatCount(List<AnchoredText> lines) {
        if (!checkRepeatEnd(lines)) return new AnchoredText("", 0, 0);
        AnchoredText firstAT = lines.get(0);
        Matcher numMatcher = Pattern.compile("(?<=[^0-9])[0-9]+(?=[ ]|"+ Patterns.DIVIDER+"|$)").matcher(firstAT.text);
        if (!numMatcher.find()) return new AnchoredText("", 0, 0);
        return new AnchoredText(numMatcher.group(), firstAT.positionInScore + numMatcher.start(), numMatcher.start());
    }

    private static void removeRepeatMarkings(List<AnchoredText> lines, boolean repeatStart, boolean repeatEnd, AnchoredText countAT) {
        if (!countAT.text.isEmpty()){
            Matcher posMatcher = Pattern.compile("(?<=\\[)[0-9]+(?=\\])").matcher(countAT.text);
            Matcher numMatcher = Pattern.compile("(?<=\\])[0-9]+").matcher(countAT.text);
            posMatcher.find();
            numMatcher.find();
            int position = Integer.parseInt(posMatcher.group());
            int numLen = numMatcher.group().length();
            String line = lines.get(0).text;
            line = line.substring(0, position)+"-".repeat(Math.max(numLen-1, 0))+line.substring(position+numLen);
            //remove extra - which overlaps with the |'s
            /*
            -----------4|
            -----------||
            ----------*||   we wanna remove the -'s on the same column as the *'s. we do that for the first measure line in the code right below. the code lower handles the case for the rest of the lines.
            ----------*||
            -----------||
            -----------||
             */
            String tmp1 = line.substring(0, position-1);
            String tmp2 = position>=line.length() ? "" : line.substring(position);
            line = tmp1+tmp2;
            lines.get(0).text = line;
        }
        for(int i=0; i<lines.size(); i++) {
            String line = lines.get(i).text;
            int linePosition = lines.get(i).positionInScore;
            if (line.startsWith("|*")){
                linePosition+=2;
                line = line.substring(2);
            }else if(line.startsWith("|")) {
                int offset;
                if (repeatStart) offset = 2;
                else offset = 1;
                linePosition += offset;
                line = line.substring(offset);
            }
            if (line.endsWith("*|"))
                line = line.substring(0, line.length()-2);
            else if (line.endsWith("|")) {
                int offset;
                if (repeatEnd) offset = 2;
                else offset = 1;
                line = line.substring(0, line.length() - offset);
            }
            lines.get(i).text = line;
            lines.get(i).positionInScore = linePosition;
            //TODO Should change positionInLine by same amount?
        }
    }

    
    public AnchoredText nameOf(String line, int pos) {
    	AnchoredText result;
        Pattern measureLineNamePttrn = Pattern.compile(Patterns.measureNameExtractPattern());
        Matcher measureLineNameMatcher = measureLineNamePttrn.matcher(line);
        if (measureLineNameMatcher.find())
        	result = new AnchoredText(measureLineNameMatcher.group(), pos + measureLineNameMatcher.start(), measureLineNameMatcher.start());
        else
            result = null;
        return result;
    }
	
	public List<TabMeasure> getMeasureList() {
		return this.tabMeasures;
	}


//	public boolean isGuitar(boolean strictCheck) {
//        for (TabMeasure measure : this.tabMeasures) {
//            if (!measure.isGuitar(strictCheck))
//                return false;
//        }
//        return true;
//    }
//
//    public boolean isDrum(boolean strictCheck) {
//        for (TabMeasure measure : this.tabMeasures) {
//            if (!measure.isDrum(strictCheck))
//                return false;
//        }
//        return true;
//    }
//
//    public boolean isBass(boolean strictCheck) {
//        for (TabMeasure measure : this.tabMeasures) {
//            if (!measure.isBass(strictCheck))
//                return false;
//        }
//        return true;
//    }

	@Override
	public List<Range> getRanges() {
		List<Range> linePositions = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			int startIdx = data.get(i).positionInScore; // ) this.positions.get(i);
			int endIdx = startIdx + data.get(i).text.length(); // this.lines.get(i).length();
			linePositions.add(new Range(startIdx, endIdx));
		}
		return linePositions;
	}

	/**
	 * Validates if all Measure objects aggregated in this TabRow have the same number of measure lines.
	 * It also validates all its aggregates i.e all Measure objects and Instruction objects that it aggregates.
	 */
	public List<ValidationError> validate() {
	    
	    //--------------Validating yourself--------------------------
	    //making sure all measures in this measure group have the same number of lines
	    boolean hasEqualMeasureLineCount = true;
	    List<Range> failPositions = new ArrayList<>();
	    int measureLineCount = 0;
	    for (TabMeasure measure : this.tabMeasures) {
	        if (measureLineCount==0)
	            measureLineCount = measure.lineCount;
	        else if(measure.lineCount!=measureLineCount) {
	            hasEqualMeasureLineCount = false;
	            failPositions.addAll(measure.getRanges());
	        }
	    }
	
	    if (!hasEqualMeasureLineCount) {
	        addError(
	                "All measures in a tablature row must have the same number of lines",
	                2,
	                failPositions
	        );
	        
	    }
	
//	    boolean hasGuitarMeasures = true;
//	    boolean hasDrumMeasures = true;
//	    for (TabMeasure measure : this.tabMeasures) {
//	        hasGuitarMeasures &= measure instanceof GuitarMeasure;
//	        hasDrumMeasures &= measure instanceof DrumMeasure;
//	    }
//	    if (!(hasGuitarMeasures || hasDrumMeasures)) {
//	        addError(
//	                "All measures in a tablature row must be of the same type (i.e. all guitar measures or all drum measures)",
//	                2,
//	                this.getRanges()
//	        );
//	        
//	    }
	
	    //--------------Validate your aggregates (only if you're valid)-------------------
	    if (!errors.isEmpty()) return errors;
	
	    for (TabMeasure measure : this.tabMeasures) {
	        errors.addAll(measure.validate());
	    }
	
	    return errors;
	}


//	@Override
//    public String toString() {
//        StringBuilder outStr = new StringBuilder();
//        for (int i=0; i<this.tabMeasures.size()-1; i++) {
//            outStr.append(this.tabMeasures.get(i).toString());
//            outStr.append("\n\n");
//        }
//        if (!this.tabMeasures.isEmpty())
//            outStr.append(this.tabMeasures.get(this.tabMeasures.size()-1).toString());
//        return outStr.toString();
//    }

}