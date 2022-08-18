package tum.auth;

import ch.jalu.configme.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tum.auth.api.TumAPI;
import tum.auth.commands.Gommemode;
import tum.auth.commands.ReloadConfig;
import tum.auth.commands.TumID;
import tum.auth.configuration.ConfigManager;
import tum.auth.datasource.DataSource;
import tum.auth.datasource.PostgreSqlDataSource;
import tum.auth.handler.Join;

import java.io.File;

public class TumAuth extends JavaPlugin {

    private File dataFolder;
    private SettingsManager settingsManager;
    private DataSource dataSource;
    private TumAPI api;


    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        settingsManager = ConfigManager.getSettings(new File(dataFolder, "config.yaml"));
        dataSource = new PostgreSqlDataSource(settingsManager);
        try {
            dataSource.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        api = new TumAPI("tum-mc");

        getCommand("gommemode").setExecutor(new Gommemode());
        getCommand("update_auth_config").setExecutor(new ReloadConfig(this));
        getCommand("tum-id").setExecutor(new TumID(this));
        getServer().getPluginManager().registerEvents(new Join(this), this);
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public TumAPI getApi() {
        return api;
    }
}
