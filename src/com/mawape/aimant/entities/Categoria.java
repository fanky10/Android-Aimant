package com.mawape.aimant.entities;

import java.io.Serializable;

public class Categoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725580950249437692L;

	private String nombre;
	// hexadecimal
	private String color;
	private String imgPath;
	private String descripcion;

	public Categoria(String nombre, String color, String imgPath) {
		this(nombre, color, imgPath, "");
	}

	public Categoria(String nombre, String color, String imgPath,
			String descripcion) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.imgPath = imgPath;
		this.descripcion = descripcion;
	}

	public String toString() {
		return nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
