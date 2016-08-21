package JavaLambda.Expressions;

import JavaLambda.Interfaces.FunctionalInterfaceExample;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Илоныч on 16.08.2016.
 * "В самом деле, преобразование в функциональный интерфейс - это единственное,
 * что вы можете сделать с лямбда-выражением в Java."
 */
public class LambdasExpressions {
    public static void main(String[] args) {
        //Пример использования функционального интерфэйса
        FunctionalInterfaceExample<IntsHolder> func = (a, b) -> (a.getX()+a.getY()) - (b.getX() + b.getY());
        //Записывать ещё можно как в обычном методе с {}, но все ветки должны возвращать результат
        //Пример с компаратором (в jdk 1.8 он стал функциональным интерфейсом)
        Comparator<IntsHolder> comparator = (a, b) -> {
            if (Integer.compare(a.getX()+a.getY(), b.getX()+b.getY()) == 0) return Integer.compare(a.getX(), b.getX());
            else return Integer.compare(a.getX()+a.getY(), b.getX()+b.getY());
        };
        System.out.println(comparator.compare(new IntsHolder(1, 2), new IntsHolder(2, 1)));
        int result = func.compare(new IntsHolder(1, 2), new IntsHolder(2, 1));
        System.out.println(result);

        List<IntsHolder> test = IntsHolder.getItemsForTest();

        //Ссылка на конструктор, и на метод
        //IntsHolder.IntsHolderFactory ret = (x,y) -> new IntsHolder(x, y);
        IntsHolder.IntsHolderFactory ret = (IntsHolder::new);
        test.add(ret.create(3, 2));
        Collections.sort(test, (func::compare));
    }
}

class IntsHolder{
    private int x;
    private int y;
    IntsHolder(int x, int y) {
        this.x = x;
        this.y = y;
    }
    static List<IntsHolder> getItemsForTest(){
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
    public interface IntsHolderFactory{
        IntsHolder create(int x, int y);
    }
}
