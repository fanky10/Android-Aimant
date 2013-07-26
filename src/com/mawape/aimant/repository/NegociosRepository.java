package com.mawape.aimant.repository;

import java.util.List;

import com.mawape.aimant.entities.Negocio;

public interface NegociosRepository {
	public List<Negocio> getNegocios();
	public List<String> getCategoriasNegocio();
}
