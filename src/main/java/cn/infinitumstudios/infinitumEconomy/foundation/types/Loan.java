package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Loan {
    private final UUID loanUUID, borrowerUUID, loanerUUID, loanBankUUID;
    private final Currency usedCurrency;
    private double value, interestRate;

    // Loan from player account
    public Loan(OfflinePlayer borrower, OfflinePlayer loaner, double value, double interestRate, Currency currency){
        this.borrowerUUID = borrower.getUniqueId();
        this.loanerUUID = loaner.getUniqueId();
        this.usedCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = null;
    }

    // Loan from bank account
    public Loan(OfflinePlayer borrower, Bank loanBank, double value, double interestRate, Currency currency){
        this.borrowerUUID = borrower.getUniqueId();
        this.loanerUUID = null;
        this.usedCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBankUUID = loanBank.getBankUUID();
    }

    public UUID getLoanUUID(){
        return loanUUID;
    }

    public UUID getBorrowerUUID(){
        return borrowerUUID;
    }

    public UUID getLoanerUUID(){
        return loanerUUID;
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
