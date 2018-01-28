package de.luc1412.em.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import de.luc1412.em.EasyMaintenance;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Luc1412 on 19.07.2017 at 23:57
 */
public class ProtocolLibManager {

	public void changeServerList() {
		EasyMaintenance.getAddonProtocolLib().getProtocolManager().addPacketListener(new PacketAdapter(EasyMaintenance.getInstance(), ListenerPriority.HIGH, Collections.singletonList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
			@Override
			public void onPacketSending(PacketEvent e) {
				if (EasyMaintenance.getUtils().getInMaintenance())
					handlePingAsync(e.getPacket().getServerPings().read(0));
			}
		});
	}

	private void handlePingAsync(WrappedServerPing ping) {
		ping.setVersionProtocol(-1);
		ping.setVersionName(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ServerPingName", false));

		if (!EasyMaintenance.getDonatorChecker().hasDonator()) {

			ping.setPlayers(Arrays.asList(
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line1", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line2", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line3", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line4", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line5", false))));

		} else {

			ping.setPlayers(Arrays.asList(
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line1", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line2", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line3", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line4", false)),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ListHoverText.Line5", false)),

					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getDonatorConfig().getString("ListHoverText.Line6")),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getDonatorConfig().getString("ListHoverText.Line7")),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getDonatorConfig().getString("ListHoverText.Line8")),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getDonatorConfig().getString("ListHoverText.Line9")),
					new WrappedGameProfile(UUID.randomUUID(), EasyMaintenance.getDonatorConfig().getString("ListHoverText.Line10"))));

		}
	}
}
