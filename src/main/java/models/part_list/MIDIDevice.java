package models.part_list;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MIDIDevice {

	 @JacksonXmlProperty(isAttribute = true)
	    private int port = 1;

}
