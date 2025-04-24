package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Bank;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class BankSQLDatabase {
    private final Connection connection;
    VaultSQLDatabase vaultSQLDatabase;

    public BankSQLDatabase (String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS bank(
                    BankName TEXT NOT NULL,
                    BankUUID TEXT PRIMARY KEY,
                    OwnerAccountUUID TEXT NOT NULL
                )
                """);
        vaultSQLDatabase = new VaultSQLDatabase(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("InfinitumEconomy")).getDataFolder() + "/economy.db");
    }

    /**
     * Creates/register a bank into the database.
     * @param bank Instance of class {@link Bank}.
     * @return {@link ResponseStatus#SUCCESS}, {@link ResponseStatus#EXISTED}, {@link ResponseStatus#FAILED}
     */
    public ResponseStatus createBank (Bank bank){
        if (bank == null) return ResponseStatus.FAILED;
        if (hasBank(bank.getBankID())) return ResponseStatus.EXISTED;
        if (hasBank(bank.getName())) return ResponseStatus.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bank (BankName, BankUUID, OwnerAccountUUID) VALUES (?,?,?)")){
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getBankID().toString());
            preparedStatement.setString(3, Objects.requireNonNull(bank.getBankOwnerID().toString()));
        } catch (SQLException e){
            return ResponseStatus.FAILED;
        }
        return ResponseStatus.SUCCESS;
    }

    /**
     * Delete a bank from the database.
     * @param bankUUID the bank's UUID.
     * @return {@link ResponseStatus#SUCCESS}, {@link ResponseStatus#NOTFOUND}, {@link ResponseStatus#FAILED}
     */
    public ResponseStatus deleteBank (UUID bankUUID){
        if (bankUUID == null) return ResponseStatus.FAILED;
        if (!hasBank(bankUUID)) return ResponseStatus.NOTFOUND;
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return ResponseStatus.FAILED;
        }
        return ResponseStatus.SUCCESS;
    }

    /**
     * Is the bank exists in the database.
     * @param bankUUID the bank's UUID.
     * @return true - the bank is founded, false - the bank does not exist in the database.
     */
    public boolean hasBank (UUID bankUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public boolean hasBank (String bankName){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE BankName = ?")){
            preparedStatement.setString(1, bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    /**
     * Get a list of banks from the owner UUID's account.
     * @param ownerUUID the bank owner's game UUID.
     * @return An array list of instance {@link Bank}
     */
    public ArrayList<Bank> getBanks (UUID ownerUUID){
        ArrayList<Bank> foundedBanks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BankName, BankUUID FROM bank WHERE OwnerAccountUUID = ?")){
            preparedStatement.setString(1, ownerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                foundedBanks.add(new Bank(
                        resultSet.getString("BankName"),
                        UUID.fromString(resultSet.getString("BankUUID")),
                        ownerUUID
                ));
            }
            return foundedBanks;
        } catch (SQLException e){
            return null;
        }
    }

    /**
     * Get a bank from the database based on the bank UUID
     * @param bankUUID the bank's UUID.
     * @return An instance of class {@link Bank}, null if not found anything
     */
    @Nullable
    public Bank getBank (UUID bankUUID){
        if (bankUUID == null) return null;
        String bankOwnerUUID, bankName;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BankName, OwnerAccountUUID FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                bankName = resultSet.getString("BankName");
                bankOwnerUUID = resultSet.getString("OwnerAccountUUID");
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }

        return new Bank(bankName, bankUUID, UUID.fromString(bankOwnerUUID));
    }

    @Nullable
    public Bank getBank (String bankName){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BankUUID, OwnerAccountUUID FROM bank WHERE BankName = ?")){
            preparedStatement.setString(1, bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Bank(bankName,
                        UUID.fromString(resultSet.getString("BankUUID")),
                        UUID.fromString(resultSet.getString("OwnerAccountUUID")));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Bank> getAllBanks(){
        ArrayList<Bank> banks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank")){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                banks.add(new Bank(
                        rs.getString("BankName"),
                        UUID.fromString(rs.getString("BankUUID")),
                        UUID.fromString(rs.getString("OwnerAccountUUID"))
                ));
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
        return banks;
    }

    public ResponseStatus updateBank(Bank bank){
        if (bank == null) return ResponseStatus.FAILED;
        if (!hasBank(bank.getBankID())) return ResponseStatus.NOTFOUND;

        // No repeated bank name
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank WHERE BankName = ? AND NOT BankUUID = ?")){
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getBankID().toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return ResponseStatus.EXISTED;
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bank SET BankName = ?, OwnerAccountUUID = ? WHERE BankUUID = ?")){
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getBankOwnerID().toString());
            preparedStatement.setString(3, bank.getBankID().toString());
            preparedStatement.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
