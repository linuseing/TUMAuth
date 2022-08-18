package tum.auth;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import tum.auth.api.InvalidTumIdException;
import tum.auth.datasource.DataSource;
import tum.auth.models.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Authenticator {
    private TumAuth tumAuth;
    private DataSource dataSource;
    private Set<Player> unauthenticatedPlayer = new HashSet<>();
    private BukkitTask cleanUp;

    public Authenticator(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
        dataSource = tumAuth.getDataSource();
        cleanUp = Bukkit.getScheduler().runTaskTimer(tumAuth, () -> unauthenticatedPlayer.removeIf(player -> {
            if (!player.isOnline()) {
                return true;
            } else {
                User user = checkToken(player);
                if (user != null) {
                    handleAuthenticatedPlayer(player, user);
                    return true;
                }
            }
            return false;
        }), 100, 100);
    }

    public void tryLogin(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            User user = dataSource.getUser(uuid);
            if (user == null) {
                handleUnknownPlayer(player);
            } else if (!checkAuthenticationStatus(user)) {
                handleUnauthenticatedPlayer(player);
            } else {
                handleAuthenticatedPlayer(player, user);
            }
        } catch (Exception e) {
            player.sendMessage("Some error occurred reaching the database or the TUM-API. Please try again later or contact an admin.");
        }
    }

    public void setTumId(Player player, String tumID) {
        try {
            String token = tumAuth.getApi().requestToken(tumID);
            User user = new User(player.getUniqueId(), tumID, false, token);
            tumAuth.getDataSource().addUser(user);
            player.sendMessage(
                    "Thank you for submitting your TUM-ID.",
                    "Now, open campus online and activate the Token '"+ tumAuth.getApi().getName() +"'.",
                    "To do this, scroll down to the application 'token management' at the bottom of the page, and make sure that the checkbox 'active' of our token is checked.",
                    "As of now, we dont require you to provide any permissions."
            );
            Bukkit.getScheduler().runTaskLater(tumAuth, () -> {
                if (unauthenticatedPlayer.contains(player)) {
                    player.kickPlayer("Please reconnect after activating the token.");
                }
            }, 1000);
        } catch (InvalidTumIdException e) {
            player.sendMessage("Your Tum id seems to be wrong.");
        } catch (Exception e) {
            player.sendMessage("Some error occurred reaching the database or the TUM-API. Please try again later or contact an admin.");
        }
    }

    public User checkToken(Player player) {
        try {
            User user = dataSource.getUser(player.getUniqueId());
            if (user == null)
                return null;

            if (checkAuthenticationStatus(user)) {
                return user;
            }
        } catch (Exception e) {
            player.sendMessage("Some error occurred reaching the database or the TUM-API. Please try again later or contact an admin.");
        }
        return null;
    }

    private void handleAuthenticatedPlayer(Player player, User user) {
        player.sendMessage("Thank you for joining!");
    }

    private void handleUnauthenticatedPlayer(Player player) {
        unauthenticatedPlayer.add(player);
        player.kickPlayer("Please activate your Token to be able to access this server.");
    }

    private void handleUnknownPlayer(Player player) {
        unauthenticatedPlayer.add(player);
        player.sendMessage(
                "Hey there.",
                "This server is exclusively for students of the Technical University of Munich.",
                "To ensure that you are a student of this excellent institution and belong here,",
                "please enter your TUM-ID (TUM-Kennung) via the command '/tum-id id'"
        );
    }

    private boolean checkAuthenticationStatus(@NotNull User user) throws Exception {
        if (user.isAuthenticated())
            return true;

        if (tumAuth.getApi().verifyToken(user.getToken())) {
            user.setAuthenticated(true);
            dataSource.updateUser(user);
            return true;
        }

        return false;
    }
}
