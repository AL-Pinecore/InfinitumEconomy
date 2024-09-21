package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WalletSQLDatabase {

    // TODO Wallet SQL

    private final Connection connection;

    public WalletSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS wallet(
                    OwnerAccountUUID TEXT PRIMARY KEY,
                    CurrencyUUID TEXT PRIMARY KEY,
                    Value DOUBLE(18, 2) DEFAULT 0
                )
                """);
    }
}
