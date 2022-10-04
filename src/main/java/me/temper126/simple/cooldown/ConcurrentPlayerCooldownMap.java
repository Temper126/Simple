package me.temper126.simple.cooldown;

import com.google.common.collect.Maps;
import me.temper126.simple.utils.TimeUtil;
import org.bukkit.entity.Player;

import java.util.Map;

public class ConcurrentPlayerCooldownMap {

	private final Map<String, Long> playerMap = Maps.newConcurrentMap();
	private String defaultCooldownId;

	public ConcurrentPlayerCooldownMap(String defaultCooldownId) {
		this.defaultCooldownId = defaultCooldownId;
	}

	/**
	 * Add a player to the cooldown map
	 *
	 * @param player     - The player being added.
	 * @param cooldownId - The ID of the cooldown.
	 * @param duration   - The duration of the cooldown.
	 */

	public void add(Player player, String cooldownId, long duration) {
		playerMap.put(player.getName() + cooldownId, System.currentTimeMillis() + duration);
	}

	public void add(Player player, long duration) {
		add(player, defaultCooldownId, duration);
	}

	public boolean isOnCooldown(Player player, String cooldownId) {
		return getRemainingMillis(player, cooldownId) > 1;
	}

	public long getRemainingMillis(Player player, String cooldownId) {
		if (!playerMap.containsKey(player.getName() + cooldownId)) {
			return 0;
		}
		return playerMap.get(player.getName() + cooldownId) - System.currentTimeMillis();
	}

	public int getRemainingSeconds(Player player, String cooldownId) {
		return (int) (getRemainingMillis(player, cooldownId) / 1000);
	}

	public String getRemainingTimeFormatted(Player player, String cooldownId) {
		return TimeUtil.getTime(getRemainingSeconds(player, cooldownId));
	}

}

