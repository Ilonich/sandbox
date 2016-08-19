package ThinkinginJava4th.Threads.ThreadsInteraction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Илоныч on 18.08.2016.
 */
public class SimpleWaitNotify {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Waxer(car));
        exec.execute(new Buffer(car));
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}
class Waxer implements Runnable{
    private Car car;
    public Waxer(Car car){
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax On! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e){
            System.out.println("Exit via interrupt");
        }
        System.out.println("Ending wax on task");
    }
}

class Buffer implements Runnable{
    private Car car;
    public Buffer(Car c){
        this.car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                car.waitForWaxing();
                System.out.println("Wax off!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e){
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending buffing task");
    }
}

class Car{
    private boolean waxOn = false;
    public synchronized void waxed(){
        waxOn = true;
        notify();
    }
    public synchronized void buffed(){
        waxOn = false;
        notify();
    }
    public synchronized void waitForWaxing() throws InterruptedException{
        while (!waxOn){
            wait();
        }
    }
    public synchronized void waitForBuffing() throws InterruptedException{
        while (waxOn){
            wait();
        }
    }
}
