import org.junit.jupiter.api.Test;
import tum.auth.api.InvalidTumIdException;
import tum.auth.api.TumAPI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TumApiTests {
    /*@Test
    public void requestNewTokenInvalid() {
        TumAPI api = new TumAPI("test-app");
        assertThrows(InvalidTumIdException.class, () -> api.requestToken("xxx"));
    }*/
}
