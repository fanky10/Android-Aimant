package com.mawape.aimant.activities;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.mawape.aimant.R;
import com.mawape.aimant.entities.Categoria;
import com.mawape.aimant.services.impl.CategoriasManagerImpl;
import com.mawape.aimant.widget.CategoriasArrayAdapter;

public class CategoriasAcivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias_list);
		init();
	}

	private void init() {
		final List<Categoria> opcionV = getCategorias();
		final CategoriasArrayAdapter adaptador = new CategoriasArrayAdapter(
				getApplicationContext(), opcionV);
		ListView lstOpciones = (ListView) findViewById(R.id.catListMenu);
		lstOpciones.setAdapter(adaptador);
		lstOpciones.setOnItemClickListener(adaptador);
		ImageView infoImg = (ImageView) findViewById(R.id.catMenuInformation);
		infoImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});

	}

	private void showDialog() {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		String title = getString(R.string.info_title);
		String message = getString(R.string.info_message);
		builder.setMessage(Html.fromHtml(message))
				.setTitle(Html.fromHtml(title))
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// do nothing at all
					}
				});

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private List<Categoria> getCategorias() {
		return new CategoriasManagerImpl(getApplicationContext())
				.getCategorias();
	}

}
