package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.InfinitumEconomy;
import cn.infinitumstudios.infinitumEconomy.utility.MessageColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EconCommand extends InfinitumCommand implements CommandExecutor, TabCompleter {
    public static String KEYWORD = "econ";
    protected PayCommand payCommand;
    protected LoanCommand loanCommand;
    protected ChequeCommand chequeCommand;
    protected BaltopCommand baltopCommand;

    public EconCommand(InfinitumEconomy plugin) {
        super(plugin);
        payCommand = new PayCommand(this);
        loanCommand = new LoanCommand(this);
        chequeCommand = new ChequeCommand(this);
        baltopCommand = new BaltopCommand(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(MessageColor.text("This command can only be ran by a player.", ChatColor.RED));
            return false;
        }

        if(args.length <= 0) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        String[] truncatedArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (args[0].toLowerCase()) {
            case "pay":
                return payCommand.execute(commandSender, truncatedArgs);
            case "loan":
                return loanCommand.execute(commandSender, truncatedArgs);
            case "cheque":
                return chequeCommand.execute(commandSender, truncatedArgs);
            case "baltop":
                return baltopCommand.execute(commandSender, truncatedArgs);
            default:
                commandSender.sendMessage(getUsage());
                return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String[] truncatedArgs = Arrays.copyOfRange(args, 1, args.length);

        if(args.length == 1){
            return Arrays.asList("pay", "loan", "cheque", "baltop");
        } else if(args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "pay":
                    return payCommand.tabComplete(commandSender, truncatedArgs);
                case "loan":
                    return loanCommand.tabComplete(commandSender, truncatedArgs);
                case "cheque":
                    return chequeCommand.tabComplete(commandSender, truncatedArgs);
                case "baltop":
                    return baltopCommand.tabComplete(commandSender, truncatedArgs);
                default:
                    commandSender.sendMessage(getUsage());
                    return null;
            }
        }
    }

    @Override
    public String getUsage() {
        return "/econ <options>";
    }
}
