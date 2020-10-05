import java.lang.Thread;

class TalkServer {
    public static void main( String[] args ){
        Talk.communicate(true);
    }
}

class TalkClient {
    public static void main( String[] args ){
        Talk.communicate(false);
    }
}

class Talk {

    private static void readFromConsole( Connection connection, Alive isAlive ){
        // Ciklusban olvasunk a System.console-ról a readLine művelettel.
        // Amit olvasunk, azt elküldjük a kapcsolaton keresztül.
        // Leállás, amikor az isAlive hamisra vált,
        // amikor a kapcsolatra írás kivételt vált ki,
        // illetve amikor a readLine null-t olvas (EOF, pl. Ctrl-D).
        // Leálláskor meghívjuk a shutdownn metódust.

        while(isAlive.get()) {
            String line;
            try {
                while (isAlive.get() && (line = System.console().readLine()) != null) {
                    connection.send(line);
                }
            } catch(Exception e) {
              System.out.println("Connection lost...");
            } finally{
              System.out.println("Shutdown...");
              shutdown(connection,isAlive);
            }
        }
    }

    private static void receiveFromConnection( Connection connection, Alive isAlive ){
        // Ciklusban olvasunk a kapcsolatról.
        // Amit olvasunk, azt kiírjuk a szabványos kimenetre.
        // Leállás, amikor az isAlive hamisra vált,
        // illetve amikor a kapcsolatról olvasás kivételt vált ki.
        // Leálláskor meghívjuk a shutdownn metódust.

        while(isAlive.get()){
            String line;
            try{
                while((line = connection.receive()) != null && isAlive.get()){
                    System.out.println(String.format(">>%s<<",line));
                }
            }catch (Exception e){
                System.out.println("Connection lost");
            } finally {
                System.out.println("Shutdown...");
                shutdown(connection,isAlive);
            }
        }

    }

    private static void shutdown( Connection connection, Alive isAlive ){
        // Bezárjuk a kapcsolatot, és az isAlive-ot hamisra állítjuk.
        try {
            connection.close();
            isAlive.stop();
        } catch (Exception e) { e.printStackTrace(); }

    }

    public static void communicate( boolean isServer ){
        // A kapott flagnek megfelelően létrehoz egy Connectiont.
        // Létrehoz egy Alive objektumot is.
        // Elindít egy szálat, mely a readFromConsole eljárást hajtja végre.
        // Ezután végrehajtja a receiveFromConnection eljárást.
        try {
            Connection connection = (isServer) ? Connection.accept() : Connection.connect();
            Alive isConnectionAlive = new Alive();
            (new Thread(()->{
                readFromConsole(connection,isConnectionAlive);
            })).start();
            receiveFromConnection(connection,isConnectionAlive);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static class Alive {
        // Nyilvántart egy logikai értéket, mely kezdetben igaz.
        // Ezt az értéket a get() metódussal le lehet kérdezni.
        // A stop() metódussal az értéket hamisra lehet állítani.
        // Mivel az osztályt több szálból használjuk, legyen szálbiztos.

        private volatile boolean isAlive;

        public Alive() {
            isAlive = true;
        }

        public synchronized boolean get(){
            return isAlive;
        }

        public synchronized void stop(){
            isAlive = false;
        }
    }
}