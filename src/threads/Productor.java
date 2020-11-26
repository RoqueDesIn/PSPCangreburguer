package threads;

import seccionCritica.Camarero;

public class Productor extends Thread {
	private int idCocinero;
	private Camarero camarero;
	private final int  MAXSEGCOC=3;
	
/**
 * Constructor de la clase productor
 * solicita una cangreburguer
 * @param idCliente
 * @param c
 */
	public Productor  (int idCocinero, Camarero c) {
		super();
		this.idCocinero = idCocinero;
		this.camarero=c;
	}
	
	public void run() {
		while (true) {
			solicitar();
		}

	}
	
	public void solicitar() {
		int espera = (int) ((Math.random()*10)%MAXSEGCOC)+1;
		
		while (true) {
			// salida por pantalla
			//System.out.println("un cocinero termina una cangreburguer."+idCocinero);
			this.camarero.get(idCocinero);
			try {
				Thread.sleep(espera*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
