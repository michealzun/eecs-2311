package converter.note;

import java.util.ArrayList;
import java.util.List;

import models.measure.note.Note;
import models.measure.note.Notehead;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Ornaments;
import models.measure.note.notations.technical.Tremolo;
import utility.AnchoredText;
import utility.Patterns;

public class DrumNoteFactory extends NoteFactory {

	public DrumNoteFactory() {
		notePattern = Patterns.DRUM_NOTE_PATTERN;
		noteGroupPattern = Patterns.DRUM_NOTE_GROUP_PATTERN;
		connectorPattern = Patterns.DRUM_NOTE_CONNECTOR;
	}

	@Override
	protected AnchoredText createConnector(List<TabNote> noteList, int idx, int endIdx, int endNote) {
			AnchoredText connectorAT = new AnchoredText("", 0, 0);
			// No connectors for drum notes
			return connectorAT;
		}

	@Override
	protected List<TabNote> createNote(String origin, int position, int distanceFromMeasureStart) {
		List<TabNote> noteList = super.createNote(origin, position, distanceFromMeasureStart);
		if (noteList.isEmpty()) {
			if (origin.strip().equalsIgnoreCase("x") || origin.strip().equalsIgnoreCase("o")
					|| origin.strip().equalsIgnoreCase("#") || origin.strip().equalsIgnoreCase("b"))
				noteList.add(new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart));
			else if (origin.strip().equalsIgnoreCase("f"))
				noteList.addAll(createFlam(origin, position, distanceFromMeasureStart));
			else if (origin.strip().equalsIgnoreCase("d"))
				noteList.add(createRoll(origin, position, distanceFromMeasureStart));
			else if (origin.strip().equalsIgnoreCase("g"))
				noteList.add(createGhost(origin, position, distanceFromMeasureStart));
			else
				noteList.add(new InvalidNote(stringNumber, origin, position, lineName, distanceFromMeasureStart));
		}
		return noteList;
	}

	@Override
	protected void addRelationship(TabNote note1, TabNote note2, String relationship) {
		// No relationships for drum notes
	}

	protected List<TabNote> createFlam(String origin, int position, int distanceFromMeasureStart) {
		TabNote graceNote = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		TabNote gracePair = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		grace(graceNote, gracePair, 1);
		List<TabNote> notes = new ArrayList<>();
		notes.add(graceNote);
		notes.add(gracePair);
		return notes;
	}

	protected List<TabNote> createDrag(String origin, int position, int distanceFromMeasureStart) {
		TabNote graceNote1 = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		TabNote graceNote2 = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		TabNote graceMain = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		grace(graceNote1, graceNote2, 1);
		grace(graceNote2, graceMain, 2);
		List<TabNote> notes = new ArrayList<>();
		notes.add(graceNote1);
		notes.add(graceNote2);
		notes.add(graceMain);
		return notes;
	}

	protected DrumNote createGhost(String origin, int position, int distanceFromMeasureStart) {
		DrumNote ghostNote = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		ghostNote.addDecorator((noteModel) -> {
			Notehead nh = new Notehead("normal");
			nh.setParentheses("yes");
			noteModel.setNotehead(nh);
			return true;
		}, "success");
		return ghostNote;
	}

	protected DrumNote createRoll(String origin, int position, int distanceFromMeasureStart) {
		DrumNote rollNote = new DrumNote(stringNumber, origin, position, this.lineName, distanceFromMeasureStart);
		rollNote.addDecorator((noteModel) -> {
			Notations n = getNonNullNotations(noteModel);
			Ornaments o;
			if (n.getOrnaments() == null) n.setOrnaments(new Ornaments());
			o = n.getOrnaments();
			Tremolo t;
			if (o.getTremolo() == null) o.setTremolo(new Tremolo(1));
			t = o.getTremolo();
			t.setType("single");
			return true;
		}, "success");
		return rollNote;
	}

	@Override
	protected void setGraceStem(Note noteModel) {
		assert noteModel.getStem().equals("up");
	}
	
	@Override
	protected void setGraceType(Note noteModel) {
		noteModel.setType("eighth");
	}

}
