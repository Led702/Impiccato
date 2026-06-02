
package impiccats;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ServerGUI implements ActionListener {
	static String parolss;
	static int vittoria=0;
	boolean chiudi=true;
	
	boolean iniziato=true;
    JFrame fr = new JFrame();
    // SFONDO
    JLabel sfondo = new JLabel(new ImageIcon("immagini1/fogliosfondo.jpeg"));
    // PANNELLO PRINCIPALE
    JPanel pannello = new JPanel();
    // COMPONENTI
    JLabel parola = new JLabel("Inserisci la parola", SwingConstants.CENTER);
    JTextField inserimento = new JTextField();
    JButton inizia = new JButton("INIZIA");
    public ServerGUI() {
        inizia.addActionListener(this);
        // layout frame
        fr.setContentPane(sfondo);
        sfondo.setLayout(new BorderLayout());
        // pannello principale trasparente
        pannello.setOpaque(false);
        pannello.setLayout(new GridLayout(3, 1, 20, 20));
        // FONT
        Font font = new Font("Segoe Script", Font.BOLD, 40);
        parola.setFont(font);
        parola.setForeground(Color.BLACK);
        parola.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        inserimento.setOpaque(false);
        inserimento.setBorder(null);
        inserimento.setHorizontalAlignment(JTextField.CENTER);
        inserimento.setFont(new Font("Segoe Script", Font.PLAIN, 22));
        inserimento.setForeground(Color.BLACK);
        inizia.setPreferredSize(new Dimension(120, 40));
        inizia.setBackground(new Color(180, 180, 180));
        inizia.setOpaque(true);
        inizia.setBorderPainted(false);
        inizia.setFocusPainted(false);
        inizia.setFont(new Font("Segoe Script", Font.BOLD, 16));
        // pannello bottone
        JPanel pannelloBottone = new JPanel();
        pannelloBottone.setOpaque(false);
        pannelloBottone.add(inizia);
        // aggiunta elementi al pannello principale
        pannello.add(parola);
        pannello.add(inserimento);
        pannello.add(pannelloBottone);
        // aggiunta al frame
        sfondo.add(pannello, BorderLayout.CENTER);
        // frame
        fr.setSize(500, 800);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        
    }
    public static void main(String[] args) {
        new ServerGUI();
    }
    public void actionPerformed(ActionEvent e) {
    	iniziato=true;
    	parolss=inserimento.getText();
        if (e.getActionCommand().equals("INIZIA")) {
        	if((parolss.trim().isEmpty())) {
        		iniziato=false ;
        		inserimento.setText("La parola non è valida");
        		
        	}
        	for(int i=0; i<parolss.length();i++) {
        		if(parolss.charAt(i)== ' ') {
        			iniziato=false;
        			inserimento.setText("La parola non è valida");
        		}
        	}
        	
        	if(iniziato) {
        		 // RIMUOVE TUTTO DAL PANNELLO PRINCIPALE
        		pannello.removeAll();
                pannello.revalidate();
                pannello.repaint();
                
                Thread thread = new Thread(() -> {
                    LogicaServer.avviaServer();
                });
                thread.start(); 
                while(chiudi) {
                System.out.println("jsbgjs");
                	if(vittoria!=0) {
                		
                		pannello.removeAll();
                		pannello.setLayout(new GridLayout(3, 1, 20, 20));

                		
                		JLabel finePartita = new JLabel(" PARTITA FINITA ", SwingConstants.CENTER);
                		finePartita.setFont(new Font("Segoe Script", Font.BOLD, 40));
                		finePartita.setForeground(Color.BLACK);

                		
                		JLabel vincitore = new JLabel(
                		        "Giocatore " + vittoria + " ha vinto",
                		        SwingConstants.CENTER);

                		vincitore.setFont(new Font("Segoe Script", Font.PLAIN, 32));
                		vincitore.setForeground(Color.BLACK);

                		
                		JLabel parolaFinale = new JLabel(
                		        "<html><center>La parola era:<br><b><i>" 
                		        + parolss + 
                		        "</i></b></center></html>",
                		        SwingConstants.CENTER);

                		parolaFinale.setFont(new Font("Segoe Script", Font.PLAIN, 26));
                		parolaFinale.setForeground(Color.BLACK);

                		
                		pannello.add(finePartita);
                		pannello.add(vincitore);
                		pannello.add(parolaFinale);

                		
                		pannello.revalidate();
                		pannello.repaint();                		

                		chiudi=false;
      		
                	}
                }
                
                
            }
        	}
           
        }
    
 }