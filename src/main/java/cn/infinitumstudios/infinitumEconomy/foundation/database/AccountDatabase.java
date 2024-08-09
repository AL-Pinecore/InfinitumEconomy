package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * @implNote The account database is a wrapper of the {@link Database} class for {@link Account} related operations. See {@link Database} implementation for more info.
 * @apiNote Before you use any methods from this class, please *read the documentations* of each method in this class.
 */
public class AccountDatabase extends Database<Account> {

    public AccountDatabase() {
        super(Reference.ACCOUNT_DATABASE_NAME, Account.class);

        PlayerJoinEvent.EVENT.register(event -> {
            refreshPlayerEntries();
        });
    }

    /**
     * @param uuid The UUID of a player.
     * @return Returns a player if the player is found in online players and offline players.
     */
    public static @Nullable Player getPlayer(UUID uuid){
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getUniqueId().equals(uuid)) {
                return player.getPlayer();
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(uuid)) {
                return player;
            }
        }

        return null;
    }

    public void refreshPlayerEntries(){
        load();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if(!player.hasPlayedBefore()){
                create(new Account(player.getUniqueId(), player.getName()));
            }
        }
        save();
    }

    public boolean addAccount(Account account){
        if(account == null) return false;

        for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
            if (player.getUniqueId().equals(account.getAccountHolder())) {
                create(account);
                return true;
            }
        }

        return false;
    }

    public boolean removeAccount(UUID uuid){
        return delete(account -> account.getAccountHolder().equals(uuid));
    }

    /**
     * @param uuid The UUID of an Account instance.
     * @implNote Please use {@link #getAccount(UUID)} if you want to get the Account using an Account UUID.
     * @return An Account instance, possibly Null.
     */
    public Optional<Account> getAccountWithHolder(UUID uuid){
        return read(account -> account.getAccountHolder().equals(uuid));
    }

    /**
     * @param uuid The UUID of an Account instance.
     * @implNote Please use {@link #getAccountWithHolder(UUID)} if you want to get the Account using a Player UUID.
     * @return An Account instance, possibly Null.
     */
    public Optional<Account> getAccount(UUID uuid){
        return read(account -> account.getAccountUUID().equals(uuid));
    }
}
