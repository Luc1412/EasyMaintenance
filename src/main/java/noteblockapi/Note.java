package noteblockapi;

/**
 * Created by Luc1412 on 03.12.2017 at 19:25
 */
public class Note {

	private byte instrument;
	private byte key;

	public Note(byte instrument, byte key) {
		this.instrument = instrument;
		this.key = key;
	}

	public byte getInstrument() {
		return instrument;
	}

	public void setInstrument(byte instrument) {
		this.instrument = instrument;
	}

	public byte getKey() {
		return key;
	}

	public void setKey(byte key) {
		this.key = key;
	}
}
