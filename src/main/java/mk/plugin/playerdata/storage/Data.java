package mk.plugin.playerdata.storage;

import java.util.HashMap;
import java.util.Map;

import mk.plugin.playerdata.main.MainPlayerData;

public abstract class Data {
	
	private String plugin;
	private Map<String, String> data = new HashMap<String, String> ();
	
	public Data(Map<String, String> data) {
		this.data = data;
		this.plugin = null;
	}
	
	public Data(Map<String, String> data, String plugin) {
		this.data = data;
		this.plugin = plugin;
	}
	
	public Map<String, String> getDataMap() {
		return this.data;
	}
	
	public boolean hasData(String id) {
		return this.data.containsKey(id.replace(".", "-"));
	}
	
	public String getValue(String id) {
		if (!hasData(id)) return null;
		return this.data.get(id.replace(".", "-"));
	}
	
	public void set(String id, String value) {
		if (value == null) {
			data.remove(id.replace(".", "-"));
			return;
		}
		data.put(id.replace(".", "-"), value);
	}
	
	public void remove(String id) {
		data.remove(id.replace(".", "-"));
	}
	
	public String getPlugin() {
		if (plugin == null) return MainPlayerData.plugin.getName();
		return this.plugin;
	}
	
	public boolean isDefaultPlugin() {
		return this.plugin == null;
	}
}
