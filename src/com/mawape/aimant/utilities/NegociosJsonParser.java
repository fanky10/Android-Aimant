package com.mawape.aimant.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mawape.aimant.entities.Negocio;

public class NegociosJsonParser extends AbstractJsonParser<Negocio> {

	public List<Negocio> parse(String jsonString, String key)
			throws JSONException {
		List<Negocio> resultList = new ArrayList<Negocio>();
		JSONObject jsonObject = (JSONObject) new JSONTokener(jsonString)
				.nextValue();
		JSONArray jsonArray = jsonObject.getJSONArray(key);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonItem = jsonArray.getJSONObject(i);
			resultList.add(createObject(jsonItem));
		}
		return resultList;
	}

	@Override
	protected Negocio createObject(JSONObject jsonItem) throws JSONException {
		String nombre = getString(jsonItem, "nombre");
		String direccion = getString(jsonItem, "direccion");
		String imgPath = getString(jsonItem, "image");
		String categoria = getString(jsonItem, "categoria");
		String telOne = getString(jsonItem, "telefono1");
		String telTwo = getString(jsonItem, "telefono2");
		String horario = getString(jsonItem, "horario");
		String web = getString(jsonItem, "web");
		String facebook = getString(jsonItem, "facebook");
		String email = getString(jsonItem, "email");
		return new Negocio(nombre, direccion, imgPath, categoria,telOne,telTwo,horario,web,facebook,email);
	}

}