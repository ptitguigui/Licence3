import java.io.IOException;
import java.net.*;

public class MulticastServer {

    private final InetAddress group;
    private final MulticastSocket multicastSocket;
    private final int port;

    private DatagramSocket socket;
    private DatagramPacket packet;

    public MulticastServer(InetAddress group, int port) throws IOException {
        this.group = group;
        this.port = port;
        multicastSocket = new MulticastSocket(port);
        multicastSocket.setTimeToLive(1);
        multicastSocket.joinGroup(group);
    }

    public void send(String message) {
        byte[] byteData = message.getBytes();
        packet = new DatagramPacket(byteData, byteData.length, group, port);

        try {
            socket = new DatagramSocket();
            socket.send(packet);
            System.out.println("sent message: " + message + " to " + group.getHostName());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to send message");
        }


        socket.close();
    }

    public static void main(String[] args) throws IOException {
        MulticastServer server = new MulticastServer(InetAddress.getByName("224.0.0.1"), 7654);
        server.send("hello");

    }


    public void leaveGroup() throws IOException {
        multicastSocket.leaveGroup(group);
    }
}
