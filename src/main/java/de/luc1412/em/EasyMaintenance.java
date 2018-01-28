package de.luc1412.em;

import de.luc1412.em.addons.ADDONProtocolLib;
import de.luc1412.em.commands.CMDEasyMaintenance;
import de.luc1412.em.commands.CommandCooldownManager;
import de.luc1412.em.commands.TABEasyMaintenance;
import de.luc1412.em.configuration.DefaultConfig;
import de.luc1412.em.configuration.DonatorConfig;
import de.luc1412.em.configuration.SettingsConfig;
import de.luc1412.em.listener.EVENTClickGUI;
import de.luc1412.em.listener.EVENTLogin;
import de.luc1412.em.listener.EVENTPingServer;
import de.luc1412.em.listener.EVENTPlayerJoin;
import de.luc1412.em.socket.SocketClient;
import de.luc1412.em.utils.DonatorChecker;
import de.luc1412.em.utils.Installer;
import de.luc1412.em.utils.MaintenanceManager;
import de.luc1412.em.utils.Utils;
import noteblockapi.NoteBlockPlayerMain;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luc1412 on 03.05.2017 at 16:06
 */
public class EasyMaintenance extends JavaPlugin {

	private static EasyMaintenance instance;
	private static Utils utils;
	private static SettingsConfig settingsConfig;
	private static ADDONProtocolLib addonProtocolLib;
	private static de.luc1412.em.Updater updater;
	private static SocketClient socketClient;
	private static MaintenanceManager maintenanceManager;
	private static de.luc1412.em.Metrics metrics;
	private static DefaultConfig defaultConfig;
	private static CommandCooldownManager commandCooldownManager;
	private static DonatorConfig donatorConfig;
	private static DonatorChecker donatorChecker;

	//NoteblockAPI
	private static NoteBlockPlayerMain noteBlockPlayerMain;

