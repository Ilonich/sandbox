package StreamAPI;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Никола on 20.08.2016.
 */
public class StreamCapabilities {
    public static void main(String[] args) {
        List<IntsHolder> test = IntsHolder.getItemsForTest();
        //фильтр - в качестве параметра предикат
        test.stream().filter(s -> s.getX() > 5).forEach(System.out::println);
        //Сортировка. Объекты внутри коллекции не сортируются. Просто возвращается отсортированная коллекция
        //можно реализовать свой компаратор
        test.stream().sorted((a, b) -> Integer.compare(a.getX()+a.getY(), b.getX()+b.getY())).findFirst().ifPresent(System.out::println);
        //Маппинг - преобразовать в другой класс, например
        test.stream().map(s -> new StringsHolder(String.valueOf(s.getX()), String.valueOf(s.getY()))).forEach(System.out::println);

    }


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
}
