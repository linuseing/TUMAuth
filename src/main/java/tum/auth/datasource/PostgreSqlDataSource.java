package tum.auth.datasource;

import ch.jalu.configme.SettingsManager;
import tum.auth.configuration.DataBaseProperties;
import tum.auth.models.User;

import java.sql.*;
import java.util.UUID;

public class PostgreSqlDataSource implements DataSource {

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String name;
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
            Class.forName("org.postgresql.Driver"); // <--------- HERE
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(String url) throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO public.\"user\" (minecraft_uuid, tum_id, authenticated, token) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, user.getMinecraftUUID().toString());
        statement.setString(2, user.getTUMId());
        statement.setBoolean(3, user.isAuthenticated());
        statement.setString(4, user.getToken());
        statement.executeUpdate();
    }

    /**
     * Update the values of a user from @param user. The minecraft_uuid and tum_id can't be changed.
     * @return updated user
     */
    public User updateUser(User user) throws SQLException {
        String sql = "UPDATE public.\"user\" SET authenticated = ?, token = ? WHERE minecraft_uuid = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setBoolean(1, user.isAuthenticated());
        statement.setString(2, user.getToken());
        statement.setString(3, user.getMinecraftUUID().toString());
        statement.executeUpdate();
        return user;
    }


    public User getUser(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM public.\"user\" WHERE minecraft_uuid = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new User(uuid, resultSet.getString("tum_id"), resultSet.getBoolean("authenticated"), resultSet.getString("token"));
        } else {
            return null;
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
