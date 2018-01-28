package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.utils.reflections.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Luc1412 on 03.05.2017 at 16:41
 */
public class Utils {

	private List<Player> GUIPlayer;
	private ReflectionUtils reflectionUtils;

	public Utils() {
		GUIPlayer = new ArrayList<>();
		reflectionUtils = new ReflectionUtils();

	}

	public void sendError(String error) {
		Bukkit.broadcastMessage("§8[EasyMaintenance] §4§lERROR: " + error + " PLUGIN WILL BE DISABLED IN 10 SECONDS!");
		Bukkit.getConsoleSender().sendMessage("§8[EasyMaintenance] §4§lERROR: " + error + " PLUGIN WILL BE DISABLED IN 10 SECONDS!");
		Bukkit.getScheduler().scheduleAsyncDelayedTask(EasyMaintenance.getInstance(), () -> Bukkit.getServer().getPluginManager().disablePlugin(EasyMaintenance.getInstance()), 200);
	}

	public boolean checkForPlugin(String pluginName) {
		if (EasyMaintenance.getInstance().getServer().getPluginManager().getPlugin(pluginName) == null) {
			Bukkit.getConsoleSender().sendMessage(getPrefix() + "§6" + pluginName + " §4was not found. Version will not override.");
			return false;
		} else {
			Bukkit.getConsoleSender().sendMessage(getPrefix() + "§6" + pluginName + " §2was found. Version will override...");
			return true;
		}
	}

	public String getTranslatedMsgOfConfig(String path, boolean prefix) {
		String cache;
		if (prefix) {
			cache = getPrefix() + EasyMaintenance.getDefaultConfig().getString("Messages." + path).replaceAll("&", "§");
		} else cache = EasyMaintenance.getDefaultConfig().getString("Messages." + path).replaceAll("&", "§");
		return cache;
	}

	public void toggleMaintenance() {
		if (EasyMaintenance.getSettingsConfig().getBoolean("Maintenance")) {
			EasyMaintenance.getSettingsConfig().setBoolean("Maintenance", false);
		} else EasyMaintenance.getSettingsConfig().setBoolean("Maintenance", true);
	}

	public boolean getInMaintenance() {
		return EasyMaintenance.getSettingsConfig().getBoolean("Maintenance");
	}

	public String getPrefix() {
		switch (EasyMaintenance.getDefaultConfig().getInt("PrefixStyle")) {
			case 1:
				return "§8|§4Easy§2Maintenance§8| §3§l->§r ";
			case 2:
				return "§7[§4Easy§2Maintenance§7]§r ";
			case 3:
				return "§7(§4Easy§2Maintenance§7)§r ";
			case 4:
				return "§2§l【§4Easy§2Maintenance§2§l】§r ";
			case 5:
				if (EasyMaintenance.getDonatorChecker().hasDonator()) {
					return EasyMaintenance.getDonatorConfig().getString("CustomPrefix");
				} else return "§8|§4Easy§2Maintenance§8| §3§l->§r ";
			default:
				sendError("INVALID PREFIX-STYLE!");
				break;
		}
		return "";
	}

	public void log(String msg) {
		if (EasyMaintenance.getDefaultConfig() != null) {
			if (EasyMaintenance.getDefaultConfig().getBoolean("DebugLog")) {
				Bukkit.getLogger().log(Level.INFO, msg);
			}
		}
	}

	public void playSoundOfConfig(Player p, Location loc, String path, float volume) {
		try {
			String[] convertedStringArray = EasyMaintenance.getDefaultConfig().getString("Sounds." + path).split(":");
			String configSound = convertedStringArray[0].toUpperCase();
			float configPitch = Float.parseFloat(convertedStringArray[1]);
			p.playSound(loc, Sound.valueOf(configSound), volume, configPitch);
		} catch (IllegalArgumentException | NullPointerException e) {
			sendError("INVALID SOUND! PATH: " + path);
		}
	}

