import java.lang.Runnable;
import java.lang.Thread;

public class FiniteDepthThread implements Runnable {

    //Fields
    int currDepth;
    int maxDepth;

    //Ctor
    public FiniteDepthThread(int currDepth,int maxDepth){
      this.currDepth = currDepth;
      this.maxDepth = maxDepth;
    }

    public FiniteDepthThread(int depth){
      this(0,depth);
    }

    // Overrides
    @Override
    public void run(){

        if(currDepth == maxDepth) return;

        System.out.println(currDepth+1 + ". level thread started...");
        (new Thread(new FiniteDepthThread(currDepth+1,maxDepth))).start();
        System.out.println("\t" + (currDepth+1) + ". level thread ended...");
    }
}