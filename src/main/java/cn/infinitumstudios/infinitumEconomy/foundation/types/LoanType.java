package cn.infinitumstudios.infinitumEconomy.foundation.types;

public enum LoanType {
    PLAYER, BANK

    // The value stored in LoanerType of the SQLite database only receives integer value, 0 and 1
    // 0 -> the loaner is a player
    // 1 -> the loaner is a bank
}
