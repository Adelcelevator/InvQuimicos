package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Institucion extends Auditoria implements Serializable {
	private static final long serialVersionUID = -1638065153921018732L;
	private String ruc, telefono, dire, representante, pais, est, nombreC;

	public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDire() {
        return dire;
    }

    public void setDire(String dire) {
        this.dire = dire;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEst() {
        return est;
    }

    public void setEst(String est) {
        this.est = est;
    }

	public String getNombreC() {
		return nombreC;
	}

	public void setNombreC(String nombreC) {
		this.nombreC = nombreC;
	}

	public Institucion(int usu_id_UltMod, Date fecha_in, Date fecha_mod, String ruc, String telefono, String dire,
			String representante, String pais, String est, String nombreC) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.ruc = ruc;
		this.telefono = telefono;
		this.dire = dire;
		this.representante = representante;
		this.pais = pais;
		this.est = est;
		this.nombreC = nombreC;
	}

	public Institucion() {
		this(0,Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now()),"","","","","", "", "");
	}

}
