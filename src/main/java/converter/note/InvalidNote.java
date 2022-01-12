package converter.note;

import java.util.List;

import models.measure.note.Note;
import utility.ValidationError;

public class InvalidNote extends TabNote {

    public InvalidNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart) {
        super(stringNumber, origin, position, lineName, distanceFromMeasureStart);
    }

    public InvalidNote(InvalidNote n) {
        super(n);
    }
    
    @Override
	public TabNote copy() {
		return new InvalidNote(this);
	}

	@Override
    public models.measure.note.Note getModel() {
        return null;
    }

    public List<ValidationError> validate() {

        addError("Unrecognized text, will be ignored.", 3, getRanges());
        return errors;
    }

	@Override
	protected void setStems(Note noteModel) {
		// No stems for invalid notes
	}
}
