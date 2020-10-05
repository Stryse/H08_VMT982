/*
6. (házi folytatása)  Készítsünk egy programot, amely az előző példához hasonlóan indít thread-eket, de most legyen véges.
( Adjunk hozzá egy konstruktor paramétert a thread osztályunkhoz, ami meghatározza, hogy milyen mélységig folytatódjon a lánc)
*/

import java.lang.Thread;

public class Main {
  public static void main(String[] args) {

      (new Thread(new FiniteDepthThread(10))).start();
  }
}