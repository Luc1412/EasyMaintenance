package de.luc1412.em.addons;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.luc1412.em.utils.ProtocolLibManager;

/**
 * Created by Luc1412 on 28.07.2017 at 19:09
 */
public class ADDONProtocolLib {

	private ProtocolManager protocolManager;
	private ProtocolLibManager protocolLibManager;

	public ADDONProtocolLib() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolLibManager = new ProtocolLibManager();
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public ProtocolLibManager getProtocolLibManager() {
		return protocolLibManager;
	}
}
