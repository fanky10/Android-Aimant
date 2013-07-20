package com.mawape.aimant.repository.impl.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.mawape.aimant.R;
import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.repository.NegociosRepository;
import com.mawape.aimant.utilities.NegociosXmlParser;

public class NegociosRepositoryImpl implements NegociosRepository {
	private static final String TAG = "NegociosActivity";
	private Context context;

	public NegociosRepositoryImpl(Context context) {
		this.context = context;
	}

	@Override
	public List<Negocio> getNegocios() {
		List<Negocio> result = new ArrayList<Negocio>();
		InputStream stream = null;
		NegociosXmlParser parser = new NegociosXmlParser();
		try {
			// TODO: get stream
			final Resources resources = getContext().getResources();
			stream = resources.openRawResource(R.raw.negocios);
			result = parser.parseContent(stream);
			Log.d(TAG, "success parsing content!");
		} catch (IOException ex) {
			Log.d(TAG, "ioException parsing content: " + ex.getMessage());
		} catch (XmlPullParserException ex) {
			Log.d(TAG, "ioException parsing content: " + ex.getMessage());
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException ex) {
				// do nothing we dont care
			}
		}
		return result;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
