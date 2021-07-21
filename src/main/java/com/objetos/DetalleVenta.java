package com.objetos;

import java.io.Serializable;

public class DetalleVenta implements Serializable {
	private static final long serialVersionUID = 6443411164325037457L;
	private int ven_id, inv_id, detalle_cantidad;
    private double detalle_valorTv, detalle_valorU;

    public DetalleVenta(int ven_id, int inv_id, int detalle_cantidad, double detalle_valorTv, double detalle_valorU) {
        this.ven_id = ven_id;
        this.inv_id = inv_id;
        this.detalle_cantidad = detalle_cantidad;
        this.detalle_valorTv = detalle_valorTv;
        this.detalle_valorU = detalle_valorU;
    }

    public DetalleVenta() {
    }

    public int getVen_id() {
        return ven_id;
    }

    public void setVen_id(int ven_id) {
        this.ven_id = ven_id;
    }

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int qui_id) {
        this.inv_id = qui_id;
    }

    public int getDetalle_cantidad() {
        return detalle_cantidad;
    }

    public void setDetalle_cantidad(int detalle_cantidad) {
        this.detalle_cantidad = detalle_cantidad;
    }

    public double getDetalle_valorTv() {
        return detalle_valorTv;
    }

    public void setDetalle_valorTv(double detalle_valorTv) {
        this.detalle_valorTv = detalle_valorTv;
    }

    public double getDetalle_valorU() {
        return detalle_valorU;
    }

    public void setDetalle_valorU(double detalle_valorU) {
        this.detalle_valorU = detalle_valorU;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "ven_id=" + ven_id + ", qui_id=" + inv_id + ", detalle_cantidad=" + detalle_cantidad + ", detalle_valorTv=" + detalle_valorTv + ", detalle_valorU=" + detalle_valorU + '}';
    }

}
