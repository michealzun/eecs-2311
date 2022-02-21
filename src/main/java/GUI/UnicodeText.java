package GUI;

import java.awt.Font;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JFrame;

import parser.Measure;
import parser.Note;
import parser.Parser;
import parser.Part;


public class UnicodeText {

	static int measureBarLength = 32;
	static int measureWidth=400;
	static int measureSpacing=100;
	static int measuresPerLine=4;

	static void drawMeasure(int measureNumber, Measure m) {

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

		for(int i = 0; i < measureBarLength; i++) {
			drawString(measureType, 50 + measureWidth * (measureNumber % measuresPerLine) + i * 30, 40 + measureSpacing * (measureNumber / measuresPerLine));
		}

		for (int i = 0; i < m.notes.size(); i++)  {
			drawNote(50 + measureWidth * (measureNumber % measuresPerLine), 40 + measureSpacing * (measureNumber / measuresPerLine), m.notes.get(i));
		}
	}

	static void drawNote(int noteX, int noteY, Note n) {
		// Note Location
		int x = noteX + n.duration; 
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
		JFrame f = new JFrame() {
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				Font font = new Font("Bravura", Font.PLAIN, 32);

				g2.setFont(font);
				g2.drawString(noteType, x, y);

			}
		};
		f.setSize(200,200);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		String input = "";

		Parser parse = new Parser();
		parse.setInput(input);
		Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
		List<Measure> measures = p.measures;

		for (int i = 0; i < measures.size(); i++) {
			drawMeasure(measures.get(i).number, measures.get(i));
		}
	}

}

