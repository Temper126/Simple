package me.temper126.simple.debugger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ExampleDebug {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Debugger db = new Debugger("Join Debug");

		for (double d = 0; d < 5000; d += 0.5) {
			player.sendMessage("The number is: " + d);
		}

		// Print the amount of total time its taken since the Debugger was created.
		db.print("Finish the loop");
	}
}
