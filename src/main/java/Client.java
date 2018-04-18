import java.net.*;
import java.io.*;
import java.util.*;

class Client {
    Socket socket;
    ObjectInputStream Sinput;        // to read the socket
    ObjectOutputStream Soutput;    // towrite on the socket

    Client(String ip, String pseudo) {
        try {
            socket = new Socket(ip, 2312);
        } catch (Exception e) {
            System.out.println("Error connection to server:" + e);
            return;
        }
        try {
            Sinput = new ObjectInputStream(socket.getInputStream());
            Soutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        Thread listen = new Thread(new Listener(Sinput));
        listen.start();
        Scanner scan = new Scanner(System.in);
        while (true) {
            String s = scan.nextLine();
            try {
                Soutput.writeObject(s);
                Soutput.flush();
            } catch (IOException e) {
                System.out.println("Error writting to the socket: " + e);
                return;
            }
        }
    }

    public static void main(String[] arg) {
        new Client("localhost"/*54.37.224.170"*/, "Dayblox");
    }
}
