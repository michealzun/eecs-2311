package converter.note;

import java.util.List;

import models.measure.note.Note;
import models.measure.note.Rest;
import utility.ValidationError;

public class RestNote extends TabNote {

    public RestNote(int stringNumber, String origin, int position, String lineName, int distanceFromMeasureStart) {
        super(stringNumber, origin, position, lineName, distanceFromMeasureStart);
    }

    public RestNote(RestNote n) {
    	super(n);
    }

	@Override
	public TabNote copy() {
		return new RestNote(this);
	}
	
    @Override
	protected void setStems(Note noteModel) {
		// Not stems for rests
	}

	@Override
	public models.measure.note.Note getModel() {
	    models.measure.note.Note noteModel = super.getModel();
	    noteModel.setRest(new Rest());
	    return noteModel;
	}

	public List<ValidationError> validate() {
	    super.validate();
	    return errors;
	}
}
