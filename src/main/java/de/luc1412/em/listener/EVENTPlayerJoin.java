package de.luc1412.em.listener;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Luc1412 on 27.07.2017 at 20:40
 */
public class EVENTPlayerJoin implements Listener {


	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (EasyMaintenance.getUtils().getInMaintenance()) {
			player.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("MaintenanceJoinMessage", true));
		}
		if (EasyMaintenance.getMaintenanceManager().getCountdownManager() != null) {
			if (EasyMaintenance.getUtils().useBossBar()) {
				EasyMaintenance.getMaintenanceManager().getCountdownManager().getBossBar().getBarManager().addPlayerToBar(player);
			}
		}
		if (EasyMaintenance.getUtils().isDefaultConfigExists()) {
			if (EasyMaintenance.getDefaultConfig().getBoolean("Updater")) {
				if (player.hasPermission("em.use")) {
                    /*
                    if (EasyMaintenance.getUpdater() != null) {
                        if (EasyMaintenance.getUpdater().getResult() != null) {
                            if (EasyMaintenance.getUpdater().getResult().equals(Updater.UpdateResult.UPDATE_AVAILABLE)) {
                                String[] messages = new String[7];
                                messages[0] = "§f§l┌§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
                                messages[1] = "§0§l│ §3§lAn update is available!";
                                messages[2] = "§f§l│ §3§lPlugin: §b" + EasyMaintenance.getUpdater().getLatestName();
                                messages[3] = "§0§l│ §3§lRelease Type: §b" + EasyMaintenance.getUpdater().getLatestType();
                                messages[4] = "§f§l│ §3§lDownload-Link:";
                                messages[5] = "§0§l│ §bhttps://goo.gl/7hdKxA";
                                messages[6] = "§f§l└§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
                                messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
                                player.sendMessage(" ");
                                player.sendMessage(" ");
                                player.sendMessage(" ");
                                player.sendMessage(" ");
                                for (String message : messages) player.sendMessage(message);
                            }
                        }
                    }
                    */
					String latest = EasyMaintenance.getUtils().getLatestVersion("https://api.spigotmc.org/legacy/update.php?resource=45348");
					String current = EasyMaintenance.getInstance().getDescription().getVersion();
					if (!latest.equals(current)) {
						String[] messages = new String[7];
						messages[0] = "§f§l┌§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
						messages[1] = "§0§l│ §3§lAn update is available!";
						messages[2] = "§f§l│ §3§lPlugin: §b" + EasyMaintenance.getInstance().getDescription().getName() + " v" + current;
						messages[3] = "§0§l│ §3§lLatest Version: §bv" + latest;
						messages[4] = "§f§l│ §3§lDownload-Link:";
						messages[5] = "§0§l│ §bhttps://goo.gl/7hdKxA";
						messages[6] = "§f§l└§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
						messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
						player.sendMessage(" ");
						player.sendMessage(" ");
						player.sendMessage(" ");
						player.sendMessage(" ");
						for (String message : messages) player.sendMessage(message);
					}
				}
			}
		}
		if (!EasyMaintenance.getSettingsConfig().getBoolean("warn_v" + EasyMaintenance.getInstance().getDescription().getVersion().replaceAll(".", "_"))) {
			if (player.hasPermission("em.use")) {
				player.sendMessage(EasyMaintenance.getUtils().getPrefix() + "§4If you updated the Plugin on a new Version there might be problems with the config.");
				player.sendMessage(EasyMaintenance.getUtils().getPrefix() + "§4Save your Values from your Config and delete the config. Than restart the Server and get a new Config.");
				EasyMaintenance.getSettingsConfig().setBoolean("warn_v" + EasyMaintenance.getInstance().getDescription().getVersion().replaceAll(".", "_"), true);
			}
		}

	}
}
