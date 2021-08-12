package com.objetos;

import com.utilitarios.UtilitarioControlador;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Venta extends Auditoria implements Serializable {

	private static final long serialVersionUID = 381977999193011730L;
	private int ven_id, ven_numFac, cli_id;
	private double ven_valorT, ven_valorIm, ven_descuento, ven_subtotal;
	private String ven_estado;
	private Date ven_fecha;

	public Venta(int ven_id, int ven_numFac, int cli_id, double ven_valorT, double ven_valorIm, double ven_descuento, String ven_estado, Date ven_fecha, Date fecha_in, Date fecha_mod, int usu_id_UltMod) {
		this.ven_id = ven_id;
		this.ven_numFac = ven_numFac;
		this.cli_id = cli_id;
		this.ven_valorT = ven_valorT;
		this.ven_valorIm = ven_valorIm;
		this.ven_descuento = ven_descuento;
		this.ven_estado = ven_estado;
		this.ven_fecha = ven_fecha;
		this.setFecha_in(fecha_in);
		this.setFecha_mod(fecha_mod);
		this.setUsu_id_UltMod(usu_id_UltMod);
		this.ven_subtotal = UtilitarioControlador.dosDeci(this.ven_valorT - this.ven_valorIm);
	}

	public Venta() {
		this(0, 0, 0, 0, 0, 0, "", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0);
	}

	public int getVen_id() {
		return ven_id;
	}

	public void setVen_id(int ven_id) {
		this.ven_id = ven_id;
	}

	public int getVen_numFac() {
		return ven_numFac;
	}

	public void setVen_numFac(int ven_numFac) {
		this.ven_numFac = ven_numFac;
	}

	public int getCli_id() {
		return cli_id;
	}

	public void setCli_id(int cli_id) {
		this.cli_id = cli_id;
	}

	public double getVen_valorT() {
		return ven_valorT;
	}

	public void setVen_valorT(double ven_valorT) {
		this.ven_valorT = ven_valorT;
	}

	public double getVen_valorIm() {
		return ven_valorIm;
	}

	public void setVen_valorIm(double ven_valorIm) {
		this.ven_valorIm = ven_valorIm;
	}

	public double getVen_descuento() {
		return ven_descuento;
	}

	public void setVen_descuento(double ven_descuento) {
		this.ven_descuento = ven_descuento;
	}

	public String getVen_estado() {
		return ven_estado;
	}

	public void setVen_estado(String ven_estado) {
		this.ven_estado = ven_estado;
	}

	public Date getVen_fecha() {
		return ven_fecha;
	}

	public void setVen_fecha(Date ven_fecha) {
		this.ven_fecha = ven_fecha;
	}

	public double getVen_subtotal() {
		return ven_subtotal;
	}

	public void setVen_subtotal(double ven_subtotal) {
		this.ven_subtotal = ven_subtotal;
	}

	@Override
	public String toString() {
		return "Venta{" + "ven_id=" + ven_id + ", ven_numFac=" + ven_numFac + ", cli_id=" + cli_id + ", ven_valorT=" + ven_valorT + ", ven_valorIm=" + ven_valorIm + ", ven_descuento=" + ven_descuento + ", ven_estado=" + ven_estado + ", ven_fecha=" + ven_fecha + '}';
	}

	public boolean hasEmptyFilds() {
		return this.equals(new Venta()) || ven_numFac == 0 || cli_id == 0 || ven_valorT == 0 || this.ven_valorIm == 0;
	}
}
