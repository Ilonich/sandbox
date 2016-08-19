package JavaLambda.Interfaces;

/**
 * Created by Илоныч on 16.08.2016.
 * Дефолтный метод, интерфейсный метод теперь может иметь реализацию
 */
public interface DefaultInterface {
    default String getDefaultString(){
        return "Yo!";
    }

    public String notDefaultMethod();
}
