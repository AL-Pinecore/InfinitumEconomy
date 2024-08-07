package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.event.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class AccountDatabase {

    public static void load(){
        PlayerJoinEvent.EVENT.register(player -> {
            if(!player.hasPlayedBefore()){

            }
        });
    }

    /**
     * @deprecated Deprecated method. Use {@link #getPlayer(UUID)} instead.
     * @param username The username of the player.
     * @return Returns a player if the player is found in online players and offline players.
     */
    @Deprecated(forRemoval = true, since = "0.0")
    public static @Nullable Player getPlayer(String username){
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equals(username)) {
                return player.getPlayer();
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(username)) {
                return player;
            }
        }

        return null;
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
}
