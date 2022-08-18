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
        tumAuth.getAuthenticator().tryLogin(player);
    }
}
