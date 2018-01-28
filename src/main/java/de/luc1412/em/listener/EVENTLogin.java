package de.luc1412.em.listener;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Luc1412 on 28.07.2017 at 10:32
 */
public class EVENTLogin implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e) {
		if (EasyMaintenance.getUtils().getInMaintenance()) {
			Player player = e.getPlayer();
			if (!player.hasPermission("em.bypass")) {
				e.setKickMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("KickMessage", false));
				e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			} else e.setResult(PlayerLoginEvent.Result.ALLOWED);
		}
	}
}
