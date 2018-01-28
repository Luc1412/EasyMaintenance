package de.luc1412.em.versions.v1_8_r2;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 06.10.2017 at 17:28
 */
public class TitleManager {

	public void sendTitle(Player p, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"), fadeInTime, stayTime, fadeOutTime);
		sendPacket(p, titlePacket);

		titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"));
		sendPacket(p, titlePacket);

		if (subtitle != null) {
			PacketPlayOutTitle subTitlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"), fadeInTime, stayTime, fadeOutTime);
			sendPacket(p, subTitlepacket);

			subTitlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"));
			sendPacket(p, subTitlepacket);
		}
	}

	private void sendPacket(Player p, Packet packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
