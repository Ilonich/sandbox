package ThinkinginJava4th.Threads.concurerency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Илоныч on 18.08.2016.
 * ThreadLocal отвечает за выделение отдельной области памяти для объекта который будут использовать потоки
 * каждому потоку - по объекту
 */
public class ThreadLocalClass {
    //Объекты ThreadLocal обычно хранятся в статических полях
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random random = new Random(47);
        @Override
        protected Integer initialValue() {
            return random.nextInt(10000);
        }
    };
    public static void increment(){
        value.set(value.get() + 1);
    }
    public static int get(){
        return value.get();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accesor(i));
        }
        TimeUnit.MILLISECONDS.sleep(2);
        exec.shutdownNow();
    }
}

class Accesor implements Runnable{
    private final int id;
    public Accesor(int id){
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThreadLocalClass.increment();
            System.out.println(this);
            Thread.yield();
        }
    }
    public String toString(){
        return "#"+id+ " " +ThreadLocalClass.get();
    }
}
