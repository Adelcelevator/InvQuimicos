package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Auditoria implements Serializable {
	private static final long serialVersionUID = 4668142019277208939L;
	private int usu_id_UltMod;
    private Date fecha_in, fecha_mod;
    
    public int getUsu_id_UltMod() {
        return usu_id_UltMod;
    }

    public void setUsu_id_UltMod(int usu_id_UltMod) {
        this.usu_id_UltMod = usu_id_UltMod;
    }

    public Date getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(Date fecha_in) {
        this.fecha_in = fecha_in;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

	public Auditoria(int usu_id_UltMod, Date fecha_in, Date fecha_mod) {
		this.usu_id_UltMod = usu_id_UltMod;
		this.fecha_in = fecha_in;
		this.fecha_mod = fecha_mod;
	}

	public Auditoria() {
		this(0,Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now()));
	}
}
