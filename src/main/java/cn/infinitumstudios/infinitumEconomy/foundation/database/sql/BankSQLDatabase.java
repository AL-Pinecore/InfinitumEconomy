package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Bank;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Vault;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BankSQLDatabase {
    private final Connection connection;
    VaultSQLDatabase vaultSQLDatabase;

    // TODO Bank SQL

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
        vaultSQLDatabase = new VaultSQLDatabase(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("InfinitumEconomy")).getDataFolder() + "/economy.db");
    }

    /**
     * Creates/register a bank into the database.
     * @param bank Instance of class {@link Bank}.
     * @return Returns true if created successfully. Returns false if the bank is already existed in the database, or failed to create a bank.
     */
    public Status createBank (Bank bank){
        if (bank == null) return Status.FAILED;
        if (hasBank(bank.getBankUUID())) return Status.EXISTED;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bank (BankName, BankUUID, OwnerAccountUUID) VALUES (?,?,?)")){
            preparedStatement.setString(1, bank.getName());
            preparedStatement.setString(2, bank.getBankUUID().toString());
            preparedStatement.setString(3, Objects.requireNonNull(this.getBankOwnerAccount(bank.getBankOwner())).toString());

        } catch (SQLException e){
            return Status.FAILED;
        }
        return Status.SUCCESS;
    }

    public Status deleteBank (UUID bankUUID){
        if (bankUUID == null) return Status.FAILED;
        if (!hasBank(bankUUID)) return Status.NOTFOUND;
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM bank WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return Status.FAILED;
        }
        return Status.SUCCESS;
    }

    public @Nullable UUID[] getBankUUIDs (UUID ownerUUID){
        List<UUID> foundedBankUUIDs = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BankUUID FROM bank WHERE OwnerAccountUUID = ?")){
            preparedStatement.setString(1, ownerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                foundedBankUUIDs.add(UUID.fromString(resultSet.getString("BankUUID")));
            }
            UUID[] uuids = new UUID[foundedBankUUIDs.size()];
            for (int i = 0 ; i < foundedBankUUIDs.size() ; i++){
                uuids[i] = foundedBankUUIDs.get(i);
            }
            return uuids;
        } catch (SQLException e){
            return null;
        }
    }

    public @Nullable Bank getBank (UUID bankUUID){
        String bankOwnerUUID, bankName;
        List<Vault> vaultList = new ArrayList<>();

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

        return new Bank(bankName, bankUUID, UUID.fromString(bankOwnerUUID), vaultSQLDatabase.getVaults(bankUUID));

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
