package mk.plugin.playerdata.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import mk.plugin.playerdata.command.PDCommand;
import mk.plugin.playerdata.file.DataFileUtils;
import mk.plugin.playerdata.listener.DataListener;
import mk.plugin.playerdata.storage.GlobalData;
import mk.plugin.playerdata.storage.PlayerDataAPI;

public class MainPlayerData extends JavaPlugin {
	
	public static MainPlayerData plugin;
	
	public static GlobalData globalData;
	
	@Override
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig();
		
		this.getCommand("playerdata").setExecutor(new PDCommand());
		
		Bukkit.getPluginManager().registerEvents(new DataListener(), this);
		
		globalData = DataFileUtils.loadGlobal(this);
		Bukkit.getOnlinePlayers().forEach(player -> {
			PlayerDataAPI.loadData(player);
		});
	}
	
	@Override
	public void onDisable() {
		PlayerDataAPI.saveGlobalData();
		Bukkit.getOnlinePlayers().forEach(player -> {
			PlayerDataAPI.saveData(player);
		});
	}
	
	
}
