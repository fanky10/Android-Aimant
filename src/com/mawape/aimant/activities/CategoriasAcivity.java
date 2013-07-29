package com.mawape.aimant.activities;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.mawape.aimant.R;
import com.mawape.aimant.services.impl.NegociosManagerImpl;
import com.mawape.aimant.widget.CategoriasArrayAdapter;

public class CategoriasAcivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias_list);
		init();
	}

	private void init() {
		final List<String> opcionV = getCategorias();
		final CategoriasArrayAdapter adaptador = new CategoriasArrayAdapter(
				getApplicationContext(), opcionV); 
		ListView lstOpciones = (ListView) findViewById(R.id.catListMenu);
		lstOpciones.setAdapter(adaptador);
		lstOpciones.setOnItemClickListener(adaptador);
	}

	private List<String> getCategorias() {
		return new NegociosManagerImpl(getApplicationContext()).getCategorias();
	}

}
