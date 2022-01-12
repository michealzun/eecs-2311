package converter.note;

public class BassNoteFactory extends GuitarNoteFactory {
	
	public BassNoteFactory() {
		super();
	}

	protected GuitarNote instantiateNote(String origin, int position, int distanceFromMeasureStart) {
			return new BassNote(stringNumber, origin, position, lineName, distanceFromMeasureStart);
	}
	
	protected GuitarNote instantiateNote(String origin, int position, int distanceFromMeasureStart, int stretch) {
		BassNote result = new BassNote(stringNumber, origin, position, lineName, distanceFromMeasureStart);
		result.stretch = stretch;
		return result;
	}
}
