import java.io.IOException;
import java.io.ObjectInputStream;

class Listener implements Runnable {
    private ObjectInputStream in;

    Listener(ObjectInputStream op) {
        in = op;
    }

    @Override
    public void run() {
        while (true) {
            String response = null;
            try {
                response = String.valueOf(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(response);
            assert response != null;
            if (response.equals("Already taken."))
                return;
        }
    }
}
