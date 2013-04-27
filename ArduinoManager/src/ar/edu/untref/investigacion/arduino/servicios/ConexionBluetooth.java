package ar.edu.untref.investigacion.arduino.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ConexionBluetooth {
	
	private static String LOG_TAG = ConexionBluetooth.class.getSimpleName();
	
	private BluetoothDevice dispositivo;
	private InputStream entrada;
	private OutputStream salida;

	public ConexionBluetooth(BluetoothDevice dispositivo, InputStream entrada, OutputStream salida) {

		Log.i(LOG_TAG, "Se estableció la conexión con el dispositivo " + dispositivo.getName());
		
		this.dispositivo = dispositivo;
		this.entrada = entrada;
		this.salida = salida;
	}
	
	public void enviar(Mensaje mensaje) throws IOException {
	
		Log.i(LOG_TAG, "Enviando comando " + mensaje.getComando() + " al dispositivo " + this.dispositivo.getName());
		
		this.salida.write( mensaje.getComando() );
	}
	
}