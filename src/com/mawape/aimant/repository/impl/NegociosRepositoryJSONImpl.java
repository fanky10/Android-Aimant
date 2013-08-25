package com.mawape.aimant.repository.impl;

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
import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.repository.NegociosRepository;
import com.mawape.aimant.services.impl.CategoriasManagerImpl;
import com.mawape.aimant.utilities.CategoriasJsonParser;
import com.mawape.aimant.utilities.NegociosJsonParser;

public class NegociosRepositoryJSONImpl implements NegociosRepository {
	private static final String TAG = NegociosRepositoryJSONImpl.class
			.getName();
	private Context context;

	public NegociosRepositoryJSONImpl(Context context) {
		this.context = context;
	}

	@Override
	public List<Negocio> getNegocios() {
		List<Negocio> resultList = new ArrayList<Negocio>();
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
			resultList = NegociosJsonParser.parse(jsonString,
					AppConstants.JSON_NEGOCIOS_KEY_LIST);
		} catch (JSONException ex) {
			Log.d(this.getClass().getName(),
					"exception parsing json:" + ex.getMessage());
		} catch (IOException ex) {
			// TODO: handle exception
			Log.d(this.getClass().getName(),
					"exception reading json:" + ex.getMessage());
		}
		return resultList;
	}

	@Override
	public List<String> getCategoriasNegocio() {
		List<String> categorias = new ArrayList<String>();
		for (Negocio negocio : getNegocios()) {
			String categoria = negocio.getCategoria();
			if (categoria != null && !categorias.contains(categoria)) {
				categorias.add(categoria);
			}
		}
		return categorias;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
