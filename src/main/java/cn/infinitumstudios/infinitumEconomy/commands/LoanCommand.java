package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Loan;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LoanCommand extends InfinitumSubcommand {

    public LoanCommand(InfinitumCommand command) {
        super(command);
    }

    @Override
    public void reloadCommand(InfinitumCommand command) {
        super.reloadCommand(command);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        /*
        *  arg[0]   Command
        *  arg[1]   Amount to borrow
        *  arg[2]   Borrow who/which player/bank
        */

        Loan loan;
        double amount;
        double interestRate;
        String receiver;

        switch (args[0]){
            case "borrow":
                try {
                    amount = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    return false;
                }
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
                    // Need adding does the server contains the bank account that they are borrowing from
                    return false;
                }
                // Send message to the player that they are borrowing from
            case "list":

            case "payback":
        }


        return false;
    }

    @Override
    public @Nullable List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
