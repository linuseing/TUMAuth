import ch.jalu.configme.SettingsManager;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.*;
import tum.auth.configuration.DataBaseProperties;
import tum.auth.datasource.PostgreSqlDataSource;
import tum.auth.models.User;

import java.sql.SQLException;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseTest {

    @TestSubject
    private PostgreSqlDataSource dataSource;

    @Mock
    private SettingsManager settingsManager;


    @BeforeAll
    void setUp() throws SQLException {
        String host = System.getenv("HOST");
        int port = Integer.parseInt(System.getenv("PORT"));
        String user = System.getenv("USER");
        String password = System.getenv("PASSWORD");
        String name = System.getenv("NAME");


        settingsManager = createMock(SettingsManager.class);

        expect(settingsManager.getProperty(DataBaseProperties.DB_HOST)).andReturn(host);
        expect(settingsManager.getProperty(DataBaseProperties.DB_PORT)).andReturn(port);
        expect(settingsManager.getProperty(DataBaseProperties.DB_USER)).andReturn(user);
        expect(settingsManager.getProperty(DataBaseProperties.DB_PASSWORD)).andReturn(password);
        expect(settingsManager.getProperty(DataBaseProperties.DB_NAME)).andReturn(name);

        replay(settingsManager);

        dataSource = new PostgreSqlDataSource(settingsManager);

        dataSource.connect();

    }


    @AfterEach
    void tearDownEach() {
        verify(settingsManager);
    }

    @AfterAll
    void tearDown() throws SQLException {
        dataSource.disconnect();
    }

    void connectionTest() {
        assertDoesNotThrow(() -> dataSource.connect());
    }

    @Disabled
    @Test
    void addUserTest() {
        User user = new User(UUID.randomUUID(), "hg89hf", true, "test-token");
        assertDoesNotThrow(() -> dataSource.addUser(user));
    }


    @Test
    void getUserTest() throws SQLException {
        User user1 = new User(UUID.fromString("63eba602-0342-42fd-9641-5fb9891f45fe"), "ge47tam", false, null);
        User user2 = dataSource.getUser(user1.getMinecraftUUID());
        assertEquals(user1.getMinecraftUUID(), user2.getMinecraftUUID());
        assertEquals(user1.getTUMId(), user2.getTUMId());
        assertEquals(user1.isAuthenticated(), user2.isAuthenticated());
        assertEquals(user1.getToken(), user2.getToken());
    }

    @Test
    void updateUserTest() {
        User user = new User(UUID.fromString("63eba602-0342-42fd-9641-5fb9891f45fe"), "ge47tam", false, null);
        assertDoesNotThrow(() -> dataSource.updateUser(user));
    }

}
