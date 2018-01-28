package de.luc1412.em.api;

import de.luc1412.em.EasyMaintenance;

/**
 * Created by Luc1412 on 04.12.2017 at 21:55
 */
public class EasyMaintenanceAPI {

	/**
	 * This method show you, if the server is in maintenance.
	 *
	 * @return a boolean wich returns true if the server is in maintenance
	 * and false if not.
	 */
	public boolean getInMaintenance() {
		return EasyMaintenance.getUtils().getInMaintenance();
	}

	/**
	 * Toggele the maintenance state.
	 */
	public void toggleMaintenance() {
		EasyMaintenance.getUtils().toggleMaintenance();
	}

	/**
	 * Set the maintenance state
	 *
	 * @param state This is the state in wich you set the Server
	 */
	public void setMaintenance(boolean state) {
		if (getInMaintenance()) {
			if (!state) {
				toggleMaintenance();
			}
		} else {
			if (state) {
				toggleMaintenance();
			}
		}
	}

}
