package parser;

import java.util.ArrayList;
import java.util.List;

public class Measure {
	public int number;
	public int divisions;
	public int fifth;
	public String clefSigh;
	public int clefLine;
	public List<Line> lines=new ArrayList<Line>();
	public List<Note> notes=new ArrayList<Note>();
	
}
