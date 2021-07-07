package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Proveedor extends Institucion implements Serializable {

	private static final long serialVersionUID = 3394518416656863785L;
	private int pro_id;

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public Proveedor(int usu_id_UltMod, Date fecha_in, Date fecha_mod, String ruc, String telefono, String dire,
			String representante, String pais, String est, String nombreC, int pro_id) {
		super(usu_id_UltMod, fecha_in, fecha_mod, ruc, telefono, dire, representante, pais, est, nombreC);
		this.pro_id = pro_id;
	}

	public Proveedor() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "", "", "", "", "", "", "", 0);
	}

	@Override
	public String toString() {
		return "Proveedor [pro_id=" + pro_id + ", getRuc()=" + getRuc() + ", getTelefono()=" + getTelefono()
				+ ", getDire()=" + getDire() + ", getRepresentante()=" + getRepresentante() + ", getPais()=" + getPais()
				+ ", getEst()=" + getEst() + ", getNombreC()=" + getNombreC() + "]";
	}
}
