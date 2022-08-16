package tum.auth.configuration;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;

import java.io.File;

public class ConfigManager {
    public static SettingsManager getSettings(File config) {
        return SettingsManagerBuilder
                .withYamlFile(config)
                .configurationData(DataBaseProperties.class)
                .create();
    }
}
