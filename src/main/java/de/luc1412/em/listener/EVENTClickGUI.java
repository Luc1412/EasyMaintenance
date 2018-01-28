package de.luc1412.em.listener;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.commands.CMDEasyMaintenance;
import de.luc1412.em.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Luc1412 on 22.08.2017 at 00:06
 */
public class EVENTClickGUI implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getClickedInventory().getTitle() != null) {
				if (e.getClickedInventory().getTitle().equals("§1§lEasy§4§lMaintenance")) {
					e.setCancelled(true);
					if (e.getWhoClicked() instanceof Player) {
						Player p = (Player) e.getWhoClicked();
						if (p.hasPermission("em.use")) {
							if (e.getAction() != InventoryAction.NOTHING) {
								ItemStack currentItem = e.getCurrentItem();
								if (currentItem.getType() != null) {
									EasyMaintenance.getUtils().playSoundOfConfig(p, p.getLocation(), "GUIClickSound", 1000);
									if (currentItem.getType().equals(Material.BARRIER) || currentItem.getType().equals(Material.INK_SACK) || currentItem.getType().equals(Material.REDSTONE_BLOCK) || currentItem.getType().equals(Material.REDSTONE_TORCH_ON)) {
										if (currentItem.getItemMeta().getDisplayName() != null) {
											if (currentItem.getItemMeta().getDisplayName().equals(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonStartItemName", false))) {
												if (!EasyMaintenance.getUtils().getInMaintenance()) {
													if (!CMDEasyMaintenance.getGuiManager().getAnimationRunning())
														CMDEasyMaintenance.getGuiManager().toggleMaintenance();
												} else
													p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ServerIsInMaintenance", true));

											} else if (currentItem.getItemMeta().getDisplayName().equals(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonStopItemName", false))) {
												if (EasyMaintenance.getUtils().getInMaintenance()) {
													if (!CMDEasyMaintenance.getGuiManager().getAnimationRunning())
														CMDEasyMaintenance.getGuiManager().toggleMaintenance();
												} else
													p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ServerIsNotInMaintenance", true));

											} else if (currentItem.getItemMeta().getDisplayName().equals(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonCloseItemName", false))) {
												p.closeInventory();
											} else if (currentItem.getItemMeta().getDisplayName().equals(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonCancelItemName", false))) {
												p.performCommand("em cancel");
												p.closeInventory();
											} else if (currentItem.getItemMeta().getDisplayName().equals(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonSkipItemName", false))) {
												p.performCommand("em skip 10s");
												p.closeInventory();
											}
										}
									}
								}
							}
						} else p.closeInventory();
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	protected void onPlayerInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = ((Player) e.getWhoClicked());
			if (EasyMaintenance.getUtils().manageGUIPlayer(Utils.ManageType.CONTAINS, p)) {
				if (e.getClickedInventory() != null) {
					if (e.getClickedInventory() == p.getInventory()) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	protected void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = ((Player) e.getPlayer());
			if (e.getInventory() != null) {
				if (e.getInventory().getTitle() != null) {
					if (e.getInventory().getTitle().equals("§1§lEasy§4§lMaintenance")) {
						EasyMaintenance.getUtils().manageGUIPlayer(Utils.ManageType.REMOVE, p);
					}
				}
			}
		}
	}
}
