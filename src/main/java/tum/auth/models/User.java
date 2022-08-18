package tum.auth.models;

import java.util.UUID;

public class User {
    private UUID minecraftUUID;
    private String TUMId;
    private boolean authenticated;
    private String token;

    public User(UUID minecarftUUID, String TUMId, boolean authenticated, String token) {
        this.minecraftUUID = minecarftUUID;
        this.TUMId = TUMId;
        this.authenticated = authenticated;
        this.token = token;
    }

    public UUID getMinecraftUUID() {
        return minecraftUUID;
    }

    public String getTUMId() {
        return TUMId;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getToken() {
        return token;
    }

    public void setMinecraftUUID(UUID minecraftUUID) {
        this.minecraftUUID = minecraftUUID;
    }

    public void setTUMId(String TUMId) {
        this.TUMId = TUMId;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
