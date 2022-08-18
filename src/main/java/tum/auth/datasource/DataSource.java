package tum.auth.datasource;

import tum.auth.models.User;

import java.sql.SQLException;
import java.util.UUID;

public interface DataSource {
    void connect() throws Exception;
    void addUser(User user) throws Exception;
    User updateUser(User user) throws Exception;
    User getUser(UUID uuid) throws Exception;
}
