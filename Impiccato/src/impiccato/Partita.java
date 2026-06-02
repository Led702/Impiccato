package impiccats;

public class Partita {

    String parola;
    char[] array;
    char[] risultato;
    int errori;
    boolean jj = true;
    String altraparola;
    String risultatovero;
    int id;


	public Partita(String parola, int id) {
        this.parola = parola;
        this.id=id;
        int l = 0;
        
        array = new char[parola.length() + (parola.length() - 1)];
        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                array[i] = parola.charAt(l);     
                l++;
            } else {
                array[i] = ' ';
                
            }
        }
        risultato = array.clone();
        altraparola = new String(risultato);
        for (int i = 2; i < array.length - 1; i += 2) {
            risultato[i] = '_';
        }
        risultatovero = new String(risultato);
    }
	public int getID() {
		return id;
	}

    
}