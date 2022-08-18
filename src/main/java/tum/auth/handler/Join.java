package tum.auth.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tum.auth.TumAuth;

import java.util.UUID;

public class Join implements Listener {

    private TumAuth tumAuth;

    public Join(TumAuth tumAuth) {
        this.tumAuth = tumAuth;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();


        event.getPlayer().kickPlayer("Please register at: 'https://duhund.com'");
    }
}
