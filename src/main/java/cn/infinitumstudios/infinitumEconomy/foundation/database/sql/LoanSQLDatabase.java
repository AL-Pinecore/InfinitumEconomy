package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Loan;
import cn.infinitumstudios.infinitumEconomy.foundation.types.LoanType;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class LoanSQLDatabase {

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
                    InterestRate DOUBLE DEFAULT 100,
                    DayLimit INT
                )
                """);
    }

    public ResponseStatus createLoan (Loan loan){
        if (hasLoan(loan.getLoanID())) return ResponseStatus.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO loan (LoanUUID, Worth, CurrencyUUID, LoanerAccountUUID, LoanerType, BorrowerAccountUUID, InterestRate, DayLimit) VALUES (?,?,?,?,?,?,?,?)")){
            preparedStatement.setString(1, loan.getLoanID().toString());
            preparedStatement.setDouble(2, loan.getValue());
            preparedStatement.setString(3, loan.getCurrencyID().toString());
            preparedStatement.setString(4, loan.getLoanerID().toString());
            preparedStatement.setInt(5, loan.getLoanType() == LoanType.PLAYER ? 0 : 1);
            preparedStatement.setString(6, loan.getBorrowerID().toString());
            preparedStatement.setDouble(7, loan.getInterestRate());
            preparedStatement.setInt(8, loan.getDayLimit());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    public ResponseStatus deleteLoan (UUID loanUUID){
        if (!hasLoan(loanUUID)) return ResponseStatus.NOTFOUND;

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM loan WHERE LoanUUID = ?")){
            ps.setString(1, loanUUID.toString());
            ps.executeUpdate();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    public boolean hasLoan (UUID loanUUID){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM loan WHERE LoanUUID = ?")){
            ps.setString(1, loanUUID.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    @Nullable
    public Loan getLoan (UUID loanUUID){
        if (!hasLoan(loanUUID)) return null;

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM loan WHERE LoanUUID = ?")){
            ps.setString(1, loanUUID.toString());
            ResultSet rs = ps.executeQuery();
            return new Loan(
                     UUID.fromString(rs.getString("LoanUUID")),
                     UUID.fromString(rs.getString("LoanerAccountUUID")),
                     UUID.fromString(rs.getString("BorrowerAccountUUID")),
                     rs.getInt("LoanerType") == 0 ? LoanType.PLAYER : LoanType.BANK,
                     rs.getDouble("Worth"),
                     rs.getDouble("InterestRate"),
                     UUID.fromString(rs.getString("CurrencyUUID")),
                     rs.getInt("DayLimit")
            );
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
    }

    public ArrayList<Loan> getLoanerLoans (UUID loaner){
        ArrayList<Loan> loans = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM loan WHERE LoanerAccountUUID = ?")){
            ps.setString(1, loaner.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                loans.add(new Loan(
                        UUID.fromString(rs.getString("LoanUUID")),
                        UUID.fromString(rs.getString("LoanerAccountUUID")),
                        UUID.fromString(rs.getString("BorrowerAccountUUID")),
                        rs.getInt("LoanerType") == 0 ? LoanType.PLAYER : LoanType.BANK,
                        rs.getDouble("Worth"),
                        rs.getDouble("InterestRate"),
                        UUID.fromString(rs.getString("CurrencyUUID")),
                        rs.getInt("DayLimit")
                ));
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }

        return loans;
    }

    public ArrayList<Loan> getBorrowerLoans (UUID borrower){
        ArrayList<Loan> loans = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM loan WHERE BorrowerAccountUUID = ?")){
            ps.setString(1, borrower.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                loans.add(new Loan(
                        UUID.fromString(rs.getString("LoanUUID")),
                        UUID.fromString(rs.getString("LoanerAccountUUID")),
                        UUID.fromString(rs.getString("BorrowerAccountUUID")),
                        rs.getInt("LoanerType") == 0 ? LoanType.PLAYER : LoanType.BANK,
                        rs.getDouble("Worth"),
                        rs.getDouble("InterestRate"),
                        UUID.fromString(rs.getString("CurrencyUUID")),
                        rs.getInt("DayLimit")
                ));
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }

        return loans;
    }

    public ResponseStatus updateLoan (Loan loan){
        if (!hasLoan(loan.getLoanID())) return ResponseStatus.NOTFOUND;

        try (PreparedStatement ps = connection.prepareStatement("UPDATE loan SET DayLimit = ? WHERE LoanUUID = ?")){
            ps.setInt(1, loan.getDayLimit());
            ps.setString(2, loan.getLoanID().toString());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
