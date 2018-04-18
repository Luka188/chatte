import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    private ServerSocket serverSocket;

    Server() {
        try {
            serverSocket = new ServerSocket(2312);
            System.out.println("Server waiting for client on port " + serverSocket.getLocalPort());
            while (true) {
                Socket socket = serverSocket.accept();  // accept connection
                System.out.println("New client asked for a connection");
                TcpThread thread = new TcpThread(socket);    // make a thread of it
                System.out.println("Starting a thread for a new Client");
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Exception on new ServerSocket: " + e);
        }
    }

    public static void main(String[] arg) {
        new Server();
    }

    private class TcpThread extends Thread {
        private String pseudo;
        private Socket socket;
        private ObjectInputStream Sinput;
        private ObjectOutputStream Soutput;

        TcpThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                Soutput = new ObjectOutputStream(socket.getOutputStream());
                Soutput.flush();
                Sinput = new ObjectInputStream(socket.getInputStream());
                pseudo = String.valueOf(Sinput.readObject());
                Sender.Glist.put(pseudo, Soutput);
                Sender.SendALl(pseudo + " joined.", pseudo, true);
            } catch (IOException e) {
                System.out.println("Exception creating new Input/output Streams: " + e);
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Thread waiting for a String from the Client");
            while (true) {
                try {
                    String message = String.valueOf(Sinput.readObject());
                    Sender.SendALl(message, pseudo, false);
                } catch (IOException e) {
                    System.err.println(pseudo + " left");;
                    try {
                        Sender.SendALl(pseudo + " left.", pseudo, true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Sender.Glist.remove(pseudo);
                    try {
                        Soutput.close();
                        Sinput.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return;
                }
                // will surely not happen with a String
                catch (ClassNotFoundException o) {
                }
            }
        }
    }
}
