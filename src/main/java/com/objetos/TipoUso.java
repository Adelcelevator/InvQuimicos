package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class TipoUso extends Auditoria implements Serializable {
	private static final long serialVersionUID = 6054282659374353011L;
	private int tuso_id;
	private String tuso_uso, tuso_est;

	public int getTuso_id() {
		return tuso_id;
	}

	public void setTuso_id(int tuso_id) {
		this.tuso_id = tuso_id;
	}

	public String getTuso_uso() {
		return tuso_uso;
	}

	public void setTuso_uso(String tuso_uso) {
		this.tuso_uso = tuso_uso;
	}

	public String getTuso_est() {
		return tuso_est;
	}

	public void setTuso_est(String tuso_est) {
		this.tuso_est = tuso_est;
	}

	public TipoUso(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int tuso_id, String tuso_uso, String tuso_est) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.tuso_id = tuso_id;
		this.tuso_uso = tuso_uso;
		this.tuso_est = tuso_est;
	}

	public TipoUso() {
		this(0,Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now()),0,"","");
	}

	@Override
	public String toString() {
		return "TipoUso{" + "tuso_id=" + tuso_id + ", tuso_uso=" + tuso_uso + ", tuso_est=" + tuso_est + '}';
	}

}
