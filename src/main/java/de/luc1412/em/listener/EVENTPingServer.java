package de.luc1412.em.listener;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;

/**
 * Created by Luc1412 on 28.07.2017 at 18:45
 */
public class EVENTPingServer implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onServerPing(ServerListPingEvent e) {
		if (EasyMaintenance.getUtils().getInMaintenance()) {
			if (EasyMaintenance.getAddonProtocolLib() != null)
				EasyMaintenance.getAddonProtocolLib().getProtocolLibManager().changeServerList();
			e.setMotd(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("MaintenanceMOTD", false));
			if (EasyMaintenance.getDefaultConfig().getBoolean("MaintenanceServerIcon")) {
				try {
					e.setServerIcon(Bukkit.loadServerIcon(new File(EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "maintenance-icon.png")));
				} catch (Exception exp) {
					exp.printStackTrace();
					EasyMaintenance.getUtils().sendError("CAN´T FIND THE \"maintenance-icon.png\" FILE OR NOT 64x64 PX BIG");
				}
			} else e.setServerIcon(Bukkit.getServerIcon());
		} else e.setMotd(EasyMaintenance.getInstance().getServer().getMotd());
	}
}
