import java.io.IOException;
import java.io.ObjectInputStream;

public class Listener implements Runnable
{
    ObjectInputStream is;
    public Listener(ObjectInputStream op)
    {
        this.is = op;
    }
    public void run()
    {
        String response = null;
        try
        {
            response = (String) is.readObject();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        System.out.println("Read back from server: " + response);
    }
}
