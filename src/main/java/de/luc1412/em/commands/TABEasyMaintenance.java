package de.luc1412.em.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Luc1412 on 29.07.2017 at 10:25
 */
public class TABEasyMaintenance implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].startsWith("c")) {
				return Collections.singletonList("cancel");
			} else if (args[0].startsWith("d")) {
				return Collections.singletonList("debug");
			} else if (args[0].startsWith("h")) {
				return Collections.singletonList("help");
			} else if (args[0].startsWith("i")) {
				return Collections.singletonList("info");
			} else if (args[0].startsWith("r")) {
				return Collections.singletonList("reload");
			} else if (args[0].startsWith("s")) {
				return Collections.singletonList("skip");
			} else if (args[0].startsWith("t")) {
				return Collections.singletonList("toggle");
			} else return Arrays.asList("cancel", "help", "info", "reload", "skip", "toggle");
		}
		return null;
	}
}
