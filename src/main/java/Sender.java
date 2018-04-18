import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

class Sender {
    static Map<String, ObjectOutputStream> map = Collections.synchronizedMap(new HashMap<>());

    static void SendAll(String message, String pseudo, boolean server) throws IOException {
        String msg;
        if (server)
            msg = message;
        else
            msg = pseudo + ": " + message;
        for (String s : map.keySet()) {
            if (!s.equals(pseudo)) {
                ObjectOutputStream out = map.get(s);
                out.writeObject(msg);
                out.flush();
            }
        }
    }

    static void list(String pseudo) {
        ObjectOutputStream out = map.get(pseudo);
        String res = map.keySet().stream().filter(f -> !f.equals(pseudo)).collect(Collectors.joining(", "));
        if (res.length() > 0)
            res = "You are connected with " + res + ".";
        else
            res = "You are alone.";
        try {
            out.writeObject(res);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
