package MainApp;

import java.util.Scanner;

import common.Constantes;
import seccionCritica.Camarero;
import threads.Cliente;
import threads.Productor;

public class MainCangre {

	public static void main(String[] args) {
		Scanner sc= new Scanner (System.in);
		
		System.out.println("Introduzca número de clientes: ");
		Constantes.N_CLIENTES=sc.nextInt();
		System.out.println("Introduzca número de cocineros: ");
		Constantes.N_COCINEROS=sc.nextInt();
		
		Cliente[] clientes = new Cliente[Constantes.N_CLIENTES];
		Productor[] cocineros = new Productor[Constantes.N_COCINEROS];
		
		//crea el hilo del camarero
		Camarero camarero = new Camarero(Constantes.N_CLIENTES);
		
		// creamos hilos-cocineros
		for(int i = 0 ; i < Constantes.N_COCINEROS ; i++){
			cocineros[i] = new Productor(i+1,camarero);
		}
		// creamos hilos-clientes
		for(int i = 0 ; i < Constantes.N_CLIENTES ; i++){
			clientes[i] = new Cliente(i+1,camarero);
		}
		
		// lanzamos los hilos de cocinero
		for(int i = 0 ; i < Constantes.N_COCINEROS ; i++){
			cocineros[i].start();
		}
			// lanzamos los hilos de cliente
		for(int i = 0 ; i < Constantes.N_CLIENTES ; i++){
			clientes[i].start();
		}
		//System.out.println("Fin");
	}
}

