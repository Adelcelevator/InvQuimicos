package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Inventario extends Auditoria implements Serializable {
	private static final long serialVersionUID = -3511856008205396656L;
	private int inv_id, inv_cantidad, qui_id, inv_desc;
	private double inv_precioCU, inv_precioUI;

	public int getInv_id() {
		return inv_id;
	}

	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}

	public int getInv_cantidad() {
		return inv_cantidad;
	}

	public void setInv_cantidad(int inv_cantidad) {
		this.inv_cantidad = inv_cantidad;
	}

	public int getQui_id() {
		return qui_id;
	}

	public void setQui_id(int qui_id) {
		this.qui_id = qui_id;
	}

	public int getInv_desc() {
		return inv_desc;
	}

	public void setInv_desc(int inv_desc) {
		this.inv_desc = inv_desc;
	}

	public double getInv_precioCU() {
		return inv_precioCU;
	}

	public void setInv_precioCU(double inv_precioCU) {
		this.inv_precioCU = inv_precioCU;
	}

	public double getInv_precioUI() {
		return inv_precioUI;
	}

	public void setInv_precioUI(double inv_precioUI) {
		this.inv_precioUI = inv_precioUI;
	}

	public Inventario(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int inv_id, int inv_cantidad, int qui_id,
			int inv_desc, double inv_precioCU, double inv_precioUI) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.inv_id = inv_id;
		this.inv_cantidad = inv_cantidad;
		this.qui_id = qui_id;
		this.inv_desc = inv_desc;
		this.inv_precioCU = inv_precioCU;
		this.inv_precioUI = inv_precioUI;
	}

	public Inventario() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 0, 0, 0, 0, 0);
	}

	@Override
	public String toString() {
		return "Inventario{" + "inv_id=" + inv_id + ", inv_cantidad=" + inv_cantidad + ", qui_id=" + qui_id + ", inv_desc=" + inv_desc + ", inv_precioCU=" + inv_precioCU + ", inv_precioUI=" + inv_precioUI + '}';
	}

}
