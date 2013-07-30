package com.mawape.aimant.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.mawape.aimant.entities.Negocio;

public class NegociosXmlParser extends AbstractXmlParser {
	private static final String DEBUG_TAG = "fanky - "
			+ NegociosXmlParser.class.getName();
	private static final String ITEM_TAG = "negocio";
	private static final String[] ATTRIBUTES_NAME = new String[] { "negocio",
			"nombre", "direccion", "imgPath", "categoria","telefono"};

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
			if (name.equals(ITEM_TAG)) {
				try {
					entries.add(readNegocio(parser));
				} catch (NoSuchFieldException e) {
					Log.d(DEBUG_TAG,
							"bad field naming, root cause: " + e.getMessage());
				} catch (IllegalAccessException e) {
					Log.d(DEBUG_TAG, "illegal accessing field, root cause: "
							+ e.getMessage());
				}
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
			throws XmlPullParserException, IOException, NoSuchFieldException,
			IllegalAccessException {
		parser.require(XmlPullParser.START_TAG, NAMESPACE, ITEM_TAG);

		Negocio negocio = new Negocio();
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tagName = parser.getName();
			String tagValue = null;
			boolean found = false;
			for (String att : ATTRIBUTES_NAME) {
				if (tagName.equals(att)) {
					tagValue = readTag(parser, att);
					setData(negocio, att, tagValue);
					found = true;
					break;
				}
			}
			if (!found) {
				skip(parser);
			}

		}
		return negocio;
	}
}
