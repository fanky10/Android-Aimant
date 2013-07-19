package com.mawape.aimant.entities;

public class Negocio {
	private String nombre;
	private String direccion;
	
	public Negocio(String nombre,String direccion){
		this.nombre = nombre;
		this.direccion = direccion;
	}
	
	public Negocio(){
		this("","");
	}
	
	public String toString(){
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
}
