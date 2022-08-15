package tum.auth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tum.auth.TumAuth;

public class ReloadConfig implements CommandExecutor {

    private final TumAuth tumAuth;

    public ReloadConfig(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        tumAuth.updateConfiguration();
        return false;
    }
}
