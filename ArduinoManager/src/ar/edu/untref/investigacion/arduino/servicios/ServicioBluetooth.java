package ar.edu.untref.investigacion.arduino.servicios;

import java.io.IOException;
import java.util.Set;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public interface ServicioBluetooth {
	
	public boolean soporta();
	public boolean estaActivo();
	public Intent activar();
	public Set<BluetoothDevice> obtenerDispositivosEnlazados();
	public int codigoActivacion();
	public String accionDispositivoEncontrado();
	public void buscarDispositivos();
	public ConexionBluetooth conectarConDispositivo(BluetoothDevice dispositivo) throws IOException;
}