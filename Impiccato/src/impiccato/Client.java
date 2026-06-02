package impiccats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
	  Socket socket;
	  PrintWriter out;
	  BufferedReader in;
	    

	    private ClientGUI gui;

	    public Client(ClientGUI gui) {
	    	this.gui=gui;
	        connetti();
	        ascolta();
	    }

		

	    private void connetti() {
	        try {
	            socket = new Socket("127.0.0.1", 6767);
	            out = new PrintWriter(socket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void inviaMessaggio(String lettera) {
	    	out.println(lettera);

	    }

	    private void ascolta() {
	        new Thread(() -> {
	            try {
	                String msg;
	                while ((msg = in.readLine()) != null) {
	                    gui.processaMessaggio(msg);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }).start();
	    }

}