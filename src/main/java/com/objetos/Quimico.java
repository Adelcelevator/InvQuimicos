package com.objetos;

import java.io.Serializable;
import java.sql.Date;

public class Quimico extends Auditoria implements Serializable {
	private static final long serialVersionUID = 2260815277515589895L;
	private int qui_id;
	private String qui_quimico, qui_CPC, qui_estado,qui_nombreC;

	public int getQui_id() {
		return qui_id;
	}

	public void setQui_id(int qui_id) {
		this.qui_id = qui_id;
	}

	public String getQui_quimico() {
		return qui_quimico;
	}

	public void setQui_quimico(String qui_quimico) {
		this.qui_quimico = qui_quimico;
	}

	public String getQui_CPC() {
		return qui_CPC;
	}

	public void setQui_CPC(String qui_CPC) {
		this.qui_CPC = qui_CPC;
	}

	public String getQui_estado() {
		return qui_estado;
	}

	public void setQui_estado(String qui_estado) {
		this.qui_estado = qui_estado;
	}

	public Quimico() {
	}

	public String getQui_nombreC() {
		return qui_nombreC;
	}

	public void setQui_nombreC(String qui_nombreC) {
		this.qui_nombreC = qui_nombreC;
	}

	public Quimico(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int qui_id, String qui_quimico, String qui_CPC,
			String qui_estado, String qui_nombreC) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.qui_id = qui_id;
		this.qui_quimico = qui_quimico;
		this.qui_CPC = qui_CPC;
		this.qui_estado = qui_estado;
		this.qui_nombreC = qui_nombreC;
	}

	@Override
	public String toString() {
		return "Quimico{" + "qui_id=" + qui_id + ", qui_quimico=" + qui_quimico + ", qui_CPC=" + qui_CPC
				+ ", qui_estado=" + qui_estado + '}';
	}

}
