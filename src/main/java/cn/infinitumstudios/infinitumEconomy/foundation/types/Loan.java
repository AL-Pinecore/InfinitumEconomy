package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Loan {
    private final UUID loanUUID, lenderUUID, borrowerUUID, loanBankUUID;
    private final Currency usedCurrency;
    private double value, interestRate;

    // Loan from player account
    public Loan(OfflinePlayer borrower, OfflinePlayer loaner, double value, double interestRate, Currency currency){
        this.lenderUUID = borrower.getUniqueId();
        this.borrowerUUID = loaner.getUniqueId();
        this.usedCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = null;
    }

    // Loan from bank account
    public Loan(OfflinePlayer borrower, Bank loanBank, double value, double interestRate, Currency currency){
        this.lenderUUID = borrower.getUniqueId();
        this.borrowerUUID = null;
        this.usedCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = loanBank.getBankUUID();
    }

    public UUID getLoanUUID(){
        return loanUUID;
    }

    public UUID getLenderUUID(){
        return lenderUUID;
    }

    public UUID getBorrowerUUID(){
        return borrowerUUID;
    }

    public UUID getLoanBankUUID(){
        return loanBankUUID;
    }

    public Currency getUsedCurrency(){
        return usedCurrency;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
