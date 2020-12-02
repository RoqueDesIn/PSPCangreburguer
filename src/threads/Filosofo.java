package threads;

import seccionCritica.Mesa;

public class Filosofo extends Thread {
private int idFilosofo;
private Mesa mesa;
private final int  MAXSEGFIL=4;
private final int MINSEGFIL=2;

public Filosofo  (int idFil, Mesa mesa) {
	super();
	this.idFilosofo = idFil;
	this.mesa=mesa;
}
public void run() {
	consumir();
}

public void consumir() {
	int espera = (int) Math.floor(((Math.random()*MAXSEGFIL)+MINSEGFIL));
	
		while (true) {
			// salida por pantalla
			//System.out.println("El cliente "+idCliente +" quiere comer una Cangreburguer.");
			// solicitamos consumir al hilo
			this.mesa.put(idFilosofo);
			// tras solicitar consumir esperamos un tiempo aleatorio
			try {
				Thread.sleep(espera*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
