package modelos;

public class RegDC {
	private int cliente;
	private long inicio;
	private long fin;
	private int contador;
	private float media;
	
	/**
	 * Constructor del modelo para controlar el rendimiento de nuestros cocineros 
	 * @param cliente
	 * @param inicio
	 * @param fin
	 */
	public RegDC(int cliente, long inicio, long fin, int contador, float media) {
		super();
		this.cliente = cliente;
		this.inicio = inicio;
		this.fin = fin;
		this.contador= contador;
		this.media=media;
	}

	/**
	 * getters y setters
	 * @return
	 */
	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
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
		System.out.print( "cliente ["+ this.cliente + "] "
						+ "inicio ["+ this.inicio + "] "
						+ "fin ["+ this.fin + "] "
						+ "media ["+ this.media + "] "
						+ "contador ["+ this.contador + "] "
						);
		System.out.println("");
	}
	
	
}
