package ar.edu.untref.investigacion.arduino.servicios;

public enum Mensaje {
	
	ENCENDER (0),
	APAGAR (1);
	
	private int comando;
	
	Mensaje(int comando) {
		this.comando = comando;
	}

	public int getComando() {
		return this.comando;
	}
	
}