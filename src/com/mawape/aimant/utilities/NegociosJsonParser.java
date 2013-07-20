package com.mawape.aimant.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.mawape.aimant.entities.Negocio;

public class NegociosJsonParser {
	public List<Negocio> readJsonStream(InputStream in) throws IOException {
		throw new NoSuchMethodError(
				"Not supported on min API-8 should start on API-11");
		// JsonReader reader = new JsonReader(new InputStreamReader(in,
		// "UTF-8"));
		// try {
		// return readMessagesArray(reader);
		// } finally {
		// reader.close();
		// }
	}

	// public List<Negocio> readMessagesArray(JsonReader reader) throws
	// IOException {
	// List<Negocio> negocios = new ArrayList<Negocio>();
	//
	// reader.beginArray();
	// while (reader.hasNext()) {
	// messages.add(readMessage(reader));
	// }
	// reader.endArray();
	// return negocios;
	// }

	// public Negocio readNegocio(JsonReader reader) throws IOException {
	// long id = -1;
	// String nombre = null;
	// String direccion = null;
	// reader.beginObject();
	// while (reader.hasNext()) {
	// String name = reader.nextName();
	// if (name.equals("id")) {
	// id = reader.nextLong();
	// } else if (name.equals("nombre")) {
	// nombre = reader.nextString();
	// } else if (name.equals("direccion")) {
	// nombre = reader.nextString();
	// } else {
	// reader.skipValue();
	// }
	// }
	// reader.endObject();
	// return new Negocio(nombre,direccion);
	// }
}