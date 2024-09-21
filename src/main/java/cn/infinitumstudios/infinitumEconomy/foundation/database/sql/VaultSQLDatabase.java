package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class VaultSQLDatabase {

    // TODO Vault SQL
    private final Connection connection;

    public VaultSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS vault(
                    VaultUUID TEXT PRIMARY KEY,
                    OwnedBankUUID TEXT PRIMARY KEY,
                    CurrencyUUID TEXT PRIMARY KEY,
                    Value DOUBLE(24, 2) DEFAULT 0
                )
        """);
    }
}
