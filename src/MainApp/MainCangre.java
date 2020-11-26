package MainApp;

import seccionCritica.Camarero;
import threads.Cliente;
import threads.Productor;

public class MainCangre {

	public static void main(String[] args) {
		final int N_CLIENTES = 2;
		final int N_COCINEROS = 1;
		
		Cliente[] clientes = new Cliente[N_CLIENTES];
		Productor[] cocineros = new Productor[N_COCINEROS];
		
		//crea el hilo del camarero
		Camarero camarero = new Camarero(N_CLIENTES);
		
		// creamos hilos-cocineros
		for(int i = 0 ; i < N_COCINEROS ; i++){
			cocineros[i] = new Productor(i+1,camarero);
		}
		// creamos hilos-clientes
		for(int i = 0 ; i < N_CLIENTES ; i++){
			clientes[i] = new Cliente(i+1,camarero);
		}
		
		// lanzamos los hilos de cocinero
		for(int i = 0 ; i < N_COCINEROS ; i++){
			cocineros[i].start();
		}
			// lanzamos los hilos de cliente
		for(int i = 0 ; i < N_CLIENTES ; i++){
			clientes[i].start();
		}
		System.out.println("Fin");
	}
}

