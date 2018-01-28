package noteblockapi;

import java.util.HashMap;

/**
 * Created by Luc1412 on 03.12.2017 at 19:18
 */
public class Layer {

	private HashMap<Integer, Note> hashMap = new HashMap<>();
	private byte volume = 100;
	private String name = "";

	public HashMap<Integer, Note> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<Integer, Note> hashMap) {
		this.hashMap = hashMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Note getNote(int tick) {
		return hashMap.get(tick);
	}

	public void setNote(int tick, Note note) {
		hashMap.put(tick, note);
	}

	public byte getVolume() {
		return volume;
	}

	public void setVolume(byte volume) {
		this.volume = volume;
	}
}
