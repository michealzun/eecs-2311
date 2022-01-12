package models.measure.note.notations;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Tied {
	@JacksonXmlProperty(isAttribute = true)
	private String type;

	public Tied(String type) {
        this.type = type;
    }
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
