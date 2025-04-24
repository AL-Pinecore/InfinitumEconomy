package cn.infinitumstudios.infinitumEconomy.foundation.interfaces;

import cn.infinitumstudios.infinitumEconomy.foundation.types.*;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public interface IDatabaseManager {
    // Economy Account Manipulation
    ResponseStatus createAccount (Account account);
    ResponseStatus deleteAccount (UUID playerUUID);
    boolean hasAccount (UUID playerUUID);
    @Nullable Account getAccount (UUID playerUUID);
    ResponseStatus updateAccount (Account account);

    // Bank Manipulation
    ResponseStatus createBank (Bank bank);
    ResponseStatus deleteBank (UUID bankUUID);
    boolean hasBank (UUID bankUUID);
    boolean hasBank (String bankName);
    @Nullable ArrayList<Bank> getBanks (UUID ownerUUID);
    @Nullable Bank getBank (UUID bankUUID);
    @Nullable Bank getBank (String bankName);
    ArrayList<Bank> getAllBanks();
    ResponseStatus updateBank (Bank bank);

    // Cheque Manipulation
    ResponseStatus createCheque (Cheque cheque);
    ResponseStatus deleteCheque (UUID chequeUUID);
    boolean hasCheque (UUID chequeUUID);
    ArrayList<Cheque> getCheques (UUID ownerUUID);
    @Nullable Cheque getCheque (UUID chequeUUID);
    ResponseStatus updateCheque (Cheque cheque);

    // Currency Manipulation
    ResponseStatus createCurrency(Currency currency);
    ResponseStatus deleteCurrency(UUID currencyUUID);
    boolean hasCurrency(UUID currencyUUID);
    boolean hasCurrency(String currencyName);
    @Nullable Currency getCurrency(UUID currencyUUID);
    @Nullable Currency getCurrency(String currencyName);
    ArrayList<Currency> getAllCurrencies();
    ResponseStatus updateCurrency(Currency currency);

    // Loan Manipulation
    ResponseStatus createLoan (Loan loan);
    ResponseStatus deleteLoan (UUID loanUUID);
    boolean hasLoan (UUID loanUUID);
    @Nullable Loan getLoan (UUID loanUUID);
    ArrayList<Loan> getLoanerLoans (UUID loaner);
    ArrayList<Loan> getBorrowerLoans (UUID borrower);
    ResponseStatus updateLoan (Loan loan);

    // Vault Manipulation
    ResponseStatus createVault (Vault vault);
    ResponseStatus deleteVault (UUID vaultUUID);
    boolean hasVault (UUID vaultUUID);
    ArrayList<Vault> getVaults (UUID bankUUID);
    @Nullable Vault getVault (UUID vaultUUID);
    ResponseStatus updateVault (Vault vault);

    // Wallet Manipulation
    ResponseStatus createWallet (Wallet wallet);
    ResponseStatus deleteWallet (UUID walletUUID);
    boolean hasWallet (UUID walletUUID);
    boolean hasCurrencyWallet (UUID currencyUUID);
    ArrayList<Wallet> getWallets (UUID owner);
    @Nullable Wallet getWallet (UUID walletUUID);
    ResponseStatus updateWallet (Wallet wallet);

}
