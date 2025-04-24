package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Cheque;
import cn.infinitumstudios.infinitumEconomy.utility.ResponseStatus;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class ChequeSQLDatabase {

    // TODO Cheque SQL
    private final Connection connection;

    public ChequeSQLDatabase(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS cheque(
                    ChequeUUID TEXT PRIMARY KEY,
                    Worth DOUBLE(18, 2) DEFAULT 0,
                    CurrencyUUID TEXT NOT NULL,
                    OwnerAccountUUID TEXT NOT NULL
                )
                """);
    }

    /**
     * Creates a cheque
     * @param cheque an instance of the Cheque class.
     * @return {@link ResponseStatus#FAILED}
     */
    public ResponseStatus createCheque(Cheque cheque){
        if (cheque == null) return ResponseStatus.FAILED;
        if (hasCheque(cheque.getChequeID())) return ResponseStatus.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cheque (ChequeUUID,  Worth, CurrencyUUID, OwnerAccountUUID) VALUES (?,?,?,?)")){
            preparedStatement.setString(1,cheque.getChequeID().toString());
            preparedStatement.setDouble(2,cheque.getWorth());
            preparedStatement.setString(3,cheque.getCurrencyID().toString());
            preparedStatement.setString(4,cheque.getOwnerID().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
        return ResponseStatus.SUCCESS;
    }

    public ResponseStatus deleteCheque (UUID chequeUUID){
        if (chequeUUID == null) return ResponseStatus.FAILED;
        if (!hasCheque(chequeUUID)) return ResponseStatus.NOTFOUND;

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cheque WHERE ChequeUUID = ?")){
            preparedStatement.setString(1,chequeUUID.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }

        return ResponseStatus.SUCCESS;
    }

    public boolean hasCheque(UUID chequeUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cheque WHERE ChequeUUID = ?")){
            preparedStatement.setString(1, chequeUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return false;
        }
    }

    public ArrayList<Cheque> getCheques(UUID ownerUUID){
        ArrayList<Cheque> cheques = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT Worth, CurrencyUUID, ChequeUUID FROM cheque WHERE OwnerAccountUUID = ?")){
            preparedStatement.setString(1, ownerUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                cheques.add(new Cheque(ownerUUID,
                        UUID.fromString(resultSet.getString("ChequeUUID")),
                        resultSet.getDouble("Worth"),
                        UUID.fromString(resultSet.getString("CurrencyUUID"))));
            }

            return cheques;
        } catch (Exception e){
            return null;
        }
    }

    @Nullable
    public Cheque getCheque(UUID chequeUUID){
        if (chequeUUID == null) return null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT Worth, CurrencyUUID, OwnerAccountUUID FROM cheque WHERE ChequeUUID = ?")){
            preparedStatement.setString(1, chequeUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Cheque(UUID.fromString(resultSet.getString("OwnerAccountUUID")),
                        chequeUUID,
                        resultSet.getDouble("Worth"),
                        UUID.fromString(resultSet.getString("CurrencyUUID")));
            } else {
                return null;
            }
        } catch (SQLException e){
            return null;
        }
    }

    @Deprecated
    public ResponseStatus updateCheque(Cheque cheque){
        if (cheque == null) return ResponseStatus.FAILED;
        if (!hasCheque(cheque.getChequeID())) return ResponseStatus.NOTFOUND;

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cheque SET Worth = ?, CurrencyUUID = ?, OwnerAccountUUID = ? WHERE ChequeUUID = ?")){
            preparedStatement.setDouble(1, cheque.getWorth());
            preparedStatement.setString(2, cheque.getCurrencyID().toString());
            preparedStatement.setString(3, cheque.getOwnerID().toString());
            preparedStatement.setString(4, cheque.getChequeID().toString());
            preparedStatement.executeUpdate();
            return ResponseStatus.SUCCESS;
        } catch (Exception e){
            Logger logger = Bukkit.getLogger();
            logger.warning(e.toString());
            return ResponseStatus.FAILED;
        }
    }
}
