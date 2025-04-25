package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Currency;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class CurrencySQLDatabase {

    private final Connection connection;

    public CurrencySQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS currency(
                    CurrencyUUID TEXT PRIMARY KEY,
                    CurrencyName TEXT NOT NULL,
                    CurrencySymbol TEXT(1),
                    UniversalWorth DOUBLE DEFAULT 1
                )
                """);
    }

    public ResponseStatus createCurrency(Currency currency){
        if (hasCurrency(currency.getCurrencyID())) return ResponseStatus.EXISTED;
        if (hasCurrency(currency.getName())) return ResponseStatus.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO currency (CurrencyUUID, CurrencyName, CurrencySymbol, UniversalWorth) VALUES (?,?,?,?)")){
            preparedStatement.setString(1, currency.getCurrencyID().toString());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, Character.toString(currency.getSymbol()));
            preparedStatement.setDouble(4, currency.getCurrencyWorth());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    @Deprecated
    public ResponseStatus deleteCurrency(UUID currencyUUID){
        if (!hasCurrency(currencyUUID)) return ResponseStatus.NOTFOUND;

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM currency WHERE CurrencyUUID = ?")){
            preparedStatement.setString(1, currencyUUID.toString());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    public boolean hasCurrency(UUID currencyUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currency WHERE CurrencyUUID = ?")) {
            preparedStatement.setString(1, currencyUUID.toString());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public boolean hasCurrency(String currencyName){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currency WHERE CurrencyName = ?")) {
            preparedStatement.setString(1, currencyName);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    @Nullable
    public Currency getCurrency(UUID currencyUUID){
        if (!hasCurrency(currencyUUID)) return null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT CurrencyName, CurrencySymbol, UniversalWorth FROM currency WHERE CurrencyUUID = ?")){
            preparedStatement.setString(1, currencyUUID.toString());
            ResultSet rs = preparedStatement.executeQuery();
            return new Currency(
                    currencyUUID,
                    rs.getString("CurrencyName"),
                    rs.getString("CurrencySymbol").toCharArray()[0],
                    rs.getDouble("UniversalWorth")
            );
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
    }

    @Nullable
    public Currency getCurrency(String currencyName){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT CurrencyUUID, CurrencySymbol, UniversalWorth FROM currency WHERE CurrencyName = ?")){
            preparedStatement.setString(1, currencyName);
            ResultSet rs = preparedStatement.executeQuery();
            return new Currency(
                    UUID.fromString(rs.getString("CurrencyUUID")),
                    currencyName,
                    rs.getString("CurrencySymbol").toCharArray()[0],
                    rs.getDouble("UniversalWorth")
            );
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }
    }

    public ArrayList<Currency> getAllCurrencies (){
        ArrayList<Currency> currencies = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currency")){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                currencies.add(new Currency(
                        UUID.fromString(rs.getString("CurrencyUUID")),
                        rs.getString("CurrencyName"),
                        rs.getString("CurrencySymbol").toCharArray()[0],
                        rs.getDouble("UniversalWorth")
                ));
            }
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return null;
        }

        return currencies;
    }

    public ResponseStatus updateCurrency(Currency currency){
        if (!hasCurrency(currency.getCurrencyID())) return ResponseStatus.NOTFOUND;

        // No repeated currency name
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM currency WHERE CurrencyName = ? AND NOT CurrencyUUID = ?")){
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getCurrencyID().toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return ResponseStatus.EXISTED;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE currency SET CurrencySymbol = ?, UniversalWorth = ? WHERE CurrencyUUID = ?")){
            preparedStatement.setString(1, Character.toString(currency.getSymbol()));
            preparedStatement.setDouble(2, currency.getCurrencyWorth());
            preparedStatement.setString(3, currency.getCurrencyID().toString());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }
}