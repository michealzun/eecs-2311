package models.measure.note.notations.technical;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Ornaments {
    
	public Ornaments() {}

	public Tremolo getTremolo() {
		return tremolo;
	}

	public void setTremolo(Tremolo tremolo) {
		this.tremolo = tremolo;
	}

	private Tremolo tremolo;
}
