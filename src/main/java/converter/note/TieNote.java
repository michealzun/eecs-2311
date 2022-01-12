package converter.note;

import java.util.List;

import models.measure.note.Note;
import utility.ValidationError;

public class TieNote extends TabNote {

    public TieNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart) {
        super(stringNumber, origin, position, lineName, distanceFromMeasureStart);
    }

    public TieNote(TieNote n) {
    	super(n);
    }

	@Override
	public TabNote copy() {
		return new TieNote(this);
	}
	
    @Override
	protected void setStems(Note noteModel) {
		// Not stems for fake notes
	}

	@Override
	public models.measure.note.Note getModel() {
	    models.measure.note.Note noteModel = super.getModel();
	    return noteModel;
	}

	public List<ValidationError> validate() {
	    super.validate();
	    return errors;
	}
}
