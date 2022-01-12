package models.part_list;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MIDIInstrument {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "midi-channel")
    private int midiChannel = 10;

    @JacksonXmlProperty(localName = "midi-program")
    private int midiProgram = 1;
    
    @JacksonXmlProperty(localName = "midi-unpitched")
    private int midiUnpitched;
    
    @JacksonXmlProperty(localName = "volume")
    private double volume = 78.7402;
    
    @JacksonXmlProperty(localName = "pan")
    private int pan = 0;
    
    public MIDIInstrument(String id, int midiUnpitched) {
        this.id = id;
        this.midiUnpitched = midiUnpitched;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getmidiUnpitched() {
        return midiUnpitched;
    }

    public void setmidiUnpitched(int midiUnpitched) {
        this.midiUnpitched = midiUnpitched;
    }
}
