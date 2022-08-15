package tum.auth;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tum.auth.commands.Gommemode;
import tum.auth.commands.ReloadConfig;
import tum.auth.handler.Join;

public class TumAuth extends JavaPlugin {

    FileConfiguration configuration;

    @Override
    public void onEnable() {
        getCommand("gommemode").setExecutor(new Gommemode());
        getCommand("update_auth_config").setExecutor(new ReloadConfig(this));
        getServer().getPluginManager().registerEvents(new Join(), this);
        updateConfiguration();
    }

    public void updateConfiguration() {
        configuration = getConfig();
        configuration.addDefault("db_host", "INSERT_DB_HOST");
        configuration.addDefault("db_port", "INSERT_DB_PORT");
        configuration.addDefault("db_user", "INSERT_DB_USER");
        configuration.addDefault("db_password", "INSERT_DB_PASSWORD");
        configuration.options().copyDefaults(true);
        saveConfig();
    }

}
