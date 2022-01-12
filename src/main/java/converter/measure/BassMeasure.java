package converter.measure;

import java.util.List;

import converter.measure_line.TabBassString;
import converter.measure_line.TabString;
import utility.AnchoredText;
import utility.Settings;

public class BassMeasure extends GuitarMeasure {

    public BassMeasure(List<AnchoredText> inputData, List<AnchoredText> inputNameData, boolean isFirstMeasure) {
        super(inputData, inputNameData, isFirstMeasure);
        MIN_LINE_COUNT = 4;
        MAX_LINE_COUNT = 4;
        tuning = Settings.getInstance().getBassTuning();
    }

	protected TabString newTabString(int stringNumber, AnchoredText data, AnchoredText name)
	{
		return new TabBassString(stringNumber, data, name);
	}
    
}
