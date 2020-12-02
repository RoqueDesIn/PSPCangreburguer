package MainApp;

import java.util.Scanner;

import common.Constantes;
import seccionCritica.Mesa;
import threads.Control;
import threads.Filosofo;

public class MainFil {

	public static void main(String[] args) {
		Scanner sc= new Scanner (System.in);
		
		System.out.println("Introduzca número de filosofos: ");
		Constantes.N_FILOSOFOS=sc.nextInt();
		System.out.println("Número de tenedores: ");
		Constantes.N_TENEDORES=Constantes.N_FILOSOFOS;
		
		Filosofo[] filosofos = new Filosofo[Constantes.N_FILOSOFOS];
		
		//crea el hilo de la mesa
		Mesa mesa = new Mesa(Constantes.N_FILOSOFOS);
		
		//crea el hilo de control
		Control control = new Control(mesa);
		//lanzamos hilo de control
		control.start();
		
		// creamos hilos-filosofos
		for(int i = 0 ; i < Constantes.N_FILOSOFOS-1 ; i++){
			filosofos[i] = new Filosofo(i+1,mesa);
		}
		// lanzamos los hilos de filosofos
		for(int i = 0 ; i < Constantes.N_FILOSOFOS-1 ; i++){
			filosofos[i].start();
		}
		
		// cierra scanner
		sc.close();
	}
}

