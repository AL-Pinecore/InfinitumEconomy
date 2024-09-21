package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @implNote The account database is a wrapper of the {@link Database} class for {@link Account} related operations. See {@link Database} implementation for more info.
 * @apiNote Before you use any methods from this class, please *read the documentations* of each method in this class.
 */
public class AccountDatabase extends Database<Account> {

    /**
     * Everytime you create an account database, you should make sure that it is actually loaded.
     */
    public AccountDatabase() {
        super(Reference.ACCOUNT_DATABASE_NAME, Account.class, new File(Path.of(Reference.DATA_FILES_DIRECTORY.toString(), Reference.ACCOUNT_DATABASE_NAME + ".json").toUri()));
    }

    /**
     * @param uuid The UUID of a player.
     * @return Returns a player if the player is found in online players and offline players.
     */
    public static @Nullable OfflinePlayer getPlayer(UUID uuid){
        return Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> offlinePlayer.getUniqueId().equals(uuid)).findFirst().orElseGet(null);
    }

    public void refreshPlayerEntries(){
        load();
        Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> !offlinePlayer.hasPlayedBefore()).forEach(offlinePlayer -> {
            create(new Account(offlinePlayer.getUniqueId(), offlinePlayer.getName()));
        });
        save();
    }

    public boolean addAccount(Account account){
        if(account == null) return false;

        Optional<OfflinePlayer> temp = Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> offlinePlayer.getUniqueId().equals(account.getAccountHolder())).findFirst().stream().findFirst();
        temp.ifPresent(offlinePlayer -> {
            create(account);
        });

        return temp.isPresent();
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

    public Optional<Account> getPlayerAccount(UUID uuid){
        return read(account -> account.getAccountHolder().equals(uuid));
    }

    public Optional<Account> getPlayerAccount(OfflinePlayer player){
        return getPlayerAccount(player.getUniqueId());
    }

    public boolean accountExists(UUID uuid){
        return getPlayerAccount(uuid).isPresent();
    }

    public boolean accountExists(OfflinePlayer player){
        return getPlayerAccount(player).isPresent();
    }

    public boolean update(Predicate<Account> accountPredicate, UnaryOperator<Account> account) {
        return super.update(accountPredicate, account);
    }

    public static void init(){
        PlayerJoinEvent.EVENT.register(event -> {
            new AccountDatabase().refreshPlayerEntries();
        });
    }
}
