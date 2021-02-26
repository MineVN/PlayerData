package mk.plugin.playerdata.storage;

import java.util.Map;

import mk.plugin.playerdata.file.DataFileUtils;
import mk.plugin.playerdata.main.MainPlayerData;

public class PlayerData extends Data {
	
	private String name;
	
	public PlayerData(String name, Map<String, String> data) {
		super(data);
		this.name = name;
	}
	
	public PlayerData(String name, Map<String, String> data, String hook) {
		super(data, hook);
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void save() {
		DataFileUtils.save(MainPlayerData.plugin, this, this.getName());
	}
	
}
