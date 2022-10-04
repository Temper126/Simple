package me.temper126.simple;

import lombok.Getter;
import me.temper126.simple.actionbar.ActionBarAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class SimplePlugin extends JavaPlugin {
	@Getter
	public static SimplePlugin instance;
	private Economy econ;


	@Override
	public void onEnable() {
		instance = this;
		this.setupEconomy();
		ActionBarAPI.enableActionBarAPI(Bukkit.getServer());

	}

	@Override
	public void onLoad() {
		System.out.println("Simple Loading UP!");
	}

	@Override
	public void onDisable() {

	}

	private void setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp != null) {
			this.econ = (Economy) rsp.getProvider();
		}

	}

	public Economy getEcon() {
		return this.econ;
	}


}
