package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

public class AccountSQLDatabase {

    private final Connection connection;

    // TODO (SQLite) supports the Balance in Account class
    public AccountSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS account(
                    Nickname TEXT NOT NULL,
                    AccountUUID TEXT PRIMARY KEY,
                    Credit INT
                )
            """);
        }
    }

    /**
     * Creates/register an economy account into the database.
     * @param account an instance of the Account class.
     * @return {@link ResponseStatus#FAILED}
     *
     */
    public ResponseStatus createAccount(Account account){
        if (account == null) return ResponseStatus.FAILED;
        if (hasAccount(account.getAccountID())) return ResponseStatus.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account (Nickname, AccountUUID, Credit) VALUES (?,?,?)")){
            preparedStatement.setString(1,account.getNickname());
            preparedStatement.setString(2,account.getAccountID().toString());
            preparedStatement.setInt(3,account.getCredit());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
        return ResponseStatus.SUCCESS;
    }

    /**
     * Deletes an economy account from the database.
     * @param playerUUID UUID of the player
     * @return Returns true if deleted successfully.
     */
    @Deprecated
    public ResponseStatus deleteAccount (UUID playerUUID){
        if (playerUUID == null) return ResponseStatus.FAILED;
        if (!hasAccount(playerUUID)) return ResponseStatus.NOTFOUND;

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE AccountUUID = ?")){
            preparedStatement.setString(1,playerUUID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    /**
     * See if a player's economy account exists in the database.
     * @param playerUUID player's UUID in server.
     * @return Returns true if the account is deleted successfully.
     */
    public boolean hasAccount(UUID playerUUID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE AccountUUID = ?")){
            preparedStatement.setString(1, playerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    /**
     * Get a player's economy account from the database.
     * @param playerUUID player's UUID in server.
     * @return Returns a player's economy account instance of class {@link Account}
     */
    @Nullable
    public Account getAccount(UUID playerUUID){
        if (playerUUID == null) return null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT Nickname, Credit FROM account WHERE AccountUUID = ?")){
            preparedStatement.setString(1, playerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Account(playerUUID,
                        resultSet.getString("Nickname"),
                        resultSet.getInt("Credit"));
            } else {
                return null;
            }
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }


    }

    public ResponseStatus updateAccount(Account account){
        if (account == null){
            return ResponseStatus.FAILED;
        }
        if (!hasAccount(account.getAccountID())){
            return ResponseStatus.NOTFOUND;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET Nickname = ?, Credit = ? WHERE AccounrUUID = ?")){
            preparedStatement.setString(1, account.getNickname());
            preparedStatement.setInt(2, account.getCredit());
            preparedStatement.setString(3, account.getAccountID().toString());
            preparedStatement.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
