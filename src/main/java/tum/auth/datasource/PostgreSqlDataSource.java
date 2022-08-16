package tum.auth.datasource;

import ch.jalu.configme.SettingsManager;
import tum.auth.configuration.DataBaseProperties;
import tum.auth.models.User;

import java.sql.*;
import java.util.UUID;

public class PostgreSqlDataSource {

    private String host;
    private int port;
    private String user;
    private String password;

    private String name;

    private Connection connection;


    public PostgreSqlDataSource(SettingsManager settings) {
        this.host = settings.getProperty(DataBaseProperties.DB_HOST);
        this.port = settings.getProperty(DataBaseProperties.DB_PORT);
        this.user = settings.getProperty(DataBaseProperties.DB_USER);
        this.password = settings.getProperty(DataBaseProperties.DB_PASSWORD);
        this.name = settings.getProperty(DataBaseProperties.DB_NAME);
        this.connection= null;
    }

    public void connect() throws SQLException {
        String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.name;
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (minecraft_uuid, TUM_id, is_authenticated, token) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, user.getMinecraftUUID().toString());
        statement.setString(2, user.getTUMId());
        statement.setBoolean(3, user.isAuthenticated());
        statement.setString(4, user.getToken());
        statement.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET TUM_id = ?, is_authenticated = ?, token = ? WHERE minecraft_uuid = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, user.getTUMId());
        statement.setBoolean(2, user.isAuthenticated());
        statement.setString(3, user.getToken());
        statement.setString(4, user.getMinecraftUUID().toString());
        statement.executeUpdate();
    }

    public void getUser(User user) throws SQLException {
        String sql = "SELECT * FROM users WHERE minecraft_uuid = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, user.getMinecraftUUID().toString());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user.setTUMId(resultSet.getString("tum_id"));
            user.setAuthenticated(resultSet.getBoolean("authenticated"));
            user.setToken(resultSet.getString("token"));
            user.setMinecraftUUID(UUID.fromString(resultSet.getString("minecraft_uuid")));
        }
    }




}
