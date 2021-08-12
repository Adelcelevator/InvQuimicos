package com.objetos;

import com.utilitarios.UtilitarioControlador;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Compra extends Auditoria implements Serializable {

	private static final long serialVersionUID = 5764746501108114218L;
	private int com_id, com_numFac, pro_id;
	private double com_valorT, com_valorIm, com_subtotal;
	private Date com_fecha;
	private String com_est;

	public int getCom_id() {
		return com_id;
	}

	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}

	public int getCom_numFac() {
		return com_numFac;
	}

	public void setCom_numFac(int com_numFac) {
		this.com_numFac = com_numFac;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public double getCom_valorT() {
		return com_valorT;
	}

	public void setCom_valorT(double com_valorT) {
		this.com_valorT = com_valorT;
	}

	public double getCom_valorIm() {
		return com_valorIm;
	}

	public void setCom_valorIm(double com_valorIm) {
		this.com_valorIm = com_valorIm;
	}

	public Date getCom_fecha() {
		return com_fecha;
	}

	public void setCom_fecha(Date com_fecha) {
		this.com_fecha = com_fecha;
	}

	public Compra() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 0, 0, 0, 0, Date.valueOf(LocalDate.now()), "");
	}

	public String getCom_est() {
		return com_est;
	}

	public void setCom_est(String com_est) {
		this.com_est = com_est;
	}

	public double getCom_subtotal() {
		return com_subtotal;
	}

	public void setCom_subtotal(double com_subtotal) {
		this.com_subtotal = com_subtotal;
	}

	public Compra(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int com_id, int com_numFac, int pro_id,
			double com_valorT, double com_valorIm, Date com_fecha, String com_est) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.com_id = com_id;
		this.com_numFac = com_numFac;
		this.pro_id = pro_id;
		this.com_valorT = com_valorT;
		this.com_valorIm = com_valorIm;
		this.com_fecha = com_fecha;
		this.com_est = com_est;
		this.com_subtotal = UtilitarioControlador.dosDeci(this.com_valorT - this.com_valorIm);
	}

	@Override
	public String toString() {
		return "Compra{" + "com_id=" + com_id + ", com_numFac=" + com_numFac + ", pro_id=" + pro_id + ", com_valorT="
				+ com_valorT + ", com_valorIm=" + com_valorIm + ", com_fecha=" + com_fecha + '}';
	}

	public boolean hasEmptyFilds() {
		return this.equals(new Compra()) || this.com_numFac == 0 || this.pro_id == 0 || this.com_valorT == 0 || this.com_valorIm == 0;
	}
}
