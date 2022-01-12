package models.measure.note.notations.technical;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Tremolo {
 
    @JacksonXmlProperty(isAttribute = true)
	private String type;

    @JacksonXmlText
    int number;

    public Tremolo(int n) {
        this.number = n;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
