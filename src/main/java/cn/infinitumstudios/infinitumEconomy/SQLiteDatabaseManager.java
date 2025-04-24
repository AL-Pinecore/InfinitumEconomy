package cn.infinitumstudios.infinitumEconomy;

import cn.infinitumstudios.infinitumEconomy.foundation.database.sql.*;
import cn.infinitumstudios.infinitumEconomy.foundation.interfaces.IDatabaseManager;
import cn.infinitumstudios.infinitumEconomy.foundation.types.*;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

class SQLiteDatabaseManager implements IDatabaseManager {
    AccountSQLDatabase accountDB = new AccountSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    BankSQLDatabase bankDB = new BankSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    ChequeSQLDatabase chequeDB = new ChequeSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    CurrencySQLDatabase currencyDB = new CurrencySQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    LoanSQLDatabase loanDB = new LoanSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    VaultSQLDatabase vaultDB = new VaultSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());
    WalletSQLDatabase walletDB = new WalletSQLDatabase(Reference.SQLITE_DATABASE_DIRECTORY.toString());

    public SQLiteDatabaseManager () throws SQLException {
    }

    @Override
    public ResponseStatus createAccount (Account account) {
        return accountDB.createAccount(account);
    }

    @Override
    @Deprecated
    public ResponseStatus deleteAccount (UUID playerUUID) {
        return accountDB.deleteAccount(playerUUID);
    }

    @Override
    public boolean hasAccount (UUID playerUUID) {
        return accountDB.hasAccount(playerUUID);
    }

    @Override
    public @Nullable Account getAccount (UUID playerUUID) {
        return accountDB.getAccount(playerUUID);
    }

    @Override
    public ResponseStatus updateAccount (Account account) {
        return accountDB.updateAccount(account);
    }

    @Override
    public ResponseStatus createBank (Bank bank) {
        return bankDB.createBank(bank);
    }

    @Override
    public ResponseStatus deleteBank (UUID bankUUID) {
        return bankDB.deleteBank(bankUUID);
    }

    @Override
    public boolean hasBank (UUID bankUUID) {
        return bankDB.hasBank(bankUUID);
    }

    @Override
    public boolean hasBank (String bankName) {
        return bankDB.hasBank(bankName);
    }

    @Override
    public @Nullable ArrayList<Bank> getBanks (UUID ownerUUID) {
        return bankDB.getBanks(ownerUUID);
    }

    @Override
    public @Nullable Bank getBank (UUID bankUUID) {
        return bankDB.getBank(bankUUID);
    }

    @Override
    public @Nullable Bank getBank (String bankName) {
        return bankDB.getBank(bankName);
    }

    @Override
    public ArrayList<Bank> getAllBanks () {
        return bankDB.getAllBanks();
    }

    @Override
    public ResponseStatus updateBank (Bank bank) {
        return bankDB.updateBank(bank);
    }

    @Override
    public ResponseStatus createCheque (Cheque cheque) {
        return chequeDB.createCheque(cheque);
    }

    @Override
    public ResponseStatus deleteCheque (UUID chequeUUID) {
        return chequeDB.deleteCheque(chequeUUID);
    }

    @Override
    public boolean hasCheque (UUID chequeUUID) {
        return chequeDB.hasCheque(chequeUUID);
    }

    @Override
    public ArrayList<Cheque> getCheques (UUID ownerUUID) {
        return chequeDB.getCheques(ownerUUID);
    }

    @Override
    public @Nullable Cheque getCheque (UUID chequeUUID) {
        return chequeDB.getCheque(chequeUUID);
    }

    @Override
    @Deprecated
    public ResponseStatus updateCheque (Cheque cheque) {
        return chequeDB.updateCheque(cheque);
    }

    @Override
    public ResponseStatus createCurrency (Currency currency) {
        return currencyDB.createCurrency(currency);
    }

    @Override
    @Deprecated
    public ResponseStatus deleteCurrency (UUID currencyUUID) {
        return currencyDB.deleteCurrency(currencyUUID);
    }

    @Override
    public boolean hasCurrency (UUID currencyUUID) {
        return currencyDB.hasCurrency(currencyUUID);
    }

    @Override
    public boolean hasCurrency (String currencyName) {
        return currencyDB.hasCurrency(currencyName);
    }

    @Override
    public @Nullable Currency getCurrency (UUID currencyUUID) {
        return currencyDB.getCurrency(currencyUUID);
    }

    @Override
    public @Nullable Currency getCurrency (String currencyName) {
        return currencyDB.getCurrency(currencyName);
    }

    @Override
    public ArrayList<Currency> getAllCurrencies () {
        return currencyDB.getAllCurrencies();
    }

    @Override
    public ResponseStatus updateCurrency (Currency currency) {
        return currencyDB.updateCurrency(currency);
    }

    @Override
    public ResponseStatus createLoan (Loan loan) {
        return loanDB.createLoan(loan);
    }

    @Override
    public ResponseStatus deleteLoan (UUID loanUUID) {
        return loanDB.deleteLoan(loanUUID);
    }

    @Override
    public boolean hasLoan (UUID loanUUID) {
        return loanDB.hasLoan(loanUUID);
    }

    @Override
    public @Nullable Loan getLoan (UUID loanUUID) {
        return loanDB.getLoan(loanUUID);
    }

    @Override
    public ArrayList<Loan> getLoanerLoans (UUID loaner) {
        return loanDB.getLoanerLoans(loaner);
    }

    @Override
    public ArrayList<Loan> getBorrowerLoans (UUID borrower) {
        return loanDB.getBorrowerLoans(borrower);
    }

    @Override
    public ResponseStatus updateLoan (Loan loan) {
        return loanDB.updateLoan(loan);
    }

    @Override
    public ResponseStatus createVault (Vault vault) {
        return vaultDB.createVault(vault);
    }

    @Override
    public ResponseStatus deleteVault (UUID vaultUUID) {
        return vaultDB.deleteVault(vaultUUID);
    }

    @Override
    public boolean hasVault (UUID vaultUUID) {
        return vaultDB.hasVault(vaultUUID);
    }

    @Override
    public ArrayList<Vault> getVaults (UUID bankUUID) {
        return vaultDB.getVaults(bankUUID);
    }

    @Override
    public @Nullable Vault getVault (UUID vaultUUID) {
        return vaultDB.getVault(vaultUUID);
    }

    @Override
    public ResponseStatus updateVault (Vault vault) {
        return vaultDB.updateVault(vault);
    }

    @Override
    public ResponseStatus createWallet (Wallet wallet) {
        return walletDB.createWallet(wallet);
    }

    @Override
    public ResponseStatus deleteWallet (UUID walletUUID) {
        return walletDB.deleteWallet(walletUUID);
    }

    @Override
    public boolean hasWallet (UUID walletUUID) {
        return walletDB.hasWallet(walletUUID);
    }

    @Override
    public boolean hasCurrencyWallet (UUID currencyUUID) {
        return walletDB.hasCurrencyWallet(currencyUUID);
    }

    @Override
    public ArrayList<Wallet> getWallets (UUID owner) {
        return walletDB.getWallets(owner);
    }

    @Override
    public @Nullable Wallet getWallet (UUID walletUUID) {
        return walletDB.getWallet(walletUUID);
    }

    @Override
    public ResponseStatus updateWallet (Wallet wallet) {
        return walletDB.updateWallet(wallet);
    }
}
