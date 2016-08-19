package StreamAPI;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Никола on 20.08.2016.
 */
public class StreamCapabilities {



}

class IntsHolder {
    private int x;
    private int y;

    IntsHolder(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static List<IntsHolder> getItemsForTest() {
        List<IntsHolder> res = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            res.add(new IntsHolder((int) (Math.random() * 10), (int) (Math.random() * 10)));
        }
        return res;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Ints[" + x +
                ", " + y +
                ']';
    }

    @FunctionalInterface
    public interface IntsHolderFactory {
        IntsHolder create(int x, int y);
    }
}

class StringsHolder {
    private String x;
    private String y;

    StringsHolder(String x, String y) {
        this.x = x;
        this.y = y;
    }


    String getX() {
        return x;
    }

    String getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Strings[" + x +
                ", " + y +
                ']';
    }

    @FunctionalInterface
    public interface StringsHolderFactory {
        StringsHolder create(String x, String y);
    }
}
