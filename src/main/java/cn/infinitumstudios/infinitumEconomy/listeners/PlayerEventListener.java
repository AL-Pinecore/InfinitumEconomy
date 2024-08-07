package cn.infinitumstudios.infinitumEconomy.listeners;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        String playerName = event.getPlayer().getName();
        String playerUUID = event.getPlayer().getUniqueId().toString();
    }
}
