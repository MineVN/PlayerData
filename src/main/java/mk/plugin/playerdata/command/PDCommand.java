package mk.plugin.playerdata.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import mk.plugin.playerdata.storage.PlayerData;
import mk.plugin.playerdata.storage.PlayerDataAPI;

public class PDCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if (!sender.hasPermission("playerdata.*")) return false;
		
		if (args.length == 0) {
			sendTut(sender);
			return false;
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			String player = args[1];
			PlayerData pd = PlayerDataAPI.getPlayerData(player);
			pd.set(args[2], args[3]);
			sender.sendMessage("Done!");
		}

		else if (args[0].equalsIgnoreCase("remove")) {
			String player = args[1];
			PlayerData pd = PlayerDataAPI.getPlayerData(player);
			pd.remove(args[2]);
			sender.sendMessage("Done!");
		}
		
		else if (args[0].equalsIgnoreCase("get")) {
			String player = args[1];
			PlayerData pd = PlayerDataAPI.getPlayerData(player);
			sender.sendMessage(pd.getValue(args[2]));
		}
		
		else if (args[0].equalsIgnoreCase("showplayers")) {
			PlayerDataAPI.getPlayerList(args[1]).forEach(p -> {
				sender.sendMessage("§a" + p);
			});
		}
		
		return false;
	}
	
	public void sendTut(CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage("§c§lPlayerData by MankaiStep");
		sender.sendMessage("§6/pd set <player> <key> <value>: §eSet value of player");
		sender.sendMessage("§6/pd remove <player> <key>: §eRemove data of player");
		sender.sendMessage("§6/pd get <player> <key>: §eShow data of player");
		sender.sendMessage("§6/pd showplayers <hook>: §eShow all players that storaged");
		sender.sendMessage("");
	}
	
	
	
}
