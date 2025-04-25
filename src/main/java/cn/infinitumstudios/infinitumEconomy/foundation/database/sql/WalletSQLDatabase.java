package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Wallet;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class WalletSQLDatabase {

    private final Connection connection;

    public WalletSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS wallet(
                    WalletUUID TEXT PRIMARY KEY,
                    OwnerAccountUUID TEXT NOT NULL,
                    CurrencyUUID TEXT NOT NULL,
                    Value DOUBLE(18, 2) DEFAULT 0
                )
                """);
    }

    public ResponseStatus createWallet (Wallet wallet){
        if (hasWallet(wallet.getWalletID())) return ResponseStatus.EXISTED;
        if (hasCurrencyWallet(wallet.getCurrencyID())) return ResponseStatus.EXISTED;

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO wallet (WalletUUID, OwnerAccountUUID, CurrencyUUID, Value) VALUES (?,?,?,?)")){
            ps.setString(1, wallet.getWalletID().toString());
            ps.setString(2, wallet.getOwnerID().toString());
            ps.setString(3, wallet.getCurrencyID().toString());
            ps.setDouble(4, wallet.getValue());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }

    public ResponseStatus deleteWallet (UUID walletUUID){
        if (!hasWallet(walletUUID)) return ResponseStatus.NOTFOUND;
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM wallet WHERE WalletUUID = ?")){
            ps.setString(1, walletUUID.toString());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }

    public boolean hasWallet (UUID walletUUID){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM wallet WHERE WalletUUID = ?")){
            ps.setString(1, walletUUID.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    // Every player can only have one wallet per each currency
    public boolean hasCurrencyWallet (UUID currencyUUID){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM wallet WHERE CurrencyUUID = ?")){
            ps.setString(1, currencyUUID.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public ArrayList<Wallet> getWallets (UUID owner){
        ArrayList<Wallet> wallets = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM wallet WHERE OwnerAccountUUID = ?")){
            ps.setString(1, owner.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                wallets.add(new Wallet(
                        UUID.fromString(rs.getString("OwnerAccountUUID")),
                        UUID.fromString(rs.getString("CurrencyUUID")),
                        UUID.fromString(rs.getString("WalletUUID")),
                        rs.getDouble("Value")
                ));
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
        return wallets;
    }

    @Nullable
    public Wallet getWallet (UUID walletUUID){
        if (!hasWallet(walletUUID)) return null;

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM wallet WHERE WalletUUID = ?")){
            ps.setString(1, walletUUID.toString());
            ResultSet rs = ps.executeQuery();
            return new Wallet(
                    UUID.fromString(rs.getString("OwnerAccountUUID")),
                    UUID.fromString(rs.getString("CurrencyUUID")),
                    UUID.fromString(rs.getString("WalletUUID")),
                    rs.getDouble("Value")
            );
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
    }

    public ResponseStatus updateWallet (Wallet wallet){
        if (!hasWallet(wallet.getWalletID())) return ResponseStatus.NOTFOUND;

        try (PreparedStatement ps = connection.prepareStatement("UPDATE wallet SET Value = ? WHERE WalletUUID = ?")){
            ps.setDouble(1, wallet.getValue());
            ps.setString(2, wallet.getWalletID().toString());
            ps.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
