package com.mawape.aimant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.mawape.aimant.R;

public class CategoriasAcivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categorias_acivity, menu);
		return true;
	}

}
