package converter.measure_line;

import converter.note.BassNoteFactory;
import converter.note.NoteFactory;
import utility.AnchoredText;

public class TabBassString extends TabGuitarString{
    public TabBassString(int stringNumber, AnchoredText dataAT, AnchoredText nameAT) {
        super(stringNumber, dataAT, nameAT);
    }

	@Override
	protected NoteFactory createNoteFactory() {
		return new BassNoteFactory();
	}
}
