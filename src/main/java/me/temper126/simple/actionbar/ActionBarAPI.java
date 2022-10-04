package me.temper126.simple.actionbar;

import me.temper126.simple.utils.SimpleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionBarAPI {

	private static String nmsver;
	private static boolean useOldMethods;

	public static void enableActionBarAPI(Server server) {
		ActionBarAPI.nmsver = server.getClass().getPackage().getName();
		ActionBarAPI.nmsver = ActionBarAPI.nmsver.substring(ActionBarAPI.nmsver.lastIndexOf(".") + 1);
		if (ActionBarAPI.nmsver.equalsIgnoreCase("v1_8_R1") || ActionBarAPI.nmsver.startsWith("v1_7_")) {
			ActionBarAPI.useOldMethods = true;
		}
	}

	public static void sendActionBar(Player player, String message) {
		if (!player.isOnline()) {
			return;
		}
		message = SimpleUtil.color(message);
		final ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
		Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
		if (actionBarMessageEvent.isCancelled()) {
			return;
		}
		try {
			final Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + ActionBarAPI.nmsver + ".entity.CraftPlayer");
			final Object craftPlayer = craftPlayerClass.cast(player);
			final Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".PacketPlayOutChat");
			final Class<?> packetClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".Packet");
			Object packet;
			if (ActionBarAPI.useOldMethods) {
				final Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatSerializer");
				final Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
				final Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
				final Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
				packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(cbc, 2);
			} else {
				final Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatComponentText");
				final Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".IChatBaseComponent");
				try {
					final Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + ActionBarAPI.nmsver + ".ChatMessageType");
					final Object[] chatMessageTypes = (Object[]) chatMessageTypeClass.getEnumConstants();
					Object chatMessageType = null;
					for (final Object obj : chatMessageTypes) {
						if (obj.toString().equals("GAME_INFO")) {
							chatMessageType = obj;
						}
					}
					final Object chatCompontentText = chatComponentTextClass.getConstructor(String.class).newInstance(message);
					packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, chatMessageTypeClass).newInstance(chatCompontentText, chatMessageType);
				} catch (ClassNotFoundException cnfe) {
					final Object chatCompontentText2 = chatComponentTextClass.getConstructor(String.class).newInstance(message);
					packet = packetPlayOutChatClass.getConstructor(iChatBaseComponentClass, Byte.TYPE).newInstance(chatCompontentText2, (byte) 2);
				}
			}
			final Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
			final Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer, new Object[0]);
			final Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			final Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			final Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendActionBar(Plugin plugin, Player player, String message, int duration) {
		sendActionBar(player, message);
		if (duration >= 0) {
			new BukkitRunnable() {
				@Override
				public void run() {
					ActionBarAPI.sendActionBar(player, "");
				}
			}.runTaskLater(plugin, (long) (duration + 1));
		}
		while (duration > 40) {
			duration -= 40;
			new BukkitRunnable() {
				@Override
				public void run() {
					ActionBarAPI.sendActionBar(player, message);
				}
			}.runTaskLater(plugin, (long) duration);
		}
	}

	public static void sendActionBarToAllPlayers(Plugin plugin, String message) {
		sendActionBarToAllPlayers(plugin, message, -1);
	}

	public static void sendActionBarToAllPlayers(Plugin plugin, String message, int duration) {
		for (final Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(plugin, p, message, duration);
		}
	}


}
