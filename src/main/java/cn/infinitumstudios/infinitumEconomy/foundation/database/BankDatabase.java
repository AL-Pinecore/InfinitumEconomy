package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Bank;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.nio.file.Path;

public class BankDatabase extends Database<Bank> {
    public BankDatabase() {
        super(Reference.BANK_DATABASE_NAME, Bank.class, new File(Path.of(Reference.DATA_FILES_DIRECTORY.toString(), Reference.BANK_DATABASE_NAME + ".json").toUri()));
    }

    // TODO add detection whether player can or cannot create bank, return true if success, false if fail
    public boolean create(String bankName, OfflinePlayer owner) {
        if (bankExists(bankName)) return false; // This is here to prevent Bank Duplicates
        super.create(new Bank(bankName, owner.getUniqueId()));
        return true;
    }

    public boolean delete(String bankName) {
        if(!bankExists(bankName)) return false;
        super.delete(bank -> bank.getName().equals(bankName));
        return true;
    }

    public boolean bankExists(String bankName) {
        return read(bank -> bank.getName().equalsIgnoreCase(bankName)).isPresent();
    }
}
