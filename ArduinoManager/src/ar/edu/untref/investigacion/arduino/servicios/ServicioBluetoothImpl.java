package ar.edu.untref.investigacion.arduino.servicios;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

public class ServicioBluetoothImpl implements ServicioBluetooth {

	private static final String LOG_TAG = ServicioBluetoothImpl.class.getSimpleName();
	
	BluetoothAdapter adapter;

	public ServicioBluetoothImpl() {
		
		this.adapter = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public boolean soporta() {
		
		return this.adapter != null;
	}

	@Override
	public boolean estaActivo() {
		
		return this.adapter.isEnabled();
	}

	@Override
	public Intent activar() {
		
		return new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

	}

	@Override
	public Set<BluetoothDevice> obtenerDispositivosEnlazados() {
		
		return this.adapter.getBondedDevices();
	}

	@Override
	public int codigoActivacion() {
		
		return 1;
	}

	@Override
	public String accionDispositivoEncontrado() {
		
		return BluetoothDevice.ACTION_FOUND;
	}

	@Override
	public void buscarDispositivos() {
		
		Log.i(LOG_TAG, "Comienza la búsqueda de dispositivos");
		
		this.adapter.cancelDiscovery();
		this.adapter.startDiscovery();
	}

	@Override
	public ConexionBluetooth conectarConDispositivo(BluetoothDevice dispositivo) throws IOException {

		Log.i(LOG_TAG, "Conectando con el dispositivo " + dispositivo.getName());
	
		BluetoothSocket socket = dispositivo.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		socket.connect();

		return new ConexionBluetooth(dispositivo, socket.getInputStream(), socket.getOutputStream());
	}

}