package com.mawape.aimant.repository.impl.mocked;

import java.util.ArrayList;
import java.util.List;

import com.mawape.aimant.entities.Negocio;
import com.mawape.aimant.repository.NegociosRepository;

public class NegociosRepositoryImpl implements NegociosRepository{

	@Override
	public List<Negocio> getNegocios() {
		return new ArrayList<Negocio>() {
			{
				add(new Negocio("Queens", "Rioja e Italia"));
				add(new Negocio("Un bar","una locacion"));
			}
		};
	}
	
}
