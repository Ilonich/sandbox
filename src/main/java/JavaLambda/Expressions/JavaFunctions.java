package JavaLambda.Expressions;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Илоныч on 16.08.2016.
 */
public class JavaFunctions {
    public static void main(String[] args) {

        //Предикат - возвращает boolean;
        Predicate<String> predicate = (s -> s.contains("s"));
        Predicate<String> predicate1 = s -> s.length() > 2;
        Predicate<String> predicate2 = s -> s.startsWith("q");
        System.out.println(predicate.test("saw")); //true
        System.out.println(predicate.negate().test("qaw")); //true
        System.out.println(predicate.and(predicate1).test("saw")); //true
        System.out.println(predicate.or(predicate2).test("qaw")); //true

        //Функции - принимает аргумент, возвращает результат
        Function<String, Integer> function = Integer::valueOf;
        Function<Integer, String> function1 = i -> i.toString().concat("2");
        System.out.println(function.compose(function1).apply(2));
        System.out.println(function.andThen(function1).apply("1"));

        //Поставщик - отдаёт заданный результат, Потребитель - производит операции над аргументом;
        Supplier<Random> random = Random::new;
        Consumer<Random> sout = (r) -> System.out.println(r.nextInt(10));
        Consumer<Random> souts = (r) -> System.out.println(r.nextFloat());
        sout.andThen(souts).accept(random.get());

    }
}
