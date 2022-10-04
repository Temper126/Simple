package me.temper126.simple.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationUtil {

	public static List<Player> getNearbyPlayers(Entity entity) {
		return getNearbyPlayers(entity.getLocation());
	}

	public static List<Player> getNearbyPlayers(Entity entity, double distance) {
		return getNearbyPlayers(entity.getLocation(), distance);
	}

	public static List<Player> getNearbyPlayers(Location location) {
		return getNearbyPlayers(location, 25.0);
	}

	public static List<Player> getNearbyPlayers(Location location, double distance) {
		double distanceSquared = distance * distance;
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> player.getWorld().getUID().equals(location.getWorld().getUID()))
				.filter(player -> player.getLocation().distanceSquared(location) < distanceSquared) // done for optimisation -- finding roots is costly.
				.collect(Collectors.toList());
	}

	public static List<Entity> getNearbyEntities(Location location, double distance) {
		return new ArrayList<>(location.getWorld().getNearbyEntities(location, distance, distance, distance));
	}

	public static List<Entity> getNearbyEntities(Entity entity, double distance) {
		return getNearbyEntities(entity.getLocation(), distance);
	}

	public static List<Entity> getNearbyEntities(Location location, EntityType entityType, double distance) {
		return getNearbyEntities(location, distance).stream().filter(entity -> entity.getType() == entityType).collect(Collectors.toList());
	}

	public static List<Entity> getNearbyEntities(Entity entity, EntityType entityType, double distance) {
		return getNearbyEntities(entity.getLocation(), entityType, distance);
	}

	public static List<Entity> getNearbyEntities(Location location, EntityType entityType, double x, double y, double z) {
		return location.getWorld().getNearbyEntities(location, x, y, z).stream().filter(entity -> entity.getType() == entityType).collect(Collectors.toList());
	}

	public static List<Entity> getNearbyEntities(Entity entity, EntityType entityType, double x, double y, double z) {
		return getNearbyEntities(entity.getLocation(), entityType, x, y, z);
	}

}
