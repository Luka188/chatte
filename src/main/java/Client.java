import java.net.*;
import java.io.*;
import java.util.*;

class Client {
    private Socket socket;
    private ObjectInputStream Sinput;
    private ObjectOutputStream Soutput;
    private String pseudo;

    Client(String ip) {
        Scanner scan = new Scanner(System.in);
        pseudo = "";
        while (pseudo.length() < 1) {
            System.out.print("Pseudo: ");
            pseudo = scan.nextLine();
        }
        try {
            socket = new Socket(ip, 2312);
        } catch (Exception e) {
            System.out.println("Error connection to server:" + e);
            return;
        }
        try {
            Sinput = new ObjectInputStream(socket.getInputStream());
            Soutput = new ObjectOutputStream(socket.getOutputStream());
            Soutput.writeObject(pseudo);
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        Thread listen = new Thread(new Listener(Sinput));
        listen.start();
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
        new Client("54.37.224.170");
    }
}
