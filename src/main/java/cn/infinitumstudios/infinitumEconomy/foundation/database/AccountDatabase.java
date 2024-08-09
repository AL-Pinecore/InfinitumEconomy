package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.UUID;

public class AccountDatabase extends Database<Account> {

    public AccountDatabase() {
        super("accounts", Account.class);
    }

    public void load(){
        super.load();
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
}
