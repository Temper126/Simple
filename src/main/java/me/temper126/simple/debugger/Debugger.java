package me.temper126.simple.debugger;

import me.temper126.simple.utils.SimpleUtil;
import org.bukkit.Bukkit;

public class Debugger {

	private long startTime;
	private String prefix;

	public Debugger(String prefix) {
		this.prefix = prefix;
		this.startTime = System.nanoTime();
	}

	public void print(Object message) {
		SimpleUtil.sendMessage(Bukkit.getConsoleSender(), "&d[ID-DEBUG] &2(" + prefix + ") &a" + message.toString());
	}

	public void printWithTime(Object message) {
		long now = System.nanoTime();
		double time = (now - startTime) / 1000000.0;
		print("&d[ID-DEBUG] &2(" + prefix + ") &a" + message.toString() + " &7[" + time + "ms]");
	}

}
