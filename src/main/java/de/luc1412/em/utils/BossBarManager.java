package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 27.07.2017 at 20:22
 */
public class BossBarManager {

	private BossBar bar;

	public void createBar(String name) {
		try {
			bar = Bukkit.createBossBar(name, BarColor.valueOf(EasyMaintenance.getDefaultConfig().getString("BossBarSettings.BossBarColor").toUpperCase()), BarStyle.valueOf(EasyMaintenance.getDefaultConfig().getString("BossBarSettings.BossBarStyle").toUpperCase()));
			for (Player all : Bukkit.getOnlinePlayers()) bar.addPlayer(all);
			bar.setProgress(0);
		} catch (IllegalArgumentException e) {
			EasyMaintenance.getUtils().sendError("INVALID BOSSBAR-COLOR OR BOSSBAR-STYLE");
		}
	}

	void setTitle(String title) {
		bar.setTitle(title);
	}

	void removeBar() {
		bar.removeAll();
	}

	void setProgress(int seconds, int maxseconds) {
		double progress = EasyMaintenance.getUtils().calcProgress(seconds, maxseconds);
		bar.setProgress(progress);
	}

	public void addPlayerToBar(Player p) {
		bar.addPlayer(p);
	}

}
