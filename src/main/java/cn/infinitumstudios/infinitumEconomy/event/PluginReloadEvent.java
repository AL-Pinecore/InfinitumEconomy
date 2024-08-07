package cn.infinitumstudios.infinitumEconomy.event;


import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.event.utility.Event;
import cn.infinitumstudios.infinitumEconomy.event.utility.EventFactory;

public interface PluginReloadEvent {
    void onCallback(InfinitumEconomy plugin);

    Event<PluginReloadEvent> EVENT = EventFactory.createArrayBacked(PluginReloadEvent.class, (listeners) -> (plugin) -> {
        for (PluginReloadEvent event : listeners) {
            event.onCallback(plugin);
        }
    });
}
