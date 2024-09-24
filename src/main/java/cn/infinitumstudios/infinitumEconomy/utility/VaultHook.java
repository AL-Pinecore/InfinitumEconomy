package cn.infinitumstudios.infinitumEconomy.utility;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {
    private final InfinitumEconomy plugin = InfinitumEconomy.getPlugin();
    private Economy provider = InfinitumEconomy.getEconomyImplementer();
    public void hook(){
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] Hooking into Vault...", plugin.getName()));
        provider = InfinitumEconomy.getEconomyImplementer();
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked into " + ChatColor.AQUA + plugin.getName());
    }

    public void unhook(){
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + plugin.getName());
    }
}
