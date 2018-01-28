package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.addons.ADDONBossBar;
import de.luc1412.em.api.MaintenanceToggleEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 22.08.2017 at 00:19
 */
public class MaintenanceStartCountdownManager {

	private ADDONBossBar bossBar;

	private int taskID = 0;
	private int ticks = 0;
	private int maxticks = 0;

	public MaintenanceStartCountdownManager(int seconds) {
		this.ticks = seconds * 20;
		this.maxticks = seconds * 20;
		if (EasyMaintenance.getUtils().useBossBar()) {
			bossBar = new ADDONBossBar(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("BossBar", false).replace("%ftime%", EasyMaintenance.getUtils().getFomatedTime(seconds)));
		}
	}


	public void startMaintenanceCountdown() {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(EasyMaintenance.getInstance(), () -> {
			switch (ticks) {
				case 72000:
				case 36000:
				case 18000:
				case 12000:
				case 6000:
				case 3600:
				case 2400:
					Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownMinutes", true).replace("%time%", Integer.toString((ticks / 60) / 20)));
					for (Player all : Bukkit.getOnlinePlayers())
						EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownRunning", 1000);
					break;
				case 1200:
				case 600:
				case 300:
				case 200:
				case 100:
				case 80:
				case 60:
				case 40:
					Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownSeconds", true).replace("%time%", Integer.toString(ticks / 20)));
					for (Player all : Bukkit.getOnlinePlayers())
						EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownRunning", 1000);
					break;
				case 20:
					Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownSeconds1", true).replace("%time%", Integer.toString(ticks / 20)));
					for (Player all : Bukkit.getOnlinePlayers())
						EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownRunning", 1000);
					break;
				case 0:
					stopCountdown(taskID);
					if (EasyMaintenance.getUtils().useBossBar()) bossBar.getBarManager().removeBar();
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (!all.hasPermission("em.bypass"))
							all.kickPlayer(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("KickMessage", false));
						Bukkit.broadcastMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ToggleOn", true));
						EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "MaintenanceStart", 1000);
					}
					MaintenanceManager.resetCountdown();
					if (!EasyMaintenance.getDefaultConfig().getBoolean("BungeeMode.Enabled")) {
						EasyMaintenance.getUtils().toggleMaintenance();
						Bukkit.getPluginManager().callEvent(new MaintenanceToggleEvent(true));
					} else EasyMaintenance.getSocketClient().sendDataToSocketServer("toggle");
					break;
				default:
					break;

			}
			if (EasyMaintenance.getUtils().useBossBar()) {
				bossBar.getBarManager().setProgress(ticks, maxticks);
				bossBar.getBarManager().setTitle(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("BossBar", false).replace("%ftime%", EasyMaintenance.getUtils().getFomatedTime(ticks / 20)));
			}
			if (ticks % 20 == 0) for (Player all : Bukkit.getOnlinePlayers()) {
				try {
					EasyMaintenance.getUtils().sendActionBar(all, EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ActionBar.CountdownRunning", false).replace("%ftime%", EasyMaintenance.getUtils().getFomatedTime(ticks / 20)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ticks--;
		}, 1, 1);
	}

	public void stopCountdown(int taskID) {
		Bukkit.getScheduler().cancelTask(taskID);
	}

	public void removeBossBar() {
		if (EasyMaintenance.getUtils().useBossBar()) bossBar.getBarManager().removeBar();
	}

	public int getSeconds() {
		return ticks / 20;
	}

	public void setSeconds(int seconds) {
		this.ticks = seconds * 20;
	}

	public int getTaskID() {
		return taskID;
	}

	public ADDONBossBar getBossBar() {
		return bossBar;
	}
}
