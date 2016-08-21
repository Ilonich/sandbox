package JavaLambda.Expressions;

import java.util.Optional;

/**
 * Created by Никола on 21.08.2016.
 * //Опциональные значения - контейнер для значения,
 * является удобным средством предотвращения NullPointerException
 * Главный минус - он не сериализуемый
 */
public class OptionalJava8 {
    public static void main(String[] args) throws Exception {
        //типа прилетает значение
        String s = "qwe";
        //С ненулевым значением
        Optional<String> optional = Optional.of(s);
        //Пустой
        Optional<String> optional1 = Optional.empty();
        //Может быть null
        Optional<String> optional2 = Optional.ofNullable(s);

        //Полезности

        //было:
        /*if (s != null){
            System.out.println(s);
        }*/
        //стало:
        optional.ifPresent(System.out::println);

        //было:
        /*if (s != null)*/
        //стало:
        optional.isPresent();

        //было:
        String test = s != null ? s : "new";
        //стало:
        String test1 = optional.orElse("new");
        String test2 = optional.orElseThrow(Exception::new);

    }

}
