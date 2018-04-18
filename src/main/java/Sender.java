import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sender {
    public static List<ObjectOutputStream> Glist = Collections.synchronizedList(new ArrayList<>());

    public static void SendALl(String message) throws IOException {
        for (ObjectOutputStream o : Glist) {
            o.writeObject(message);
            o.flush();
        }
    }
}
