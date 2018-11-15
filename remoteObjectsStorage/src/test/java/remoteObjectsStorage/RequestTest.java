package remoteObjectsStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import remoteObjectsStorage.Client.RemoteClient;
import remoteObjectsStorage.Server.MTServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {

    private MTServer mtServer;
    private RemoteClient remoteClient;
    private RemoteClient remoteClient2;
    private RemoteClient remoteClient3;

    @BeforeEach
    void setup() {
        mtServer = new MTServer(8080);
        mtServer.run();
        remoteClient = new RemoteClient("localhost", 8080);
        remoteClient2 = new RemoteClient("localhost", 8080);
        remoteClient3 = new RemoteClient("localhost", 8080);
    }

    @Test
    void testAddObject() throws IOException, ClassNotFoundException {
        String string = "Test object";
        Integer integer = 3;

        boolean firstResult = remoteClient.addObject("test", string);
        boolean secondResult = remoteClient3.addObject("test", string);
        boolean thirdResult = remoteClient2.addObject("testtest", integer);

        mtServer.stop();

        assertAll(
                () -> assertTrue(firstResult),
                () -> assertFalse(secondResult),
                () -> assertTrue(thirdResult)
        );

    }
}
