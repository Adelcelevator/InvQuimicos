package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Usuario implements Serializable {
	private static final long serialVersionUID = -2073121264597476990L;
	private int usu_id, tus_id;
	private String usu_usuario, usu_contra, usu_nombre, usu_apellido, usu_telefono, usu_direccion, usu_estado;
	private Date fecha_in, fecha_mod;
	public Usuario(int usu_id, 
			int tus_id, 
			String usu_usuario, 
			String usu_contra, 
			String usu_nombre,
			String usu_apellido, 
			String usu_telefono, 
			String usu_direccion, 
			String usu_estado, 
			Date fecha_in,
			Date fecha_mod) {
		this.usu_id = usu_id;
		this.tus_id = tus_id;
		this.usu_usuario = usu_usuario;
		this.usu_contra = usu_contra;
		this.usu_nombre = usu_nombre;
		this.usu_apellido = usu_apellido;
		this.usu_telefono = usu_telefono;
		this.usu_direccion = usu_direccion;
		this.usu_estado = usu_estado;
		this.fecha_in = fecha_in;
		this.fecha_mod = fecha_mod;
	}

	public Usuario() {
		this(0, 0, "", "", "", "", "", "", "", Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now()));
	}

	public int getUsu_id() {
		return usu_id;
	}

	public void setUsu_id(int usu_id) {
		this.usu_id = usu_id;
	}

	public int getTus_id() {
		return tus_id;
	}

	public void setTus_id(int tus_id) {
		this.tus_id = tus_id;
	}

	public String getUsu_usuario() {
		return usu_usuario;
	}

	public void setUsu_usuario(String usu_usuario) {
		this.usu_usuario = usu_usuario;
	}

	public String getUsu_contra() {
		return usu_contra;
	}

	public void setUsu_contra(String usu_contra) {
		this.usu_contra = usu_contra;
	}

	public String getUsu_nombre() {
		return usu_nombre;
	}

	public void setUsu_nombre(String usu_nombre) {
		this.usu_nombre = usu_nombre;
	}

	public String getUsu_apellido() {
		return usu_apellido;
	}

	public void setUsu_apellido(String usu_apellido) {
		this.usu_apellido = usu_apellido;
	}

	public String getUsu_telefono() {
		return usu_telefono;
	}

	public void setUsu_telefono(String usu_telefono) {
		this.usu_telefono = usu_telefono;
	}

	public String getUsu_direccion() {
		return usu_direccion;
	}

	public void setUsu_direccion(String usu_direccion) {
		this.usu_direccion = usu_direccion;
	}

	public String getUsu_estado() {
		return usu_estado;
	}

	public void setUsu_estado(String usu_estado) {
		this.usu_estado = usu_estado;
	}

	@Override
	public String toString() {
		return "Usuario{" + "usu_id=" + usu_id + ", tus_id=" + tus_id + ", usu_usuario=" + usu_usuario + ", usu_contra="
				+ usu_contra + ", usu_nombre=" + usu_nombre + ", usu_apellido=" + usu_apellido + ", usu_telefono="
				+ usu_telefono + ", usu_direccion=" + usu_direccion + ", usu_estado=" + usu_estado + ", fecha_in="
				+ fecha_in + ", fecha_mod=" + fecha_mod + '}';
	}

}
