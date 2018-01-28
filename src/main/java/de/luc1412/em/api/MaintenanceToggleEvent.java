package de.luc1412.em.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Luc1412 on 21.08.2017 at 00:38
 */
public class MaintenanceToggleEvent extends Event implements Cancellable {

	public static HandlerList handlerList = new HandlerList();

	public boolean canceled = false;

	private boolean maintenance;

	public MaintenanceToggleEvent(boolean maintenance) {
		this.maintenance = maintenance;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.canceled = canceled;
	}


	public boolean getMaintenance() {
		return maintenance;
	}


}
