package de.luc1412.em.versions.v1_8_r2;

import com.google.gson.JsonSyntaxException;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Luc1412 on 02.08.2017 at 18:54
 */
public class JsonChatManager {

	public void sendEventMessage(Player p, String json) {
		try {
			IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(json);
			PacketPlayOutChat packet = new PacketPlayOutChat(icbc, (byte) 1);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		} catch (JsonSyntaxException e) {
		}
	}
}
