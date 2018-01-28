package noteblockapi;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luc1412 on 03.12.2017 at 19:37
 */
public class NoteBlockPlayerMain {
	public static NoteBlockPlayerMain plugin;

	public Map<String, ArrayList<SongPlayer>> playingSongs = Collections.synchronizedMap(new HashMap<>());
	public Map<String, Byte> playerVolume = Collections.synchronizedMap(new HashMap<String, Byte>());

	private boolean disabling = false;

	public static boolean isReceivingSong(Player p) {
		return ((plugin.playingSongs.get(p.getName()) != null) && (!plugin.playingSongs.get(p.getName()).isEmpty()));
	}

	public static void stopPlaying(Player p) {
		if (plugin.playingSongs.get(p.getName()) == null) {
			return;
		}
		for (SongPlayer s : plugin.playingSongs.get(p.getName())) {
			s.removePlayer(p);
		}
	}

	public static void setPlayerVolume(Player p, byte volume) {
		plugin.playerVolume.put(p.getName(), volume);
	}

	public static byte getPlayerVolume(Player p) {
		Byte b = plugin.playerVolume.get(p.getName());
		if (b == null) {
			b = 100;
			plugin.playerVolume.put(p.getName(), b);
		}
		return b;
	}

	public void onEnable() {
		plugin = this;

	}

	public void onDisable() {
		disabling = true;
		Bukkit.getScheduler().cancelTasks(EasyMaintenance.getInstance());
	}

	public void doSync(Runnable r) {
		EasyMaintenance.getInstance().getServer().getScheduler().runTask(EasyMaintenance.getInstance(), r);
	}

	public void doAsync(Runnable r) {
		EasyMaintenance.getInstance().getServer().getScheduler().runTaskAsynchronously(EasyMaintenance.getInstance(), r);
	}

	protected boolean isDisabling() {
		return disabling;
	}
}
