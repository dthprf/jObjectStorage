package remoteObjectsStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import remoteObjectsStorage.Client.RemoteClient;
import remoteObjectsStorage.Server.MTServer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private MTServer mtServer;
    private RemoteClient remoteClient;
    private RemoteClient remoteClient2;
    private RemoteClient remoteClient3;
    private Thread thread;

    @BeforeEach
    void setup() {
        mtServer = new MTServer(8080);
        thread = new Thread(mtServer);
        thread.start();
        remoteClient = new RemoteClient("localhost", 8080);
        remoteClient2 = new RemoteClient("localhost", 8080);
        remoteClient3 = new RemoteClient("localhost", 8080);
    }

    @Test
    void testAddObject() throws InterruptedException {
        String string = "Test object";
        Integer integer = 3;

        countDownLatch.await(1, TimeUnit.SECONDS);

        boolean firstResult = remoteClient.addObject("test", string);
        boolean secondResult = remoteClient3.addObject("test", string);
        boolean thirdResult = remoteClient2.addObject("testtest", integer);

        mtServer.stop();
        thread.join();

        assertAll(
                () -> assertTrue(firstResult),
                () -> assertFalse(secondResult),
                () -> assertTrue(thirdResult)
        );

    }

    @Test
    void testRemoveObject() throws InterruptedException {
        String string = "Test object";
        Integer integer = 3;

        countDownLatch.await(1, TimeUnit.SECONDS);

        remoteClient.addObject("test", string);
        remoteClient2.addObject("testtest", integer);

        boolean firstResult = remoteClient.removeObject("test");
        boolean secondResult = remoteClient3.removeObject("test");
        boolean thirdResult = remoteClient2.removeObject("testtest");

        mtServer.stop();
        thread.join();

        assertAll(
                () -> assertTrue(firstResult),
                () -> assertFalse(secondResult),
                () -> assertTrue(thirdResult)
        );

    }

    @Test
    void testGetObject() throws InterruptedException{
        String string = "Test Object";
        Integer integer = 3;

        countDownLatch.await(1, TimeUnit.SECONDS);

        remoteClient.addObject("testString", string);
        remoteClient2.addObject("testInteger", integer);

        String expectedString = (String) remoteClient3.getObject("testString");
        Integer expectedInteger = (Integer) remoteClient.getObject("testInteger");

        remoteClient2.removeObject("testInteger");

        assertAll(
//                () -> assertThrows(IllegalArgumentException.class, () ->
//                    remoteClient2.getObject("testInteger")),
                () -> assertEquals(expectedString, string),
                () -> assertEquals(expectedInteger, integer)
        );
    }
}
