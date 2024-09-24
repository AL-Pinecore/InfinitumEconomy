package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Vault;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VaultSQLDatabase {

    // TODO Vault SQL
    private final Connection connection;

    public VaultSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS vault(
                    VaultUUID TEXT PRIMARY KEY,
                    OwnerUUID TEXT NOT NULL,
                    OwnedBankUUID TEXT NOT NULL,
                    CurrencyUUID TEXT NOT NULL,
                    Value DOUBLE(24, 2) DEFAULT 0
                )
        """);
    }

    public List<Vault> getVaults(UUID bankUUID){

        List<Vault> vaults = new ArrayList<>();
        // TODO checks if vault existence

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT VaultUUID, OwnerUUID, CurrencyUUID, Value FROM vault WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                vaults.add(new Vault(
                        UUID.fromString(resultSet.getString("VaultUUID")),
                        bankUUID,
                        UUID.fromString(resultSet.getString("OwnerUUID")),
                        resultSet.getString("CurrencyUUID"),
                        resultSet.getDouble("Value")));
            }
            return vaults;
        } catch (SQLException e) {
            return null;
        }

    }

    public Vault getVault(UUID vaultUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT OwnerUUID, OwnedBankUUID, CurrencyUUID, Value FROM vault WHERE VaultUUID = ?")){
            preparedStatement.setString(1, vaultUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Vault(
                        vaultUUID,
                        UUID.fromString(resultSet.getString("OwnedBankUUID")),
                        UUID.fromString(resultSet.getString("OwnerUUID")),
                        resultSet.getString("CurrencyUUID"),
                        resultSet.getDouble("Value"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

}
