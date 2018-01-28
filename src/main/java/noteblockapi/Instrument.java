package noteblockapi;

import org.bukkit.Sound;

/**
 * Created by Luc1412 on 03.12.2017 at 19:28
 */
public class Instrument {

	public static Sound getInstrument(byte instrument) {
		if (CompatibilityUtils.getCompatibility() == CompatibilityUtils.NoteBlockCompatibility.pre1_9) {
			switch (instrument) {
				case 0:
					return Sound.valueOf("NOTE_PIANO");
				case 1:
					return Sound.valueOf("NOTE_BASS_GUITAR");
				case 2:
					return Sound.valueOf("NOTE_BASS_DRUM");
				case 3:
					return Sound.valueOf("NOTE_SNARE_DRUM");
				case 4:
					return Sound.valueOf("NOTE_STICKS");
				default:
					return Sound.valueOf("NOTE_PIANO");
			}
		} else {
			switch (instrument) {
				case 0:
					return Sound.valueOf("BLOCK_NOTE_HARP");
				case 1:
					return Sound.valueOf("BLOCK_NOTE_BASS");
				case 2:
					return Sound.valueOf("BLOCK_NOTE_BASEDRUM");
				case 3:
					return Sound.valueOf("BLOCK_NOTE_SNARE");
				case 4:
					return Sound.valueOf("BLOCK_NOTE_HAT");
			}

			if (CompatibilityUtils.getCompatibility() == CompatibilityUtils.NoteBlockCompatibility.post1_12) {
				switch (instrument) {
					case 5:
						return Sound.valueOf("BLOCK_NOTE_GUITAR");
					case 6:
						return Sound.valueOf("BLOCK_NOTE_FLUTE");
					case 7:
						return Sound.valueOf("BLOCK_NOTE_BELL");
					case 8:
						return Sound.valueOf("BLOCK_NOTE_CHIME");
					case 9:
						return Sound.valueOf("BLOCK_NOTE_XYLOPHONE");
				}
			}

			return Sound.valueOf("BLOCK_NOTE_HARP");

		}
	}

	public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
		switch (instrument) {
			case 0:
				return org.bukkit.Instrument.PIANO;
			case 1:
				return org.bukkit.Instrument.BASS_GUITAR;
			case 2:
				return org.bukkit.Instrument.BASS_DRUM;
			case 3:
				return org.bukkit.Instrument.SNARE_DRUM;
			case 4:
				return org.bukkit.Instrument.STICKS;
			default:
				return org.bukkit.Instrument.PIANO;
		}
	}

	public static boolean isCustomInstrument(byte instrument) {
		if (CompatibilityUtils.getCompatibility() != CompatibilityUtils.NoteBlockCompatibility.post1_12) {
			return instrument > 4;
		} else {
			return instrument > 9;
		}
	}

	public static byte getCustomInstrumentFirstIndex() {
		if (CompatibilityUtils.getCompatibility() != CompatibilityUtils.NoteBlockCompatibility.post1_12) {
			return 5;
		} else {
			return 10;
		}
	}
}
