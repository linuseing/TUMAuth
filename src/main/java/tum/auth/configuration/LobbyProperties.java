package tum.auth.configuration;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class LobbyProperties implements SettingsHolder {
    public static final Property<Integer> WELCOME_X =
            newProperty("welcome_lobby.x", 0);

    public static final Property<Integer> WELCOME_Y =
            newProperty("welcome_lobby.y", 0);

    public static final Property<Integer> WELCOME_Z =
            newProperty("welcome_lobby.z", 0);

    public static final Property<String> WELCOME_WORLD =
            newProperty("welcome_lobby.world", "world");

    public static final Property<Integer> MEMBERS_X =
            newProperty("members_lobby.x", 0);

    public static final Property<Integer> MEMBERS_Y =
            newProperty("members_lobby.y", 0);

    public static final Property<Integer> MEMBERS_Z =
            newProperty("members_lobby.z", 0);

    public static final Property<String> MEMBERS_WORLD =
            newProperty("members_lobby.world", "world");

}
