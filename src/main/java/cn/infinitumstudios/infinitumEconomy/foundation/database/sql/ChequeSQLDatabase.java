package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChequeSQLDatabase {
    private final Connection connection;

    public ChequeSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS cheque(
                    ChequeUUID TEXT PRIMARY KEY,
                    Worth DOUBLE(18, 2) DEFAULT 0,
                    CurrencyUUID TEXT PRIMARY KEY,
                    OwnerAccountUUID TEXT PRIMARY KEY,
                    BankUUID TEXT PRIMARY KEY,
                    Nickname TEXT NOT NULL
                )
                """);
    }
}
