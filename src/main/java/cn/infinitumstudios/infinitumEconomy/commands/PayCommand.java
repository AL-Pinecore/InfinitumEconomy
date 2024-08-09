package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class PayCommand extends InfinitumSubcommand implements CommandExecutor, TabCompleter{
    InfinitumEconomy plugin;
    AccountDatabase accountDatabase = new AccountDatabase();
    public PayCommand(InfinitumEconomy plugin) {
        super(plugin);
        this.plugin = plugin;
        accountDatabase.load();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (Bukkit.getPlayerUniqueId(args[0]) == null) return false;

        Player fromAccount = (Player) sender;
        UUID fromAccountUUID = fromAccount.getUniqueId();
        UUID targetAccountUUID = Bukkit.getPlayerUniqueId(args[0]);

        double amount;

        try
        {
            amount = Double.parseDouble(args[1]);
        }
        catch (Exception e)
        {
            return false;
        }

        Optional<Account> accFrom = accountDatabase.read(Account -> {
            if(Account.getAccountUUID() == fromAccountUUID && Account.getBalance() - amount >= 0){
                Account.decrementBalance(amount);
                return true;
            } else {
                return false;
            }
        });

        if(accFrom.isEmpty()) return false;

        Optional<Account> accTarget = accountDatabase.read(Account -> {
            if(Account.getAccountHolder() == targetAccountUUID){
                Account.incrementBalance(amount);
                return true;
            } else {
                return false;
            }
        });

        return accTarget.isPresent();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            List<String> suggestion = new ArrayList<>();
            for(Player player : plugin.getServer().getOnlinePlayers()){
                suggestion.add(player.getName());
            }
            return suggestion;
        } else if (args.length == 2){
            List<String> suggestion = new ArrayList<>();
            suggestion.add("<Amount>");
            return  suggestion;
        } else {
            return null;
        }
    }
}
