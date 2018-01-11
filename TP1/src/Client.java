import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {


    private DatagramSocket socket;
    private DatagramPacket packet;

    public void send(String message, InetAddress dest, int port) {
        byte[] byteData = message.getBytes();
        packet = new DatagramPacket(byteData, byteData.length, dest, port);

        try {
            socket = new DatagramSocket();
            socket.send(packet);
            System.out.println("sent message: " + message + " to " + dest.getHostName());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to send message");
        }


        socket.close();
    }

}
