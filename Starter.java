//   This is the begining point of this mini web server
//   All static contents must be under ui directory 
import java.net.ServerSocket;
import java.net.Socket;

public class Starter {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            System.out.println("Mini web server started at port: " + serverSocket.getLocalPort());
            while (true) {
                Socket socket = serverSocket.accept();
                new RequestHandler(socket).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}