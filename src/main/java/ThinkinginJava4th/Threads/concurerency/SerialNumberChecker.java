package ThinkinginJava4th.Threads.concurerency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Илоныч on 18.08.2016.
 * Пример как в многопоточной среде общий ресурс может выдать некорректное значение
 * если не использовать блокировки или атомарные операции (в классе SerialNumberGenerator)
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable{
        @Override
        public void run() {
            while (true){
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serial)) {
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }
    //Дупликат возникает когда в общем ресурсе (генераторе) не синхронизирован метод next
    // или не выполнен как атомарная операция
    //volatile в многопроцессорной среде не гарантирует даже то что помещение переменной во внешнюю память
    //даст переменной всегда определённое состояние, это возможно только если операция атомарна;
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("No duplicates detected");
        System.exit(0);
    }

//виновник
    public static class SerialNumberGenerator {
        private static volatile int serialNumber = 0;
        public static int nextSerialNumber(){
            return serialNumber++;
        }
    }
}
// Переиспользует хранилище, не будет outofmemory
class CircularSet {
    private int[] array;
    private int len;
    private int index = 0;
    public CircularSet(int size){
        array = new int[size];
        len = size;
        //Инициация значением вне значений выдаваемых SerialNumberGenerator
        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }
    public synchronized void add(int i){
        array[index] = i;
        //Перезапись значений с начала при выходе за пределы массива
        index = ++index % len;
    }
    public synchronized boolean contains(int val){
        for (int j = 0; j < len; j++) {
            if (array[j] == val) return true;
        }
        return false;
    }
}
