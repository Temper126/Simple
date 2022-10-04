package me.temper126.simple.cooldown;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class ExampleCooldownMap {

	// init the cooldownmap with a settings ID.
	public static final String COOLDOWN_ID = "block-break";
	private PlayerCooldownMap playerCooldownMap = new PlayerCooldownMap(COOLDOWN_ID);

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		// Check if the player is on a cooldown.
		if (playerCooldownMap.isOnCooldown(player, COOLDOWN_ID)) {
			String timeRemaining = playerCooldownMap.getRemainingTimeFormatted(player, COOLDOWN_ID);
			player.sendMessage("You must wait " + timeRemaining + " before breaking another block!");
			event.setCancelled(true);
		} else {
			// Add the player to the cooldown, duration in millis
			playerCooldownMap.add(player, 5000);
		}
	}


}

