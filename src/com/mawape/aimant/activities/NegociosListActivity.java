package com.mawape.aimant.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.mawape.aimant.R;
import com.mawape.aimant.constants.AppConstants;
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
		String categoriaSeleccionada = (String) bundle
				.get(AppConstants.CATEGORIA_SELECCIONADA_KEY);
		final List<Negocio> opcionV = getInforme(categoriaSeleccionada);
		Log.d(TAG, "el retorno fue: " + opcionV);
		ListView lstOpciones = (ListView) findViewById(R.id.lstMenu);
		lstOpciones.setAdapter(new NegociosArrayAdapter(
				getApplicationContext(), opcionV));
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
