package ar.edu.untref.investigacion.arduino.adaptadores;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.edu.untref.investigacion.arduino.R;

public class AdaptadorDeDispositivosBluetooth extends ArrayAdapter<BluetoothDevice> {
	
	private Context contexto;
	
	public AdaptadorDeDispositivosBluetooth(Context contexto, int textViewResourceId) {
		
		super(contexto, textViewResourceId);
		this.contexto = contexto;
	}
	
	@Override
	public View getView(int posicion, View view, ViewGroup padre) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.dispositivos_item, null);
		}
		
		TextView nombreDispositivo = (TextView) view.findViewById(R.id.nombreDispositivo);
		TextView direccionMacDispositivo = (TextView) view.findViewById(R.id.direccionMacDispositivo);
		
		BluetoothDevice dispositivo = this.getItem(posicion);
		
		String nombre = dispositivo.getName() != null ? dispositivo.getName() : "Sin nombre";
		nombreDispositivo.setText(nombre);
		direccionMacDispositivo.setText(dispositivo.getAddress());
		
		return view;
	}

}