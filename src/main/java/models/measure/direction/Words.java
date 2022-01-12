package models.measure.direction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Words {
    @JacksonXmlProperty(isAttribute = true, localName = "relative-x")
    double relativeX;

    @JacksonXmlProperty(isAttribute = true, localName = "relative-y")
    double relativeY;

    @JacksonXmlText
    String repeatText;

    public double getRelativeX() {
        return relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public String getRepeatText() {
        return repeatText;
    }

    public void setRelativeX(double relativeX) {
        this.relativeX = relativeX;
    }

    public void setRelativeY(double relativeY) {
        this.relativeY = relativeY;
    }

    public void setRepeatText(String repeatText) {
        this.repeatText = repeatText;
    }
}
