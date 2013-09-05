package com.mawape.aimant.activities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mawape.aimant.R;
import com.mawape.aimant.constants.AppConstants;
import com.mawape.aimant.entities.Categoria;
import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.services.impl.NegociosManagerImpl;
import com.mawape.aimant.widget.NegociosArrayAdapter;

public class NegociosListActivity extends BaseActivity {

	private static final String TAG = "NegociosActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.negocios_list);
		init();
	}

	private void init() {
		Bundle bundle = getIntent().getExtras();
		Categoria categoriaSeleccionada = (Categoria) bundle
				.get(AppConstants.CATEGORIA_SELECCIONADA_KEY);
		String currentFilter = bundle.getString(AppConstants.CATEGORIA_FILTER_KEY);
		// configure background.
		View view = findViewById(R.id.negociosListLayout);
		view.setBackgroundColor(Color.parseColor("#"
				+ categoriaSeleccionada.getColor()));

		final List<Negocio> opcionV = getInforme(categoriaSeleccionada
				.getNombre());
		NegociosArrayAdapter adapter = new NegociosArrayAdapter(
				getApplicationContext(), opcionV, categoriaSeleccionada);
		ListView lstOpciones = (ListView) findViewById(R.id.lstMenu);
		lstOpciones.setAdapter(adapter);
		lstOpciones.setOnItemClickListener(adapter);	

		// configure with current selected category
		configureMenuBar(categoriaSeleccionada, true, adapter);
		filterList(currentFilter);
	}

	/**
	 * la idea es sacar el informecin de la db :D
	 */
	protected List<Negocio> getInforme() {
		return new NegociosManagerImpl(getApplicationContext()).getNegocios();
	}

	protected List<Negocio> getInforme(String currentCategoria) {
		List<Negocio> negocios = new ArrayList<Negocio>();
		for (Negocio n : getInforme()) {
			if (n.getCategoria().equals(currentCategoria)) {
				negocios.add(n);
			}
		}

		return negocios;
	}
}
