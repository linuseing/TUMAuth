package tum.auth.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tum.auth.TumAuth;
import tum.auth.datasource.DataSource;
import tum.auth.models.User;

import java.util.UUID;

public class Join implements Listener {

    private final TumAuth tumAuth;
    private final DataSource dataSource;

    public Join(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
        this.dataSource = tumAuth.getDataSource();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        try {
            User user = dataSource.getUser(uuid);
            if (user == null) {
                player.chat("Please set your TUM-ID using /tumid <id>");
                return;
            }
            if (!user.isAuthenticated() && user.getToken() != null && tumAuth.getApi().verifyToken(user.getToken())) {
                user.setAuthenticated(true);
                dataSource.updateUser(user);
                return;
            }
            if (user.isAuthenticated())
                return;
            player.kickPlayer("Please activate your Token to be able to access this server.");
        } catch (Exception e) {
            player.kickPlayer("Some error occurred reaching the database. Please try again later or contact an admin."+e.toString());
        }
    }
}
