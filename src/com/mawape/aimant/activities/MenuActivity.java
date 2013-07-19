package com.mawape.aimant.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mawape.aimant.R;

public class MenuActivity extends BaseActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_list);

		ListView menuList = (ListView) findViewById(R.id.ListView_Menu);

		String[] items = { getResources().getString(R.string.bar_opc1),
				getResources().getString(R.string.bar_opc2),
				getResources().getString(R.string.bar_opc3), "Exit" };

		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		menuList.setAdapter(adapt);

		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View itemClicked,
					int position, long id) {

				// Note: if the list was built "by hand" the id could be used.
				// As-is, though, each item has the same id
				TextView textView = (TextView) itemClicked;
				String strText = textView.getText().toString();

				if (strText.equalsIgnoreCase("Exit")) {
					// Launch the Scores Activity
					System.exit(0);
				}

			}
		});

	}
}
