package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class UsoQuimico extends Auditoria implements Serializable {
	private static final long serialVersionUID = -2227465928149414757L;
	private int tuso_id, qui_id;

	public int getTuso_id() {
		return tuso_id;
	}

	public void setTuso_id(int tuso_id) {
		this.tuso_id = tuso_id;
	}

	public int getQui_id() {
		return qui_id;
	}

	public void setQui_id(int qui_id) {
		this.qui_id = qui_id;
	}

	public UsoQuimico(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int tuso_id, int qui_id) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.tuso_id = tuso_id;
		this.qui_id = qui_id;
	}

	public UsoQuimico() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 0);
	}

	@Override
	public String toString() {
		return "UsoQuimico{" + "tuso_id=" + tuso_id + ", qui_id=" + qui_id + '}';
	}

}
