package utility;

public class AnchoredText {

	public String text;
	public int positionInScore;
	public int positionInLine;
	
	public AnchoredText(String s, int pIS, int pIL)
	{
		text = s;
		positionInScore = pIS;
		positionInLine = pIL;
	}
}
