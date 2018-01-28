package noteblockapi;

import org.bukkit.Sound;

/**
 * Created by Luc1412 on 03.12.2017 at 19:19
 */
public class CustomInstrument {

	private byte index;
	private String name;
	private String soundfile;
	private byte pitch;
	private byte press;
	private Sound sound;

	public CustomInstrument(byte index, String name, String soundfile, byte pitch, byte press) {
		this.index = index;
		this.name = name;
		this.soundfile = soundfile.replaceAll(".ogg", "");
		if (this.soundfile.equalsIgnoreCase("pling")) {
			switch (CompatibilityUtils.getCompatibility()) {
				case CompatibilityUtils.NoteBlockCompatibility.pre1_9:
					this.sound = Sound.valueOf("NOTE_PLING");
					break;
				case CompatibilityUtils.NoteBlockCompatibility.pre1_12:
				case CompatibilityUtils.NoteBlockCompatibility.post1_12:
					this.sound = Sound.valueOf("BLOCK_NOTE_PLING");
					break;
			}
		}
		this.pitch = pitch;
		this.press = press;
	}

	public byte getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getSoundfile() {
		return soundfile;
	}

	public Sound getSound() {
		return sound;
	}
}
