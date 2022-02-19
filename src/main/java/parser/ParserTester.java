package parser;

import java.util.List;

public class ParserTester {
	static String s="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n"
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
	
	public static void main(String[] args) {

		Parser parse=new Parser();
		parse.setInput(s);	
		Part p = parse.getSheetInfo().get(0);
		Measure m = p.measures.get(0);
		Note n=m.notes.get(0);
		
		System.out.println("number of parts : " + parse.getSheetInfo().size());
		System.out.println("1st parts id: " + p.id);
		System.out.println("1st part number of measures : " + p.measures.size());
		System.out.println("1st measure number: " + p.measures.get(0).number);
		System.out.println("1st measure divisions: " + m.divisions);
		System.out.println("1st measure fifth: " + m.fifth);
		System.out.println("1st measure clefSigh: " + m.clefSigh);
		System.out.println("1st measure clefLine: " + m.clefLine);
		System.out.println("1st measure number of lines: " + m.lines.size());
		
		
		System.out.println("1st measure number of notes: " + m.notes.size());
		System.out.println("1st note step:" + n.step);
		System.out.println("1st note octave: " + n.octave);
		System.out.println("1st note duration: " + n.duration);
		System.out.println("1st note voice: " + n.voice);
		System.out.println("1st note type: " + n.type);
		System.out.println("1st note string: " + n.string);
		System.out.println("1st note fret: " + n.fret);
	}
}
