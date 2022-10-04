package me.temper126.simple.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleUtil {

	public static String formatDisplayName(ItemStack item) {
		String displayName = item.getItemMeta().getDisplayName();
		if (displayName == null) displayName = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));

		return displayName;
	}

	public static ItemStack createItem(Material material, String name, int amount, int data, String... lore) {
		ItemStack item = new ItemStack(material, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(name));
		meta.setLore(color(Lists.newArrayList(lore)));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createItem(Material material, String name, String... lore) {
		return createItem(material, name, 1, 0, lore);
	}

	public static ItemStack createItem(Material material, String name, int amount, String... lore) {
		return createItem(material, name, amount, 1, lore);
	}

	public static ItemStack createItem(Material material, String name, int amount, int data, List<String> lore) {
		return createItem(material, name, amount, data, lore.toArray(new String[lore.size()]));
	}

	public static ItemStack createItem(Material material, String name, List<String> lore) {
		return createItem(material, name, lore.toArray(new String[lore.size()]));
	}

	public static ItemStack modifyItem(ItemStack item, ItemMeta meta) {
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack modifyItem(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(name));
		return modifyItem(item, meta);
	}

	public static ItemStack modifyItem(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(name));
		meta.setLore(color(lore));
		return modifyItem(item, meta);
	}

	public static int roundInventorySize(int count) {
		return (count + (9 - 1)) / 9 * 9;
	}

	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String stripColor(String message) {
		return ChatColor.stripColor(message);
	}

	public static List<String> color(List<String> messages, ChatColor color) {
		return messages.stream().map(str -> color + str).collect(Collectors.toList());
	}

	public static List<String> color(List<String> messages) {
		return messages.stream().map(SimpleUtil::color).collect(Collectors.toList());
	}

	public static void playSound(Player player, Sound sound) {
		player.playSound(player.getLocation(), sound, 1, 1);
	}

	public static void sendMessage(LivingEntity entity, String message) {
		entity.sendMessage(color(message));
	}

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(color(message));
	}

	public static void sendMessage(CommandSender sender, List<String> messages) {
		sender.sendMessage(SimpleUtil.color(messages).toArray(new String[messages.size()]));
	}

	public static void fillInventory(Inventory inventory, ItemStack item) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, item);
			}
		}
	}

	public static void fillInventory(Inventory inventory) {
		fillInventory(inventory, SimpleUtil.createItem(Material.STAINED_GLASS_PANE, "&7", 1, 15, "&7"));
	}

	public static boolean hasArmorOn(Player player) {
		for (ItemStack item : player.getInventory().getArmorContents()) {
			if (item == null) {
				continue;
			}
			if (item != null && item.getType() != Material.AIR) {
				return true;
			}
		}
		return false;
	}

	public static double extractDouble(String original) {
		String raw = ChatColor.stripColor(original);
		raw = raw.replaceAll("[\\s+a-zA-Z :]", "").replace("%", "");
		return tryParseDouble(raw);
	}

	public static int extractInt(String original) {
		String raw = ChatColor.stripColor(original);
		return tryParseInt(raw.replaceAll("[^0-9]", ""));
	}

	public static int tryParseInt(String unparsed) {
		try {
			return Integer.parseInt(unparsed);
		} catch (Exception e) {
			return -1;
		}
	}

	public static double tryParseDouble(String unparsed) {
		try {
			return Double.parseDouble(unparsed);
		} catch (Exception e) {
			return -1;
		}
	}

	public static String format(Number num) {
		return NumberFormat.getInstance().format(num);
	}

	public static String format(Number num, String pattern) {
		return new DecimalFormat(pattern).format(num);
	}

	public static String formatBoolean(boolean enabled) {
		return color(enabled ? "&a&lENABLED" : "&c&lDISABLED");
	}

}
