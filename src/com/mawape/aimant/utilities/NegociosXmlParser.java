package com.mawape.aimant.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.mawape.aimant.entities.Negocio;

public class NegociosXmlParser extends AbstractXMLParser {
	// We don't use namespaces
	private static final String NEGOCIO_TAG = "negocio";
	private static final String NEGOCIO_NOMBRE_TAG = "nombre";
	private static final String NEGOCIO_DIRECCION_TAG = "direccion";
	private static final String NEGOCIO_IMG_PATH_TAG = "imgPath";

	@Override
	protected List getDataFromXML(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<Negocio> entries = new ArrayList<Negocio>();

		parser.require(XmlPullParser.START_TAG, NAMESPACE, FEED_TAG);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(NEGOCIO_TAG)) {
				entries.add(readNegocio(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	// Parses the contents of an entry. If it encounters a title, summary, or
	// link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the
	// tag.
	private Negocio readNegocio(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, NAMESPACE, NEGOCIO_TAG);
		String nombre = null;
		String direccion = null;
		String imgPath = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(NEGOCIO_NOMBRE_TAG)) {
				nombre = readTag(parser, NEGOCIO_NOMBRE_TAG);
			} else if (name.equals(NEGOCIO_DIRECCION_TAG)) {
				direccion = readTag(parser, NEGOCIO_DIRECCION_TAG);
			} else if (name.equals(NEGOCIO_IMG_PATH_TAG)) {
				imgPath = readTag(parser, NEGOCIO_IMG_PATH_TAG);
			} else {
				skip(parser);
			}
		}
		return new Negocio(nombre, direccion, imgPath);
	}

}
