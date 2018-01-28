package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.api.MaintenanceToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 07.09.2017 at 18:56
 */
public class MaintenanceManager {

	private static MaintenanceStartCountdownManager countdownManager;

	static void resetCountdown() {
		countdownManager = null;
	}

	public void toggleCountdown(Player p, String[] args) {
		if (countdownManager == null) {
			if (!EasyMaintenance.getUtils().getInMaintenance()) {
				if (args.length == 1) {
					countdownManager = new MaintenanceStartCountdownManager(EasyMaintenance.getDefaultConfig().getInt("DefaultCountdown"));
					countdownManager.startMaintenanceCountdown();
				} else if (args.length <= 4) {
					int seconds = 0;
					for (int i = 1; i < args.length; i++) {
						try {
							seconds = seconds + EasyMaintenance.getUtils().convertArgToSeconds(args[i]);
						} catch (NumberFormatException e) {
							p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("InvalidNumber", true));
							break;
						}
					}
					countdownManager = new MaintenanceStartCountdownManager(seconds);
					countdownManager.startMaintenanceCountdown();
				} else
					p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
			} else {
				Bukkit.getPluginManager().callEvent(new MaintenanceToggleEvent(false));
				EasyMaintenance.getUtils().toggleMaintenance();
				Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ToggleOff", true));
				for (Player all : Bukkit.getOnlinePlayers())
					EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "MaintenanceEnd", 1000);
			}
		} else
			p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsRunning", true));
	}

	public void cancelCountdown(Player p) {
		if (countdownManager != null) {
			countdownManager.stopCountdown(countdownManager.getTaskID());
			countdownManager.removeBossBar();
			for (Player all : Bukkit.getOnlinePlayers()) {
				try {
					EasyMaintenance.getUtils().sendActionBar(all, EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ActionBar.CountdownStopped", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
				EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownStopped", 1000);
				all.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownStopped", true));
			}
			resetCountdown();

		} else
			p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsNotRunning", true));
	}

	/*
	CONSOLE
	 */

	public void skipCountdown(Player p, String[] args) {
		if (countdownManager != null) {
			if (args.length >= 2) {
				if (args.length <= 4) {
					int seconds = 0;
					for (int i = 1; i < args.length; i++) {
						try {
							seconds = seconds + EasyMaintenance.getUtils().convertArgToSeconds(args[i]);
						} catch (NumberFormatException e) {
							Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("InvalidNumber", true));
							break;
						}
					}
					if (countdownManager.getSeconds() > seconds) {
						int current = countdownManager.getSeconds();
						int chache = current - seconds;
						countdownManager.setSeconds(chache);
					} else
						p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("SkipTimeToHigh", true));
				} else
					p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
			} else
				p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
		} else
			p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsNotRunning", true));
	}

	public void toggleCountdown(CommandSender sender, String[] args) {
		if (countdownManager == null) {
			if (!EasyMaintenance.getUtils().getInMaintenance()) {
				if (args.length == 1) {
					countdownManager = new MaintenanceStartCountdownManager(EasyMaintenance.getDefaultConfig().getInt("DefaultCountdown"));
					countdownManager.startMaintenanceCountdown();
				} else if (args.length <= 4) {
					int seconds = 0;
					for (int i = 1; i < args.length; i++) {
						try {
							seconds = seconds + EasyMaintenance.getUtils().convertArgToSeconds(args[i]);
						} catch (NumberFormatException e) {
							sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("InvalidNumber", true));
							break;
						}
					}
					countdownManager = new MaintenanceStartCountdownManager(seconds);
					countdownManager.startMaintenanceCountdown();
				} else
					sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
			} else {
				Bukkit.getPluginManager().callEvent(new MaintenanceToggleEvent(false));
				EasyMaintenance.getUtils().toggleMaintenance();
				Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ToggleOff", true));
				for (Player all : Bukkit.getOnlinePlayers())
					EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "MaintenanceEnd", 1000);
			}
		} else
			sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsRunning", true));
	}

	public void cancelCountdown(CommandSender sender) {
		if (countdownManager != null) {
			countdownManager.stopCountdown(countdownManager.getTaskID());
			countdownManager.removeBossBar();
			for (Player all : Bukkit.getOnlinePlayers()) {
				try {
					EasyMaintenance.getUtils().sendActionBar(all, EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ActionBar.CountdownStopped", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
				EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownStopped", 1000);
				all.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownStopped", true));
			}
			resetCountdown();

		} else
			sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsNotRunning", true));
	}

	public void skipCountdown(CommandSender sender, String[] args) {
		if (countdownManager != null) {
			if (args.length >= 2) {
				if (args.length <= 4) {
					int seconds = 0;
					for (int i = 1; i < args.length; i++) {
						try {
							seconds = seconds + EasyMaintenance.getUtils().convertArgToSeconds(args[i]);
						} catch (NumberFormatException e) {
							Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("InvalidNumber", true));
							break;
						}
					}
					if (countdownManager.getSeconds() > seconds) {
						int current = countdownManager.getSeconds();
						int chache = current - seconds;
						countdownManager.setSeconds(chache);
					} else
						sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("SkipTimeToHigh", true));
				} else
					sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
			} else
				sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
		} else
			sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsNotRunning", true));
	}

	public MaintenanceStartCountdownManager getCountdownManager() {
		return countdownManager;
	}
}
