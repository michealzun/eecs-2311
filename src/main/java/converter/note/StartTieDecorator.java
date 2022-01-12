package converter.note;

import java.util.ArrayList;
import java.util.List;

import models.measure.note.Note;
import models.measure.note.notations.Notations;
import models.measure.note.notations.Tied;

public class StartTieDecorator implements NoteModelDecorator {

	@Override
	public boolean applyTo(Note noteModel) {
            if (noteModel.getNotations() == null) noteModel.setNotations(new Notations());
    	    Notations notations = noteModel.getNotations();
            if (notations.getTieds() == null) notations.setTieds(new ArrayList<>());
            List<Tied> tieds = notations.getTieds();
            tieds.add(new Tied("start"));
            return true;
	}
}
