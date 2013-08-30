package com.mawape.aimant.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegocioMapActivity;
import com.mawape.aimant.constants.AppConstants;
import com.mawape.aimant.entities.Categoria;
import com.mawape.aimant.entities.Negocio;

public class NegociosArrayAdapter extends ArrayAdapter<Negocio> {

	private final Object mLock = new Object();
	private List<Negocio> filteredValues;
	private List<Negocio> originalValues;
	private Filter filter;
	private Categoria currentCategoria;

	public NegociosArrayAdapter(Context context, List<Negocio> values,
			Categoria currentCategoria) {
		super(context, R.layout.negocios_row, values);
		this.filteredValues = values;
		this.currentCategoria = currentCategoria;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final Negocio negocioSeleccionado = getItem(position);

		View rowView = inflater.inflate(R.layout.negocios_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.negRowNombre);
		textView.setText(negocioSeleccionado.getNombre());
		TextView txtDir = (TextView) rowView.findViewById(R.id.negRowDir);
		txtDir.setText(negocioSeleccionado.getDireccion());

		if (negocioSeleccionado.getImgPath() != null
				&& negocioSeleccionado.getImgPath().length() > 0) {
			String imgPath = negocioSeleccionado.getImgPath();
			String mDrawableName = imgPath.substring(0,
					imgPath.lastIndexOf("."));// no-extension
			Integer imgResourceId = getContext().getResources().getIdentifier(
					mDrawableName, "drawable", getContext().getPackageName());
			if (imgResourceId > 0) {
				ImageView imgView = (ImageView) rowView
						.findViewById(R.id.negRowImg);
				imgView.setImageResource(imgResourceId);
			}
		}

		ImageView btnCall = (ImageView) rowView
				.findViewById(R.id.negRowBtnCall);
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				makePhoneCall(negocioSeleccionado.getTelefonoPrimario());
			}
		});

		ImageView btnMap = (ImageView) rowView.findViewById(R.id.negRowBtnMap);
		btnMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showMap(negocioSeleccionado);
			}
		});

		return rowView;
	}

	public Negocio getItem(int position) {
		return filteredValues.get(position);
	}

	private void makePhoneCall(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		getContext().startActivity(intent);
	}

	private void showMap(Negocio negocioSeleccionado) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(AppConstants.CATEGORIA_SELECCIONADA_KEY,
				currentCategoria);
		bundle.putSerializable(AppConstants.NEGOCIO_SELECCIONADO_KEY,
				negocioSeleccionado);
		Intent intent = new Intent(getContext(), NegocioMapActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		getContext().startActivity(intent);
	}

	public List<Negocio> getFilteredValues() {
		return filteredValues;
	}

	public Filter getFilter() {
		if (filter == null) {
			filter = new NegociosFilter();
		}
		return filter;
	}

	private class NegociosFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			String prefix = constraint.toString().toLowerCase();
			List<Negocio> resultValues = new ArrayList<Negocio>();

			if (originalValues == null) {
				synchronized (mLock) {
					// first time
					originalValues = new ArrayList<Negocio>(filteredValues);
				}
			}
			if (prefix != null && prefix.length() > 0) {
				for (Negocio negocio : originalValues) {
					if (negocio.getNombre().toLowerCase().startsWith(prefix)) {
						resultValues.add(negocio);
					}
				}
			} else {// if nothing input then all
				resultValues.addAll(originalValues);
			}
			results.values = resultValues;
			results.count = resultValues.size();
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredValues = (List<Negocio>) results.values;
			notifyDataSetChanged();
			clear();
			for (Negocio n : filteredValues) {
				add(n);
			}
			notifyDataSetInvalidated();

		}

	}

}