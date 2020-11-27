package seccionCritica;

import java.util.ArrayList;

import common.Constantes;
import modelos.RegDC;

public class Camarero {
	// contador de cangreburgures solicitadas x cliente
	private int contaSoli [];
	// array para controlar el tiempo que se tarda en serir una cangreburguer
	// idcliente, inicio en ms, fin en ms,contador de cangreburguers consumidas, media
	private ArrayList<RegDC> dataControl;
	// run > true o wait > false
	boolean runWait;
	// bandeja de entrada
	ArrayList<Integer> bandeja;
	// cola de clientes
	ArrayList<Integer> colaClientes;
	int nClientes;
	// número de cangreburgures consumidas
	int contaBurguers;

	/*
	 * Constructor
	 */
	public Camarero(int n_CLIENTES) {
		// iniciamos contadores
		contaSoli = new int [n_CLIENTES];
		contaBurguers =0;
		nClientes=n_CLIENTES;
		// creamos los contadores de cangreburguers consunidas de cada cliente y lo iniciamos a 0
		for (int i=0;i<n_CLIENTES;i++ ) {
			contaSoli [i]=0;
		}
		// creamos la bandeja vacia
		bandeja = new ArrayList<Integer>();
		colaClientes = new ArrayList<Integer>();
		
		// cargamos los clientes en nuestro array
		dataControl = new ArrayList<RegDC>();
		for (int i=0;i<n_CLIENTES;i++) {
			// idcliente, inicio ms, fin ms, contador, media
			RegDC regCliente= new RegDC(i+1,0,0,0,0);
			dataControl.add(regCliente);
		}
		
		// todos los hilos empiezan corriendo
		runWait=true;
	}


	/*
	 * producir cangreburguer
	 * @param idcliente que consume cangreburguer
	 */
	public synchronized void get(int idCocinero) {
		// añadimos cangreburguer a la bandeja
		bandeja.add(idCocinero);
		// abre la veda de cangreburguers y avisa a los hilos
		runWait=true;
		notify();
	}
	
	/*
	 * Consumir cangreburguers
	 * @param idcliente que consume Cangreburguer
	 */
	public synchronized void put(int idCliente) {
		
		while (!runWait) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// iniciamos el tiempo en nuestro data control
		inicio(idCliente);
		// si hay cangreburguers en la bandeja se consume la primera
		if (bandeja.size()>0) {
			bandeja.remove(0);
			// una cangreburguer es comida
			contaSoli[idCliente-1]++;
			// registramos el tiempo de fin en nuestro datacontrol
			fin(idCliente);
			contaBurguers++;
			// quitamos el cliente de la cola
			for (int i=0;i<colaClientes.size();i++) {
				if (colaClientes.get(i)==idCliente) colaClientes.remove(i);
			}
			// el cliente puede volver a comer
			runWait=true;
		} else {
			//si no hay cangreburguers en la bandeja añadimos cliente a la cola
			colaClientes.add(idCliente);
			// el cliente debe esperar hasta que haya cangreburguers en la bandeja
			runWait=false;
		}
		notify();
		// salida por pantalla
		System.out.println("-------------------------------------");
		// imprime array de control de media de servicio en mesa
		imprime(idCliente);
		System.out.print("Cangreburguers solicitadas: ");
		for (int i=0; i < (contaSoli.length);i++) {
			System.out.print( " ["+ contaSoli[i] + "] ");
		}
		System.out.println("Hay "+bandeja.size()+" Cangreburguers en la bandeja");
		System.out.println("CangreBurguers consumidas: "+contaBurguers);


	}
	
	/**
	 * imprime el dataControl
	 * @param idCliente
	 */
	private void imprime(int idCliente) {
		System.out.println("Datos de los clientes: ");
		for (int i=0;i<dataControl.size();i++) {
			dataControl.get(i).imprime();
		}
		float totalMedia=0;
		float totalContadores=0;
		for (int i=0;i<dataControl.size();i++) {
			totalMedia=totalMedia+dataControl.get(i).getMedia();
			totalContadores=totalContadores+dataControl.get(i).getContador();
		}
		System.out.println("Media general: "+(totalMedia/nClientes));
		System.out.println("Solicitudes totales: "+(totalContadores));
		System.out.println("Bandeja:"+(bandeja.size()));
		System.out.println("Cocineros: "+Constantes.N_COCINEROS+" Clientes: "+Constantes.N_CLIENTES);
	}

	/**
	 * registra el tiempo de consumo ed un cliente y calcula la media ponderada
	 * @param idCliente
	 */
	private void fin(int idCliente) {
		int i=0;
		boolean encontrado=false;
		float totAnt=0;
		float media=0;
		float total=0;
		
		while ((i<dataControl.size()) || !encontrado){
			if (idCliente==i+1) {
				dataControl.get(i).setFin(System.nanoTime());
				// hallamos el total anterior multiplicando la media por el contador
				if (dataControl.get(i).getMedia()==0) {
					totAnt=0;
				} else {
					totAnt=dataControl.get(i).getMedia()*dataControl.get(i).getContador();
				}
				// hallamos la media nueva
				total = totAnt + (dataControl.get(i).getFin()-dataControl.get(i).getInicio());
				media = total/(dataControl.get(i).getContador()+1);
				
				System.out.println(dataControl.get(i).getFin()-dataControl.get(i).getInicio()
						+"total:"+total+" contador:"+dataControl.get(i).getContador()+1);
				
				// sumamos uno al contador y guardamos la media nueva
				dataControl.get(i).setMedia(media);
				dataControl.get(i).setContador((dataControl.get(i).getContador()+1));
				encontrado=true;
			}
			i++;
		}
	}


	/**
	 *  registra el tiempo de solicitud de un cliente
	 * @param idCliente
	 */
	private void inicio(int idCliente) {
		int i=0;
		boolean encontrado=false;
		
		while ((i<dataControl.size()) || !encontrado){
			if (idCliente==i+1) {
				dataControl.get(i).setInicio(System.nanoTime());
				dataControl.get(i).setContador(dataControl.get(i).getContador()+1);
				encontrado=true;
			}
			i++;
		}
	}
	
	//**************************fin
}
