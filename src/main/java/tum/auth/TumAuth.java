package tum.auth;

import ch.jalu.configme.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tum.auth.commands.Gommemode;
import tum.auth.commands.ReloadConfig;
import tum.auth.configuration.ConfigManager;
import tum.auth.datasource.DataSource;
import tum.auth.datasource.PostgreSqlDataSource;
import tum.auth.handler.Join;

import java.io.File;

public class TumAuth extends JavaPlugin {

    private File dataFolder;
    private SettingsManager settingsManager;
    private DataSource dataSource;


    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        settingsManager = ConfigManager.getSettings(new File(dataFolder, "config.yaml"));
        dataSource = new PostgreSqlDataSource(settingsManager);

        getCommand("gommemode").setExecutor(new Gommemode());
        getCommand("update_auth_config").setExecutor(new ReloadConfig(this));
        getServer().getPluginManager().registerEvents(new Join(), this);
    }


}
