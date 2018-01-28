package de.luc1412.em.commands;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.gui.GUIManager;
import de.luc1412.em.utils.EmTitleAnimation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 03.05.2017 at 16:22
 */
public class CMDEasyMaintenance implements CommandExecutor {

	private static GUIManager guiManager;

	public static GUIManager getGuiManager() {
		return guiManager;
	}

	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        /*
            -------------------------------- PLAYER ------------------------------------------------------------------
            */
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("em.use")) {
					if (EasyMaintenance.getDefaultConfig().getBoolean("GUI.Enabled")) {
						guiManager = new GUIManager(p);
					}
				} else {
					if (EasyMaintenance.getDonatorChecker().hasDonator()) {
						p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("NoPermission", true));
						return false;
					}
					long cd = EasyMaintenance.getCommandCooldownManager().isPlayerInCooldown(p);
					if (cd > 0L) {
						p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("CooldownMessage", true).replace("%seconds%", String.valueOf(cd)));
						return false;
					}
					EasyMaintenance.getCommandCooldownManager().addPlayer(p);
				}
				String[] messages = new String[8];
				messages[0] = "§f§l┌§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
				messages[1] = "[\"\",{\"text\":\"§0§l│\"},{\"text\":\" §3§lPlugin Author: §bLuc1412\"}]";
				messages[2] = "[\"\",{\"text\":\"§f§l│\"},{\"text\":\" §3§lPlugin Version: §b" + EasyMaintenance.getInstance().getDescription().getVersion() + "\"}]";
				messages[3] = "[\"\",{\"text\":\"§0§l│\"},{\"text\":\" §1§lTwitter: \"},{\"text\":\"§9@luc141201\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://twitter.com/luc141201\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to come on my Twitter-Profile!\",\"color\":\"gray\"}]}}},{\"text\":\" *klick*\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://twitter.com/luc141201\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§7Click to come on my Twitter-Profile!\"}]}}}]";
				messages[4] = "[\"\",{\"text\":\"§f§l│\"},{\"text\":\" §3§lDiscord: \"},{\"text\":\"§bLuc1412 Support \",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/mqrJ9qG\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to join the Discord-Server!\",\"color\":\"gray\"}]}}},{\"text\":\"*klick*\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/mqrJ9qG\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§7Click to join the Discord-Server!\"}]}}}]";
				messages[5] = "[\"\",{\"text\":\"§0§l│\"},{\"text\":\" §6§lDonator Version: \"},{\"text\":\"§eSpigotMC.org \",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/easymaintenance-bukkit-spigot-version.45348/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§7Click to get on my SpigotMC Page!\"}]}}},{\"text\":\"§7*klick*\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/easymaintenance-bukkit-spigot-version.45348/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§7Click to get on my SpigotMC Page!\"}]}}}]";
				messages[6] = "[\"\",{\"text\":\"§f§l│\"},{\"text\":\" §bUse §7/§1Easy§4Maintenance §7help §bfor help!\"}]";
				messages[7] = "§0§l└§f§l─§0§l─§f§l─§0§l─§f§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l├§f§l─§0§l─§f§l─§0§l┤";
				messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
				p.sendMessage(" ");
				p.sendMessage(" ");
				p.sendMessage(" ");
				p.sendMessage(" ");
				p.sendMessage(messages[0]);
				try {
					EasyMaintenance.getUtils().sendEventMessage(p, messages[1]);
					EasyMaintenance.getUtils().sendEventMessage(p, messages[2]);
					EasyMaintenance.getUtils().sendEventMessage(p, messages[3]);
					EasyMaintenance.getUtils().sendEventMessage(p, messages[4]);
					EasyMaintenance.getUtils().sendEventMessage(p, messages[5]);
					EasyMaintenance.getUtils().sendEventMessage(p, messages[6]);
				} catch (Exception e) {
					e.printStackTrace();
				}

				p.sendMessage(messages[7]);

				new EmTitleAnimation(p);

			} else {
				if (p.hasPermission("em.use")) {
					if (args[0].equalsIgnoreCase("help")) {
						String[] messages = new String[18];
						messages[0] = "§f§l┌§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l├§0§l─§f§l─§0§l─§f§l┤";
						messages[1] = "§0§l│ §3§l[Aliases: /em, /maintenance]";
						messages[2] = "§f§l│ §bUse §7/§1§lEasy§4§lMaintenance §7toggle §2[Time] §8§l-";
						messages[3] = "§0§l│ §bfor toggling the Status!";
						messages[4] = "§f§l│ §bUse §7/§1§lEasy§4§lMaintenance §7cancel §8§l-";
						messages[5] = "§0§l│ §bfor cancel the Countdown!";
						messages[6] = "§f§l│ §bUse §7/§1§lEasy§4§lMaintenance §7skip §2[Time] §8§l-";
						messages[7] = "§0§l│ §bfor skip the Countdown Time!";
						messages[8] = "§f§l│ §bUse §7/§1§lEasy§4§lMaintenance §7info §8§l-";
						messages[9] = "§0§l│ §bfor get information about the";
						messages[10] = "§f§l│ §bplugin/maintenance status!";
						messages[11] = "§0§l│ §bUse §7/§1§lEasy§4§lMaintenance §7debug §8§l-";
						messages[12] = "§f§l│ §bfor debug the plugin!";
						messages[13] = "§0§l│ §bUse §7/§1§lEasy§4§lMaintenance §7relaod §8§l-";
						messages[14] = "§f§l│ §bfor reload the Config!";
						messages[15] = "§0§l│ §bIf you toggle the Maintenance to on.";
						messages[16] = "§f§l│ §bThere Start a default Countdown of§3§l " + EasyMaintenance.getInstance().getConfig().getInt("DefaultCountdown") + " §bSeconds";
						messages[17] = "§0§l└§f§l─§0§l─§f§l─§0§l─§f§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l├§f§l─§0§l─§f§l─§0§l┤";
						messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						for (String message : messages) p.sendMessage(message);
                         /*
                        TOGGLE MAINTENANCE --- /EM TOGGLE +-+ PLAYER +-+
                         */
					} else if (args[0].equalsIgnoreCase("toggle")) {
						EasyMaintenance.getMaintenanceManager().toggleCountdown(p, args);

                                /*
                                STOP COUNTDOWN --- /EM CANCEL +-+ PLAYER +-+
                                 */
					} else if (args[0].equalsIgnoreCase("cancel")) {
						EasyMaintenance.getMaintenanceManager().cancelCountdown(p);

                                /*
                                SKIP --- /EM SKIP +-+ PLAYER +-+
                                 */
					} else if (args[0].equalsIgnoreCase("skip")) {
						EasyMaintenance.getMaintenanceManager().skipCountdown(p, args);

                                /*
                                INFO --- /EM INFO
                                 */
					} else if (args[0].equalsIgnoreCase("info")) {
						String[] messages = new String[9];
						messages[0] = "§0§l┌§f§l─§0§l─§f§l─§0§l─§f§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l├§f§l─§0§l─§f§l─§0§l┤";
						if (EasyMaintenance.getUtils().getInMaintenance()) {
							messages[1] = "§f§l│ §3§lStatus: §4§lThe Server is currently";
							messages[2] = "§0§l│ §4§lin Maintenance";
						} else {
							messages[1] = "§f§l│ §3§lStatus: §2§lThe Server isn't currently";
							messages[2] = "§0§l│ §2§lin Maintenance";
						}
						messages[3] = "§f§l│ §3§lName: §b" + EasyMaintenance.getInstance().getDescription().getName();
						messages[4] = "§0§l│ §3§lAuthor: §b" + EasyMaintenance.getInstance().getDescription().getAuthors().get(0);
						messages[5] = "§f§l│ §3§lVersion: §b" + EasyMaintenance.getInstance().getDescription().getVersion();
						messages[6] = "§0§l│ §3§lSoft Depend: §b" + EasyMaintenance.getInstance().getDescription().getSoftDepend().get(0);
						messages[7] = "§f§l│ §3§lDescription: §b" + EasyMaintenance.getInstance().getDescription().getDescription();
						messages[8] = "§0§l└§f§l─§0§l─§f§l─§0§l─§f§l┤§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l├§f§l─§0§l─§f§l─§0§l┤";
						messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						for (String message : messages) p.sendMessage(message);

                                /*
                                DEBUG --- /EM DEBUG +-+ PLAYER +-+
                                 */
					} else if (args[0].equalsIgnoreCase("debug")) {
						String[] messages = new String[12];
						messages[0] = "§0§l┌§f§l─§0§l─§f§l─§0§l─§f§l┤§1§lEasy§4§lMaintenance §3§lDEBUG§0§l├§f§l─§0§l─§f§l─§0§l─§f§l─§0§l─§f§l┤";
						messages[1] = "§f§l│ §3§lJava Version: §b" + System.getProperty("java.version");
						messages[2] = "§0§l│ §3§lJava Class Path: §b" + System.getProperty("java.class.path");
						messages[3] = "§f§l│ §3§lJava Home Path:";
						messages[4] = "§0§l│ §b" + System.getProperty("java.home");
						messages[5] = "§f§l│ §3§lJava Vendor: §b" + System.getProperty("java.vendor");
						messages[6] = "§0§l│ §3§lOS Name: §b" + System.getProperty("os.name");
						messages[7] = "§f§l│ §3§lOS Version: §b" + System.getProperty("os.version");
						messages[8] = "§0§l│ §3§lOS Architecture: §b" + System.getProperty("os.arch");
						messages[9] = "§f§l│ §3§lMinecraft Version: §b" + EasyMaintenance.getUtils().getCurrentVersionAsString();
						messages[10] = "§0§l│ §3§lPlugin Version: §b" + EasyMaintenance.getInstance().getDescription().getVersion();
						messages[11] = "§f§l└§0§l─§f§l─§0§l─§f§l─§0§l┤§1§lEasy§4§lMaintenance §3§lDEBUG§f§l├§0§l─§f§l─§0§l─§f§l─§0§l─§f§l─§0§l┤";
						messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						p.sendMessage(" ");
						for (String message : messages) p.sendMessage(message);
                                /*
                            RELOAD CONFIG --- /EM RELOAD +-+ PLAYER +-+
                             */
					} else if (args[0].equalsIgnoreCase("reload")) {
						EasyMaintenance.getDefaultConfig().reloadConfig();
						if (EasyMaintenance.getDonatorChecker().hasDonator())
							EasyMaintenance.getDonatorConfig().reloadConfig();
						Bukkit.getScheduler().scheduleAsyncDelayedTask(EasyMaintenance.getInstance(), () -> p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ReloadConfig", true)), 20 * 3);
					} else p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
				} else p.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("NoPermission", true));
			}

            /*
            -------------------------------- CONSOLE ------------------------------------------------------------------
            */

		} else {
			if (args.length == 0) {
				String[] messages = new String[8];
				messages[0] = "§f§l|§0§l-§f§l-§0§l-§f§l-§0§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l|§0§l-§f§l-§0§l-§f§l-§0§l-§f§l-§0§l|";
				messages[1] = "§0§l| §3§lPlugin Author: §bLuc1412";
				messages[2] = "§f§l| §3§lPlugin Version: §b" + EasyMaintenance.getInstance().getDescription().getVersion();
				messages[3] = "§0§l| §9Twitter: @luc141201 §7(https://twitter.com/luc141201)";
				messages[4] = "§f§l| §3Discord: Luc1412 Support §7(https://discord.gg/mqrJ9qG)";
				messages[5] = "§0§l| §6Donator Version: §eSpigotmc.org §7(https://www.spigotmc.org/resources/easymaintenance-bukkit-spigot-version.45348/)";
				messages[6] = "§f§l| §bUse §7/§1Easy§4Maintenance §7help §bfor help!";
				messages[7] = "§0§l|§f§l-§0§l-§f§l-§0§l-§f§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l|§f§l-§0§l-§f§l-§0§l-§f§l-§0§l-§f§l|";
				messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
				sender.sendMessage(" ");
				sender.sendMessage(" ");
				sender.sendMessage(" ");
				sender.sendMessage(" ");
				for (String message : messages) sender.sendMessage(message);
			} else {
				if (args[0].equalsIgnoreCase("help")) {
					String[] messages = new String[17];
					messages[0] = "§f§l|§0§l-§f§l-§0§l-§f§l-§0§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l|§0§l-§f§l-§0§l-§f§l-§0§l-§f§l-§0§l|";
					messages[1] = "§0§l| §3§l[Aliases: /em, /maintenance]";
					messages[2] = "§f§l| §bUse §7/§1§lEasy§4§lMaintenance §7toggle §2[Time] §8§l-";
					messages[3] = "§0§l| §bfor toggling the Status!";
					messages[4] = "§f§l| §bUse §7/§1§lEasy§4§lMaintenance §7cancel §8§l-";
					messages[5] = "§0§l| §bfor cancel the Countdown!";
					messages[6] = "§f§l| §bUse §7/§1§lEasy§4§lMaintenance §7skip §2[Time] §8§l-";
					messages[7] = "§0§l| §bfor skip the Countdown Time!";
					messages[8] = "§f§l| §bUse §7/§1§lEasy§4§lMaintenance §7info §8§l-";
					messages[9] = "§0§l| §bfor get information about the plugin/maintenance status!";
					messages[11] = "§0§l| §bUse §7/§1§lEasy§4§lMaintenance §7debug §8§l-";
					messages[12] = "§f§l| §bfor debug the plugin!";
					messages[12] = "§f§l| §bUse §7/§1§lEasy§4§lMaintenance §7relaod §8§l-";
					messages[13] = "§0§l| §bfor reload the Config!";
					messages[14] = "§f§l| §bIf you toggle the Maintenance to on.";
					messages[15] = "§0§l| §bThere Start a default Countdown of§3§l " + EasyMaintenance.getInstance().getConfig().getInt("DefaultCountdown") + " §bSeconds";
					messages[16] = "§f§l|§0§l-§f§l-§0§l-§f§l-§0§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l|§0§l-§f§l-§0§l-§f§l-§0§l-§f§l-§0§l|";
					messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					for (String message : messages) sender.sendMessage(message);
                         /*
                        TOGGLE MAINTENANCE --- /EM TOGGLE +-+ CONSOLE +-+
                         */
				} else if (args[0].equalsIgnoreCase("toggle")) {
					EasyMaintenance.getMaintenanceManager().toggleCountdown(sender, args);

                                /*
                                STOP COUNTDOWN --- /EM CANCEL +-+ CONSOLE +-+
                                 */
				} else if (args[0].equalsIgnoreCase("cancel")) {
					EasyMaintenance.getMaintenanceManager().cancelCountdown(sender);

                                /*
                                SKIP --- /EM SKIP +-+ CONSOLE +-+
                                 */
				} else if (args[0].equalsIgnoreCase("skip")) {
					EasyMaintenance.getMaintenanceManager().skipCountdown(sender, args);

                                /*
                                INFO --- /EM INFO +-+ CONSOLE +-+
                                 */
				} else if (args[0].equalsIgnoreCase("info")) {
					String[] messages = new String[8];
					messages[0] = "§0§l|§f§l-§0§l-§f§l-§0§l-§f§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§0§l|§f§l-§0§l-§f§l-§0§l-§f§l-§0§l-§f§l|";
					if (EasyMaintenance.getUtils().getInMaintenance()) {
						messages[1] = "§f§l| §3§lStatus: §4§lThe Server is currenly in Maintenance";
					} else messages[1] = "§f§l| §3§lStatus: §2§lThe Server isn't currenly in Maintenance";
					messages[2] = "§0§l| §3§lName: §b" + EasyMaintenance.getInstance().getDescription().getName();
					messages[3] = "§f§l| §3§lAuthor: §b" + EasyMaintenance.getInstance().getDescription().getAuthors().get(0);
					messages[4] = "§0§l| §3§lVersion: §b" + EasyMaintenance.getInstance().getDescription().getVersion();
					messages[5] = "§f§l| §3§lSoft Depend: §b" + EasyMaintenance.getInstance().getDescription().getSoftDepend().get(0);
					messages[6] = "§0§l| §3§lDescription: §b" + EasyMaintenance.getInstance().getDescription().getDescription();
					messages[7] = "§f§l|§0§l-§f§l-§0§l-§f§l-§0§l|§1§lEasy§4§lMaintenance §3§lby Luc1412§f§l|§0§l-§f§l-§0§l-§f§l-§0§l-§f§l-§0§l|";
					messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					for (String message : messages) sender.sendMessage(message);

                        /*
                        DEBUG --- /EM DEBUG +-+ CONSOLE +-+
                         */
				} else if (args[0].equalsIgnoreCase("debug")) {
					String[] messages = new String[11];
					messages[0] = "§0§l|§f§l-§0§l-§f§l-§0§l-§f§l|§1§lEasy§4§lMaintenance §3§lDEBUG§0§l|§f§l-§0§l-§f§l-§0§l-§f§l-§0§l-§f§l|";
					messages[1] = "§f§l| §3§lJava Version: §b" + System.getProperty("java.version");
					messages[2] = "§0§l| §3§lJava Class Path: §b" + System.getProperty("java.class.path");
					messages[3] = "§f§l| §3§lJava Home Path: §b " + System.getProperty("java.home");
					messages[4] = "§0§l| §3§lJava Vendor: §b" + System.getProperty("java.vendor");
					messages[5] = "§f§l| §3§lOS Name: §b" + System.getProperty("os.name");
					messages[6] = "§0§l| §3§lOS Version: §b" + System.getProperty("os.version");
					messages[7] = "§f§l| §3§lOS Architecture: §b" + System.getProperty("os.arch");
					messages[8] = "§0§l| §3§lMinecraft Version: §b" + EasyMaintenance.getUtils().getCurrentVersionAsString();
					messages[9] = "§f§l| §3§lPlugin Version: §b" + EasyMaintenance.getInstance().getDescription().getVersion();
					messages[10] = "§0§l|§f§l-§0§l-§f§l-§0§l-§f§l|§1§lEasy§4§lMaintenance §3§lDEBUG§0§l|§f§l-§0§l-§f§l-§0§l-§f§l-§0§l-§f§l|";
					messages = EasyMaintenance.getUtils().translateMessagesToCfgValues(messages);
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					sender.sendMessage(" ");
					for (String message : messages) sender.sendMessage(message);
                                /*
                            RELOAD CONFIG --- /EM RELOAD +-+ CONSOLE +-+
                             */
				} else if (args[0].equalsIgnoreCase("reload")) {
					EasyMaintenance.getDefaultConfig().reloadConfig();
					if (EasyMaintenance.getDonatorChecker().hasDonator())
						EasyMaintenance.getDonatorConfig().reloadConfig();
					Bukkit.getScheduler().scheduleAsyncDelayedTask(EasyMaintenance.getInstance(), () -> sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ReloadConfig", true)), 20 * 3);
				} else sender.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Usage", true));
			}
		}
		return false;
	}
}
