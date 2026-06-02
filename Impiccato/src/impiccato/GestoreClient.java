package impiccats;
import java.io.*;
import java.net.Socket;

public class GestoreClient extends Thread {

     final BufferedReader ingresso;
     final PrintWriter uscita;
     Partita partita;


    public GestoreClient(BufferedReader ingresso, PrintWriter uscita, Partita partita) {
        this.ingresso = ingresso;
        this.uscita = uscita;
        this.partita = partita;
    }

    @Override
    public void run() {
    	   try {

               String lettera;

               while ((lettera = ingresso.readLine()) != null) {

            	   LogicaServer.gestisciMossa(lettera, partita, uscita);
               }

           } catch (Exception e) {
               uscita.println("DISCONNESSO");
           }
    }
}
