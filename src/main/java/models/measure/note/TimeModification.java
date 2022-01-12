package models.measure.note;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "time-modification")
@JsonPropertyOrder({"actual-notes", "normal-notes"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TimeModification {

	public TimeModification(int an, int nn) {
        this.actualNotes = an;
        this .normalNotes = nn;
    }
	
	@JacksonXmlProperty(localName = "actual-notes")
	private int actualNotes;
	@JacksonXmlProperty(localName = "normal-notes")
	private int normalNotes;
	
	public int getActualNotes() {
		return actualNotes;
	}
	
	public void setActualNotes(int actualNotes) {
		this.actualNotes = actualNotes;
	}
	
	public int getNormalNotes() {
		return normalNotes;
	}
	
	public void setNormalNotes(int normalNotes) {
		this.normalNotes = normalNotes;
	}
	
	
}
