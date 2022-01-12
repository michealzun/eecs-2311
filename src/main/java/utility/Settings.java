package utility;

import java.util.Arrays;

import converter.Instrument;
import converter.InstrumentSetting;

public class Settings {

    private Settings(){}
    private static final Settings instance = new Settings();
    public static Settings getInstance(){
        return instance;
    }
    
    public int criticalErrorCutoff = 1;
    public int errorSensitivity = 4;
    public String outputFolder = System.getProperty("user.home");
    public String inputFolder = System.getProperty("user.home");
    public int tsNum = 4;
    public int tsDen = 4;
    public String title = "";
    public String artist = "";
    
    private String[][] defaultGuitarTuning = {{"E","4"},{"B","3"},{"G","3"},{"D","3"},{"A","2"},{"E","2"}};
    private String[][] defaultBassTuning = {{"G","2"},{"D","2"},{"A","1"},{"E","1"}};
    
    private String[][] guitarTuning = defaultGuitarTuning;
    private String[][] bassTuning = defaultBassTuning;
    
    public DoubleDigitStyle ddStyle = DoubleDigitStyle.NOTE_ON_SECOND_DIGIT_STRETCH;
    
    public int guitarMeasureStartPadding = 3;
    public int drumsMeasureStartPadding = 0;
    
    private InstrumentSetting instrumentSetting = InstrumentSetting.AUTO;
    private Instrument detectedInstrument = Instrument.NONE;
    
    public Instrument getInstrument() {
    	switch (getInstrumentSetting()) {
    	case AUTO: return getDetectedInstrument();
    	case GUITAR: return Instrument.GUITAR;
    	case BASS: return Instrument.BASS;
    	case DRUMS: return Instrument.DRUMS;
    	default: return Instrument.GUITAR;
    	}
    }
    
    public void setDefaultTuning() {
    	guitarTuning = defaultGuitarTuning;
        bassTuning = defaultBassTuning;
    }

	public InstrumentSetting getInstrumentSetting() {
		return instrumentSetting;
	}

	public void setInstrumentSetting(InstrumentSetting instrumentSetting) {
		this.instrumentSetting = instrumentSetting;
		if (instrumentSetting == InstrumentSetting.AUTO) detectedInstrument = Instrument.NONE;
	}

	private Instrument getDetectedInstrument() {
		return detectedInstrument;
	}

	public void setDetectedInstrument(Instrument detectedInstrument) {
		this.detectedInstrument = detectedInstrument;
	}

	public String[][] getGuitarTuning() {
		return Arrays.copyOf(guitarTuning, guitarTuning.length);
	}

	public void setGuitarTuning(int string, String pitch, int octave) {
		guitarTuning[string][0] = pitch;
		guitarTuning[string][1] = octave + "";
	}
	
	public void setGuitarTuning(int string, String pitch) {
		guitarTuning[string][0] = pitch;
	}

	public String[][] getBassTuning() {
		return Arrays.copyOf(bassTuning, bassTuning.length);
	}

	public void setBassTuning(int string, String pitch, int octave) {
		bassTuning[string][0] = pitch;
		bassTuning[string][1] = octave + "";
	}
	
	public void setBassTuning(int string, String pitch) {
		bassTuning[string][0] = pitch;
	}
	
}