package de.luc1412.em.commands;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luc1412 on 07.10.2017 at 09:11
 */
public class CommandCooldownManager {

	private HashMap<Player, Long> cooldownPlayer;

	public CommandCooldownManager() {
		cooldownPlayer = new HashMap<>();
	}

	public void addPlayer(Player p) {
		if (!cooldownPlayer.containsKey(p)) {
			long currentMillis = System.currentTimeMillis();
			long cooldownMillis = currentMillis + TimeUnit.SECONDS.toMillis(60);
			cooldownPlayer.put(p, cooldownMillis);
		}
	}

	public long isPlayerInCooldown(Player p) {
		if (cooldownPlayer.containsKey(p)) {
			long currentMillis = System.currentTimeMillis();
			long playerMillis = cooldownPlayer.get(p);
			if (currentMillis >= playerMillis) {
				cooldownPlayer.remove(p);
			} else {
				long out = (currentMillis - playerMillis) * -1;
				out = TimeUnit.MILLISECONDS.toSeconds(out);
				return out;
			}
		}
		return 0L;
	}

}
