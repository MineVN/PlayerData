package mk.plugin.playerdata.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import mk.plugin.playerdata.main.MainPlayerData;
import mk.plugin.playerdata.storage.GlobalData;
import mk.plugin.playerdata.storage.PlayerData;

public class DataFileUtils {
	
	public static void checkFolder(Plugin plugin) {
		new File(plugin.getDataFolder() + "//player").mkdirs();
	}
	
	public static File checkFolder(Plugin plugin, String hook) {
		File file = new File(plugin.getDataFolder() + "//hook//" + hook.toLowerCase());
		file.mkdirs();
		return file;
	}
	
	public static File checkFile(Plugin plugin, String name) {
		checkFolder(plugin);
		File dF = new File(plugin.getDataFolder() + "//player//" + name + ".yml");
		if (!dF.exists())
			try {
				dF.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return dF;
	}
	
	public static File checkFile(Plugin plugin, String name, String hook) {
		checkFolder(plugin, hook);
		File dF = new File(plugin.getDataFolder() + "//hook//" + hook.toLowerCase() + "//" + name + ".yml");
		if (!dF.exists())
			try {
				dF.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return dF;
	}
	
	public static File checkGlobalFile(Plugin plugin) {
		File dF = new File(plugin.getDataFolder()+ "//global.yml");
		if (!dF.exists())
			try {
				dF.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return dF;
	}
	
	private static void save(File file, PlayerData data) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> map = data.getDataMap();
		map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

		config.getKeys(false).forEach(k -> {
			config.set(k, null);
		});
		
		map.forEach((id, value) -> {
			config.set(id, value);
		});
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(Plugin plugin, PlayerData data, String name) {
		File file = data.isDefaultPlugin() ? checkFile(plugin, name) : checkFile(plugin, name, data.getPlugin());
		save(file, data);
	}
	
	public static void saveGlobal(Plugin plugin) {
		GlobalData data = MainPlayerData.globalData;
		File file = checkGlobalFile(plugin);
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		config.getKeys(false).forEach(k -> {
			config.set(k, null);
		});
		
		data.getDataMap().forEach((id, value) -> {
			config.set(id, value);
		});
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static PlayerData load(String name, File file, String hook) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> map = new HashMap<String, String> ();
		config.getConfigurationSection("").getKeys(false).forEach(id -> {
			String value = config.getString(id);
			map.put(id, value);
		});
		
		PlayerData data = new PlayerData(name, map, hook);
		
		return data;
	}
	
	public static PlayerData load(Plugin plugin, String name) {
		File file = checkFile(plugin, name);
		return load(name, file, null);
	}
	
	public static PlayerData load(Plugin plugin, String name, String hook) {
		File file = checkFile(plugin, name, hook);
		return load(name, file, hook);
	}
	 
	public static GlobalData loadGlobal(Plugin plugin) {
		File file = checkGlobalFile(plugin);
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> map = new HashMap<String, String> ();
		config.getConfigurationSection("").getKeys(false).forEach(id -> {
			String value = config.getString(id);
			map.put(id, value);
		});
		
		GlobalData data = new GlobalData(map);
		
		return data;
	}
	
}
