package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegocioMapActivity;
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
		final Negocio negocioSeleccionado = getNegocios().get(position);

		View rowView = inflater.inflate(R.layout.negocios_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.negRowNombre);
		textView.setText(negocioSeleccionado.getNombre());

		if (negocioSeleccionado.getImgPath() != null) {
			String mDrawableName = negocioSeleccionado.getImgPath();
			int imgResourceId = getContext().getResources().getIdentifier(
					mDrawableName, "drawable", getContext().getPackageName());
			ImageView imgView = (ImageView) rowView
					.findViewById(R.id.negRowImg);
			imgView.setImageResource(imgResourceId);
		}

		ImageButton btnCall = (ImageButton) rowView
				.findViewById(R.id.negRowBtnCall);
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				makePhoneCall(negocioSeleccionado.getTelefono());
			}
		});

		ImageButton btnMap = (ImageButton) rowView
				.findViewById(R.id.negRowBtnMap);
		btnMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showMap();
			}
		});

		return rowView;
	}

	private void makePhoneCall(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		getContext().startActivity(intent);
	}

	private void showMap() {
		Intent intent = new Intent(getContext(), NegocioMapActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}

	public List<Negocio> getNegocios() {
		return negocios;
	}
}