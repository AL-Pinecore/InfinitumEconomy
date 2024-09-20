package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Bank;

public class BankDatabase extends Database<Bank> {
    public BankDatabase(String fileName, Class<Bank> classOfT) {
        super(fileName, classOfT);
    }
}
