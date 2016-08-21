package ThinkinginJava4th.Threads.concurerency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Илоныч on 18.08.2016.
 * Сравнение в скорости - синхронизированный блок против синхронизированного метода
 * А также способ использовать неприспособленный к многопоточности класс
 * Паттерн класса PairManager "шаблонный метод"
 * И есть пример с менеджером использующим Lock, что здесь
 * не в кассу (объект не блокируется и чекстэйт выдаст эксепшн).
 */
public class CriticalSection {
    //Сравнение двух подходов
    static void testApproaches(PairManager pm1, PairManager pm2){
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator pmnp1 = new PairManipulator(pm1), pmnp2 = new PairManipulator(pm2);
        PairChecker pch1 = new PairChecker(pm1), pch2 = new PairChecker(pm2);
        exec.execute(pmnp1);
        exec.execute(pmnp2);
        exec.execute(pch1);
        exec.execute(pch2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e){
            System.out.println("Sleep interrupted");
        }
        System.out.println("pm1 " + pmnp1 + "\npm2 " + pmnp2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pman1 = new PairManagerSection(), pman2 = new PairManagerWhole();
        testApproaches(pman1, pman2);
    }
}
//non thread-safe
class Pair {
    private int x, y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Pair(){
        this(0, 0);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void incrementX(){
        x++;
    }
    public void incrementY(){
        y++;
    }
    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public class PairValueNotEqualException extends RuntimeException{
        public PairValueNotEqualException(){
            super("Pair values not equal:" + Pair.this);
        }
    }
    public void checkState(){
        if(x!=y){
            throw new PairValueNotEqualException();
        }
    }
}

abstract class PairManager{
    AtomicInteger checkCounter = new AtomicInteger();
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
    public synchronized Pair getPair(){
        //копия для сохранения оригинала в безопасности
        return new Pair(p.getX(), p.getY());
    }
    //Предполагается что операция занимает время
    protected void store(Pair p){
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException ignored){}
    }
    public abstract void increment();
}

//Синхронизация всего метода
class PairManagerWhole extends PairManager{

    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}
class PairManagerSection extends PairManager{
    @Override
    public void increment() {
        Pair temp;
        synchronized (this){
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}
class PairManagerSectionLock extends PairManager{
    private Lock lock = new ReentrantLock();
    //checkstate будет орать
    @Override
    public void increment() {
        Pair temp;
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}
class PairManipulator implements Runnable{
    private PairManager pm;
    public PairManipulator(PairManager pm){
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return pm.getPair() + " counter = " + pm.checkCounter.get();
    }
}
class PairChecker implements Runnable{
    private PairManager pm;
    public PairChecker(PairManager pm){
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}