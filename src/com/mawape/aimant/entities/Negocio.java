package com.mawape.aimant.entities;

import java.io.Serializable;

public class Negocio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -94230443911200775L;

	private String nombre;
	private String direccion;
	private String imgPath;
	private String categoria;
	private String telefonoPrimario;
	private String telefonoSecundario;
	private String horario;
	private String web;
	private String facebook;
	private String email;

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
		this(nombre, direccion, imgPath, categoria, "");
	}
	
	public Negocio(String nombre, String direccion, String imgPath,
			String categoria, String telefono) {
		this(nombre,direccion,imgPath,categoria,telefono,null,null,null,null,null);
	}
	
	

	public Negocio(String nombre, String direccion, String imgPath,
			String categoria, String telefonoPrimario,
			String telefonoSecundario, String horario, String web,
			String facebook, String email) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.imgPath = imgPath;
		this.categoria = categoria;
		this.telefonoPrimario = telefonoPrimario;
		this.telefonoSecundario = telefonoSecundario;
		this.horario = horario;
		this.web = web;
		this.facebook = facebook;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Negocio [nombre=" + nombre + ", direccion=" + direccion
				+ ", imgPath=" + imgPath + ", categoria=" + categoria
				+ ", telefonoPrimario=" + telefonoPrimario
				+ ", telefonoSecundario=" + telefonoSecundario + ", horario="
				+ horario + ", web=" + web + ", facebook=" + facebook
				+ ", email=" + email + "]";
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

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonoPrimario() {
		return telefonoPrimario;
	}

	public void setTelefonoPrimario(String telefonoPrimario) {
		this.telefonoPrimario = telefonoPrimario;
	}

	public String getTelefonoSecundario() {
		return telefonoSecundario;
	}

	public void setTelefonoSecundario(String telefonoSecundario) {
		this.telefonoSecundario = telefonoSecundario;
	}
}
