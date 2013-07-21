package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mawape.aimant.R;
import com.mawape.aimant.entities.Negocio;

public class NegociosArrayAdapter extends ArrayAdapter<Negocio> {
	private final List<Negocio> negocios;

	public NegociosArrayAdapter(Context context, List<Negocio> values) {
		super(context, R.layout.negocios_row, values);
		this.negocios = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Negocio negocioSeleccionado = getNegocios().get(position); 
		
		View rowView = inflater.inflate(R.layout.negocios_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.negRowNombre);
		textView.setText(negocioSeleccionado.getNombre());
		
		if (negocioSeleccionado.getImgPath()!=null) {
			String mDrawableName = negocioSeleccionado.getImgPath();
			int resID = getContext().getResources().getIdentifier(
					mDrawableName, "drawable", getContext().getPackageName());
			rowView.setBackgroundResource(resID);
		}
		return rowView;
	}

	public List<Negocio> getNegocios() {
		return negocios;
	}
}