package seccionCritica;

import java.util.ArrayList;

import modelos.RegDC;

public class Camarero {
	// contador de cangreburgures consumidas x cliente
	private int contaBurguer [];
	// array para controlar el tiempo que se tarda en serir una cangreburguer
	// idcliente, inicio en ms, fin en ms,contador de cangreburguers consumidas, media
	private ArrayList<RegDC> dataControl;

	// bandeja de entrada
	ArrayList<Integer> bandeja;
	// cola de clientes
	ArrayList<Integer> colaClientes;

	/*
	 * Constructor
	 */
	public Camarero(int n_CLIENTES) {
		// iniciamos contadores
		contaBurguer = new int [n_CLIENTES];
		
		// creamos los contadores de cangreburguers consunidas de cada cliente y lo iniciamos a 0
		for (int i=0;i<n_CLIENTES;i++ ) {
			contaBurguer [i]=0;
		}
		// creamos la bandeja vacia
		bandeja = new ArrayList<Integer>();
		colaClientes = new ArrayList<Integer>();
		
		// cargamos los clientes en nuestro array
		dataControl = new ArrayList<RegDC>();
		for (int i=0;i<n_CLIENTES;i++) {
			// idcliente, inicio ms, fin ms, contador, media
			RegDC regCliente= new RegDC(i+1,0,0,1,1);
			dataControl.add(regCliente);
		}
	}


	/*
	 * producir cangreburguer
	 * @param idcliente que consume cangreburguer
	 */
	public synchronized int get(int idCocinero) {

		// añadimos cangreburguer a la bandeja
		bandeja.add(idCocinero);
		
		// salida por pantalla
		System.out.println("Añadimos una Cangreburguer a la bandeja. Hay "+bandeja.size()+" Cangreburguers en la bandeja");
		return contaBurguer[idCocinero-1];
	}
	
	/*
	 * Consumir cangreburguers
	 * @param idcliente que consume Cangreburguer
	 */
	public synchronized void put(int idCliente) {
		
		// iniciamos el tiempo en nuestro data control
		inicio(idCliente);
		// si hay cangreburguers en la bandeja se consume la primera
		if (bandeja.size()>0) {
			bandeja.remove(0);
			// una cangreburguer es comida
			contaBurguer[idCliente-1]++;
			// registramos el tiempo de fin en nuestro datacontrol
			fin(idCliente);
			// quitamos el cliente de la cola
			for (int i=0;i<colaClientes.size();i++) {
				if (colaClientes.get(i)==idCliente) colaClientes.remove(i);
			}
		} else {
			System.out.println("El cliente "+idCliente+" no ha podido comer.");
			//añadimos cliente a la cola
			colaClientes.add(idCliente);
		}

		// salida por pantalla
		System.out.print("Cangreburguers consumidas: ");
		for (int i=0; i < (contaBurguer.length);i++) {
			System.out.print( " ["+ contaBurguer[i] + "] ");
		}
		System.out.println("Hay "+bandeja.size()+" Cangreburguers en la bandeja");
		System.out.println("-------------------------------------");
		// imprime array de control de media de servicio en mesa
		imprime(idCliente);
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
		// calcula la media general
		float mediaGeneral =0;
		float totalMedia=0;
		float totalContadores=0;
		for (int i=0;i<dataControl.size();i++) {
			totalMedia=totalMedia+dataControl.get(i).getMedia();
			totalContadores=totalContadores+dataControl.get(i).getContador();
		}
		System.out.println("Media total:     "+(totalMedia/totalContadores));
		
	}

	/**
	 * registra el tiempo de consumo ed un cliente y calcula la media ponderada
	 * @param idCliente
	 */
	private void fin(int idCliente) {
		int i=0;
		boolean encontrado=false;
		
		while ((i<dataControl.size()) || !encontrado){
			if (idCliente==i+1) {
				dataControl.get(idCliente-1).setFin(System.currentTimeMillis());
				// hallamos el total anterior multiplicando la media por el contador
				float totAnt=dataControl.get(i).getMedia()*dataControl.get(i).getContador();
				// hallamos la media nueva
				float total = totAnt + (dataControl.get(i).getFin()-dataControl.get(i).getInicio());
				float media = total/(dataControl.get(i).getContador()+1);
				// sumamos uno al contador y guardamos la media nueva
				dataControl.get(idCliente-1).setMedia(media);
				dataControl.get(idCliente-1).setContador((dataControl.get(i).getContador()+1));
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
				dataControl.get(i).setInicio(System.currentTimeMillis());
				dataControl.get(i).setContador(dataControl.get(i).getContador()+1);
				encontrado=true;
			}
			i++;
		}
	}
	
	//**************************fin
}
