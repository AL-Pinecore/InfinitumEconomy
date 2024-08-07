package cn.infinitumstudios.infinitumEconomy.event;


import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.event.utility.Event;
import cn.infinitumstudios.infinitumEconomy.event.utility.EventFactory;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface PlayerJoinEvent {
    void onCallback(OfflinePlayer player);

    Event<PlayerJoinEvent> EVENT = EventFactory.createArrayBacked(PlayerJoinEvent.class, (listeners) -> (player) -> {
        for (PlayerJoinEvent event : listeners) {
            event.onCallback(player);
        }
    });
}
