package cn.infinitumstudios.infinitumEconomy.event.listeners;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {
    InfinitumEconomy ie;
    public PlayerEventListener(InfinitumEconomy ie){
        this.ie = ie;
    }
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event){
        OfflinePlayer player = event.getPlayer();
        ie.setPlayerAccount(player);
    }
}
