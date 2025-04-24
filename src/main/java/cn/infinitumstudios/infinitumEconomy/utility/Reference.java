package cn.infinitumstudios.infinitumEconomy.utility;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;

import java.nio.file.Path;

public class Reference {
    public static final Path DATA_FILES_DIRECTORY = Path.of(InfinitumEconomy.get().getDataFolder().getAbsolutePath(), "data");
    public static final Path SQLITE_DATABASE_DIRECTORY = Path.of(InfinitumEconomy.get().getDataFolder().getAbsolutePath(), "data", "economy.db");;

    public static final String ACCOUNT_DATABASE_NAME = "accounts";
    public static final String CURRENCY_DATABASE_NAME = "currencies";
    public static final String BANK_DATABASE_NAME = "banks";
}
