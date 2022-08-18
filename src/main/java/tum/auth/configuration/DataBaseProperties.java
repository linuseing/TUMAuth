package tum.auth.configuration;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class DataBaseProperties implements SettingsHolder {
    public static final Property<String> DB_HOST =
            newProperty("db.host", "-Default-");

    public static final Property<Integer> DB_PORT =
            newProperty("db.port", 0);

    public static final Property<String> DB_USER =
            newProperty("db.user", "-Default-");

    public static final Property<String> DB_PASSWORD =
            newProperty("db.password", "-Default-");
    public static final Property<String> DB_NAME =
            newProperty("db.name", "-Default-");
}
