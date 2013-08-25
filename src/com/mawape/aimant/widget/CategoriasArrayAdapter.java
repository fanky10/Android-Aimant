package com.mawape.aimant.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawape.aimant.R;
import com.mawape.aimant.activities.NegociosListActivity;
import com.mawape.aimant.constants.AppConstants;
import com.mawape.aimant.entities.Categoria;

public class CategoriasArrayAdapter extends ArrayAdapter<Categoria> implements
		AdapterView.OnItemClickListener, Filterable {
	private final Object mLock = new Object();
	private Filter filter;
	private List<Categoria> filteredValues = new ArrayList<Categoria>();
	// copy of initialized filtered values
	private List<Categoria> originalValues;

	public CategoriasArrayAdapter(Context context, List<Categoria> values) {
		super(context, R.layout.categorias_row, R.id.catRowName, values);
		this.filteredValues = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.categorias_row, parent, false);
		Categoria categoriaSeleccionada = getItem(position);
		if (categoriaSeleccionada != null) {

			TextView txtName = (TextView) rowView.findViewById(R.id.catRowName);
			txtName.setText(categoriaSeleccionada.getNombre());

			RelativeLayout layoutWrapper = (RelativeLayout) rowView
					.findViewById(R.id.catRowWrapper);
			GradientDrawable bgShape = (GradientDrawable) layoutWrapper
					.getBackground();
			bgShape.setColor(Color.parseColor("#"
					+ categoriaSeleccionada.getColor()));

			String imgPath = categoriaSeleccionada.getImgPath();
			String mDrawableName = imgPath.substring(0,
					imgPath.lastIndexOf("."));// no
												// extension
			Integer imgResourceId = getContext().getResources().getIdentifier(
					mDrawableName, "drawable", getContext().getPackageName());
			if (imgResourceId > 0) {
				ImageView imgView = (ImageView) rowView
						.findViewById(R.id.catRowIcon);
				imgView.setImageResource(imgResourceId);
			}
		}
		return rowView;
	}

	@Override
	public Categoria getItem(int position) {
		return filteredValues.get(position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
		startIntent(position);
	}

	private void startIntent(int position) {
		final Categoria currentCategoria = getItem(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable(AppConstants.CATEGORIA_SELECCIONADA_KEY,
				currentCategoria);

		Intent intent = new Intent(getContext(), NegociosListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		getContext().startActivity(intent);
	}

	public Filter getFilter() {
		if (filter == null) {
			filter = new CategoriasFilter();
		}
		return filter;// super.getFilter();
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public List<Categoria> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<Categoria> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public List<Categoria> getOriginalValues() {
		return originalValues;
	}

	public void setOriginalValues(List<Categoria> originalValues) {
		this.originalValues = originalValues;
	}

	private class CategoriasFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			String prefix = constraint.toString().toLowerCase();
			List<Categoria> resultValues = new ArrayList<Categoria>();

			if (originalValues == null) {
				synchronized (mLock) {
					// first time
					originalValues = new ArrayList<Categoria>(filteredValues);
				}
			}
			if (prefix != null && prefix.length() > 0) {
				for (Categoria categoria : originalValues) {
					if (categoria.getNombre().toLowerCase().startsWith(prefix)) {
						resultValues.add(categoria);
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
			filteredValues = (List<Categoria>) results.values;
			notifyDataSetChanged();
			clear();
			for (Categoria c : filteredValues) {
				add(c);
			}
			notifyDataSetInvalidated();

		}

	}
}