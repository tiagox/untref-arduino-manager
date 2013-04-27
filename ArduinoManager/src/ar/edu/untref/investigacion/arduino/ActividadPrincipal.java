package ar.edu.untref.investigacion.arduino;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ar.edu.untref.investigacion.arduino.adaptadores.AdaptadorDeDispositivosBluetooth;
import ar.edu.untref.investigacion.arduino.servicios.ServicioBluetooth;
import ar.edu.untref.investigacion.arduino.servicios.ServicioBluetoothImpl;

public class ActividadPrincipal extends Activity {

	private ServicioBluetooth bluetooth;
	private AdaptadorDeDispositivosBluetooth adaptador;

	private final BroadcastReceiver nuevoDispositivoBluetoothEncontrado = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	    	
	        String accion = intent.getAction();
	        if (bluetooth.accionDispositivoEncontrado().equals(accion)) {

	        	BluetoothDevice dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            adaptador.add(dispositivo);
	        }
	    }
	};

	OnClickListener buscar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			adaptador.clear();
			
			mostrarMensaje("Buscando dispositivos...");
			bluetooth.buscarDispositivos();
		}
	};
	
	private OnItemClickListener dispositivoSeleccionado = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adaptador, View item, int posicion, long id) {
			
			BluetoothDevice seleccionado = (BluetoothDevice) adaptador.getItemAtPosition(posicion);
			mostrarMensaje("Se seleccionó a: " + seleccionado.getName() );
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);

		this.bluetooth = new ServicioBluetoothImpl();

		IntentFilter filter = new IntentFilter(bluetooth.accionDispositivoEncontrado());
		registerReceiver(nuevoDispositivoBluetoothEncontrado, filter);
		
		findViewById(R.id.btnBuscarDispositivos).setOnClickListener(buscar);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(nuevoDispositivoBluetoothEncontrado);
	}

	@Override
	protected void onResume() {

		super.onResume();

		if (!bluetooth.soporta()) {
			mostrarMensaje("No hay soporte para Bluetooth");
		}
 
		if ( !bluetooth.estaActivo() ) {
			startActivityForResult(bluetooth.activar(), bluetooth.codigoActivacion());
		}

		
		this.adaptador = new AdaptadorDeDispositivosBluetooth(this, R.layout.dispositivos_item);
		this.adaptador.addAll( this.bluetooth.obtenerDispositivosEnlazados() );
		this.adaptador.notifyDataSetChanged();
		
		ListView lista = (ListView) findViewById(R.id.dispositivos);
		lista.setAdapter(this.adaptador);
		lista.setOnItemClickListener(this.dispositivoSeleccionado);
	}

	public void mostrarMensaje(String mensaje) {
		
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			mostrarMensaje("Bluetooth activado");
		} else {
			mostrarMensaje("Bluetooth no activado");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.actividad_principal, menu);
		return true;
	}
	
}