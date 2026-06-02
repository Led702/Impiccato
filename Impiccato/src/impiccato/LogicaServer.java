package impiccats;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextField;

public class LogicaServer {

    static final int PORTA = 6767;
    static PrintWriter uscita1;
    static PrintWriter uscita2;
    static String parolainiziale;
    static ServerGUI s;
    static Partita partita1;
    static Partita partita2;

    public static void avviaServer() {
        try {
            ServerSocket server = new ServerSocket(PORTA);
            System.out.println("Server avviato");

            // Connessione giocatore 1
            Socket client1 = server.accept();
            BufferedReader ingresso1 = new BufferedReader(
                new InputStreamReader(client1.getInputStream())
            );
            uscita1 = new PrintWriter(client1.getOutputStream(), true);

            // Connessione giocatore 2
            Socket client2 = server.accept();
            BufferedReader ingresso2 = new BufferedReader(
                new InputStreamReader(client2.getInputStream())
            );
            uscita2 = new PrintWriter(client2.getOutputStream(), true);
     
            System.out.println("Client connessi");

            uscita1.println("PLAYER1");
            uscita2.println("PLAYER2");

            uscita1.println("START");
            uscita2.println("START");
            
            partita1=new Partita(ServerGUI.parolss, 1);
            partita2=new Partita(ServerGUI.parolss, 2);
            
            uscita1.println(partita1.risultatovero);
            uscita2.println(partita2.risultatovero);

            // Un thread per client: ognuno ascolta le mosse del proprio giocatore
            new Thread(new GestoreClient(ingresso1, uscita1, partita1)).start();
            new Thread(new GestoreClient(ingresso2, uscita2, partita2)).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Elabora una mossa: valida la cella, aggiorna il tabellone, controlla fine partita
    public static void gestisciMossa(String lettera, Partita partita,PrintWriter uscita) {
        boolean controllo = false;

        for (int i = 1; i < partita.array.length - 1; i++) {
            if (partita.array[i] == lettera.charAt(0)) {
                partita.risultato[i] = lettera.charAt(0);
                controllo = true;
            }
        }

        partita.parola = new String(partita.risultato);

        if (!controllo) {
            partita.errori++;
            uscita.println("x");
        } else {
            uscita.println(partita.parola);

            if (!partita.parola.contains("_")) {
                uscita.println("HAI VINTO");
                if(partita.getID()==1) {
                	uscita2.println("HAI PERSO");
                	ServerGUI.vittoria=1;
                }
                else {
                	uscita1.println("HAI PERSO");
                	ServerGUI.vittoria=2;
                }
            }
        }
    }

}