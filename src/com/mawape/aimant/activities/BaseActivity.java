package com.mawape.aimant.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawape.aimant.R;
import com.mawape.aimant.entities.Categoria;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	protected void configureMenuBar(Categoria categoria, boolean canGoBack) {
		if (categoria != null) {// configure color and title
			TextView txtTitle = (TextView) findViewById(R.id.commonMenuTitle);
			txtTitle.setText(categoria.getNombre());
			
			View view = findViewById(R.id.menu_bar);
			view.setBackgroundColor(Color.parseColor("#" + categoria.getColor()));
		}

		// handling menu info click
		ImageView infoImg = (ImageView) findViewById(R.id.commonMenuInformation);
		infoImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}

	protected void configureMenuBar() {
		configureMenuBar(null, false);
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
}
