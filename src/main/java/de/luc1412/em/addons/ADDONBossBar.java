package de.luc1412.em.addons;


import de.luc1412.em.utils.BossBarManager;

/**
 * Created by Luc1412 on 28.07.2017 at 19:02
 */
public class ADDONBossBar {

	private BossBarManager barManager;

	public ADDONBossBar(String name) {
		barManager = new BossBarManager();
		barManager.createBar(name);
	}

	public BossBarManager getBarManager() {
		return barManager;
	}
}
