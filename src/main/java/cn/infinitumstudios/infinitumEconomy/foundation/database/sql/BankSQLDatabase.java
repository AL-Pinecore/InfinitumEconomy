package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Bank;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class BankSQLDatabase {
    private final Connection connection;

    public BankSQLDatabase (String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS bank(
                    BankName TEXT NOT NULL,
                    BankUUID TEXT PRIMARY KEY,
                    OwnerAccountUUID TEXT PRIMARY KEY
                )
                """);
    }

    /**
     * Creates/register a bank into the database.
     * @param bank Instance of class {@link Bank}.
     * @return Returns true if created successfully. Returns false if the bank is already existed in the database, or failed to create a bank.
     */
    public boolean createBank (Bank bank){
        if (bank == null || hasBank(bank.getBankUUID())) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bank (BankName, BankUUID, OwnerAccountUUID) VALUES (?,?,?)")){
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getBankUUID().toString());
            preparedStatement.setString(3, Objects.requireNonNull(this.getBankOwnerAccount(bank.getBankOwner())).toString());

        } catch (SQLException e){
            return false;
        }
        return true;
    }

    public boolean deleteBank (UUID bankUUID){
        if (bankUUID == null) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return false;
        }
        return true;
    }

    public Bank[] getBanks (UUID ownerUUID){
        return null;
    }

    public Bank getBank (UUID bankUUID){
        return null;
    }

    public boolean hasBank (UUID bankUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            return false;
        }
    }

    private UUID getBankOwnerAccount (UUID bankOwnerUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT AccountUUID FROM account WHERE AccountHolderUUID = ?")){
            preparedStatement.setString(1, bankOwnerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return UUID.fromString(resultSet.getString("AccountUUID"));
            } else {
                return null;
            }
        } catch (SQLException e){
            return null;
        }
    }
}
