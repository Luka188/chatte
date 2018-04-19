import java.net.*;
import java.io.*;
import java.util.*;

class Client {
    private Client(String ip) {
        Scanner scan = new Scanner(System.in);
        String pseudo = "";
        while (pseudo.length() < 1) {
            System.out.print("Pseudo: ");
            pseudo = scan.nextLine();
        }
        Socket socket;
        try {
            socket = new Socket(ip, 2312);
        } catch (Exception e) {
            System.out.println("Error connection to server:" + e);
            return;
        }
        ObjectInputStream sinput;
        ObjectOutputStream soutput;
        try {
            sinput = new ObjectInputStream(socket.getInputStream());
            soutput = new ObjectOutputStream(socket.getOutputStream());
            soutput.writeObject(pseudo);
        } catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        Thread listen = new Thread(new Listener(sinput));
        listen.start();
        while (true) {
            if (!listen.isAlive())
                return;
            String s = scan.nextLine();
            try {
                soutput.writeObject(s);
                soutput.flush();
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
