package com.mawape.aimant.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mawape.aimant.entities.Negocio;

public class NegociosJsonParser {

	public static List<Negocio> parse(String jsonString, String key)
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

	private static Negocio createObject(JSONObject jsonItem)
			throws JSONException {
		String nombre = getString(jsonItem,"nombre");
		String direccion = getString(jsonItem,"direccion");
		String imgPath = getString(jsonItem,"imgPath");
		String categoria = getString(jsonItem,"categoria");
		return new Negocio(nombre, direccion, imgPath, categoria);
	}
	
	private static String getString(JSONObject jsonItem, String key) throws JSONException{
		if(!jsonItem.has(key) || jsonItem.isNull(key)){
			return null;
		}else {
			return jsonItem.getString(key);
		}
	}

}