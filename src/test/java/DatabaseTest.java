import ch.jalu.configme.SettingsManager;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tum.auth.configuration.DataBaseProperties;
import tum.auth.datasource.PostgreSqlDataSource;

import java.sql.SQLException;

import static org.easymock.EasyMock.*;

public class DatabaseTest {

    @TestSubject
    private PostgreSqlDataSource dataSource;

    private SettingsManager settingsManager;


    private static String host;
    private static int port;
    private static String user;
    private static String password;

    private static String name;


    @BeforeAll
    static void setUp() {
        host = System.getenv("HOST");
        port = Integer.parseInt(System.getenv("PORT"));
        user = System.getenv("USER");
        password = System.getenv("PASSWORD");
        name = System.getenv("NAME");

    }

    @BeforeEach
    void setUpEach() {
        settingsManager = createMock(SettingsManager.class);
    }

    @Test
    void connectionTest() {
        expect(settingsManager.getProperty(DataBaseProperties.DB_HOST)).andReturn(host);
        expect(settingsManager.getProperty(DataBaseProperties.DB_PORT)).andReturn(port);
        expect(settingsManager.getProperty(DataBaseProperties.DB_USER)).andReturn(user);
        expect(settingsManager.getProperty(DataBaseProperties.DB_PASSWORD)).andReturn(password);
        expect(settingsManager.getProperty(DataBaseProperties.DB_NAME)).andReturn(name);

        replay(settingsManager);

        dataSource = new PostgreSqlDataSource(settingsManager);

        try {
            dataSource.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        verify(settingsManager);
    }


}
