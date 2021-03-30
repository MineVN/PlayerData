package mk.plugin.playerdata.backup;

import mk.plugin.playerdata.main.MainPlayerData;
import org.bukkit.scheduler.BukkitRunnable;

public class BackupTask extends BukkitRunnable {

    private BackupTask() {}

    @Override
    public void run() {
        if (Backups.needBackup()) Backups.backup();
    }

    public static void start() {
        new BackupTask().runTaskTimerAsynchronously(MainPlayerData.plugin, 0, 20);
    }

}
