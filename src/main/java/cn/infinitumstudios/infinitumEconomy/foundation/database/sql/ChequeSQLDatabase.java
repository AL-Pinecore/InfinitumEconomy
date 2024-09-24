package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Cheque;
import cn.infinitumstudios.infinitumEconomy.utility.Status;

import java.sql.*;
import java.util.UUID;

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
                    OwnerAccountUUID TEXT NOT NULL,
                    OwnerNickname TEXT NOT NULL
                )
                """);
    }

    /**
     * Creates a cheque
     * @param cheque an instance of the Cheque class.
     * @return {@link Status#FAILED}
     */
    public Status createCheque(Cheque cheque){
        if (cheque == null) return Status.FAILED;
        if (hasCheque(cheque.getUUID())) return Status.EXISTED;

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cheque (ChequeUUID,  Worth, CurrencyUUID, OwnerAccountUUID, OwnerNickname) VALUES (?,?,?,?,?)")){
            preparedStatement.setString(1,cheque.getUUID().toString());
            preparedStatement.setDouble(2,cheque.getWorth());
            preparedStatement.setString(3,cheque.getCurrencyUUID().toString());
            preparedStatement.setString(4,cheque.getOwnerUUID().toString());
            preparedStatement.setString(5,cheque.getOwnerName());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            return Status.FAILED;
        }
        return Status.SUCCESS;
    }

//    public Status deleteCheque (UUID che){
//        if (playerUUID == null) return Status.FAILED;
//        if (!hasAccount(playerUUID)) return Status.NOTFOUND;
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE AccountHolderUUID = ?")){
//            preparedStatement.setString(1,playerUUID.toString());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e){
//            return Status.FAILED;
//        }
//
//        return Status.SUCCESS;
//    }

    public boolean hasCheque(UUID chequeUUID){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cheque WHERE ChequeUUID = ?")){
            preparedStatement.setString(1, chequeUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            return false;
        }
    }

}
