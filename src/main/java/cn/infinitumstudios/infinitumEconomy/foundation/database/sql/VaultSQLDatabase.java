package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Vault;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class VaultSQLDatabase {

    private final Connection connection;

    public VaultSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS vault(
                    VaultUUID TEXT PRIMARY KEY,
                    BankUUID TEXT NOT NULL,
                    CurrencyUUID TEXT NOT NULL,
                    Value DOUBLE(24, 2) DEFAULT 0
                )
        """);
    }

    public ResponseStatus createVault (Vault vault){
        if (hasVault(vault.getVaultID())) return ResponseStatus.EXISTED;
        if (hasCurrencyVault(vault.getCurrencyID())) return ResponseStatus.EXISTED;

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO vault (VaultUUID, BankUUID, CurrencyUUID, Value) VALUES (?,?,?,?)")){
            ps.setString(1, vault.getVaultID().toString());
            ps.setString(2, vault.getOwnedBankID().toString());
            ps.setString(3, vault.getCurrencyID().toString());
            ps.setDouble(4, vault.getValue());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }

    public ResponseStatus deleteVault (UUID vaultUUID){
        if (!hasVault(vaultUUID)) return ResponseStatus.NOTFOUND;

        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM vault WHERE VaultUUID = ?")){
            ps.setString(1, vaultUUID.toString());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }

    public boolean hasVault (UUID vaultUUID){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM vault WHERE VaultUUID = ?")){
            ps.setString(1, vaultUUID.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public boolean hasCurrencyVault (UUID currencyUUID){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM vault WHERE CurrencyUUID = ?")){
            ps.setString(1, currencyUUID.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public ArrayList<Vault> getVaults (UUID bankUUID){
        ArrayList<Vault> vaults = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT VaultUUID, CurrencyUUID, Value FROM vault WHERE BankUUID = ?")){
            preparedStatement.setString(1, bankUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                vaults.add(new Vault(
                        UUID.fromString(resultSet.getString("VaultUUID")),
                        bankUUID,
                        UUID.fromString(resultSet.getString("CurrencyUUID")),
                        resultSet.getDouble("Value")));
            }
            return vaults;
        } catch (SQLException e) {
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }

    }

    @Nullable
    public Vault getVault (UUID vaultUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BankUUID, CurrencyUUID, Value FROM vault WHERE VaultUUID = ?")){
            preparedStatement.setString(1, vaultUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Vault(
                        vaultUUID,
                        UUID.fromString(resultSet.getString("BankUUID")),
                        UUID.fromString(resultSet.getString("CurrencyUUID")),
                        resultSet.getDouble("Value"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public ResponseStatus updateVault (Vault vault){
        if (!hasVault(vault.getVaultID())) return ResponseStatus.NOTFOUND;

        try (PreparedStatement ps = connection.prepareStatement("UPDATE vault SET BankUUID = ?, Value = ? WHERE VaultUUID = ?")) {
            ps.setString(1, vault.getOwnedBankID().toString());
            ps.setDouble(2, vault.getValue());
            ps.setString(3, vault.getVaultID().toString());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
