import java.io.IOException;
import java.io.ObjectInputStream;

public class Listener implements Runnable {
    ObjectInputStream is;

    public Listener(ObjectInputStream op) {
        this.is = op;
    }

    public void run() {
        while (true) {
            String response = null;
            try {
                response = String.valueOf(is.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(response);
            if (response.equals("Already taken."))
                return;
        }
    }
}
