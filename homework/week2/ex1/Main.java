/*
Készítsünk egy programot, amely elindít egy szálat és terminál.
Az elindított szál is indítson egy másik szálat, majd termináljon, majd így tovább. Mi történik?
*/

import java.lang.Thread;

public class Main {
  public static void main(String[] args) {
    
    (new Thread(new InfiniteThread())).start();
  }
}
