package cn.infinitumstudios.infinitumEconomy.utility;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class Reference {
    public static Path DATA_FILES_DIRECTORY = Path.of(InfinitumEconomy.get().getDataFolder().getAbsolutePath(), "data");
}
