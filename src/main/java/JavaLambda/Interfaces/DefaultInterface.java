package JavaLambda.Interfaces;

import java.lang.*;

/**
 * Created by Илоныч on 16.08.2016.
 * Дефолтный метод, интерфейсный метод теперь может иметь реализацию
 *
 1) Родительские классы выиграют. Если суперкласс предоставляет конкретный метод,
 методы по умолчанию с тем же именем и типами параметров просто игнорируются.
 2) Интерфейсы сталкиваются. Если супер интерфейс предоставляет метод по умолчанию,
 а другой интерфейс поставляет метод с тем же именем и типами параметров (по умолчанию или нет),
 то вы должны разрешить конфликт путем переопределения этого метода.

 * Метод суперкласса имеет значение, любой метод по умолчанию из интерфейса просто игнорируется
 * Правило «класс побеждает» обеспечивает совместимость с Java 7. Если вы добавляете методы по умолчанию к интерфейсу,
 * это не имеет никакого влияния на код, который работал до того, как появились методы по умолчанию.
 * Но имейте в виду: вы не имеете права создавать метод по умолчанию, который переопределяет один из методов
 * класса Object.
 */
@java.lang.FunctionalInterface
public interface DefaultInterface {
    default String getDefaultString(){
        return "Yo!";
    }

    public String notDefaultMethod();
}