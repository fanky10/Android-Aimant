package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegociosListActivity;
import com.mawape.aimant.constants.AppConstants;

public class CategoriasArrayAdapter extends ArrayAdapter<String> implements
		AdapterView.OnItemClickListener {
	private final List<String> categorias;

	public CategoriasArrayAdapter(Context context, List<String> values) {
		super(context, R.layout.categorias_row, R.id.catRowTextView, values);
		this.categorias = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		// TODO: setColor
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
		startIntent(position);
	}

	private void startIntent(int position) {
		final String currentCategoria = categorias.get(position);

		Intent intent = new Intent(getContext(), NegociosListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(AppConstants.CATEGORIA_SELECCIONADA_KEY,
				currentCategoria);
		Log.d(CategoriasArrayAdapter.class.getName(), "current context: "
				+ getContext());
		getContext().startActivity(intent);
	}

	public List<String> getCategorias() {
		return categorias;
	}

}