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
    protected BalanceCommand balanceCommand;
    protected SellCommand sellCommand;
    protected BuyCommand buyCommand;

    public EconCommand(InfinitumEconomy plugin) {
        super(plugin);
        payCommand = new PayCommand(this);
        loanCommand = new LoanCommand(this);
        chequeCommand = new ChequeCommand(this);
        baltopCommand = new BaltopCommand(this);
        balanceCommand = new BalanceCommand(this);
        sellCommand = new SellCommand(this);
        buyCommand = new BuyCommand(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(MessageColor.text("This command can only be ran by a player.", ChatColor.RED));
            return false;
        }

        if(args.length == 0) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        String[] truncatedArgs = Arrays.copyOfRange(args, 1, args.length);

        return switch (args[0].toLowerCase()) {
            case "pay" -> payCommand.execute(commandSender, truncatedArgs);
            case "loan" -> loanCommand.execute(commandSender, truncatedArgs);
            case "cheque" -> chequeCommand.execute(commandSender, truncatedArgs);
            case "baltop" -> baltopCommand.execute(commandSender, truncatedArgs);
            case "balance" -> balanceCommand.execute(commandSender, truncatedArgs);
            case "sell" -> sellCommand.execute(commandSender, truncatedArgs);
            case "buy" -> buyCommand.execute(commandSender, truncatedArgs);
            default -> {
                commandSender.sendMessage(getUsage());
                yield false;
            }
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String[] truncatedArgs = Arrays.copyOfRange(args, 1, args.length);

        if(args.length == 1){
            return Arrays.asList("pay", "loan", "cheque", "baltop", "balance", "sell", "buy");
        } else if(args.length == 2) {
            return switch (args[0].toLowerCase()) {
                case "pay" -> payCommand.tabComplete(commandSender, truncatedArgs);
                case "loan" -> loanCommand.tabComplete(commandSender, truncatedArgs);
                case "cheque" -> chequeCommand.tabComplete(commandSender, truncatedArgs);
                case "baltop" -> baltopCommand.tabComplete(commandSender, truncatedArgs);
                case "balance" -> balanceCommand.tabComplete(commandSender, truncatedArgs);
                case "sell" -> sellCommand.tabComplete(commandSender, truncatedArgs);
                case "buy" -> buyCommand.tabComplete(commandSender, truncatedArgs);

                default -> {
                    commandSender.sendMessage(getUsage());
                    yield null;
                }
            };
        } else {
            return null;
        }
    }

    @Override
    public String getUsage() {
        return "/econ <options>";
    }
}
