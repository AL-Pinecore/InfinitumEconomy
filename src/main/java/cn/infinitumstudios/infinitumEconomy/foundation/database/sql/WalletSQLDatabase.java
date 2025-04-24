package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Wallet;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class WalletSQLDatabase {

    // TODO Wallet SQL

    private final Connection connection;

    public WalletSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS wallet(
                    WalletUUID TEXT PRIMARY KEY,
                    OwnerAccountUUID TEXT NOT NULL,
                    CurrencyUUID TEXT NOT NULL,
                    Value DOUBLE(18, 2) DEFAULT 0
                )
                """);
    }

    public ResponseStatus createWallet (Wallet wallet){
        return null;
    }

    public ResponseStatus deleteWallet (UUID walletUUID){
        return null;
    }

    public boolean hasWallet (UUID walletUUID){
        return false;
    }

    // Every player can only have one wallet per each currency
    public boolean hasCurrencyWallet (UUID currencyUUID){
        return false;
    }

    public ArrayList<Wallet> getWallets (UUID owner){
        return null;
    }

    @Nullable
    public Wallet getWallet (UUID walletUUID){
        return null;
    }

    public ResponseStatus updateWallet (Wallet wallet){
        return null;
    }
}
