
import java.net.*;
import java.io.*;
import java.util.*;
public class Client
{
    ObjectInputStream Sinput;		// to read the socket
    ObjectOutputStream Soutput;	// towrite on the socket
    Socket socket;

    // Constructor connection receiving a socket number
    Client(int port) {
        // we use "localhost" as host name, the server is on the same machine
        // but you can put the "real" server name or IP address
        try {
            socket = new Socket( "localhost"/*"54.37.224.170"*/, port);
        }
        catch(Exception e) {
            System.out.println("Error connectiong to server:" + e);
            return;
        }
        System.out.println("Connection accepted " +
                socket.getInetAddress() + ":" +
                socket.getPort() + "\n");

        /* Creating both Data Streams */
        try
        {
            Sinput  = new ObjectInputStream(socket.getInputStream());
            Soutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Exception creating new Input/output Streams: " + e);
            return;
        }
        // my connection is established
        // send the question (String) to the server
        boolean keep = true;
        Scanner scan = new Scanner(System.in);
        while(keep)
        {
            String s = scan.nextLine();
            try
            {
                Soutput.writeObject(s);
                Soutput.flush();
            } catch (IOException e)
            {
                System.out.println("Error writting to the socket: " + e);
                return;
            }
            // read back the answer from the server
            try
            {

                    String response = (String) Sinput.readObject();
                    System.out.println("Read back from server: " + response);
            } catch (Exception e)
            {

                e.printStackTrace();
            }
        }
        try{
            Sinput.close();
            Soutput.close();
        }
        catch(Exception e) {}
    }

    public static void main(String[] arg) {
        new Client(2312);
    }
}
