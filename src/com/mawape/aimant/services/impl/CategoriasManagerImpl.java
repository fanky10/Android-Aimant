package com.mawape.aimant.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.mawape.aimant.R;
import com.mawape.aimant.constants.AppConstants;
import com.mawape.aimant.entities.Categoria;
import com.mawape.aimant.services.CategoriasManager;
import com.mawape.aimant.utilities.CategoriasJsonParser;

public class CategoriasManagerImpl implements CategoriasManager {
	private Context context;

	public CategoriasManagerImpl(Context context) {
		this.context = context;
	}

	@Override
	public List<Categoria> getCategorias() {
		List<Categoria> resultList = new ArrayList<Categoria>();
		try {
			InputStream is = getContext().getResources().openRawResource(
					R.raw.model_objects);
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			String jsonString = writer.toString();
			resultList = CategoriasJsonParser.parse(jsonString, AppConstants.JSON_CATEGORIAS_KEY_LIST);
		} catch (JSONException ex) {
			Log.d(CategoriasManagerImpl.class.getName(),
					"exception parsing json:" + ex.getMessage());
		} catch (IOException ex) {
			// TODO: handle exception
		}
		return resultList;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
