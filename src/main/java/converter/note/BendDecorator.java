package converter.note;

import models.measure.note.Note;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Bend;
import models.measure.note.notations.technical.Technical;

public class BendDecorator implements NoteModelDecorator {
	
	protected int width;
	
	public BendDecorator(int width) {
		this.width = width;
	}

	@Override
	public boolean applyTo(Note noteModel) {
		if (noteModel.getNotations() == null) noteModel.setNotations(new Notations());
	    Notations notations = noteModel.getNotations();
	    if (notations.getTechnical() == null) notations.setTechnical(new Technical());
	    Technical technical = notations.getTechnical();
	    Bend bend = new Bend(width);
	    technical.setBend(bend);
		return true;
	}

}
