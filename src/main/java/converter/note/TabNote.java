package converter.note;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import converter.Instrument;
import converter.ScoreComponent;
import models.measure.note.Chord;
import models.measure.note.Dot;
import models.measure.note.TimeModification;
import utility.Patterns;
import utility.Range;
import utility.ValidationError;

public abstract class TabNote extends ScoreComponent implements Comparable<TabNote> {
    public boolean startsWithPreviousNote;
    public String text;
    public String lineName;
    public int dotCount;
    public Instrument instrument;
    int stringNumber;
    public int distance;
    public int graceDistance = 0;
    int position;
    public int duration;
    public String sign;
    public int voice;
    public boolean isGrace;
    //public static boolean SLASHED_GRACE = true;
    protected Map<NoteModelDecorator, String> decorators = new LinkedHashMap<>();
    public int divisions = 0;
    int beatType;
    int beatCount;
    protected int tuplet = 1;
    public boolean mustSplit;
    public int stretch = 1;
    protected String type = "";
    protected boolean explicitType = false;
    
    // A pattern that matches the note components of a measure line, like (2h7) or 8s3 or 12 or 4/2, etc.
    // It doesn't have to match the correct notation. It should be as vague as possible, so it matches anything that "looks"
    //like a note component (e.g it should match something like e|-------h3(-----|, even though it is invalid ) this makes it so that
    //even though incorrect, we still recognise the whole thing as a measure, and we get to the stage where we are trying to convert this
    //particular note. We thus will know the exact place where the problem is instead of the whole measure not being recognised as an
    // actual measure just because of that error and we flag the whole measure as an error instead of this one, small, specific
    // area of hte measure (the pattern for detecting measure groups uses this pattern)
    public static String COMPONENT_PATTERN = "[^-\\n\\r" + Patterns.DIVIDER_COMPONENTS+"]";
    public static String PATTERN = getNotePattern();

    private static String getNotePattern() {
        return "(" + Patterns.GUITAR_NOTE_GROUP_PATTERN + "|" + Patterns.DRUM_NOTE_GROUP_PATTERN + "|" + COMPONENT_PATTERN+"+" + ")";
    }

    public TabNote(int stringNumber, String text, int position, String lineName, int distanceFromMeasureStart) {
        this.text = text;
        this.lineName = lineName;
        this.position = position;
        this.stringNumber = stringNumber;
        this.duration = 1;
        this.distance = distanceFromMeasureStart;
        this.voice = 1;
    }
    
