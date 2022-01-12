package utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuitarUtils {
    public static String[] KEY_LIST = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static boolean isValidName(String name) {
        return getValidGuitarNames().contains(name.strip());
    }

	public static Set<String> getValidGuitarNames() {
	    String[] names = KEY_LIST;
	    HashSet<String> nameList = new HashSet<>();
	    HashSet<String> result = new HashSet<>();
	    nameList.addAll(Arrays.asList(names));
	    for (String name : names) {
	        nameList.add(name.toLowerCase());
	    }
	    for (String name : nameList) {
	    	for (int octave = 0; octave < 10 ; octave++)
	    	{
	    		result.add(name + octave);
	    	}
	    }
	    result.addAll(nameList);
	    // The guitar string can be nameless (standard tuning assumed)
	    result.add("");
	    return result;
	}
	
	public static double isGuitarLineLikelihood(String name, String line) {
	    double lineNameWeight = 0.5;  // weight attached when the line name is a guitar line name
	    double noteGroupWeight = 0.5;   // ratio of notes that are guitar notes vs {all other notes, both valid and invalid}
	
	    if (!GuitarUtils.isValidName(name))
	        return 0;
	    double score = lineNameWeight;
	    line = line.replaceAll("\s", "");
	
	    int charGroups = 0;
	    Matcher charGroupMatcher = Pattern.compile("[^-]+").matcher(line);
	    while (charGroupMatcher.find())
	        charGroups++;
	
	    int noteGroups = 0;
	    Matcher noteGroupMatcher = Pattern.compile(Patterns.GUITAR_NOTE_GROUP_PATTERN).matcher(line);
	    while (noteGroupMatcher.find()) {
	        //in-case a guitar note group has -'s inside it (e.g 5---h3 is a valid guitar note group for a hammer on,
	        // but will distort the ratio of character group to note group because one note group contains 2 character groups)
	        charGroupMatcher = Pattern.compile("[^-]+").matcher(line);
	        while(charGroupMatcher.find())
	            noteGroups++;
	    }
	    if (charGroups==0)
	        score += noteGroupWeight;
	    else
	        score += ((double) noteGroups/(double) charGroups)*noteGroupWeight;
	    return score;
	}

}
