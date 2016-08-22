package StreamAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Никола on 20.08.2016.
 * Distinct - возвращает стрим без дубликатов (для метода equals)
 * Skip - пропустить N первых элементов
 * Limit - ограничить выборку определенным количеством первых элементов
 * Терминальные - findFirst/findAny/collect/count/anyMatch/noneMatch/allMatch/min/max/forEach/forEachOrdered/toArray
 * reduce... Стоит обратить внимание на reduce и collect
 */
public class StreamCapabilities {
    public StreamCapabilities() throws IOException {
    }

    public static void main(String[] args) {
        List<IntsHolder> test = IntsHolder.getItemsForTest();
        //фильтр - в качестве параметра предикат
        test.stream().filter(s -> s.getX() > 5).forEach(System.out::println);
        //Сортировка. Объекты внутри коллекции не сортируются. Просто возвращается отсортированная коллекция
        //можно реализовать свой компаратор
        test.stream().sorted((a, b) -> Integer.compare(a.getX()+a.getY(), b.getX()+b.getY())).findFirst().ifPresent(System.out::println);
        //Маппинг - позволяет преобразовать в другой класс, например. Параметр - Function
        test.stream().map(s -> new StringsHolder(String.valueOf(s.getX()), String.valueOf(s.getY()))).forEach(System.out::println);
        //Reduce (терминальный) позволяет выполнять агрегатные функции над
        // всей коллекцией (такие как сумма, нахождение минимального или максимального значение и т.п.),
        // он возвращает одно значение для стрима. Варианты:
        // 1) функция получает два аргумента — значение полученное на прошлых шагах и текущее значение, сами операции могут быть сравнение, суммированием и т.п..
        test.stream().reduce((a, b) -> new IntsHolder(a.getX()+b.getX(), a.getY()+b.getY())).ifPresent(System.out::println);
        // 2) метод принимает идентифицирующее значение, и функцию из первого варианта .
        IntsHolder summaryX_Y = test.stream().reduce(new IntsHolder(0, 0), (a, b) -> {a.setX(a.getX()+b.getX()); a.setY(a.getY()+b.getY()); return a;});
        System.out.println(summaryX_Y);
        // 3) метод принимает идентифицирующее значение, аккумулятор - BitFunction , и BinaryOperator "combiner",
        //который по сути нужен для параллелизма, чтобы суммировать результаты разных потоков.
        int intsholderssum = test.stream().reduce(0, (a, b) -> (a += b.getY()+b.getX()), (sum1, sum2) -> sum1 + sum2);
        System.out.println(intsholderssum);
        //flatmap - как map, но преобразовывает в стрим с одним, несколькими или ни одним элементов для каждого элемента входящего стрима
        test.stream().flatMap(f -> Stream.of(f.getX(), f.getY())).collect(Collectors.toList());
        //collect - состоит из четырех различных операций: поставщик, аккумулятор, объединитель и финишер,
        // которые можно передать с помощью Collector.of(), есть много встроенных готовых коллекторов
        String allints = test.stream().collect(StringBuilder::new, (b, h) -> b.append(h.getX()).append(h.getY()), StringBuilder::append).toString();
        System.out.println(allints);

    }
    //создание потоков
    Stream<String> streamFromValues = Stream.of("a1", "a2", "a3"); //из значений
    String[] array = {"a1","a2","a3"};
    Stream<String> streamFromArrays = Arrays.stream(array); //из массива
    Stream<String> streamFromFiles = Files.lines(Paths.get("file.txt")); // Создание стрима из файла (каждая строка в файле будет отдельным элементом в стриме)
    IntStream streamFromString = "123".chars(); //из строки
    Stream<Object> streamBuilder = Stream.builder().add("a1").add("a2").add("a3").build(); //Stream.builder
    Stream<IntsHolder> stream = IntsHolder.getItemsForTest().parallelStream(); //Создание параллельного стрима
    Stream<Integer> streamFromIterate = Stream.iterate(1, n -> n + 1); // Бесконечный стрим с помощью iterate(начальное_условие, выражение_генерации)
    Stream<String> streamFromGenerate = Stream.generate(() -> "a1"); // Бесконечный стрим с помощью generate(выражение_генерации)
    //переиспользование потоков
    Supplier<Stream<String>> streamSupplier =
            () -> Stream.of("dd2", "aa2", "bb1", "bb3", "cc")
                    .filter(s -> s.startsWith("a"));
    //    streamSupplier.get().anyMatch(s -> true);
    //    streamSupplier.get().noneMatch(s -> true);

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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
