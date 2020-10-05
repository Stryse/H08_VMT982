import java.lang.Runnable;
import java.lang.Thread;

public class ForkBomb implements Runnable {

    @Override
    public void run(){
      System.out.println("Thread started...");
      
      // Thread is sleeping so stdout is more readable
      try{ 
            Thread.sleep(500);
      }catch(InterruptedException e){
            System.out.println("Sleep Interrupted!");
      }

      for(int i = 0; i < 2; ++i){
        (new Thread(new ForkBomb())).start();
      }
    }
}