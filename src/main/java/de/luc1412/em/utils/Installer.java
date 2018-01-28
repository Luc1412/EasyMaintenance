package de.luc1412.em.utils;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.configuration.DefaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

/**
 * Created by Luc1412 on 02.10.2017 at 22:36
 */
public class Installer implements Listener {

	private final String SETUP_PREFIX = "§7§l[§1§lEasy§4Maintenance§8§l-§3§lSetup§7§l]§r ";

	public Installer() {
		Bukkit.getPluginManager().registerEvents(this, EasyMaintenance.getInstance());
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("em.use")) {
			e.setCancelled(true);
			String msg = e.getMessage();
			if (msg.equalsIgnoreCase("en") || e.getMessage().equalsIgnoreCase("de")) {
				String lang = (msg.equalsIgnoreCase("en")) ? "English" : "German";
				String vers = (EasyMaintenance.getUtils().getCurrentVersionAsInt() == 8) ? "1.8.x" : "1.9.x - 1.12.x";
				p.sendMessage(SETUP_PREFIX + "§bThe Plugin was §asuccessfully §binstalled! §6" + lang + " " + vers + " §bVersion.");
				HandlerList.unregisterAll(this);
				Callback callback = this::startPlugin;
				Thread thread = new Thread(() -> {
					if (msg.equalsIgnoreCase("en")) {
						if (EasyMaintenance.getUtils().getCurrentVersionAsInt() == 8) {
							EasyMaintenance.getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/config_1.8_En.yml", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "config.yml");
						} else
							EasyMaintenance.getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/config_1.12_En.yml", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "config.yml");
					} else {
						if (EasyMaintenance.getUtils().getCurrentVersionAsInt() == 8) {
							EasyMaintenance.getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/config_1.8_De.yml", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "config.yml");
						} else
							EasyMaintenance.getUtils().downloadFileFromGitHub("https://raw.githubusercontent.com/Luc1412/EasyMaintenance/master/config_1.12_De.yml", EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "config.yml");
					}
					callback.done();
				});
				thread.start();
			} else {
				p.sendMessage(SETUP_PREFIX + "§4Error! §bYour Input is not valid! Try again!");
				p.sendMessage(SETUP_PREFIX + "§bPlease enter §6'de' §bfor German or §6'en' §bfor english §bConfig!");
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("em.use")) {
			p.sendMessage(SETUP_PREFIX + "§bPlease enter §6'de' §bfor German or §6'en' §bfor english §bConfig!");
			new EmTitleAnimation(p);
		}
	}

	private void startPlugin() {
		EasyMaintenance.setDefaultConfig(new DefaultConfig());
		EasyMaintenance.init();
	}


}
