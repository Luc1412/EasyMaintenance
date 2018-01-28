package noteblockapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CompatibilityUtils {

	public static final String OBC_DIR = Bukkit.getServer().getClass().getPackage().getName();
	public static final String NMS_DIR = OBC_DIR.replaceFirst("org.bukkit.craftbukkit", "net.minecraft.server");

	public static Class<?> getMinecraftClass(String name) {
		try {
			return Class.forName(NMS_DIR + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getCraftBukkitClass(String name) {
		try {
			return Class.forName(OBC_DIR + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected static int getCompatibility() {
		if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7")) {
			return NoteBlockCompatibility.pre1_9;
		} else if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11")) {
			return NoteBlockCompatibility.pre1_12;
		} else {
			return NoteBlockCompatibility.post1_12;
		}
	}

	protected static boolean isSoundCategoryCompatible() {
		return !Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9") && !Bukkit.getVersion().contains("1.10");
	}

	protected static void playSound(Player p, Location location, String sound, SoundCategory category, float volume, float pitch) {
		try {
			if (isSoundCategoryCompatible()) {
				Method m = Player.class.getMethod("playSound", Location.class, String.class, Class.forName("org.bukkit.SoundCategory"), float.class, float.class);
				Class<? extends Enum> sc = (Class<? extends Enum>) Class.forName("org.bukkit.SoundCategory");
				Enum<?> scenum = Enum.valueOf(sc, category.name());
				m.invoke(p, location, sound, scenum, volume, pitch);
			} else {
				Method m = Player.class.getMethod("playSound", Location.class, String.class, float.class, float.class);
				m.invoke(p, location, sound, volume, pitch);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void playSound(Player p, Location location, Sound sound, SoundCategory category, float volume, float pitch) {
		try {
			if (isSoundCategoryCompatible()) {
				Method m = Player.class.getMethod("playSound", Location.class, Sound.class, Class.forName("org.bukkit.SoundCategory"), float.class, float.class);
				Class<? extends Enum> sc = (Class<? extends Enum>) Class.forName("org.bukkit.SoundCategory");
				Enum<?> scenum = Enum.valueOf(sc, category.name());
				m.invoke(p, location, sound, scenum, volume, pitch);
			} else {
				Method m = Player.class.getMethod("playSound", Location.class, Sound.class, float.class, float.class);
				m.invoke(p, location, sound, volume, pitch);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<CustomInstrument> get1_12Instruments() {
		ArrayList<CustomInstrument> instruments = new ArrayList<>();
		instruments.add(new CustomInstrument((byte) 0, "Guitar", "guitar.ogg", (byte) 0, (byte) 0));
		instruments.add(new CustomInstrument((byte) 0, "Flute", "flute.ogg", (byte) 0, (byte) 0));
		instruments.add(new CustomInstrument((byte) 0, "Bell", "bell.ogg", (byte) 0, (byte) 0));
		instruments.add(new CustomInstrument((byte) 0, "Chime", "icechime.ogg", (byte) 0, (byte) 0));
		instruments.add(new CustomInstrument((byte) 0, "Xylophone", "xylobone.ogg", (byte) 0, (byte) 0));
		return instruments;
	}

	public static class NoteBlockCompatibility {
		public static final int pre1_9 = 0;
		public static final int pre1_12 = 1;
		public static final int post1_12 = 2;
	}
}