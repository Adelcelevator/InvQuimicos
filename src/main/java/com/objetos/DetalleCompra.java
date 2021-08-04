package com.objetos;

import java.io.Serializable;

public class DetalleCompra implements Serializable {

	private static final long serialVersionUID = 3240269729095480048L;
	private int com_id, inv_id, detalle_cantidad;
	private double detalle_valorTc, detalle_valorU, auxpv;

	public int getCom_id() {
		return com_id;
	}

	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}

	public int getInv_id() {
		return inv_id;
	}

	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}

	public int getDetalle_cantidad() {
		return detalle_cantidad;
	}

	public void setDetalle_cantidad(int detalle_cantidad) {
		this.detalle_cantidad = detalle_cantidad;
	}

	public double getDetalle_valorTc() {
		return detalle_valorTc;
	}

	public void setDetalle_valorTc(double detalle_valorTC) {
		this.detalle_valorTc = detalle_valorTC;
	}

	public double getDetalle_valorU() {
		return detalle_valorU;
	}

	public void setDetalle_valorU(double detalle_valorU) {
		this.detalle_valorU = detalle_valorU;
	}

	public double getAuxpv() {
		return auxpv;
	}

	public void setAuxpv(double auxpv) {
		this.auxpv = auxpv;
	}

	public DetalleCompra(int com_id, int inv_id, int detalle_cantidad, double detalle_valorTC, double detalle_valorU, double auxpv) {
		this(com_id,inv_id,detalle_cantidad,detalle_valorTC,detalle_valorU);
		this.auxpv = auxpv;
	}
	
	public DetalleCompra(int com_id, int inv_id, int detalle_cantidad, double detalle_valorTC, double detalle_valorU) {
		this.com_id = com_id;
		this.inv_id = inv_id;
		this.detalle_cantidad = detalle_cantidad;
		this.detalle_valorTc = detalle_valorTC;
		this.detalle_valorU = detalle_valorU;
	}

	public DetalleCompra() {
		this(0, 0, 0, 0, 0, 0);
	}

	@Override
	public String toString() {
		return "DetalleCompra{" + "com_id=" + com_id + ", inv_id=" + inv_id + ", detalle_cantidad=" + detalle_cantidad + ", detalle_valorTC=" + detalle_valorTc + ", detalle_valorU=" + detalle_valorU + '}';
	}

}
