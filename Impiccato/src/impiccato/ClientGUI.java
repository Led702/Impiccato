package impiccats;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class ClientGUI implements KeyListener {
   Client client;
   JFrame fr = new JFrame();
   JLabel sfondo = new JLabel(new ImageIcon("immagini1/fogliosfondo.jpeg"));
   JPanel pannello = new JPanel();
   JPanel pannello1 = new JPanel();
   JPanel pannello2 = new JPanel();
   JPanel pannello3 = new JPanel(); 

   JLabel nome = new JLabel("Giocatore 1", SwingConstants.CENTER);
   JLabel attesa = new JLabel("Connessione", SwingConstants.CENTER);
   JLabel gioco = new JLabel("", SwingConstants.CENTER);
  
   JLabel omino = new JLabel();
   char cerca = ' ';
   int errori = 0;
   boolean attesaAttiva = true;
   public ClientGUI() {
       Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       int larghezza = screen.width;
       int altezza = screen.height;
       // Font
       Font fontNome = new Font("Segoe Script", Font.BOLD, 55);
       Font fontAttesa = new Font("Segoe Script", Font.BOLD, 80);
       nome.setFont(fontNome);
       attesa.setFont(fontAttesa);
       gioco.setFont(fontAttesa);
       nome.setForeground(Color.BLACK);
       attesa.setForeground(Color.BLACK);
       gioco.setForeground(new Color (97, 96, 91));
       fr.setContentPane(sfondo);
       sfondo.setLayout(null);
       pannello.setOpaque(false);
       pannello1.setOpaque(false);
       pannello2.setOpaque(false);
       pannello3.setOpaque(false);
       pannello.setLayout(null);
       pannello1.setLayout(new BorderLayout());
       pannello2.setLayout(new BorderLayout());
       pannello3.setLayout(new BorderLayout());
       pannello.setBounds(0, 0, larghezza, altezza);
       pannello1.setBounds((larghezza - 600) / 2, 80, 600, 100);
       pannello2.setBounds(larghezza / 8, altezza / 2 - 120, (larghezza * 3) / 4, 250);
             pannello3.setBounds(larghezza / 8, altezza - 300, (larghezza * 3) / 4, 250);
     
       omino.setBounds((larghezza - 400) / 2, altezza / 2 - 250, 400, 400);
       pannello1.add(nome, BorderLayout.CENTER);
       pannello2.add(attesa, BorderLayout.CENTER);
       pannello3.add(gioco, BorderLayout.CENTER);
       pannello.add(pannello1);
       pannello.add(pannello2);
       pannello.add(omino);
       sfondo.add(pannello);
       fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
       fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       fr.addKeyListener(this);
       fr.setVisible(true);
       animazioneAttesa();
       client = new Client(this);


   }
   public void animazioneAttesa() {
       Thread t = new Thread(() -> {
           String[] testi = {"Connessione", "Connessione.", "Connessione..", "Connessione..."};
           int i = 0;
           while (attesaAttiva) {
               try {
                   String testo = testi[i];
                   SwingUtilities.invokeLater(() -> attesa.setText(testo));
                   i = (i + 1) % testi.length;
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }
           }
       });
       t.start();
   }
   public void mostraGioco() {
       attesaAttiva = false;
       SwingUtilities.invokeLater(() -> {
           pannello.remove(pannello2);
           pannello.add(pannello3);
           pannello.revalidate();
           pannello.repaint();
           
       });
   }
   public void processaMessaggio(String messaggio) {
	   if(messaggio.equals("PLAYER1")) {

	        SwingUtilities.invokeLater(() -> {
	            nome.setText("Giocatore 1");
	        });
	    }

	    else if(messaggio.equals("PLAYER2")) {

	        SwingUtilities.invokeLater(() -> {
	            nome.setText("Giocatore 2");
	            
	        });
	    }else if (messaggio.equals("START")) {
           mostraGioco();
       }
       else if (messaggio.equals("x")) {
           if (errori < 12) {
               errori++;
               
               String percorso = "immagini2/" + errori + ".png";
               SwingUtilities.invokeLater(() -> {
                   ImageIcon icon = new ImageIcon(percorso);
                   Image img = icon.getImage().getScaledInstance(omino.getWidth(), omino.getHeight(), Image.SCALE_SMOOTH);
                   omino.setIcon(new ImageIcon(img));
               });
           }
          
           if (errori >= 11) {
               SwingUtilities.invokeLater(() -> {
                   
                   pannello3.removeAll();

                   JLabel perso = new JLabel("HAI PERSO!!", SwingConstants.CENTER);
                   perso.setFont(new Font("Segoe Script", Font.BOLD, 100));
                   perso.setForeground(new Color (232, 70, 70));

                   pannello3.add(perso, BorderLayout.CENTER);

                   pannello3.revalidate();
                   pannello3.repaint();
                   String percorso = "immagini2/12.png";
                   SwingUtilities.invokeLater(() -> {
                       ImageIcon icon = new ImageIcon(percorso);
                       Image img = icon.getImage().getScaledInstance(omino.getWidth(), omino.getHeight(), Image.SCALE_SMOOTH);
                       omino.setIcon(new ImageIcon(img));
                   });
               });
               
           }
       }
       else if(messaggio.equals("HAI VINTO")) {
    	   SwingUtilities.invokeLater(() -> {

               pannello3.removeAll();

               JLabel perso = new JLabel("HAI VINTO!!", SwingConstants.CENTER);
             
               perso.setFont(new Font("Segoe Script", Font.BOLD, 100));
               perso.setForeground(new Color(97, 199, 106));
              
               pannello3.add(perso, BorderLayout.CENTER);

               pannello3.revalidate();
               pannello3.repaint();
           });
    	   String percorso = "immagini2/12.png";
           SwingUtilities.invokeLater(() -> {
               ImageIcon icon = new ImageIcon(percorso);
               Image img = icon.getImage().getScaledInstance(omino.getWidth(), omino.getHeight(), Image.SCALE_SMOOTH);
               omino.setIcon(new ImageIcon(img));
           });
    	   
       }
       else if(messaggio.equals("HAI PERSO")) {
    	   SwingUtilities.invokeLater(() -> {
               pannello3.removeAll();

               JLabel perso = new JLabel("HAI PERSO!!", SwingConstants.CENTER);
               
               perso.setFont(new Font("Segoe Script", Font.BOLD, 100));
               perso.setForeground(new Color (232, 70, 70));
              
               pannello3.add(perso, BorderLayout.CENTER);
              
               pannello3.revalidate();
               pannello3.repaint();
           });
    	   String percorso = "immagini2/12.png";
           SwingUtilities.invokeLater(() -> {
               ImageIcon icon = new ImageIcon(percorso);
               Image img = icon.getImage().getScaledInstance(omino.getWidth(), omino.getHeight(), Image.SCALE_SMOOTH);
               omino.setIcon(new ImageIcon(img));
           });
    	   
       }
       else {
           // Aggiorna la parola solo se non abbiamo ancora perso
           if (errori < 11) {
        	  // System.out.println("La parola deve essere scitta: "+messaggio);
               SwingUtilities.invokeLater(() -> gioco.setText(messaggio));
           }
       }
   }
   
   @Override public void keyPressed(KeyEvent e) {
       cerca = e.getKeyChar();
       if (client != null && errori < 11) { // Blocca l'invio tasti se hai già perso
           client.inviaMessaggio(String.valueOf(cerca));
       }
   }
   @Override public void keyTyped(KeyEvent e) {}
   @Override public void keyReleased(KeyEvent e) {}
   public static void main(String[] args) {
       new ClientGUI();
   }
}
