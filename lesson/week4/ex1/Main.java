import java.util.concurrent.Semaphore;
import java.lang.Thread;

public class Main {

  public static void main(String[] args){

    SafeAccount sfAccount = new SafeAccount();

    Thread thread2 = new Thread(()->{ sfAccount.increment(10); });
    thread2.start();

    Thread thread3 = new Thread(()->{ sfAccount.increment(100); });
    thread3.start();
    
    Thread thread4 = new Thread(()->{ sfAccount.increment(1000); });
    thread4.start();

    Thread thread5 = new Thread(()->{ sfAccount.increment(10000); });
    thread5.start();

    sfAccount.increment(69);

    try {
          thread2.join();
          thread3.join();
          thread4.join();
          thread5.join();
    } catch (InterruptedException e) {
          System.out.println("Interrupted!");
          return;
    }

    System.out.println("Final: " + sfAccount.getMoney());

  }
}

abstract class Account {

    protected int money = 0;

    public abstract void increment(int amount);

    public int getMoney(){
        return money;
    }
}

class SafeAccount extends Account {

  private Semaphore lock;

  public SafeAccount() {
    lock = new Semaphore(1);
  }

  public void increment(int amount) {
    
    try{ 
      
      System.out.println(Thread.currentThread().getName() + " is operating");
      
      lock.acquire(); 
      money += amount;
      System.out.println("New money: " + getMoney());
      Thread.sleep(100);

    } catch(InterruptedException e) {
    } finally {
      lock.release();
    }
  }
}