	public String getCurrentVersionAsString() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(46) + 1);
	}

	public int getCurrentVersionAsInt() {
		String chache;
		chache = getCurrentVersionAsString().substring(3, getCurrentVersionAsString().length());
		chache = chache.substring(0, chache.length() - 3);
		return Integer.valueOf(chache);
	}


	public void sendActionBar(Player player, String msg) throws Exception {
		if (EasyMaintenance.getDefaultConfig().getBoolean("SendActionBar")) {
			Class<?> clazz = Class.forName("de.luc1412.em.versions." + getCurrentVersionAsString().toLowerCase() + ".ActionBarManager");
			Method barMethod = clazz.getDeclaredMethod("sendBar", Player.class, String.class);
			barMethod.invoke(clazz.newInstance(), player, msg);
		}
	}

	public void sendEventMessage(Player player, String json) throws Exception {
		Class<?> clazz = Class.forName("de.luc1412.em.versions." + getCurrentVersionAsString().toLowerCase() + ".JsonChatManager");
		Method eventMsgMethod = clazz.getDeclaredMethod("sendEventMessage", Player.class, String.class);
		eventMsgMethod.invoke(clazz.newInstance(), player, json);
	}

	public void sendTitle(Player p, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) throws Exception {
		Class<?> clazz = Class.forName("de.luc1412.em.versions." + getCurrentVersionAsString().toLowerCase() + ".TitleManager");
		Method titelMethod = clazz.getDeclaredMethod("sendTitle", Player.class, String.class, String.class, int.class, int.class, int.class);
		titelMethod.invoke(clazz.newInstance(), p, title, subtitle, fadeInTime, stayTime, fadeOutTime);
	}

	public boolean isDefaultConfigExists() {
		return new File(EasyMaintenance.getInstance().getDataFolder(), "config.yml").exists();
	}

	String getFomatedTime(int seconds) {
		String formatedSeconds;
		String formatedMinutes;
		String formatedHoures;
		String formatedTime;
		if (seconds >= 3600) {
			int convertedHoures = seconds / 3600;
			int convertedMinutes = (seconds % 3600) / 60;
			int convertedSeconds = (seconds % 3600) % 60;
			formatedHoures = (convertedHoures < 10) ? "0" + convertedHoures : String.valueOf(convertedHoures);
			formatedMinutes = (convertedMinutes < 10) ? "0" + convertedMinutes : String.valueOf(convertedMinutes);
			formatedSeconds = (convertedSeconds < 10) ? "0" + convertedSeconds : String.valueOf(convertedSeconds);
		} else if (seconds >= 60) {
			int convertedMinutes = seconds / 60;
			int convertedSeconds = seconds % 60;
			formatedHoures = "00";
			formatedMinutes = (convertedMinutes < 10) ? "0" + convertedMinutes : String.valueOf(convertedMinutes);
			formatedSeconds = (convertedSeconds < 10) ? "0" + convertedSeconds : String.valueOf(convertedSeconds);
		} else {
			formatedHoures = "00";
			formatedMinutes = "00";
			formatedSeconds = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
		}
		formatedTime = formatedHoures + ":" + formatedMinutes + ":" + formatedSeconds;
		return formatedTime;
	}

	double calcProgress(int seconds, int secondsmax) {
		double progress;
		progress = 100.0 / (double) secondsmax * (double) seconds;
		progress = 100.0 - progress;
		progress = progress / 100.0;
		return progress;
	}

	public boolean useBossBar() {
		return getCurrentVersionAsInt() >= 9 && EasyMaintenance.getDefaultConfig().getBoolean("BossBar");
	}

	int convertArgToSeconds(String arg) throws NumberFormatException {
		if (arg.endsWith("s")) {
			String numberAsString = arg.substring(0, arg.length() - 1);
			return Integer.valueOf(numberAsString);
		} else if (arg.endsWith("m")) {
			String numberAsString = arg.substring(0, arg.length() - 1);
			int chache = Integer.valueOf(numberAsString);
			chache = chache * 60;
			return chache;
		} else if (arg.endsWith("h")) {
			String numberAsString = arg.substring(0, arg.length() - 1);
			int chache = Integer.valueOf(numberAsString);
			chache = chache * 60 * 60;
			return chache;
		}
		throw new NumberFormatException();
	}

	public void downloadImage(String url_P, String path, String fileName) {

		EasyMaintenance.getUtils().log("Prepare the Image Download...");

		InputStream inputStream;

		OutputStream outputStream;

		String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

		try {
			URL url = new URL(url_P);
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);
			inputStream = con.getInputStream();
			outputStream = new FileOutputStream(path + File.separator + fileName);
			byte[] buffer = new byte[1024];
			int lenght;
			while ((lenght = inputStream.read(buffer)) != -1) {
				EasyMaintenance.getUtils().log("Downloading Image! Remaining: " + lenght);
				outputStream.write(buffer, 0, lenght);
			}
			outputStream.close();
			inputStream.close();
			EasyMaintenance.getUtils().log("Download was successfully completed...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] translateMessagesToCfgValues(String[] messages) {
		if (EasyMaintenance.getDefaultConfig().getInt("MessagesStyle") == 2) for (int i = 0; i < messages.length; i++)
			messages[i] = messages[i].replaceAll("§f", "§1").replaceAll("§0", "§4");
		if (EasyMaintenance.getDefaultConfig().getInt("MessagesStyle") == 3)
			for (int i = 0; i < messages.length; i++) messages[i] = messages[i].replaceAll("§f", "§4");
		if (EasyMaintenance.getDefaultConfig().getInt("MessagesStyle") == 4) for (int i = 0; i < messages.length; i++)
			messages[i] = messages[i].replaceAll("§f", "§1").replaceAll("§0", "§2");
		if (EasyMaintenance.getDefaultConfig().getInt("MessagesStyle") == 5) for (int i = 0; i < messages.length; i++)
			messages[i] = messages[i].replaceAll("§f", "§4").replaceAll("§0", "§2");
		if (EasyMaintenance.getDefaultConfig().getInt("MessagesStyle") == 6) for (int i = 0; i < messages.length; i++)
			messages[i] = messages[i].replaceAll("§f", "§4").replaceAll("§0", "§e");
		return messages;
	}

	private boolean isRedirected(Map<String, List<String>> header) {
		for (String hv : header.get(null)) {
			if (hv.contains(" 301 ") || hv.contains(" 302 ")) return true;
		}
		return false;
	}

	public void downloadFileFromGitHub(String link, String filePath) {
		try {
			URL url = new URL(link);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			Map<String, List<String>> header = http.getHeaderFields();
			while (isRedirected(header)) {
				link = header.get("Location").get(0);
				url = new URL(link);
				http = (HttpURLConnection) url.openConnection();
				header = http.getHeaderFields();
			}
			InputStream input = http.getInputStream();
			byte[] buffer = new byte[4096];
			int n;
			OutputStream output = new FileOutputStream(new File(filePath));
			while ((n = input.read(buffer)) != -1) {
				output.write(buffer, 0, n);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLatestVersion(String url) {
		String latest = "";

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			latest = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latest;
	}


	public boolean manageGUIPlayer(ManageType type, Player p) {
		if (type.equals(ManageType.ADD)) {
			if (!GUIPlayer.contains(p)) GUIPlayer.add(p);
		} else if (type.equals(ManageType.CONTAINS)) {
			return GUIPlayer.contains(p);
		} else if (type.equals(ManageType.REMOVE)) {
			if (GUIPlayer.contains(p)) GUIPlayer.remove(p);
		}
		return false;
	}

	public ReflectionUtils getReflectionUtils() {
		return reflectionUtils;
	}

	public enum ManageType {
		ADD,
		CONTAINS,
		REMOVE
	}
}
