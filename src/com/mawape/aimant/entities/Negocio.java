package com.mawape.aimant.entities;

public class Negocio {
	private String nombre;
	private String direccion;
	private String imgPath;
	private String categoria;

	public Negocio(String nombre, String direccion, String imgPath) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.imgPath = imgPath;
	}

	public Negocio(String nombre, String direccion) {
		this(nombre, direccion, null);
	}

	public Negocio() {
		this("", "");
	}

	public String toString() {
		return nombre + " - " + direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
