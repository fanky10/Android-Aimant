package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegociosListActivity;
import com.mawape.aimant.constants.AppConstants;
import com.mawape.aimant.entities.Categoria;

public class CategoriasArrayAdapter extends ArrayAdapter<Categoria> implements
		AdapterView.OnItemClickListener {
	private final List<Categoria> categorias;

	public CategoriasArrayAdapter(Context context, List<Categoria> values) {
		super(context, R.layout.categorias_row, R.id.catRowTextView, values);
		this.categorias = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = super.getView(position, convertView, parent);
		RelativeLayout layoutWrapper = (RelativeLayout) rowView
				.findViewById(R.id.catRowWrapper);
		GradientDrawable bgShape = (GradientDrawable) layoutWrapper
				.getBackground();
		bgShape.setColor(Color.parseColor("#"
				+ categorias.get(position).getColor()));
		return rowView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
		startIntent(position);
	}

	private void startIntent(int position) {
		final Categoria currentCategoria = categorias.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable(AppConstants.CATEGORIA_SELECCIONADA_KEY,
				currentCategoria);

		Intent intent = new Intent(getContext(), NegociosListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		getContext().startActivity(intent);
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

}