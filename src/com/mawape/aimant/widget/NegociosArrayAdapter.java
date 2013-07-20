package com.mawape.aimant.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mawape.aimant.R;
import com.mawape.aimant.entities.Negocio;

public class NegociosArrayAdapter extends ArrayAdapter<Negocio> {
	  private final Context context;
	private final List<Negocio> values;

	  public NegociosArrayAdapter(Context context, List<Negocio> values) {
	    super(context, R.layout.negocios_row, values);
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.negocios_row, parent, false);
//	    TextView textView = (TextView) rowView.findViewById(R.id.label);
//	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//	    textView.setText(values[position]);
//	    // Change the icon for Windows and iPhone
//	    String s = values[position];
//	    if (s.startsWith("iPhone")) {
//	      imageView.setImageResource(R.drawable.no);
//	    } else {
//	      imageView.setImageResource(R.drawable.ok);
//	    }

	    return rowView;
	  }
	} 