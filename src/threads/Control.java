package threads;

import seccionCritica.Mesa;

/**
 * Clase que activa a los filósofos que están comiendo
 * @author Roque
 *
 */
public class Control extends Thread{
	private Mesa mesa;
	
	public Control (Mesa mesa) {
		this.mesa=mesa;
	}
	
	public void run() {
		producir();
	}

	public void producir() {
			while (true) {			
				this.mesa.get();
			}

	}
}
