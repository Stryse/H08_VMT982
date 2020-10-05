import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;

public class Main {

    static final int N = 100;
    static void addPrimesToList(int from, int to, List<Integer> list) {
        for(int i = from; i <= to; ++i) {

            boolean isPrime = true;
            for(int j = 2; j < (int)Math.sqrt(i)+1; j++) {
                if(i % j == 0){
                    isPrime = false;
                    break;
                }
            }
            if(isPrime)
                list.add(i);
        }
    }

    public static void main(String[] args) {

        List<Integer> nums = Collections.synchronizedList(new ArrayList<>());

        Thread t = new Thread(()->addPrimesToList(N/2,N,nums));
        t.start();

        addPrimesToList(2,N/2-1,nums);

        try{ t.join(); } catch (InterruptedException e) {
            System.out.println("Interrupted!");
            return;
        }

        for(int num : nums) {
            System.out.println(num);
        }
    }
}
