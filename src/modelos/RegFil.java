package modelos;

public class RegFil {
	private int filosofo;
	private long inicio;
	private long fin;
	private int contador;
	private float media;
	
	/**
	 * Constructor del modelo para controlar el rendimiento de nuestros cocineros 
	 * @param filosofo
	 * @param inicio
	 * @param fin
	 */
	public RegFil(int filosofo, long inicio, long fin, int contador, float media) {
		super();
		this.filosofo = filosofo;
		this.inicio = inicio;
		this.fin = fin;
		this.contador= contador;
		this.media=media;
	}

	/**
	 * getters y setters
	 * @return
	 */
	public int getFilosofo() {
		return filosofo;
	}

	public void setFilosofo(int filosofo) {
		this.filosofo = filosofo;
	}

	public long getInicio() {
		return inicio;
	}

	public void setInicio(long l) {
		this.inicio = l;
	}

	public long getFin() {
		return fin;
	}

	public void setFin(long l) {
		this.fin = l;
	}

	public void setContador(int contador) {
		this.contador=contador;
	}
	
	public int getContador() {
		return this.contador;
	}

	public void setMedia(float media2) {
		this.media=media2;
		
	}

	public float getMedia() {
		return this.media;
	}

	public void imprime() {
		System.out.print( "Filósofo ["+ this.filosofo + "] "
						+ "inicio ["+ this.inicio + "] "
						+ "fin ["+ this.fin + "] "
						+ "media ["+ this.media + "] "
						+ "contador ["+ this.contador + "] "
						);
		System.out.println("");
	}
	
	
}