    public TabNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart, int voice) {
        this(stringNumber, origin, position, lineName, distanceFromMeasureStart);
        this.voice = voice;
    }
    
    
    /** Copy constructor used for tied notes. Does not copy duration or decorators
     * @param n - The note to copy
     */
    public TabNote(TabNote n) {
        this.startsWithPreviousNote = n.startsWithPreviousNote;
        this.text = n.text;
        this.lineName = n.lineName;
        //public int dotCount;
        this.instrument = n.instrument;
        this.stringNumber = n.stringNumber;
        //public int distance;
        //int position;
        //public int duration;
        this.sign = n.sign;
        this.voice = n.voice;
        this.isGrace = n.isGrace;
        // Decorators are not copied
        setDecorators(new LinkedHashMap<>());
        this.divisions = n.divisions;
        this.beatType = n.beatType;
        this.beatCount = n.beatCount;
        this.mustSplit = n.mustSplit;
        this.stretch = n.stretch;
    }
    
    public void setDivisions(int divisions) {
    	this.divisions = divisions;
    	setType();
    	if (type.equals("1024th")) this.mustSplit = true;
    }
    
    public void setBeatType(int bt) {
    	this.beatType = bt;
    }
    
    public void setBeatCount(int bc) {
    	this.beatCount = bc;
    }
    
    public void setDuration(int duration) {
	    this.duration = duration;
	    if (divisions > 0) {
	    	setType();
	    	if (type.equals("1024th")) 
	    		this.mustSplit = true;
	    	else
	    		this.mustSplit = false;
	    }
	}

    public void setDuration(String timing, int div) {
    	explicitType = true;
    	char base = timing.charAt(0);
    	switch (base) {
    	case 'w': type = "whole"; duration = div * 4; break;
    	case 'h': type = "half"; duration = div * 2; break;
    	case 'q': type = "quarter"; duration = div; break;
    	case 'e': type = "eighth"; duration = div / 2; break;
    	case 's': type = "16th"; duration = div / 4; break;
    	case 't': type = "32nd"; duration = div / 8; break;
    	default: assert false: "There should not be any other base timing characters";
    	}
    	if (timing.length() > 1) {
    		if (timing.charAt(1) == '.') {
    			dotCount = timing.length() - 1;
    			for (int i = 0; i < dotCount; i++) duration = duration + duration / 2;
    		}
    		else {
    			tuplet = Integer.parseInt(timing.substring(1));
    			duration = duration * 2 / 3; //TODO Update for 5-tuplets etc.
    		}
    	}		
    }

	public int getDuration() {
    	return duration;
    }

    public void addDecorator(NoteModelDecorator noteDecor, String message) {
        this.getDecorators().put(noteDecor, message);
    }

    protected void setType() {
    	dotCount = 0;
    	tuplet = 1;
    	int RESOLUTION = 192;  // 3 x 2^6
    	int noteVal = RESOLUTION * duration / (divisions * 4);
    	switch (noteVal) { 
    	case 0: type = ""; break; // Grace note
    	case 3: type = "64th"; break;
    	case 4: tuplet = 3; type = "32nd"; break;
    	case 6: type = "32nd"; break;
    	case 8: tuplet = 3; type = "16th"; break;
    	case 9: dotCount = 1; type = "32nd"; break;
    	case 12: type = "16th"; break;
    	case 16: tuplet = 3; type = "eighth"; break;
    	case 18: dotCount = 1; type = "16th"; break;
    	case 21: dotCount = 2; type = "16th"; break;
    	case 24: type = "eighth"; break;
    	case 32: tuplet = 3; type = "quarter"; break;
    	case 36: dotCount = 1; type = "eighth"; break;
    	case 42: dotCount = 2; type = "eighth"; break;
    	case 45: dotCount = 3; type = "eighth"; break;
    	case 48: type = "quarter"; break;
    	case 64: tuplet = 3; type = "half"; break;
    	case 72: dotCount = 1; type = "quarter"; break;
    	case 84: dotCount = 2; type = "quarter"; break;
    	case 90: dotCount = 3; type = "quarter"; break;
    	case 93: dotCount = 4; type = "quarter"; break;
    	case 96: type = "half"; break;
    	case 128: tuplet = 3; type = "whole"; break;
    	case 144: dotCount = 1; type = "half"; break; // 3 quarters
    	case 168: dotCount = 2; type = "half"; break;
    	case 180: dotCount = 3; type = "half"; break;
    	case 186: dotCount = 4; type = "half"; break;
    	case 189: dotCount = 5; type = "half"; break;
    	case 192: type = "whole"; break;
    	case 288: dotCount = 1; type = "whole";  break;
    	case 336: dotCount = 2; type = "whole"; break;
    	case 360: dotCount = 3; type = "whole"; break;
    	case 372: dotCount = 4; type = "whole"; break;
    	case 378: dotCount = 6; type = "whole"; break;
    	default: type = "1024th"; break;
    	}
    }

    public int compareTo(TabNote other) {
    	int result = this.distance - other.distance;
    	if (result == 0) result = this.graceDistance - other.graceDistance;
        return result;
    }
	
    public abstract TabNote copy();
    
    protected abstract void setStems(models.measure.note.Note noteModel);
    
	public Map<NoteModelDecorator, String> getDecorators() {
		return decorators;
	}

	public void setDecorators(Map<NoteModelDecorator, String> decorators) {
		this.decorators = decorators;
	}

	public models.measure.note.Note getModel() {
		
		models.measure.note.Note noteModel = new models.measure.note.Note();
		
	    if (this.startsWithPreviousNote) noteModel.setChord(new Chord());
	    noteModel.setDuration(this.duration);
	    noteModel.setVoice(this.voice);
	
	    
	    if (!type.isEmpty())
	        noteModel.setType(type);
	    if (tuplet == 3) {
	       	TimeModification tm = new TimeModification(3,2);
	    	noteModel.setTimeModification(tm);	
	    }
	    List<Dot> dots = new ArrayList<>();
	    for (int i=0; i<this.dotCount; i++){
	        dots.add(new Dot());
	    }
	    if (!dots.isEmpty())
	        noteModel.setDots(dots);
	
	    setStems(noteModel);
	    
	    for (NoteModelDecorator noteDecor : this.getDecorators().keySet()) {
	        if (getDecorators().get(noteDecor).equals("success"))
	            noteDecor.applyTo(noteModel);
	    }
	
	    return noteModel;
	}

	@Override
	public List<Range> getRanges() {
		List<Range> ranges = new ArrayList<>();
		ranges.add(new Range(position,position+text.length()));
		return ranges;
	}
	
	public List<ValidationError> validate() {
	    if (!this.text.equals(this.text.strip())) {
	        addError(
	                "Adding whitespace might result in different timing than you expect.",
	                3,
	                getRanges());
	        
	    }
	    return errors;
	}
}
