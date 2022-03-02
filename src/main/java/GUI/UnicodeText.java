package GUI;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import parser.Measure;
import parser.Note;
import parser.Parser;
import parser.Part;

public class UnicodeText extends Pane {
	private Parser parse = new Parser();
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int measureBarLength = 8;
	private int measureWidth = 200;
	private int measureSpacing = 100;
	private int measuresPerLine = 4;
	private int fontSize = 50;

	public UnicodeText(String input) {
		// Parsed XML string
		parse.setInput(input);
		// Create the Canvas
		canvas = new Canvas(600, 600);
	    // Set the width of the Canvas
	    canvas.setWidth(600);
	    // Set the height of the Canvas
	    canvas.setHeight(600);

	    // Get the graphics context of the canvas
	    gc = canvas.getGraphicsContext2D();
	    gc.setFont(new Font("Bravura", fontSize));
	   
	    // Draw Text
		Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
		determineMode(p);
		
		
	    // Set the Style-properties of the Pane
	    this.setStyle("-fx-padding: 10;" +
	            "-fx-border-style: solid inside;" +
	            "-fx-border-width: 2;" +
	            "-fx-border-insets: 5;" +
	            "-fx-border-radius: 5;" +
	            "-fx-border-color: white;" +
	            "-fx-background-color: white");
	    
	    // Add the Canvas to the Pane
	    this.getChildren().add(canvas); 
	}
	
	
	private void determineMode(Part p) {
		//always default to five line staff for now
		switch(p.id) {
			default: 
				List<Measure> measures = p.measures;
				for (int i = 0; i < measures.size(); i++) {
					drawMeasureStandard(measures.get(i));
				}
				break;
				
		}
	}

	private void drawMeasureStandard(Measure m) {
		/*
		String measureType = "";
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
			
			break;
		case 6:
			measureType="\uD834\uDD1B";
			break;
		}
		*/
		
		
		String measureType="\uD834\uDD1A";
		int measureStartingX = 100 + measureWidth * ((m.number-1) % measuresPerLine);
		int measureStartingY = 100 + measureSpacing * ((m.number-1) / measuresPerLine);
		
		//draw treble clef
		if(m.number==1) {
			 gc.fillText("\uD83C\uDFBC", measureStartingX-45 , measureStartingY+2);
		}
		
		//draw line for each measure
		for(int i = 0; i < measureBarLength; i++) {
			 gc.fillText(measureType, measureStartingX + i * 23 +7, measureStartingY);
		}
		// draw measure separation line
		if((m.number-1) % measuresPerLine == 0) {
			 gc.fillText("\uD834\uDD00",measureStartingX, measureStartingY-10);
			 gc.fillText("\uD834\uDD00",measureStartingX, measureStartingY+10);
		}
		 gc.fillText("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY-10);
		 gc.fillText("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY+10);
		
		 
		 //draw each note
		 int xDisplacement=0;
		 
		for (int i = 0; i < m.notes.size(); i++)  {
			drawNoteStandard(measureStartingX+xDisplacement, measureStartingY, m.notes.get(i));
			xDisplacement+=(m.notes.get(i).duration/m.divisions*measureWidth);
		}
	}

	private void drawNoteStandard(int x, int y, Note n) {
		// Note Location
		int yDisplacement=n.octave*7;
		switch(n.step){
			case "B":
				yDisplacement+=1;
				break;
			case "C":
				yDisplacement+=2;
				break;
			case "D":
				yDisplacement+=3;
				break;
			case "E":
				yDisplacement+=4;
				break;
			case "F":
				yDisplacement+=5;
				break;
			case "G":
				yDisplacement+=6;
				break;
			default:
				break;
		}
		y+=yDisplacement*5;
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

		 gc.fillText(noteType, x, y);
	}

}

