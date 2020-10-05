/*
 Készítsünk egy "fork-bomb" programot; elindít egy thread-et,
 ami aztán elindít még két thread-et, amelyek szintén elindítanak két thread-et, és így tovább...
*/

import java.lang.Thread;

class Main {
  public static void main(String[] args) {
    
    (new Thread(new ForkBomb())).start();
  }
}