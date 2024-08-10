package cn.infinitumstudios.infinitumEconomy.foundation.types;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class Loan {
    private final String borrower, loaner, loanBank;
    private final UUID borrowerUUID, loanerUUID, loanBankUUID;
    private final Currency usingCurrency;
    private double value, interestRate;
    private final UUID loanUUID;

    // Loan from player account
    public Loan(OfflinePlayer borrower, OfflinePlayer loaner, double value, double interestRate, Currency currency){
        this.borrower = borrower.getName();
        this.borrowerUUID = borrower.getUniqueId();
        this.loaner = loaner.getName();
        this.loanerUUID = loaner.getUniqueId();
        this.usingCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBank = null;
        this.loanBankUUID = null;
    }

    // Loan from bank account
    public Loan(OfflinePlayer borrower, Bank loanBank, double value, double interestRate, Currency currency){
        this.borrower = borrower.getName();
        this.borrowerUUID = borrower.getUniqueId();
        this.loaner = null;
        this.loanerUUID = null;
        this.usingCurrency = currency;
        this.value = value;
        this.interestRate = interestRate;
        this.loanUUID = UUID.randomUUID();
        this.loanBank = loanBank.getName();
        this.loanBankUUID = loanBank.getBankUUID();
    }

    public UUID getLoanUUID(){
        return loanUUID;
    }

    public String getBorrower(){
        return borrower;
    }

    public String getLoaner(){
        return loaner;
    }

    public String getLoanBank(){
        return loanBank;
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

    public Currency getUsingCurrency(){
        return usingCurrency;
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
