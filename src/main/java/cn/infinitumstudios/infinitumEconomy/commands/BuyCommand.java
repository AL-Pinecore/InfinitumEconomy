package cn.infinitumstudios.infinitumEconomy.commands;

import cn.infinitumstudios.infinitumEconomy.foundation.database.AccountDatabase;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuyCommand extends InfinitumSubcommand{
    protected AccountDatabase db = new AccountDatabase();
    public BuyCommand (InfinitumCommand command) {
        super(command);
    }
    @Override
    public void reloadCommand(InfinitumCommand command) {
        super.reloadCommand(command);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        return super.execute(sender, args);
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return super.tabComplete(sender, args);
    }
}
