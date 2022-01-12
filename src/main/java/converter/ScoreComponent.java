package converter;

import utility.AnchoredText;
import utility.Range;
import utility.Settings;
import utility.ValidationError;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreComponent {
	
	public AnchoredText at;
	public abstract List<ValidationError> validate();

	public List<ValidationError> errors = new ArrayList<>();

	public void addError(String message, int priority, List<Range> rangeList) {
		ValidationError error = new ValidationError(message, priority, rangeList);

		if (Settings.getInstance().errorSensitivity >= error.getPriority())
			errors.add(error);
	}
	
	public abstract List<Range> getRanges();
}
