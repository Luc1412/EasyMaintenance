package de.luc1412.em.gui;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.commands.CMDEasyMaintenance;
import de.luc1412.em.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Luc1412 on 22.08.2017 at 00:19
 */
class InventoryAnimationManager {

	private int taskID;
	private int slot = 10;
	private boolean animationRunning = false;

	private Inventory GUI;
	private Player p;

	InventoryAnimationManager(Player p, Inventory GUI) {
		this.GUI = GUI;
		this.p = p;
		startAnimation();
	}

	private void startAnimation() {
		if (EasyMaintenance.getMaintenanceManager().getCountdownManager() == null) {
			animationRunning = true;
			taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(EasyMaintenance.getInstance(), () -> {
				EasyMaintenance.getUtils().playSoundOfConfig(p, p.getLocation(), "GUIClickSound", 1000);
				if (EasyMaintenance.getUtils().getInMaintenance()) {
					GUI.setItem(slot, CMDEasyMaintenance.getGuiManager().getGreenStatusItem());
				} else GUI.setItem(slot, CMDEasyMaintenance.getGuiManager().getRedStatusItem());
				if (slot == 17) {
					cancelTask(taskID);
					p.closeInventory();
					EasyMaintenance.getUtils().manageGUIPlayer(Utils.ManageType.REMOVE, p);
					p.performCommand("em toggle");
					animationRunning = false;
				}
				slot++;
			}, 20, 10);
		} else EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownIsRunning", true);
	}

	private void cancelTask(int taskID) {
		Bukkit.getScheduler().cancelTask(taskID);
	}

	boolean getAnimationRunning() {
		return animationRunning;
	}
}