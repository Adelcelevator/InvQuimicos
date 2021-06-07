package com.objetos;

import java.io.Serializable;

public class TipoUsuario implements Serializable {
	private static final long serialVersionUID = 6948832361132267408L;
	private int tus_id;
	private String tus_tipo, tus_est;

	public TipoUsuario() {
	}

	public TipoUsuario(int tus_id, String tus_tipo, String tus_est) {
		this.tus_id = tus_id;
		this.tus_tipo = tus_tipo;
		this.tus_est = tus_est;
	}

	public int getTus_id() {
		return tus_id;
	}

	public void setTus_id(int tus_id) {
		this.tus_id = tus_id;
	}

	public String getTus_tipo() {
		return tus_tipo;
	}

	public void setTus_tipo(String tus_tipo) {
		this.tus_tipo = tus_tipo;
	}

	public String getTus_est() {
		return tus_est;
	}

	public void setTus_est(String tus_est) {
		this.tus_est = tus_est;
	}

	@Override
	public String toString() {
		return "TipoUsuario{" + "tus_id=" + tus_id + ", tus_tipo=" + tus_tipo + ", tus_est=" + tus_est + '}';
	}

}
