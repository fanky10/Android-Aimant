package com.mawape.aimant.services.impl;

import java.util.List;

import android.content.Context;

import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.repository.NegociosRepository;
import com.mawape.aimant.repository.impl.mocked.NegociosRepositoryImpl;
import com.mawape.aimant.services.NegociosManager;

public class NegociosManagerImpl implements NegociosManager {
	private NegociosRepository negociosRepository;
	public NegociosManagerImpl(Context context) {
		this.negociosRepository = new NegociosRepositoryImpl();
	}

	@Override
	public List<Negocio> getNegocios() {
		return getNegociosRepository().getNegocios();
	}

	public NegociosRepository getNegociosRepository() {
		return negociosRepository;
	}

	public void setNegociosRepository(NegociosRepository negociosRepository) {
		this.negociosRepository = negociosRepository;
	}

}
