package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.database.CurrencyDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoneyCommand implements CommandExecutor, TabCompleter {

    AccountDatabase db = new AccountDatabase();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        db.load();

        String targetPlayerName = args[1];
        double amount;
        try{
            amount = roundTwoDecimals(Double.parseDouble(args[2]));
        } catch(Exception e){
            sender.sendMessage("You did not write a number.");
            return false;
        }


        Optional<Account> targetAccount = db.getAccountWithHolder(((Player) sender).getUniqueId());

        boolean targetUpdateSuccess = false;

        switch (args[0]){
            case "add":
                if (targetAccount.isPresent()){
                    targetUpdateSuccess = db.update(
                            account -> account.getAccountHolder().equals(targetAccount.get().getAccountHolder()),
                            account -> {
                                account.incrementBalance(amount);
                                return account;
                            }
                    );
                } else {
                    sender.sendMessage("Target account does not exist!");
                    return false;
                }

                return targetUpdateSuccess;

            case "remove":
                if (targetAccount.isPresent()) {
                    targetUpdateSuccess = db.update(
                            account -> account.getAccountHolder().equals(targetAccount.get().getAccountHolder()),
                            account -> {
                                boolean success = account.decrementBalance(amount);
                                if (!success) {
                                    sender.sendMessage("Target player don't have enough money.");
                                }
                                return success ? account : null;
                            }
                    );
                } else {
                    sender.sendMessage("Target account does not exist!");
                    return false;
                }

                return targetUpdateSuccess;

            case "set":
                if (targetAccount.isPresent()){
                    targetUpdateSuccess = db.update(
                            account -> account.getAccountHolder().equals(targetAccount.get().getAccountHolder()),
                            account -> {
                                account.setBalance(amount);
                                return account;
                            }
                    );
                } else {
                    sender.sendMessage("Target account does not exist!");
                    return false;
                }

                return targetUpdateSuccess;

        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestion = new ArrayList<>();
        if (args.length == 1){
            suggestion.add("add");
            suggestion.add("remove");
            suggestion.add("set");
            return suggestion;
        } else if(args.length == 2){
            for (Player player: Bukkit.getOnlinePlayers()){
                suggestion.add(player.getName());
            }
            return suggestion;
        } else {
            return null;
        }
    }
    // Usage: /money add|remove|set <player> <amount>
    // args[1] -> target player
    // args[2] -> amount
    public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }
}
