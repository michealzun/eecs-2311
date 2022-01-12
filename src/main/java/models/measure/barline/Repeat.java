package models.measure.barline;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Repeat {
    @JacksonXmlProperty(isAttribute = true)
    private String direction;

    @JacksonXmlProperty(isAttribute = true)
    private String winged;

    @JacksonXmlProperty(isAttribute = true)
	private String times;

    public String getDirection() {
        return direction;
    }

    public String getWinged() {
        return winged;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setWinged(String winged) {
        this.winged = winged;
    }

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
}
