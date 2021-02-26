package mk.plugin.playerdata.storage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import mk.plugin.playerdata.file.DataFileUtils;
import mk.plugin.playerdata.main.MainPlayerData;

public class PlayerDataAPI {
	
	private static Map<String, PlayerData> dataMap = new HashMap<String, PlayerData> ();
	
	private static Map<String, Map<String, PlayerData>> pluginData = Maps.newHashMap();
	
	public static boolean hasData(String name) {
		return dataMap.containsKey(name);
	}
	
	public static boolean hasData(Player player) {
		return hasData(player.getName());
	}
	
	@Deprecated
	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getName());
	}
	
	@Deprecated
	public static PlayerData getPlayerData(String name) {
		if (!hasData(name)) loadData(name);
		return dataMap.get(name);
	}
	
	public static GlobalData getGlobalData() {
		return MainPlayerData.globalData;
	}
	
	public static void saveGlobalData() {
		DataFileUtils.saveGlobal(MainPlayerData.plugin);
	}
	
	@Deprecated
	public static void saveData(String name) {
		PlayerData data = getPlayerData(name);
		DataFileUtils.save(MainPlayerData.plugin, data, name);
	}
	
	@Deprecated
	public static void saveData(Player player) {
		saveData(player.getName());
	}
	
	public static void removeData(String name) {
		dataMap.remove(name);
	}
	
	public static void removeData(Player player) {
		dataMap.remove(player.getName());
	}
	
	public static void loadData(String name) {
		PlayerData data = DataFileUtils.load(MainPlayerData.plugin, name);
		dataMap.put(name, data);
	}
	
	public static void loadData(Player player) {
		loadData(player.getName());
	}

	
	/*
	 * New methods
	 */
	
	
	public static void load(String name, String hook) {
		PlayerData data = DataFileUtils.load(MainPlayerData.plugin, name, hook);
		dataMap.put(name, data);
	}
	
	public static PlayerData get(String player) {
		return get(player, null);
	}
	
	public static PlayerData get(Player player) {
		return get(player, null);
	}
	
	public static PlayerData get(Player player, String hook) {
		return get(player.getName(), hook);
	}
	
	public static PlayerData get(String name, String hook) {
		if (hook == null) {
			if (!hasData(name)) load(name, hook);
			return dataMap.get(name);
		}
		
		if (pluginData.containsKey(hook.toLowerCase())) {
			if (pluginData.get(hook.toLowerCase()).containsKey(name)) return pluginData.get(hook.toLowerCase()).get(name);
		}
		
		PlayerData data = DataFileUtils.load(MainPlayerData.plugin, name, hook);
		Map<String, PlayerData> plData = pluginData.getOrDefault(hook.toLowerCase(), Maps.newHashMap());
		plData.put(name, data);
		pluginData.put(hook.toLowerCase(), plData);
		
		return data;
	}
	
	public static void saveAndClearCache(String name) {
		// Save
		saveData(name);
		
		// Clear
		removeData(name);	
		for (String hook : Sets.newHashSet(pluginData.keySet())) {
			if (pluginData.get(hook).containsKey(name)) {
				pluginData.get(hook).get(name).save();
				pluginData.get(hook).remove(name);
			}
		}
	}
	
	public static List<String> getPlayerList(String hook) {
		File folder = DataFileUtils.checkFolder(MainPlayerData.plugin, hook);
		return Lists.newArrayList(folder.listFiles()).stream().map(f -> f.getName().replace(".yml", "")).collect(Collectors.toList());
	}
	
}
