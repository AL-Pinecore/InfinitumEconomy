package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountSQLDatabase {

    private final Connection connection;

    public AccountSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS account(
                    Username TEXT NOT NULL,
                    AccountUUID TEXT PRIMARY KEY,
                    AccountHolderUUID TEXT PRIMARY KEY,
                    Nickname TEXT NOT NULL
                )
                """);
    }

}
