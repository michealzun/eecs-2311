package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrumUtils {
	
	public static HashMap<DrumPiece, DrumPieceInfo> drumSet = new HashMap<>();
	private static HashMap<String, DrumPiece> drumNickNames = new HashMap<>();;
	
public static void createDrumSet() {
		
	    // The nicknames are probably not needed
		List<String> kickNames = new ArrayList<String>(Arrays.asList("B","BD"));
		DrumPieceInfo kick = new DrumPieceInfo(kickNames, "P1-I36", "Bass Drum 1", "F", 4);
		
		List<String> snareNames = new ArrayList<String>(Arrays.asList("S","SD"));
		DrumPieceInfo snare = new DrumPieceInfo(snareNames, "P1-I39", "Snare", "C", 5);
		
		List<String> closedhihatNames = new ArrayList<String>(Arrays.asList("HH","H"));
		DrumPieceInfo closedHiHat = new DrumPieceInfo(closedhihatNames, "P1-I43", "Closed Hi-Hat", "G", 5);
		
		List<String> openHiHatNames = new ArrayList<String>(Arrays.asList("HH","H"));
		DrumPieceInfo openHiHat = new DrumPieceInfo(openHiHatNames, "P1-I47", "Open Hi-Hat", "E", 5);
		
		List<String> rideNames = new ArrayList<String>(Arrays.asList("R","RD"));
		DrumPieceInfo ride = new DrumPieceInfo(rideNames, "P1-I52", "Ride Cymbal 1", "F", 5);
		
		List<String> rideBellNames = new ArrayList<String>(Arrays.asList("R","RD"));
		DrumPieceInfo rideBell = new DrumPieceInfo(rideBellNames, "P1-I54", "Ride Bell", "F", 5);
		
		List<String> crashNames = new ArrayList<String>(Arrays.asList("C","CC"));
		DrumPieceInfo crash = new DrumPieceInfo(crashNames, "P1-I50", "Crash Cymbal 1", "A", 5);
		
		List<String> chinaNames = new ArrayList<String>(Arrays.asList("CH"));
		DrumPieceInfo china = new DrumPieceInfo(chinaNames, "P1-I53", "Chinese Cymbal 1", "C", 6);
		
		List<String> highTomNames = new ArrayList<String>(Arrays.asList("T","HT"));
		DrumPieceInfo highTom = new DrumPieceInfo(highTomNames, "P1-I48", "Low-Mid Tom", "E", 5);
		
		List<String> midTomNames = new ArrayList<String>(Arrays.asList("t","MT"));
		DrumPieceInfo midTom = new DrumPieceInfo(midTomNames, "P1-I46", "Low Tom", "D", 5);
		
		List<String> floorTomNames = new ArrayList<String>(Arrays.asList("F","FT"));
		DrumPieceInfo floorTom = new DrumPieceInfo(floorTomNames, "P1-I44", "High Floor Tom", "A", 4);
		
		List<String> lowFloorTomNames = new ArrayList<String>(Arrays.asList("f","ft"));
		DrumPieceInfo lowFloorTom = new DrumPieceInfo(lowFloorTomNames, "P1-I42", "Low Floor Tom", "G", 4);
		
		List<String> pedalHiHatNames = new ArrayList<String>(Arrays.asList("PH","Hf"));
		DrumPieceInfo pedalHiHat = new DrumPieceInfo(pedalHiHatNames, "P1-I45", "Pedal Hi-Hat", "D", 4);
		
		drumSet.put(DrumPiece.Bass_Drum_1, kick);
		drumSet.put(DrumPiece.Snare, snare);
		drumSet.put(DrumPiece.Open_Hi_Hat, openHiHat);
		drumSet.put(DrumPiece.Closed_Hi_Hat, closedHiHat);
		drumSet.put(DrumPiece.Ride_Cymbal_1, ride);
		drumSet.put(DrumPiece.Ride_Bell, rideBell);
		drumSet.put(DrumPiece.Crash_Cymbal_1, crash);
		drumSet.put(DrumPiece.Chinese_Cymbal, china);
		drumSet.put(DrumPiece.Low_Mid_Tom, highTom);
		drumSet.put(DrumPiece.Low_Tom, midTom);
		drumSet.put(DrumPiece.High_Floor_Tom, floorTom);
		drumSet.put(DrumPiece.Low_Floor_Tom, lowFloorTom);
		drumSet.put(DrumPiece.Pedal_Hi_Hat, pedalHiHat);
	}

	public static void createDrumNickNames() {
		
		List<String> kickNames = new ArrayList<String>(Arrays.asList("B","BD"));
		List<String> snareNames = new ArrayList<String>(Arrays.asList("S","SD"));
		List<String> hihatNames = new ArrayList<String>(Arrays.asList("H","HH"));
		List<String> rideNames = new ArrayList<String>(Arrays.asList("R","RD","RC"));
		List<String> chinaNames = new ArrayList<String>(Arrays.asList("CH"));
		List<String> crashNames = new ArrayList<String>(Arrays.asList("C","CC"));
		List<String> highTomNames = new ArrayList<String>(Arrays.asList("T","HT"));
		List<String> midTomNames = new ArrayList<String>(Arrays.asList("t","MT"));
		List<String> floorTomNames = new ArrayList<String>(Arrays.asList("F","FT"));
		List<String> lowFloorTomNames = new ArrayList<String>(Arrays.asList("f","ft"));
		List<String> pedalHiHatNames = new ArrayList<String>(Arrays.asList("PH","Hf"));

		for (String s: kickNames) drumNickNames.put(s, DrumPiece.Bass_Drum_1);
		for (String s: snareNames) drumNickNames.put(s, DrumPiece.Snare);
		for (String s: hihatNames) drumNickNames.put(s, DrumPiece.Closed_Hi_Hat);
		for (String s: rideNames) drumNickNames.put(s, DrumPiece.Ride_Cymbal_1);
		for (String s: chinaNames) drumNickNames.put(s, DrumPiece.Chinese_Cymbal);
		for (String s: crashNames) drumNickNames.put(s, DrumPiece.Crash_Cymbal_1);
		for (String s: highTomNames) drumNickNames.put(s, DrumPiece.Low_Mid_Tom);
		for (String s: midTomNames) drumNickNames.put(s, DrumPiece.Low_Tom);
		for (String s: floorTomNames) drumNickNames.put(s, DrumPiece.High_Floor_Tom);
		for (String s: lowFloorTomNames) drumNickNames.put(s, DrumPiece.Low_Floor_Tom);
		for (String s: pedalHiHatNames) drumNickNames.put(s, DrumPiece.Pedal_Hi_Hat);
		
	}
	
	public static DrumPiece getDrumPiece(String nickName, String hit)
	{
		DrumPiece result =  DrumUtils.drumNickNames.get(nickName);
		if ((hit.equals("o")) && (result == DrumPiece.Closed_Hi_Hat)) 
			result = DrumPiece.Open_Hi_Hat;
		if ((hit.equals("b")) && (result == DrumPiece.Ride_Cymbal_1)) 
			result = DrumPiece.Ride_Bell;
		return result;
	}
	
    public static Set<String> getNickNameSet() {
        return drumNickNames.keySet();
    }
    
    public static double isDrumLineLikelihood(String name, String line) {
	    double lineNameWeight = 0.5;  // weight attached when the line name is a drum line name
	    double noteGroupWeight = 0.5;   // ratio of notes that are drum notes vs {all other notes, both valid and invalid}
	
	    if (!getNickNameSet().contains(name.strip()))
	        return 0;
	    double score = lineNameWeight;
	    line = line.replaceAll("\s", "");
	
	    int charGroups = 0;
	    Matcher charGroupMatcher = Pattern.compile("[^-]+").matcher(line);
	    while (charGroupMatcher.find())
	        charGroups++;
	
	    int noteGroups = 0;
	    Matcher noteGroupMatcher = Pattern.compile(Patterns.DRUM_NOTE_GROUP_PATTERN).matcher(line);
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
