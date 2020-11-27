package threads;

import seccionCritica.Camarero;

public class Cliente extends Thread {
private int idCliente;
private Camarero camarero;
private final int  MAXSEGCLI=3;

public Cliente  (int idCliente, Camarero camarero) {
	super();
	this.idCliente = idCliente;
	this.camarero=camarero;
}
public void run() {
	consumir();
}

public void consumir() {
	int espera = (int) ((Math.random()*10)%MAXSEGCLI)+1;
	
		while (true) {
			// salida por pantalla
			//System.out.println("El cliente "+idCliente +" quiere comer una Cangreburguer.");
			// solicitamos consumir al hilo
			this.camarero.put(idCliente);
			// tras solicitar consumir esperamos un tiempo aleatorio
			try {
				Thread.sleep(espera*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
