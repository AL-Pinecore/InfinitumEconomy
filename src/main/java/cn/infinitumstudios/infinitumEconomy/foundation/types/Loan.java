package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.database.sql.LoanSQLDatabase;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Loan{
    private final UUID loanUUID, loanerUUID, borrowerUUID, currencyUUID;
    private final double value, interestRate;
    private int dayLimit;

    // The value stored in LoanerType column of the SQLite database only receives integer value, only 0 and 1
    // if the value is 0 -> the loaner is a player
    // if the value is 1 -> the loaner is a bank
    private final LoanType type;

    public Loan(OfflinePlayer loaner, OfflinePlayer borrower, double value, double interestRate, UUID currency, int dayLimit){
        this.borrowerUUID = borrower.getUniqueId();
        this.loanerUUID = loaner.getUniqueId();

        // Load type means is the loaner a player or a bank.
        // if the loan type is LoanType.PLAYER, then the loanerUUID will be the UUID of a player.
        // if the loan type is LoanType.BANK, then the loanerUUID will be the UUID of a bank.
        this.type = LoanType.PLAYER;

        this.currencyUUID = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.dayLimit = dayLimit;
    }

    public Loan(Bank bank, OfflinePlayer borrower, double value, double interestRate, UUID currency, int dayLimit){
        this.borrowerUUID = borrower.getUniqueId();
        this.loanerUUID = bank.getBankID();

        // Load type means is the loaner a player or a bank.
        // if the loan type is LoanType.PLAYER, then the loanerUUID will be the UUID of a player.
        // if the loan type is LoanType.BANK, then the loanerUUID will be the UUID of a bank.
        this.type = LoanType.BANK;

        this.currencyUUID = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.dayLimit = dayLimit;
    }

    public Loan(UUID loanUUID, UUID loanerUUID, UUID borrowerUUID, LoanType loanType, double value, double interestRate, UUID currency, int dayLimit){
        this.loanUUID = loanUUID;
        this.loanerUUID = loanerUUID;
        this.borrowerUUID = borrowerUUID;
        this.type = loanType;
        this.value = value;
        this.interestRate = interestRate;
        this.currencyUUID = currency;
        this.dayLimit = dayLimit;
    }

    public UUID getLoanID (){
        return loanUUID;
    }

    public UUID getLoanerID (){
        return loanerUUID;
    }

    public UUID getBorrowerID (){
        return borrowerUUID;
    }

    public LoanType getLoanType (){
        return type;
    }

    public UUID getCurrencyID (){
        return currencyUUID;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public double getValue(){
        return value;
    }

    public int getDayLimit () {
        return dayLimit;
    }

    public void setDayLimit (int dayLimit) {
        this.dayLimit = dayLimit;
    }
}
