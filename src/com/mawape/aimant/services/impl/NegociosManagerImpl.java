package com.mawape.aimant.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.repository.NegociosRepository;
import com.mawape.aimant.repository.impl.xml.NegociosRepositoryImpl;
import com.mawape.aimant.services.NegociosManager;

public class NegociosManagerImpl implements NegociosManager {
	private NegociosRepository negociosRepository;

	public NegociosManagerImpl(Context context) {
		this.negociosRepository = new NegociosRepositoryImpl(context);
	}

	@Override
	public List<Negocio> getNegocios() {
		List<Negocio> negocios = getNegociosRepository().getNegocios();
		Collections.sort(negocios, new Comparator<Negocio>() {

			@Override
			public int compare(Negocio lhs, Negocio rhs) {
				return lhs.getNombre().compareTo(rhs.getNombre());
			}

		});
		return negocios;
	}
	
	public List<String> getCategorias(){
		List<String> categorias = getNegociosRepository().getCategoriasNegocio();
		Collections.sort(categorias,new Comparator<String>() {

			@Override
			public int compare(String lhs, String rhs) {
				return lhs.compareTo(rhs);
			}
			
		});
		return categorias;
	}

	public NegociosRepository getNegociosRepository() {
		return negociosRepository;
	}

	public void setNegociosRepository(NegociosRepository negociosRepository) {
		this.negociosRepository = negociosRepository;
	}

}
