package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.MessageColor;
import cn.infinitumstudios.infinitumEconomy.utility.ThreadExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PayCommand extends InfinitumSubcommand{
    protected AccountDatabase db = new AccountDatabase();
    public PayCommand(InfinitumCommand command) {
        super(command);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        // Usage: /econ pay <amount> <...players>

        if(args.length < 2) {
            sender.sendMessage(command.getUsage());
            return false;
        }

        if(Arrays.stream(args).anyMatch(s -> s.equals(sender.getName()))) {
            sender.sendMessage(MessageColor.text("You cannot pay yourself.", ChatColor.RED));
            return false;
        }

        if(Arrays.stream(args).anyMatch(s -> Bukkit.getOnlinePlayers().stream().anyMatch(o -> s.equals(o.getName())))) { // Checks whether all the usernames listed are in the list of online players
            sender.sendMessage(MessageColor.text("You cannot pay an offline player.", ChatColor.RED));
            return false;
        }

        double payment = 0;
        try {
            payment = Double.parseDouble(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(command.getUsage());
            return false;
        }

        db.load();

        List<UUID> targetUUIDs = Arrays.stream(args)
                .skip(1) // Skip the amount argument
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getUniqueId)
                .toList();

        Optional<Account> fromAccount = db.getAccountWithHolder(((Player) sender).getUniqueId());

        if (fromAccount.isPresent() && !targetUUIDs.isEmpty()) {
            double paymentPerPlayer = payment / targetUUIDs.size();

            // Update sender's account
            double finalPayment = payment;
            boolean senderUpdateSuccess = db.update(
                    account -> account.getAccountHolder().equals(fromAccount.get().getAccountHolder()),
                    account -> {
                        boolean success = account.decrementBalance(finalPayment);
                        if (!success) {
                            sender.sendMessage("You don't have enough money.");
                        }
                        return success ? account : null;
                    }
            );

            if (!senderUpdateSuccess) {
                return false;
            }

            // Update recipients' accounts
            AtomicInteger successfulTransfers = new AtomicInteger(0);
            targetUUIDs.forEach(uuid -> {
                boolean recipientUpdateSuccess = db.update(
                        account -> account.getAccountHolder().equals(uuid),
                        account -> {
                            account.incrementBalance(paymentPerPlayer);
                            return account;
                        }
                );
                if (recipientUpdateSuccess)
                    successfulTransfers.incrementAndGet();
            });

            sender.sendMessage(MessageColor.text(
                    String.format("You transferred a total of %s to %d account(s).",
                            Currency.DEFAULT.value(paymentPerPlayer * successfulTransfers.get(), false),
                            successfulTransfers.get()),
                    ChatColor.GREEN
            ));

            ThreadExecutor.run(() -> {
                db.save();
                AccountDatabase.loadAll();
            });
            return true;
        } else {
            sender.sendMessage(MessageColor.text("Invalid Target/Sender Account.", ChatColor.RED));
            return false;
        }
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1){
            List<String> suggestion = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers())
                suggestion.add(player.getName());
            return suggestion;
        } else {
            return null;
        }
    }
}
