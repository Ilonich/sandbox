package JavaLambda.Interfaces;

/**
 * Created by Илоныч on 16.08.2016.
 * Аннотация функционального интерфейса ограничивает количество методов до 1
 * В основном, этот интерфэйс означает что реализация данного интерфэйса может быть заменена лямбдой
 * Внутри Lambda выражений запрещено обращаться к default методам.
 */
@java.lang.FunctionalInterface
public interface FunctionalInterfaceExample<T> {
    int compare(T a, T b);
}
