package ThinkinginJava4th.Threads.concurerency;

/**
 * Created by Илоныч on 18.08.2016.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber(){
        return serialNumber++;
    }
}
