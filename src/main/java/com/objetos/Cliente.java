package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Cliente extends Institucion implements Serializable {
	private static final long serialVersionUID = 4938491271287662877L;
	private int cli_id;

	public int getCli_id() {
		return this.cli_id;
	}

	public void setCli_id(int cli_id) {
		this.cli_id = cli_id;
	}

	public Cliente(int usu_id_UltMod, Date fecha_in, Date fecha_mod, String ruc, String telefono, String dire,
			String representante, String pais, String est, String nombreC, int cli_id) {
		super(usu_id_UltMod, fecha_in, fecha_mod, ruc, telefono, dire, representante, pais, est, nombreC);
		this.cli_id = cli_id;
	}

	public Cliente() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "", "", "", "", "", "", "", 0);
	}

	@Override
	public String toString() {
		return "Cliente [cli_id=" + cli_id + ", getRuc()=" + getRuc() + ", getTelefono()=" + getTelefono()
				+ ", getDire()=" + getDire() + ", getRepresentante()=" + getRepresentante() + ", getPais()=" + getPais()
				+ ", getEst()=" + getEst() + ", getNombreC()=" + getNombreC() + "]";
	}

}
