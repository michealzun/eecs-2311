package models.measure.note;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Notehead {
    @JacksonXmlProperty(isAttribute = true)
    String parentheses;
    
    @JacksonXmlText
    String type;
    
    public Notehead(String t) {
        this.type = t;
    }

    public String getParentheses() {
        return parentheses;
    }

    public void setParentheses(String parentheses) {
        this.parentheses = parentheses;
    }
}
