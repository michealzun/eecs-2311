package utility;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Patterns {
    public static final String SPACEORTAB = "[^\\S\\n\\r]";
    //public static final String SPACEORTAB = "[ \t]";
    //public static final String COMMENT = "^[^\\S\\n\\r]*#.+(?=\\n)";
    public static final String DIVIDER_COMPONENTS = "|{}";
    public static final String DIVIDER = "["+DIVIDER_COMPONENTS+"]";

	public static final String FRET = "((?<=^|[^0-9])[0-9]{1,2}(?=$|[^0-9]))";
    public static final String GRACE = "(g"+ FRET +"([hps]"+ FRET +")+)";
    public static final String HARMONIC = "\\["+ FRET +"\\]";

	public static final String GUITAR_NOTE_PATTERN = "("+GRACE+"|"+HARMONIC+"|"+ FRET +"|R|T)";
    public static final String GUITAR_NOTE_CONNECTOR = "[hpbsHPBS\\/\\\\]";
    public static final String GUITAR_NOTE_GROUP_PATTERN = "("+GUITAR_NOTE_PATTERN+"(-*"+GUITAR_NOTE_CONNECTOR+ "-*" +GUITAR_NOTE_PATTERN+")*)";
    
    public static final String DRUM_NOTE_PATTERN = "[xXoOdDfbgFRT#]";
	public static final String DRUM_NOTE_GROUP_PATTERN = DRUM_NOTE_PATTERN +"+";
    public static final String DRUM_NOTE_CONNECTOR = "$a"; //there are no connectors, so this is a regex that never matches anything. an a after the end of the string
    
    // e------------ or |e---------------- or |e|-------------------- when it is the first measure of the measure group (start of line, SOL)
    public static String START_OF_LINE = "(" + startOfLinePattern() + insidesPattern() + ")";

    //|--------------------- when it is in between other measures
    public static String MIDDLE_OF_LINE = "("+Patterns.DIVIDER+"+" + insidesPattern()+")";

    private static String INSIDES_PATTERN_SPECIAL_CASE = "$a"; // doesn't match anything
    
    private static String nonDashDividerWhitespace() {
    	//return "(?:" + NoteFactory.GUITAR_NOTE_PATTERN + "|" + NoteFactory.GUITAR_NOTE_CONNECTOR + "|" + NoteFactory.DRUM_NOTE_PATTERN + ")";
        return "[^-\\n" + DIVIDER_COMPONENTS + SPACEORTAB +"+]"; // Added + to exclude drum tab timing
    }

    /**
     * A very general, very vague "inside a tab row line" pattern. We want to be as general and vague as possible so that
     * we delay catching erroneous user input until we are able to pinpoint where the error is exactly. e.g. if this
     * pattern directly detects a wrong note here, a Note object will never be created. It will just tell the user the
     * measure line where the error is, not the precise note which caused the error.
     * This regex pattern verifies if it is surrounded by |'s or a measure line name and captures a max of one | at each end
     * only if it is surrounded by more than one | (i.e ||------| extracts |------ and ||------||| extracts |------|, but |------| extracts ------)
     * @return the bracket-enclosed String regex pattern.
     */
    public static String insidesPattern() {
    	StringBuilder pattern = new StringBuilder();
    	pattern.append("("+INSIDES_PATTERN_SPECIAL_CASE);
    	pattern.append("|"+INSIDES_PATTERN_SPECIAL_CASE);
    	pattern.append("|");
    	
    	// Positive lookbehind
    	pattern.append("(?<=");
    			pattern.append("(([ \\r\\n]|^)" + actualName() + ")");   // actualName can be empty string
    			pattern.append("(?=[ -][^" + DIVIDER_COMPONENTS + "])");
    		pattern.append("|");
    			pattern.append(DIVIDER);
    	pattern.append(")");
    	
    	// Actual expression to match
    	pattern.append(DIVIDER + "?");
    	pattern.append("(");
    		pattern.append("(" + SPACEORTAB + "{0,2}[-*]+)");
    			pattern.append("|");
    		pattern.append("(" + SPACEORTAB + "{0,2}"+ nonDashDividerWhitespace() + "+" + SPACEORTAB + "{0,2}-*)");
    		pattern.append(")");
    	pattern.append("(" + nonDashDividerWhitespace() + "+-*)*");  // matches multiple notes separated by dashes
    	pattern.append("(" + nonDashDividerWhitespace() + "+ *)?");  // optional note(s) at the end + optional spaces
    	pattern.append("(" + DIVIDER + "?" + "(?=" + DIVIDER + "))");
    	
    	pattern.append(")");
        return pattern.toString();
    }

    //Matches the name and possible divider(s) after it
    private static String startOfLinePattern() {
        StringBuilder pattern = new StringBuilder();
        pattern.append("(");
        pattern.append(SPACEORTAB + "*" + DIVIDER + "*" + SPACEORTAB + "*");
        pattern.append(actualName());
        pattern.append(SPACEORTAB + "*");
        pattern.append("((?=-)|(?:" + DIVIDER + "+))");
        pattern.append(")");
        return pattern.toString();
    }
    
    public static String measureNameExtractPattern() {
        StringBuilder pattern = new StringBuilder();
        pattern.append("(?<=^" + DIVIDER + "*" + ")");
        pattern.append(SPACEORTAB + "*");
        pattern.append(actualName());
        pattern.append(SPACEORTAB + "*");
        pattern.append("(?=-|" + DIVIDER + ")");  // what's ahead is a dash or a divider
        return pattern.toString();
    }
    
    private static String actualName() {
        Iterator<String> measureLineNames = getValidNames().iterator();
        StringBuilder pattern = new StringBuilder();
        pattern.append("(?:"+measureLineNames.next());
        while(measureLineNames.hasNext()) {
            pattern.append("|"+measureLineNames.next());
        }
        pattern.append(")");
        return pattern.toString();
    }
    
    private static Set<String> getValidNames() {
        HashSet<String> nameSet = new HashSet<>();
        nameSet.addAll(GuitarUtils.getValidGuitarNames());
        nameSet.addAll(DrumUtils.getNickNameSet());
        return nameSet;
    }
}
