package GUI;

import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import parser.Measure;
import parser.Note;
import parser.Parser;
import parser.Part;


public class UnicodeText {
	Parser parse = new Parser();
	static int measureBarLength = 8;
	static int measureWidth=200;
	static int measureSpacing=100;
	static int measuresPerLine=4;
	static int fontSize=47;
	static JFrame f;
	
	public UnicodeText(String input) {
		parse.setInput(input);
        f = new JFrame();
        f.setFont(new Font("Bravura", Font.PLAIN, fontSize));
    f.setSize(1000,1000);
    f.setVisible(true);
	}
	
	public void run() {
		Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
		List<Measure> measures = p.measures;

		for (int i = 0; i < measures.size(); i++) {
			drawMeasure(measures.get(i));
		}
	}
	
	
	static void drawMeasure( Measure m) {
		String measureType="";
		switch(m.lines.size()) {
		case 1:
			measureType="\uD834\uDD16";
			break;
		case 2:
			measureType="\uD834\uDD17";
			break;
		case 3:
			measureType="\uD834\uDD18";
			break;
		case 4:
			measureType="\uD834\uDD19";
			break;
		case 5:
			measureType="\uD834\uDD1A";
			break;
		case 6:
			measureType="\uD834\uDD1B";
			break;
		}
		int measureStartingX = 100 + measureWidth * ((m.number-1) % measuresPerLine);
		int measureStartingY = 100 + measureSpacing * ((m.number-1) / measuresPerLine);
		

		for(int i = 0; i < measureBarLength; i++) {
			System.out.println(measureStartingX + i * 50 +7);
			drawString(measureType, measureStartingX + i * 23 +7, measureStartingY);
		}
		//measure seps
		if((m.number-1) % measuresPerLine==0) {
			drawString("\uD834\uDD00",measureStartingX, measureStartingY-10);
			drawString("\uD834\uDD00",measureStartingX, measureStartingY+10);
		}
		drawString("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY-10);
		drawString("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY+10);
		

		
		for (int i = 0; i < m.notes.size(); i++)  {
			drawNote(measureStartingX, measureStartingY, m.notes.get(i));
		}
	}

	static void drawNote(int noteX, int noteY, Note n) {
		// Note Location
		int x = noteX; 
		int y = noteY; 

		//WIP

		// Note Type
		String noteType="";
		switch(n.type) {
		case "whole":
			noteType="\uD834\uDD5D";
			break;
		case "half":
			noteType="\uD834\uDD5E";
			break;
		case "quarter":
			noteType="\uD834\uDD5F";
			break;
		case "eighth":
			noteType="\uD834\uDD60";
			break;
		case "sixteenth":
			noteType="\uD834\uDD61";
			break;
		case "thirtysixth":
			noteType="\uD834\uDD62";
			break;
		}
		drawString(noteType, x, y);
	}

	private static void drawString(String noteType, int x, int y) {
		f.getGraphics().drawString(noteType, x, y);
	}



}

