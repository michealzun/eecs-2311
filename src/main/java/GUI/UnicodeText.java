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
		int x = noteX + ???; //+ x of the note in the measure
		int y = noteY + ???; // + y of the note in the measure

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

		String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\r\n"
				+ "<score-partwise version=\"3.1\">\r\n" + "  <identification>\r\n"
				+ "    <creator type=\"composer\"></creator>\r\n" + "  </identification>\r\n" + "  <part-list>\r\n"
				+ "    <score-part id=\"P1\">\r\n" + "      <part-name>Guitar</part-name>\r\n" + "    </score-part>\r\n"
				+ "  </part-list>\r\n" + "  <part id=\"P1\">\r\n" + "    <measure number=\"1\">\r\n"
				+ "      <attributes>\r\n" + "        <divisions>16</divisions>\r\n" + "        <key>\r\n"
				+ "          <fifths>0</fifths>\r\n" + "        </key>\r\n" + "        <clef>\r\n"
				+ "          <sign>TAB</sign>\r\n" + "          <line>5</line>\r\n" + "        </clef>\r\n"
				+ "        <staff-details>\r\n" + "          <staff-lines>6</staff-lines>\r\n"
				+ "          <staff-tuning line=\"1\">\r\n" + "            <tuning-step>E</tuning-step>\r\n"
				+ "            <tuning-octave>2</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"2\">\r\n" + "            <tuning-step>A</tuning-step>\r\n"
				+ "            <tuning-octave>2</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"3\">\r\n" + "            <tuning-step>D</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"4\">\r\n" + "            <tuning-step>G</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"5\">\r\n" + "            <tuning-step>B</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"6\">\r\n" + "            <tuning-step>E</tuning-step>\r\n"
				+ "            <tuning-octave>4</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "        </staff-details>\r\n" + "      </attributes>\r\n" + "      <note>\r\n"
				+ "        <pitch>\r\n" + "          <step>E</step>\r\n" + "          <octave>2</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>6</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>2</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>5</string>\r\n" + "            <fret>2</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>4</string>\r\n" + "            <fret>2</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>4</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>1</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "    </measure>\r\n"
				+ "    <measure number=\"2\">\r\n" + "      <attributes>\r\n" + "        <divisions>16</divisions>\r\n"
				+ "        <key>\r\n" + "          <fifths>0</fifths>\r\n" + "        </key>\r\n"
				+ "      </attributes>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>4</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>64</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>whole</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>1</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <chord/>\r\n"
				+ "        <pitch>\r\n" + "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>64</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>whole</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <chord/>\r\n"
				+ "        <pitch>\r\n" + "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n"
				+ "          <octave>3</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>E</step>\r\n"
				+ "          <octave>3</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>4</string>\r\n" + "            <fret>2</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>B</step>\r\n"
				+ "          <octave>2</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>5</string>\r\n" + "            <fret>2</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>E</step>\r\n"
				+ "          <octave>2</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>6</string>\r\n" + "            <fret>0</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "    </measure>\r\n"
				+ "  </part>\r\n" + "</score-partwise>\r\n" + "";

		Parser parse = new Parser();
		parse.setInput(input);
		Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
		List<Measure> measures = p.measures;

		for (int i = 0; i < measures.size(); i++) {
			drawMeasure(measures.get(i).number, measures.get(i));
		}
	}

}

