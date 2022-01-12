package converter.note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.Instrument;
import models.measure.note.Note;
import models.measure.note.Pitch;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Technical;
import utility.GuitarUtils;
import utility.Range;
import utility.ValidationError;

public class GuitarNote extends TabNote {
    public static String FRET_PATTERN = "([0-9]{1,2})";
    public static String GRACE_PATTERN = getGracePattern();
    public static String PATTERN = getPattern();


    private static String getPattern() {
        return "("+ FRET_PATTERN +"|"+ GRACE_PATTERN +")";
    }

    protected String step;
    protected int alter;
    protected int octave;
    protected int fret;
	private String noteDetails;

    private static String getGracePattern() {
        return "(g"+ FRET_PATTERN +"[hp]"+ FRET_PATTERN +")";
    }

    public GuitarNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart) {
        super(stringNumber, origin, position, lineName, distanceFromMeasureStart);
        this.instrument = Instrument.GUITAR;
        this.fret = Integer.parseInt(origin);
        this.noteDetails = noteDetails(this.lineName, this.fret);
        if (noteDetails == null) return;
        this.step = this.step(noteDetails);
        this.alter = this.alter(noteDetails);
        this.octave = this.octave(noteDetails);
        this.sign = this.fret + "";
    }

    public GuitarNote(GuitarNote n) {
    	super(n);
        this.step = n.step;
        this.alter = n.alter;
        this.octave = n.octave;
        this.fret = n.fret;
    	this.noteDetails = n.noteDetails;
    }
    
    public int getFret() {
        return this.fret;
    }

    protected String noteDetails(String lineName, int fret) {
        String noteDetails = "";
        String name = lineName.strip();
        String[] nameList = GuitarUtils.KEY_LIST;

        int currentOctave;
        Matcher lineOctaveMatcher = Pattern.compile("(?<=[^0-9])[0-9]+$").matcher(name);
        if (lineOctaveMatcher.find()) {
            name = name.substring(0, lineOctaveMatcher.start());
            currentOctave = Integer.parseInt(lineOctaveMatcher.group());
        }else
            currentOctave = this.getDefaultOctave(stringNumber);

        boolean nameFound = false;
        int counter = 0;
        while(fret>=0){
            int idx = counter%nameList.length;
            if (counter>2* nameList.length && !nameFound)
                return null;
            if (nameFound)
                fret--;
            if (nameList[idx].equalsIgnoreCase(name))
                nameFound = true;
            if (nameFound) {
                if (idx == 0)
                    currentOctave++;
                if (fret==0) {
                    noteDetails = nameList[idx];
                    break;
                }
            }
            counter++;
        }

        return noteDetails+currentOctave;
    }

    protected int getDefaultOctave(int stringNumber) {
    	int result;
    	switch (stringNumber) {
	    	case 1: result = 4; break;
	    	case 2: result = 3; break;
	    	case 3: result = 3; break;
	    	case 4: result = 3; break;
	    	case 5: result = 2; break;
	    	case 6: result = 2; break;
	    	default: result = 0; break;
    	}
        return result;
    }

    protected String step(String noteDetails) {
        Matcher matcher = Pattern.compile("^[a-zA-Z]+").matcher(noteDetails);
        if (matcher.find())
            return matcher.group().toUpperCase();
        return "";
    }

    protected int alter(String noteDetails) {
        if (noteDetails.contains("#"))
            return 1;
        return 0;
    }

    //decide octave of note
    protected int octave(String noteDetails) {
        Matcher lineOctaveMatcher = Pattern.compile("(?<=[^0-9])[0-9]+$").matcher(noteDetails);
        lineOctaveMatcher.find();
        return Integer.parseInt(lineOctaveMatcher.group());
    }

	@Override
	public TabNote copy() {
		return new GuitarNote(this);
	}
	
    @Override
	protected void setStems(Note noteModel) {
		// Not setting stems for guitars at this point
	}

	@Override
	public models.measure.note.Note getModel() {
    	
	    models.measure.note.Note noteModel = super.getModel();
	    
	    noteModel.setPitch(new Pitch(this.step, this.alter, this.octave));

	    if (noteModel.getNotations() == null) noteModel.setNotations(new Notations());
	    Notations notations = noteModel.getNotations();
	    if (notations.getTechnical() == null) notations.setTechnical(new Technical());
	    Technical technical = notations.getTechnical();
 	    
	    technical.setString(this.stringNumber);
	    technical.setFret(this.fret);
	    notations.setTechnical(technical);
	    noteModel.setNotations(notations);
	    
	    return noteModel;
	}

	public List<ValidationError> validate() {
	    super.validate();
	    
	    for (NoteModelDecorator noteDecor : this.getDecorators().keySet()) {
	        String resp = getDecorators().get(noteDecor);
	        if (resp.equals("success")) continue;
	        Matcher matcher = Pattern.compile("(?<=^\\[)[0-9](?=\\])").matcher(resp);
	        matcher.find();
	        int priority = Integer.parseInt(matcher.group());
	        String message = resp.substring(matcher.end()+1);;
	        int startIdx = this.position;
	        int endIdx = this.position + this.text.length();
	
	        matcher = Pattern.compile("(?<=^\\[)[0-9]+,[0-9]+(?=\\])").matcher(message);
	        if (matcher.find()) {
	            String positions = matcher.group();
	            matcher = Pattern.compile("[0-9]+").matcher(positions); matcher.find();
	            startIdx = Integer.parseInt(matcher.group()); matcher.find();
	            endIdx = Integer.parseInt(matcher.group());
	            message = message.substring(matcher.end()+2);
	        }
	
	        addError(message, priority, new ArrayList<>(Collections.singleton(new Range(startIdx, endIdx))));
	        
	    }
	
	    if (this.noteDetails == null)
	        addError("this note could not be identified", 1, getRanges());

	    return errors;
	}
}