	public static void init() {
		if (!getDefaultConfig().getBoolean("BungeeMode.Enabled")) {
        /*
        -------------------- COMMANDS --------------------
         */
			getUtils().log("Initialize Commands...");
			getInstance().getCommand("easymaintenance").setExecutor(new CMDEasyMaintenance());

			commandCooldownManager = new CommandCooldownManager();
			getUtils().log("Initialize Command Cooldown Manager...");


        /*
        -------------------- TABCOMPLETER --------------------
         */
			getUtils().log("Initialize TabCompleter...");
			getInstance().getCommand("easymaintenance").setTabCompleter(new TABEasyMaintenance());


        /*
        -------------------- LISTENER --------------------
         */
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(new EVENTPlayerJoin(), getInstance());
			pm.registerEvents(new EVENTLogin(), getInstance());
			pm.registerEvents(new EVENTPingServer(), getInstance());
			pm.registerEvents(new EVENTClickGUI(), getInstance());
			getUtils().log("Initialize Listener...");

		}

        /*
        -------------------- PROTOCOLLIB --------------------
         */
		getUtils().log("Check for ProtocoleLib...");
		if (getUtils().checkForPlugin("ProtocolLib")) addonProtocolLib = new ADDONProtocolLib();

	    /*
        -------------------- UPDATER --------------------
         */
		if (EasyMaintenance.getInstance().getConfig().getBoolean("Updater")) {
			getUtils().log("Initialize Updater...");
			getUtils().log("Check for Updates...");
			updater = new de.luc1412.em.Updater(EasyMaintenance.getInstance(), 274061, EasyMaintenance.getInstance().getFile(), de.luc1412.em.Updater.UpdateType.NO_DOWNLOAD, true);
		}

        /*
        -------------------- METRICS --------------------
         */
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("plugin_version_used", () -> (donatorChecker.hasDonator()) ? "Donator" : "Normal"));
		getUtils().log("Adding Custom Chart: plugin_version_used");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("maintenance_status", () -> (getUtils().getInMaintenance()) ? "Maintenance Mode" : "Normal Mode"));
		getUtils().log("Adding Custom Chart: maintenance_status");
		metrics.addCustomChart(new de.luc1412.em.Metrics.AdvancedBarChart("configurations", () -> {
			Map<String, int[]> values = new HashMap<>();
			values.put("Updater", (getDefaultConfig().getBoolean("Updater")) ? new int[]{1, 0} : new int[]{0, 1});
			values.put("ActionBar", (getDefaultConfig().getBoolean("SendActionBar")) ? new int[]{1, 0} : new int[]{0, 1});
			values.put("BossBar", (getDefaultConfig().getBoolean("BossBar") || getUtils().getCurrentVersionAsInt() >= 9) ? new int[]{1, 0} : new int[]{0, 1});
			values.put("Maintenance Icon", (getDefaultConfig().getBoolean("MaintenanceServerIcon")) ? new int[]{1, 0} : new int[]{0, 1});
			values.put("GUI", (getDefaultConfig().getBoolean("GUI.Enabled")) ? new int[]{1, 0} : new int[]{0, 1});
			return values;
		}));
		getUtils().log("Adding Custom Chart: configurations");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("prefix_style_used", () -> String.valueOf(getDefaultConfig().getInt("PrefixStyle"))));
		getUtils().log("Adding Custom Chart: prefix_style_used");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("messages_style_used", () -> String.valueOf(getDefaultConfig().getInt("MessagesStyle"))));
		getUtils().log("Adding Custom Chart: messages_style_used");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("default_countdown", () -> String.valueOf(getDefaultConfig().getInt("DefaultCountdown"))));
		getUtils().log("Adding Custom Chart: default_countdown");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("bossbar_color", () -> (getDefaultConfig().getBoolean("BossBar") | getUtils().getCurrentVersionAsInt() >= 9) ? getDefaultConfig().getString("BossBarSettings.BossBarColor").toUpperCase() : "Not Used"));
		getUtils().log("Adding Custom Chart: bossbar_color");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("bossbar_style", () -> (getDefaultConfig().getBoolean("BossBar") | getUtils().getCurrentVersionAsInt() >= 9) ? getDefaultConfig().getString("BossBarSettings.BossBarStyle").toUpperCase() : "Not Used"));
		getUtils().log("Adding Custom Chart: bossbar_style");
		metrics.addCustomChart(new de.luc1412.em.Metrics.SimplePie("gui_design_item", () -> (getDefaultConfig().getBoolean("GUI.Enabled")) ? String.valueOf(getDefaultConfig().getInt("GUI.DesignItemColor")) : "Not Used"));
		getUtils().log("Adding Custom Chart: gui_design_item");
		//metrics.addCustomChart(new Metrics.SimplePie("", () -> (this.getConfig().getBoolean("BungeeMode.Enabled")) ? "BungeeMode" : "Single Mode"));
		//getUtils().log("Adding Custom Chart: ");
	}

	public static EasyMaintenance getInstance() {
		return instance;
	}

	public static Utils getUtils() {
		return utils;
	}

	public static SettingsConfig getSettingsConfig() {
		return settingsConfig;
	}

	public static ADDONProtocolLib getAddonProtocolLib() {
		return addonProtocolLib;
	}

	public static de.luc1412.em.Updater getUpdater() {
		return updater;
	}

	public static SocketClient getSocketClient() {
		return socketClient;
	}

	public static MaintenanceManager getMaintenanceManager() {
		return maintenanceManager;
	}

	public static DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	public static void setDefaultConfig(DefaultConfig defaultConfig) {
		EasyMaintenance.defaultConfig = defaultConfig;
	}

	public static CommandCooldownManager getCommandCooldownManager() {
		return commandCooldownManager;
	}

	public static DonatorConfig getDonatorConfig() {
		return donatorConfig;
	}

	public static DonatorChecker getDonatorChecker() {
		return donatorChecker;
	}

	@Override
	public void onLoad() {
		instance = this;
		Bukkit.getConsoleSender().sendMessage("§8|§1Easy§4Maintenance§8| §3->§r §2The Plugin was successfully loaded!");
	}

	@Override
	public void onEnable() {
         /*
        -------------------- UTILS --------------------
         */
		utils = new Utils();
		getUtils().log("Initialize Utils...");


        /*
        -------------------- DONATOR-CHECK --------------------
        */
		donatorChecker = new DonatorChecker();
		getUtils().log("Check for Donator Version...");

        /*
        -------------------- DONATOR-CONFIG --------------------
        */
		if (donatorChecker.hasDonator()) {
			donatorConfig = new DonatorConfig();
			getUtils().log("Create Donator Config...");
		}

        /*
        -------------------- SETTINGS-CONFIG --------------------
         */
		settingsConfig = new SettingsConfig();
		getUtils().log("Initialize SettingsConfig and create it if didnt exists...");

        /*
        -------------------- MAINTENANCE-MANAGER --------------------
         */
		maintenanceManager = new MaintenanceManager();
		getUtils().log("Initialize MaintenanceManager...");

        /*
        -------------------- METRICS --------------------
         */
		metrics = new de.luc1412.em.Metrics(this);
		getUtils().log("Initialize Metrics...");


        /*
        -------------------- DOWNLOAD MAINTENANCE ICON --------------------
         */
		if (!(new File(this.getDataFolder() + File.separator + "maintenance-icon.png").exists()))
			getUtils().downloadImage("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/maintenance-icon.png", this.getDataFolder().toString(), "maintenance-icon.png");


		if (!(new File(this.getDataFolder() + File.separator + "Song.nbs").exists())) {
			if (getUtils().getCurrentVersionAsInt() == 12) {
				getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/Song-1.12.nbs", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "Song.nbs");
			} else {
				getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/Song-1.8.nbs", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "Song.nbs");
			}
		}

        /*
        -------------------- BUNGEEMODE SOCKETCLIENTS --------------------
         */
		if (this.getConfig().getBoolean("BungeeMode.Enabled")) {
			getUtils().log("Initialize SocketClient...");
			socketClient = new SocketClient(this.getConfig().getString("BungeeMode.CommunicationHost"), this.getConfig().getInt("BungeeMode.CommunicationPort"));
		}


        /*
        -------------------- INSTALLER --------------------
         */
		if (getUtils().isDefaultConfigExists()) {
			defaultConfig = new DefaultConfig();
			init();
		} else new Installer();

        /*
        -------------------- EPIC START MESSAGE :P --------------------
         */
		Bukkit.getConsoleSender().sendMessage("§9|§7~~~~~~~~~~~~~~~~~~~~~~§1Easy§4Maintenance§7~~~~~~~~~~~~~~~~~~~~~§9|\n" +
				"                 §9|§1Easy§4Maintenance §e" + EasyMaintenance.getInstance().getDescription().getVersion() + " §aBY LUC1412 WAS SUCCESSFULLY ENABLED!§9|\n" +
				"                 §9|§4ALL RIGHTS RESERVED BY LUC1412!                           §9|\n" +
				"                 §9|§4YOU ARE NOT ALLOWED TO DECOMPILE THIS PLUGIN!             §9|\n" +
				"                 §9|§7~~~~~~~~~~~~~~~~~~~~~~§1Easy§4Maintenance§7~~~~~~~~~~~~~~~~~~~~~§9|");


		noteBlockPlayerMain = new NoteBlockPlayerMain();
		noteBlockPlayerMain.onEnable();
	}

	@Override
	public void onDisable() {
        /*
        -------------------- BUNGEEMODE SOCKETCLIENTS --------------------
         */
		if (this.getConfig().getBoolean("BungeeMode.Enabled")) {
			getUtils().log("Disconnect SocketClient from Socket Server...");
			socketClient = new SocketClient(this.getConfig().getString("BungeeMode.CommunicationHost"), this.getConfig().getInt("BungeeMode.CommunicationPort"));
		}
		getUtils().log("Closing LookupService");

		noteBlockPlayerMain.onDisable();

        /*
        -------------------- GOOD BYE MESSAGE ^^ --------------------
         */
		Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §2The Plugin was successfully disabled!");
		Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §2Thank you for using §1Easy§4Maintenance§2. Have a nice Day ;)");
	}
}
