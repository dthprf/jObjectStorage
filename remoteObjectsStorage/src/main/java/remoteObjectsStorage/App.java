package remoteObjectsStorage;

import remoteObjectsStorage.Client.RemoteClient;
        import remoteObjectsStorage.Server.MTServer;

        import java.io.IOException;
        import java.util.Scanner;

public class App {

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String host = scanner.nextLine();
        String macbook = "192.168.10.55";
        String local = "localhost";

        if(host.equals("server")) {
            MTServer mtServer = new MTServer(8080);
            mtServer.run();
        } else if (host.equals("client")) {

            RemoteClient remoteClient1 = new RemoteClient(local, 8080);
            RemoteClient remoteClient2 = new RemoteClient(local, 8080);
            String string = "POKA POKA";
            remoteClient1.addObject("bart", string);
            System.out.println(remoteClient2.getObject("bart"));
            System.out.println(remoteClient2.removeObject("bart"));
            System.out.println(remoteClient2.removeObject("bart"));
        }
    }
}