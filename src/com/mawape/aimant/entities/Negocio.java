package com.mawape.aimant.entities;

import java.io.Serializable;

public class Negocio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -94230443911200775L;
	
	private String nombre;
	private String direccion;
	private String imgPath;
	private String categoria;
	private String telefono;

	public Negocio(String nombre, String direccion, String imgPath) {
		this(nombre, direccion, imgPath, "");
	}

	public Negocio(String nombre, String direccion) {
		this(nombre, direccion, null);
	}

	public Negocio() {
		this("", "");
	}

	public Negocio(String nombre, String direccion, String imgPath,
			String categoria) {
		this(nombre,direccion,imgPath,categoria,"");
	}

	public Negocio(String nombre, String direccion, String imgPath,
			String categoria, String telefono) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.imgPath = imgPath;
		this.categoria = categoria;
		this.telefono = telefono;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
