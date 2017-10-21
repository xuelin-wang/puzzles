package xl.learn;

/**
 * Created by xuelin on 10/16/17.
 */
public class HelloThread extends Thread {

    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello");
        }
    }

    public synchronized void sayHello() {
        notify();
    }

    public static void main(String[] args) throws InterruptedException {
        HelloThread thread = new HelloThread();
        thread.start();
        while (true) {
            sleep(1000);
            thread.sayHello();
        }
    }
}
