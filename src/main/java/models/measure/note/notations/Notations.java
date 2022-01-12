package models.measure.note.notations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import models.measure.note.notations.technical.Ornaments;
import models.measure.note.notations.technical.Technical;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"tied", "slur", "slide", "technical"})
public class Notations {
    Technical technical;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "slur")
    List<Slur> slurs;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "slide")
    List<Slide> slides;
    
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "tied")
    private List<Tied> tieds;
    
    private Ornaments ornaments;

    public List<Slur> getSlurs() {
        return slurs;
    }

    public Technical getTechnical() {
        return technical;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlurs(List<Slur> slurs) {
        this.slurs = slurs;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

	public Ornaments getOrnaments() {
		return ornaments;
	}

	public void setOrnaments(Ornaments ornaments) {
		this.ornaments = ornaments;
	}

	public List<Tied> getTieds() {
		return tieds;
	}

	public void setTieds(List<Tied> tieds) {
		this.tieds = tieds;
	}
}
