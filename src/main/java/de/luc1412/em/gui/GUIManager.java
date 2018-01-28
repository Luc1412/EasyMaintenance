package de.luc1412.em.gui;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Luc1412 on 21.08.2017 at 01:20
 */
public class GUIManager {

	private ItemStack redStatusItem;
	private ItemStack greenStatusItem;
	private ItemStack buttonStartItem;
	private ItemStack buttonCloseItem;
	private ItemStack buttonStopItem;
	private ItemStack buttonCancelItem;
	private ItemStack buttonSkipItem;

	private Player p;
	private Inventory inv;

	private InventoryAnimationManager inventoryAnimationManager;

	public GUIManager(Player p) {
		createItems();
		open(p);
		this.p = p;
	}

	private Inventory getInv() {
		inv = Bukkit.createInventory(null, 45, "§1§lEasy§4§lMaintenance");

		/*
		DESIGN ITEM
		 */
		for (int i = 0; i < 9; i++) inv.setItem(i, getDesignItem());
		inv.setItem(9, getDesignItem());
		inv.setItem(17, getDesignItem());
		for (int i = 18; i < 27; i++) inv.setItem(i, getDesignItem());
		inv.setItem(27, getDesignItem());
		inv.setItem(31, getDesignItem());
		inv.setItem(35, getDesignItem());
		for (int i = 36; i < 45; i++) inv.setItem(i, getDesignItem());

		/*
		STATUS ITEM
		 */
		if (EasyMaintenance.getUtils().getInMaintenance()) {
			for (int i = 10; i < 17; i++) inv.setItem(i, redStatusItem);
		} else for (int i = 10; i < 17; i++) inv.setItem(i, greenStatusItem);

		/*
		BUTTONS
		 */
		inv.setItem(28, buttonStartItem);
		inv.setItem(30, buttonStopItem);
		inv.setItem(32, buttonCancelItem);
		inv.setItem(34, buttonSkipItem);
		inv.setItem(44, buttonCloseItem);

		return inv;
	}

	private void open(Player p) {
		p.openInventory(getInv());
		EasyMaintenance.getUtils().manageGUIPlayer(Utils.ManageType.ADD, p);
	}

	private ItemStack getDesignItem() {
		ItemStack designItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0, (byte) EasyMaintenance.getDefaultConfig().getInt("GUI.DesignItemColor"));
		ItemMeta designMeta = designItem.getItemMeta();
		designMeta.setDisplayName(" ");
		designItem.setItemMeta(designMeta);
		return designItem;
	}

	private void createItems() {
		int amount = 1;
		short damage = 0;

		if (!(EasyMaintenance.getUtils().getCurrentVersionAsInt() >= 12)) {
			/*
		ITEM GREEN-STATUS
		 */
			greenStatusItem = new ItemStack(Material.STAINED_CLAY, amount, damage, (byte) 5);
			ItemMeta greenStatusMeta = greenStatusItem.getItemMeta();
			greenStatusMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.GreenStatusItemName", false));
			greenStatusItem.setItemMeta(greenStatusMeta);

		/*
		ITEM RED-STATUS
		 */
			redStatusItem = new ItemStack(Material.STAINED_CLAY, amount, damage, (byte) 14);
			ItemMeta redStatusMeta = redStatusItem.getItemMeta();
			redStatusMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.RedStatusItemName", false));
			redStatusItem.setItemMeta(redStatusMeta);
		} else {
			/*
		ITEM GREEN-STATUS
		 */
			greenStatusItem = new ItemStack(Material.CONCRETE, amount, damage, (byte) 5);
			ItemMeta greenStatusMeta = greenStatusItem.getItemMeta();
			greenStatusMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.GreenStatusItemName", false));
			greenStatusItem.setItemMeta(greenStatusMeta);

		/*
		ITEM RED-STATUS
		 */
			redStatusItem = new ItemStack(Material.CONCRETE, amount, damage, (byte) 14);
			ItemMeta redStatusMeta = redStatusItem.getItemMeta();
			redStatusMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.RedStatusItemName", false));
			redStatusItem.setItemMeta(redStatusMeta);
		}

		/*
		ITEM BUTTON-START
		 */
		buttonStartItem = new ItemStack(Material.INK_SACK, amount, damage, (byte) 10);
		ItemMeta buttonStartMeta = buttonStartItem.getItemMeta();
		buttonStartMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonStartItemName", false));
		buttonStartItem.setItemMeta(buttonStartMeta);

		/*
		ITEM BUTTON-CLOSE
		 */
		buttonCloseItem = new ItemStack(Material.REDSTONE_BLOCK, amount);
		ItemMeta buttonCloseMeta = buttonCloseItem.getItemMeta();
		buttonCloseMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonCloseItemName", false));
		buttonCloseItem.setItemMeta(buttonCloseMeta);

		/*
		ITEM BUTTON-STOP
		 */
		buttonStopItem = new ItemStack(Material.INK_SACK, amount, damage, (byte) 1);
		ItemMeta buttonStopMeta = buttonStopItem.getItemMeta();
		buttonStopMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonStopItemName", false));
		buttonStopItem.setItemMeta(buttonStopMeta);

		/*
		ITEM BUTTON-CANCEL
		 */
		buttonCancelItem = new ItemStack(Material.BARRIER, amount, damage);
		ItemMeta buttonCancelMeta = buttonCancelItem.getItemMeta();
		buttonCancelMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonCancelItemName", false));
		buttonCancelItem.setItemMeta(buttonCancelMeta);

		/*
		ITEM BUTTON-SKIP
		 */
		buttonSkipItem = new ItemStack(Material.REDSTONE_TORCH_ON, amount, damage);
		ItemMeta buttonSkipMeta = buttonSkipItem.getItemMeta();
		buttonSkipMeta.setDisplayName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("GUI.ButtonSkipItemName", false));
		buttonSkipItem.setItemMeta(buttonSkipMeta);
	}

	public void toggleMaintenance() {
		inventoryAnimationManager = new InventoryAnimationManager(p, inv);
	}

	ItemStack getGreenStatusItem() {
		return greenStatusItem;
	}

	ItemStack getRedStatusItem() {
		return redStatusItem;
	}

	public boolean getAnimationRunning() {
		return (inventoryAnimationManager != null) && inventoryAnimationManager.getAnimationRunning();
	}
}
