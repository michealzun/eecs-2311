package models.measure.note.notations.technical;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Bend {
	
	@JacksonXmlProperty(isAttribute = true)
	private String shape;
	@JacksonXmlProperty(localName = "bend-alter")
	private double bendAlter;
	
	public Bend(double alter) {
		this.setBendAlter(alter);
	}
	
	public Bend(String shape, double alter) {
		this.setShape(shape);
		this.setBendAlter(alter);
	}

	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}

	public double getBendAlter() {
		return bendAlter;
	}

	public void setBendAlter(double bendAlter) {
		this.bendAlter = bendAlter;
	}


}
