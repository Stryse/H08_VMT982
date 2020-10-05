import java.lang.Thread;
import java.lang.Runnable;

public class Main {

  public static void main(String[] args){

    (new Thread(()->{ 
      while(true){
        System.out.println("Bye!");
        Thread.yield();
      }
    })).start();

    while(true){
      System.out.println("Hi!");
      Thread.yield();
    }
  }
}