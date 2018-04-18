import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

class Sender {
    static Map<String, ObjectOutputStream> Glist = Collections.synchronizedMap(new HashMap<>());

    static void SendALl(String message, String pseudo, boolean server) throws IOException {
        String msg;
        if (server)
            msg = message;
        else
            msg = pseudo + ": " + message;
        for (String s : Glist.keySet()) {
            if (!s.equals(pseudo)) {
                ObjectOutputStream out = Glist.get(s);
                out.writeObject(msg);
                out.flush();
            }
        }
    }
}
