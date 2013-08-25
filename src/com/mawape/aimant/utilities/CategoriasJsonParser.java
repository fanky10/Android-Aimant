package com.mawape.aimant.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mawape.aimant.entities.Categoria;

public class CategoriasJsonParser {

	public static List<Categoria> parse(String jsonString, String key)
			throws JSONException {
		List<Categoria> resultList = new ArrayList<Categoria>();
		JSONObject jsonObject = (JSONObject) new JSONTokener(jsonString)
				.nextValue();
		JSONArray jsonArray = jsonObject.getJSONArray(key);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonItem = jsonArray.getJSONObject(i);
			resultList.add(createObject(jsonItem));
		}
		return resultList;
	}

	private static Categoria createObject(JSONObject jsonItem)
			throws JSONException {
		String nombre = jsonItem.getString("nombre");
		String color = jsonItem.getString("color");
		String imgPath = jsonItem.getString("imgPath");
		return new Categoria(nombre, color, imgPath);
	}

}
