package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegociosListActivity;
import com.mawape.aimant.constants.AppConstants;

public class CategoriasArrayAdapter extends ArrayAdapter<String> {
	private final List<String> categorias;

	public CategoriasArrayAdapter(Context context, List<String> values) {
		super(context, R.layout.negocios_row, values);
		this.categorias = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final String currentCategoria = categorias.get(position);
		View rowView = inflater.inflate(R.layout.categorias_row, parent, false);
		// configure single button action
		Button btn = (Button) rowView.findViewById(R.id.catRowBtn);
		btn.setText(currentCategoria);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(),
						NegociosListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(AppConstants.CATEGORIA_SELECCIONADA_KEY,
						currentCategoria);
				Log.d(CategoriasArrayAdapter.class.getName(),
						"current context: " + getContext());
				getContext().startActivity(intent);
			}
		});
		return rowView;
	}

	public List<String> getCategorias() {
		return categorias;
	}
}