package tum.auth.models;

import java.util.UUID;

public class User {
    private UUID minecraftUUID;
    private String TUMId;
    private boolean isAuthenticated;
    private String token;

    public User(UUID minecarftUUID, String TUMId, boolean isAuthenticated, String token) {
        this.minecraftUUID = minecarftUUID;
        this.TUMId = TUMId;
        this.isAuthenticated = isAuthenticated;
        this.token = token;
    }

    public UUID getMinecraftUUID() {
        return minecraftUUID;
    }

    public String getTUMId() {
        return TUMId;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
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
        isAuthenticated = authenticated;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
