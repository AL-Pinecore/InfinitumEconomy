package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoanSQLDatabase {

    // TODO Loan SQL

    private final Connection connection;

    public LoanSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS loan(
                    LoanUUID TEXT PRIMARY KEY,
                    Worth DOUBLE(18, 2),
                    CurrencyUUID TEXT PRIMARY KEY,
                    LenderAccountUUID TEXT PRIMARY KEY,
                    BorrowerAccountUUID TEXT PRIMARY KEY,
                    BankUUID TEXT PRIMARY KEY,
                    InterestRate DECIMAL(5,4) DEFAULT 100
                )
                """);
    }
}
