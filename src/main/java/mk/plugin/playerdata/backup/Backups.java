package mk.plugin.playerdata.backup;

import com.google.common.collect.Lists;
import mk.plugin.playerdata.main.MainPlayerData;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Backups {

    private static boolean player;
    private static boolean global;
    private static List<String> hooks = Lists.newArrayList();

    public static void reload(FileConfiguration config) {
        player = config.getBoolean("backup.player");
        global = config.getBoolean("backup.global");
        hooks = config.getStringList("backup.hooks");
    }

    public static String getNowKey() {
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonth().getValue();
        int year = LocalDate.now().getYear();
        return day + " " + month + " " + year;
    }

    public static boolean needBackup() {
        var key = getNowKey();
        var folder2 = new File(MainPlayerData.plugin.getDataFolder() + "//backup//" + key);
        return !folder2.exists();
    }

    public static void backup() {
        // Check folder
        var folder = new File(MainPlayerData.plugin.getDataFolder() + "//backup");
        if (!folder.exists()) folder.mkdirs();

        // Get Date
        var name = getNowKey();

        // Check exist
        var folder2 = new File(MainPlayerData.plugin.getDataFolder() + "//backup//" + name);
        if (folder2.exists()) return;
        folder2.mkdirs();

        String path = MainPlayerData.plugin.getDataFolder() + "//backup//" + name;

        // Player
        if (player) {
            var sourceFolder = new File(MainPlayerData.plugin.getDataFolder() + "//player");
            var toFolder = new File(path + "//player");
            try {
                FileUtils.copyDirectory(sourceFolder, toFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Global
        if (global) {
            var source = new File(MainPlayerData.plugin.getDataFolder() + "//global.yml");
            var to = new File(path + "//global.yml");
            try {
                FileUtils.copyFile(source, to);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Hook
        for (String hook : hooks) {
            var sourceFolder = new File(MainPlayerData.plugin.getDataFolder() + "//hook//" + hook);
            if (!sourceFolder.exists()) continue;

            var toFolder = new File(path + "//hook//" + hook);
            try {
                FileUtils.copyDirectory(sourceFolder, toFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
