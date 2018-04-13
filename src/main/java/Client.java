
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    ObjectInputStream Sinput;        // to read the socket
    ObjectOutputStream Soutput;    // towrite on the socket
    Socket socket;

    // Constructor connection receiving a socket number
    Client(String ip, String pseudo) {
        // create socket to server
        try {
            socket = new Socket(ip, 2312);
        } catch (Exception e) {
            System.out.println("Error connection to server:" + e);
            return;
        }

        // debug success connection
        System.out.println("Connection accepted " +
                socket.getInetAddress() + ":" +
                socket.getPort());

        /* Creating both Data Streams */
        try {
            Sinput = new ObjectInputStream(socket.getInputStream());
            Soutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        Thread b = new Thread(new Listener(Sinput));
        b.start();
        // my connection is established
        // send the question (String) to the server
        boolean keep = true;
        Scanner scan = new Scanner(System.in);
        while (keep) {
            String s = scan.nextLine();
            try {
                Soutput.writeObject(s);
                Soutput.flush();
            } catch (IOException e) {
                System.out.println("Error writting to the socket: " + e);
                return;
            }
            // read back the answer from the server
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Sinput.close();
            Soutput.close();
        } catch (Exception e) {
        }
    }

    public static void main(String[] arg) {
        new Client("54.37.224.170", "Dayblox");
    }
}
