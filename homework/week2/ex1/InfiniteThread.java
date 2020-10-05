import java.lang.Runnable;
import java.lang.Thread;

public class InfiniteThread implements Runnable {

  @Override
  public void run(){
    
    System.out.println("New Thread started...");
    new Thread(new InfiniteThread()).start();
    System.out.println("Thread ended...");
    return;
  }

}
