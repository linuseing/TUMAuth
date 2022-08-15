package tum.auth;

import org.bukkit.plugin.java.JavaPlugin;
import tum.auth.commands.Gommemode;
import tum.auth.handler.Join;

public class TumAuth extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("gommemode").setExecutor(new Gommemode());
        getServer().getPluginManager().registerEvents(new Join(), this);
    }

}
