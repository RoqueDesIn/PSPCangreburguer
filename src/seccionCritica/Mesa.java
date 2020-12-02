package seccionCritica;

import java.util.ArrayList;

import common.Constantes;
import modelos.RegFil;

public class Mesa {
	// Array de boolaenos para controlar el uso de los tenedores
	// false = tenedor no usado, true = tenedor en uso
	private boolean tenedores [];
	// array para controlar el tiempo que se tarda en pensar
	// idFilisofo, inicio en ms, fin en ms,contador de comidas, media
	private ArrayList<RegFil> dataControl;
	// run > true o wait > false
	boolean runWait;
	
	int nFilosofos;
	// número de cangreburgures consumidas
	int contaComidas;

	/*
	 * Constructor
	 */
	public Mesa(int n_filosofos) {
		// iniciamos contadores
		contaComidas =0;
		nFilosofos=n_filosofos;
		tenedores = new boolean [nFilosofos];
		// creamos los controladores de tenedores y lo iniciamos a false > no usados
		for (int i=0;i<n_filosofos;i++ ) {
			tenedores [i]=false;
		}
		
		// cargamos los filosofos en nuestro array
		dataControl = new ArrayList<RegFil>();
		for (int i=0;i<n_filosofos;i++) {
			// idFiosofo, inicio ms, fin ms, contador, media
			RegFil regFilosofo= new RegFil(i+1,0,0,0,0);
			dataControl.add(regFilosofo);
		}
		
		// todos los hilos empiezan corriendo
		runWait=true;
	}


	/*
	 * Consumir o pensar he ahi la cuestión
	 * @param idFilosofo 
	 */
	public synchronized void put(int idFilosofo) {
		
		// el hilo espera si el semáforo está cerrado.
		while (!runWait) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// iniciamos el tiempo en nuestro data control
		inicio(idFilosofo);

		// si los tenedores está libres puede comer
		if (idFilosofo==1) { // es el filosofo 1
			// el filosofo puede comer lo paramos y seteamos los tenedores
			if (!tenedores[tenedores.length-1] && !tenedores[idFilosofo+1])	{
				runWait=false;
				tenedores[tenedores.length-1]=true;
				tenedores[idFilosofo+1]=true;
				contaComidas++;
			} else {
			// si los tenedores están ocupados es que estaba comiendo
			// suelta los tenedores y vuelve a pensar
				if (tenedores[tenedores.length-1] && tenedores[idFilosofo+1])	{
					runWait=true;
					tenedores[tenedores.length-1]=false;
					tenedores[idFilosofo+1]=false;
					fin(idFilosofo);
				}
			}
		} else { // es el último filósofo
			// el filosofo puede comer lo paramos y seteamos los tenedores
			if (idFilosofo==tenedores.length-1) {
				if (!tenedores[0] && !tenedores[idFilosofo-1])	{
					runWait=false;
					tenedores[0]=true;
					tenedores[idFilosofo-1]=true;
					contaComidas++;
				} else {
				// si los tenedores están ocupados es que estaba comiendo
				// suelta los tenedores y vuelve a pensar
					if (tenedores[0] && tenedores[idFilosofo-1])	{
						runWait=true;
						tenedores[0]=false;
						tenedores[idFilosofo-1]=false;
						fin(idFilosofo);
					}
				}
			} else { // no es ni el último ni el primer filósofo
				// el filosofo puede comer lo paramos y seteamos los tenedores
				if (!tenedores[idFilosofo+1] && !tenedores[idFilosofo-1])	{
					runWait=false;
					tenedores[idFilosofo+1]=true;
					tenedores[idFilosofo-1]=true;
					contaComidas++;
				} else {
				// si los tenedores están ocupados es que estaba comiendo
				// suelta los tenedores y vuelve a pensar
					if (tenedores[idFilosofo+1] && tenedores[idFilosofo-1])	{
						runWait=true;
						tenedores[idFilosofo+1]=false;
						tenedores[idFilosofo-1]=false;
						fin(idFilosofo);
					}
				}
			}
		}
		
		// notifica al filósofo que está esperando
		notify();
		// salida por pantalla
		imprime(idFilosofo);
	}
	
	/**
	 * activa a los filosofos que están comiendo
	 * @param idFilosofo
	 */
	public synchronized void get() {
		notify();
		runWait=true;
	
	}
	/**
	 * imprime el dataControl
	 * @param idFilosofo
	 */
	private void imprime(int idFilosofo) {
		System.out.println("-------------------------------------");
		System.out.println("Datos de los filósofos: ");
		if (dataControl.size()<100){
			for (int i=0;i<dataControl.size();i++) {
				dataControl.get(i).imprime();
			}
		}

		System.out.print("Comidas solicitadas: ");
		if (dataControl.size()<100){
			for (int i=0; i < (tenedores.length);i++) {
				System.out.print( " ["+ tenedores[i] + "] ");
			}
		}
		System.out.println("");
		float totalMedia=0;
		float totalContadores=0;
		for (int i=0;i<dataControl.size();i++) {
			totalMedia=totalMedia+dataControl.get(i).getMedia();
			totalContadores=totalContadores+dataControl.get(i).getContador();
		}
		System.out.println("Solicitudes totales: "+(totalContadores));
		System.out.println("Comidas consumidas: "+contaComidas);
		System.out.println("Filósofos NO servidos: "+(totalContadores-contaComidas));
		System.out.println("Media general: "+(totalMedia/nFilosofos));
		System.out.println("Filósofos: "+Constantes.N_FILOSOFOS);
	}

	/**
	 * registra el tiempo de consumo de un filósofo y calcula la media ponderada
	 * @param idFilosofo
	 */
	private void fin(int idFilosofo) {
		int i=0;
		boolean encontrado=false;
		float totAnt=0;
		float media=0;
		float total=0;
		
		while ((i<dataControl.size()) || !encontrado){
			if (idFilosofo==i+1) {
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
				
				// sumamos uno al contador y guardamos la media nueva
				dataControl.get(i).setMedia(media);
				dataControl.get(i).setContador((dataControl.get(i).getContador()+1));
				encontrado=true;
			}
			i++;
		}
	}


	/**
	 *  registra el tiempo de solicitud de un filósofo
	 * @param idFilosofo
	 */
	private void inicio(int idFilosofo) {
		int i=0;
		boolean encontrado=false;
		
		while ((i<dataControl.size()) || !encontrado){
			if (idFilosofo==i+1) {
				dataControl.get(i).setInicio(System.nanoTime());
				dataControl.get(i).setContador(dataControl.get(i).getContador()+1);
				encontrado=true;
			}
			i++;
		}
	}
	
	//**************************fin
}
