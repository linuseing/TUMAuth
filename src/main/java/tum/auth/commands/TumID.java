package tum.auth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tum.auth.TumAuth;
import tum.auth.api.InvalidTumIdException;
import tum.auth.models.User;

import java.io.IOException;

public class TumID implements CommandExecutor {

    private TumAuth tumAuth;

    public TumID(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Please provide your TUM-ID");
            return false;
        } else if (args.length > 1) {
            sender.sendMessage("Too many arguments!");
            return false;
        }

        String id = args[0];

        if (sender instanceof Player player) {
            tumAuth.getAuthenticator().setTumId(player, id);
            return true;
        } else {
            sender.sendMessage("This command may only be used by players.");
            return false;
        }
    }
}
