package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.UUID;

public class AccountSQLDatabase {

    private final Connection connection;

    public AccountSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS account(
                    Nickname TEXT NOT NULL,
                    AccountUUID TEXT PRIMARY KEY,
                    AccountHolderUUID TEXT PRIMARY KEY
                )
            """);
        }
    }

    /**
     * Creates an economy account.
     * @param account an instance of the Account class.
     * @return Returns true if created successfully.
     */
    public boolean createAccount(Account account){
        if (account == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account (Nickname, AccountUUID, AccountHolderUUID) VALUES (?,?,?)")){
            preparedStatement.setString(1,account.getNickname());
            preparedStatement.setString(2,account.getAccountUUID().toString());
            preparedStatement.setString(3,account.getAccountHolder().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return false;
        }

        return true;
    }

    /**
     * Deletes an economy account.
     * @param account an instance of the Account class.
     * @return Returns true if deleted successfully.
     */

    public boolean removeAccount(Account account){
        if (account == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE AccountHolderUUID = ?")){
            preparedStatement.setString(1,account.getAccountHolder().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return false;
        }

        return true;
    }

    /**
     * See if a player's economy account exists.
     * @param playerUUID player's UUID in server.
     * @return Returns true if deleted successfully.
     */

    public boolean hasAccount(UUID playerUUID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE AccountUUID = ?")){
            preparedStatement.setString(1, playerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            return false;
        }
    }

    /**
     * Get a player's economy account.
     * @param playerUUID player's UUID in server.
     * @return Returns true if deleted successfully.
     */
    public @Nullable Account getAccount(UUID playerUUID){
        if (playerUUID == null) return null;
        UUID accountUUID;
        String nickname;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT Nickname FROM account WHERE AccountHolderUUID = ?")){
            preparedStatement.setString(1, playerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                nickname = resultSet.getString("Nickname");
            } else {
                return null;
            }
        } catch (SQLException e){
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT AccountUUID FROM account WHERE AccountHolderUUID = ?")){
            preparedStatement.setString(1, playerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                accountUUID = UUID.fromString(resultSet.getString("AccountUUID"));
            } else {
                return null;
            }
        } catch (SQLException e){
            return null;
        }

        return new Account(accountUUID, playerUUID, nickname);
    }

    protected boolean closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()){
            connection.close();
            return true;
        } else {
            return false;
        }
    }


}
