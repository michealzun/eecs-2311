package converter.measure_line;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import converter.ScoreComponent;
import converter.note.NoteFactory;
import converter.note.TabNote;
import utility.AnchoredText;
import utility.Range;
import utility.Settings;
import utility.ValidationError;

public abstract class TabString extends ScoreComponent {
    public String line;
    public String name;
    int namePosition;
    int position;
    public List<TabNote> noteList;
    public int padding;

    protected TabString(int stringNumber, AnchoredText dataAT, AnchoredText nameAT) {
        this.line = dataAT.text;
        this.position = dataAT.positionInScore;
        this.name = nameAT.text;
        this.namePosition = nameAT.positionInScore;
        padding = line.length();
        Matcher paddingMatcher = Pattern.compile("[^-]").matcher(line);
        if (paddingMatcher.find()) padding = paddingMatcher.start();
        this.noteList = this.createNoteList(stringNumber, this.line, position);
    }

    public List<TabNote> getNoteList() {
        List<TabNote> noteList = new ArrayList<>();
        for (ValidationError error : this.validate()) {
            if (error.getPriority() <= Settings.getInstance().criticalErrorCutoff) {
                return noteList;
            }
        }
        for (TabNote note : this.noteList) {
            List<ValidationError> errors = note.validate();
            boolean criticalError = false;
            for (ValidationError error : errors) {
                if (error.getPriority() <= Settings.getInstance().criticalErrorCutoff) {
                    criticalError = true;
                    break;
                }
            }
            if (!criticalError)
                noteList.add(note);
        }
        return noteList;
    }

    
	/**
	 * Generates a list of notes of any type (guitar, bass, drums)
	 * @param stringNumber
	 * @param line
	 * @param position
	 * @return the list of Note objects
	 */
	protected List<TabNote> createNoteList(int stringNumber, String line, int position) {
		NoteFactory nf = createNoteFactory();
		List<TabNote> noteList = new ArrayList<>();
		Matcher noteMatcher = Pattern.compile(TabNote.PATTERN).matcher(line);
		while (noteMatcher.find()) {
			String match = noteMatcher.group();
			String leadingStr = line.substring(0, noteMatcher.start()).replaceAll("\s", "");
			int distanceFromMeasureStart = leadingStr.length();
			if (!match.isBlank()) {
				noteList.addAll(nf.getNotes(stringNumber, match, position + noteMatcher.start(), this.name, distanceFromMeasureStart));
			}
		}
		return noteList;
	}
	
	protected abstract NoteFactory createNoteFactory();

	@Override
	public List<Range> getRanges() {
		List<Range> ranges = new ArrayList<>();
		ranges.add(new Range(position,position+line.length()));
		return null;
	}
	
	/**
	 * Provides a warning for whitespace in the tab
	 * @return a List<ValidationError> for all locations that contain whitespaces
	 */
	public List<ValidationError> validate() {
	
	    if (this.line.length()-this.line.replaceAll("\s", "").length() != 0) {
	        addError(
	                "Adding whitespace might result in different timing than you expect.",
	                3,
	                getRanges());
	        
	    }
	
	    return errors;
	}

}
