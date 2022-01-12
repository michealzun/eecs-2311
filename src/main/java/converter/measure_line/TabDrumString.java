package converter.measure_line;

import java.util.List;

import converter.note.DrumNoteFactory;
import converter.note.NoteFactory;
import converter.note.TabNote;
import utility.AnchoredText;
import utility.DrumUtils;
import utility.Settings;
import utility.ValidationError;

public class TabDrumString extends TabString {

    public TabDrumString(int stringNumber, AnchoredText dataAT, AnchoredText nameAT) {
        super(stringNumber, dataAT, nameAT);    
    }
    
	@Override
	protected NoteFactory createNoteFactory() {
		return new DrumNoteFactory();
	}

    public List<ValidationError> validate() {
        
        if (!DrumUtils.getNickNameSet().contains(this.name.strip())) {
            addError("This drum piece is not recognized. Update Settings -> Current Song Settings to include it", 1, getRanges());
        }

        for (ValidationError error : errors) {
            if (error.getPriority() <= Settings.getInstance().criticalErrorCutoff) {
                return errors;
            }
        }

        for (TabNote note : this.noteList)
            errors.addAll(note.validate());

        return errors;
    }
}
