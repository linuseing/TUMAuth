import ch.jalu.configme.SettingsManager;
import org.junit.jupiter.api.Test;
import tum.auth.configuration.ConfigManager;
import tum.auth.configuration.DataBaseProperties;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {
    @Test
    public void defaults() {
        SettingsManager manager = ConfigManager.getSettings(new File("tests/config/empty.yaml"));
        assertEquals(manager.getProperty(DataBaseProperties.DB_HOST), "-Default-");
        assertEquals(manager.getProperty(DataBaseProperties.DB_PASSWORD), "-Default-");
        assertEquals(manager.getProperty(DataBaseProperties.DB_USER), "-Default-");
        assertEquals(manager.getProperty(DataBaseProperties.DB_PORT), 0);
    }

    @Test
    public void config() {
        SettingsManager manager = ConfigManager.getSettings(new File("tests/config/config.yaml"));
        assertEquals(manager.getProperty(DataBaseProperties.DB_HOST), "db.org");
        assertEquals(manager.getProperty(DataBaseProperties.DB_PASSWORD), "password");
        assertEquals(manager.getProperty(DataBaseProperties.DB_USER), "user");
        assertEquals(manager.getProperty(DataBaseProperties.DB_PORT), 5432);
    }
}
