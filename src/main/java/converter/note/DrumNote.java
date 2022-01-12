package converter.note;

import java.util.List;

import converter.Instrument;
import models.measure.note.Note;
import models.measure.note.Notehead;
import models.measure.note.Unpitched;
import utility.DrumPiece;
import utility.DrumPieceInfo;
import utility.DrumUtils;
import utility.ValidationError;

public class DrumNote extends TabNote{

    private DrumPiece drumPiece;
    private DrumPieceInfo drumPieceInfo;

    public DrumNote (int stringNumber, String origin, int position, String lineName, int distanceFromMeasure){
        super(stringNumber, origin, position, lineName, distanceFromMeasure);
        this.instrument = Instrument.DRUMS;
        drumPiece = DrumUtils.getDrumPiece(lineName.strip(), origin.strip());
        //TODO Debug voice 2 issues
        //if ((drumPiece == DrumPiece.Bass_Drum_1) || (drumPiece == DrumPiece.Bass_Drum_2))
        //    this.voice = 2;
        drumPieceInfo = DrumUtils.drumSet.get(drumPiece);
    }

    public DrumNote(DrumNote n)
    {
    	super(n);
    	this.drumPiece = n.drumPiece;
    	this.drumPieceInfo = n.drumPieceInfo;
    }

    @Override
	protected void setStems(Note noteModel) {
    	//noteModel.setStem(drumPiece == DrumPiece.Bass_Drum_1 ? "down" : "up");
        noteModel.setStem("up");	
	}

	@Override
    public models.measure.note.Note getModel(){ 
    	models.measure.note.Note noteModel = super.getModel();
        noteModel.setUnpitched(new Unpitched(drumPieceInfo.getStep(), drumPieceInfo.getOctave()));
        noteModel.setInstrument(new models.measure.note.Instrument(this.drumPieceInfo.getMidiID()));
        String noteHead = this.text.strip();
        if ((noteHead.equalsIgnoreCase("x")) || ((noteHead.equalsIgnoreCase("o") && drumPiece == DrumPiece.Open_Hi_Hat)))
            noteModel.setNotehead(new Notehead("x"));
        if (drumPiece == DrumPiece.Ride_Bell) noteModel.setNotehead(new Notehead("diamond"));
        return noteModel;
    }

    public List<ValidationError> validate() {
        super.validate();
        return errors;
    }

	@Override
	public TabNote copy() {
		return new DrumNote(this);
	}

}