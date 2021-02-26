package mk.plugin.playerdata.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mk.plugin.playerdata.main.MainPlayerData;
import mk.plugin.playerdata.storage.PlayerDataAPI;

public class DataListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		Bukkit.getScheduler().runTaskLaterAsynchronously(MainPlayerData.plugin, () -> {
			PlayerDataAPI.saveAndClearCache(player.getName());
			MainPlayerData.plugin.getLogger().info("§6Saved data " + player.getName());
		}, 5);
	}
	
}
