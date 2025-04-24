package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Loan;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class LoanSQLDatabase {

    // TODO Loan SQL

    private final Connection connection;

    public LoanSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();

        // The value stored in LoanerType column of the SQLite database only receives integer value, only 0 and 1
        // if the value is 0 -> the loaner is a player
        // if the value is 1 -> the loaner is a bank

        statement.execute("""
                CREATE TABLE IF NOT EXISTS loan(
                    LoanUUID TEXT PRIMARY KEY,
                    Worth DOUBLE(18, 2),
                    CurrencyUUID TEXT NOT NULL,
                    LoanerAccountUUID TEXT NOT NULL,
                    LoanerType INT,
                    BorrowerAccountUUID TEXT NOT NULL,
                    InterestRate DECIMAL(5,4) DEFAULT 100,
                    DayLimit INT
                )
                """);
    }

    public ResponseStatus createLoan (Loan loan){
        return null;
    }

    public ResponseStatus deleteLoan (UUID loanUUID){
        return null;
    }

    public boolean hasLoan (UUID loanUUID){
        return false;
    }

    @Nullable
    public Loan getLoan (UUID loanUUID){
        return null;
    }

    public ArrayList<Loan> getLoanerLoans (UUID loaner){
        return null;
    }

    public ArrayList<Loan> getBorrowerLoans (UUID borrower){
        return null;
    }

    public ResponseStatus updateLoan (Loan loan){
        return null;
    }
}
