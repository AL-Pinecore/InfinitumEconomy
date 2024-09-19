package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class WalletSQLDatabase {

    private final Connection connection;

    public WalletSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS wallet(
                    OwnerAccountUUID varchar(36),
                    CurrencyUUID varchar(36),
                    Value DOUBLE(18, 2)
                )
                """);
    }
}
