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
            try {
                User user = tumAuth.getDataSource().getUser(player.getUniqueId());
                if (user != null) {
                    sender.sendMessage(
                            "You already set your TUM-ID.",
                            "Please contact an admin if you want to unbind your account"
                    );
                    return false;
                }
                try {
                    String token = tumAuth.getApi().requestToken(id);
                    tumAuth.getDataSource().addUser(new User(player.getUniqueId(), args[0], false, token));
                } catch (InvalidTumIdException ignore) {
                    player.sendMessage("Your TUM-ID seems to be invalid. Please try again!");
                    return false;
                } catch (IOException ignore) {
                    player.sendMessage("Some error occurred reaching the database. Please try again later or contact an admin.");
                    return false;
                }
            } catch (Exception e) {
                player.sendMessage("Some error occurred reaching the database. Please try again later or contact an admin.");
            }
        } else {
            sender.sendMessage("This command may only be used by players.");
        }

        return false;
    }
}
