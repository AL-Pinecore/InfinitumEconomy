package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CurrencySQLDatabase {

    private final Connection connection;

    public CurrencySQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS currency(
                    CurrencyUUID TEXT PRIMARY KEY,
                    CurrencyName TEXT NOT NULL,
                    CurrencyPluralName TEXT NOT NULL,
                    CurrencySymbol CHAR,
                    UniversalWorth DECIMAL(8, 6)
                )
                """);
    }
}
