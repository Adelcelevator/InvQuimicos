package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.objetos.DetalleVenta;
import com.utilitarios.UtilitarioMod;

public class DetalleCotizacionMod extends UtilitarioMod<DetalleVenta> implements Serializable {
	private static final long serialVersionUID = 1873202432379815622L;

	@Override
	public List<DetalleVenta> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_detalle_venta WHERE ven_id=?");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new DetalleVenta(rst.getInt("ven_id"), rst.getInt("inv_id"),
								rst.getInt("detalle_cantidad"), rst.getDouble("detalle_valorTv"),
								rst.getDouble("detalle_valorU")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL DETALLE DE LA COTIZACION CON ID " + id + ": " + e.getMessage());
		}
		return this.getLis();
	}

	// Consulta de escritura
	@Override
	public boolean guardar(DetalleVenta nuevo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_detalle_venta(ven_id, inv_id, \"detalle_valorTv\", detalle_cantidad, \"detalle_valorU\") VALUES (?, ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getVen_id());
			pst.setInt(2, nuevo.getInv_id());
			pst.setDouble(3, nuevo.getDetalle_valorTv());
			pst.setInt(4, nuevo.getDetalle_cantidad());
			pst.setDouble(5, nuevo.getDetalle_valorU());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR EL DETALLE DE LA COTIZACION: " + e.getMessage());
		}
		return this.isFue();
	}

	public boolean actualizar(DetalleVenta actual, DetalleVenta viejo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_detalle_venta SET ven_id=?, inv_id=?, \"detalle_valorTv\"=?, detalle_cantidad=?, \"detalle_valorU\"=? WHERE ven_id=? inv_id=?;");
			pst.setInt(1, actual.getVen_id());
			pst.setInt(2, actual.getInv_id());
			pst.setDouble(3, actual.getDetalle_valorTv());
			pst.setInt(4, actual.getDetalle_cantidad());
			pst.setDouble(5, actual.getDetalle_valorU());
			pst.setInt(6, actual.getVen_id());
			pst.setInt(7, actual.getInv_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL ACTUALIZAR PARTE DEL DETALLE DE LA COTIZACION: " + e.getMessage());
		}
		return this.isFue();
	}

	public boolean borrar(DetalleVenta actual) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn
					.prepareStatement("DELETE FROM public.tbl_detalle_venta WHERE ven_id =? AND inv_id =?;");
			pst.setInt(1, actual.getVen_id());
			pst.setInt(2, actual.getInv_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL ELIMINAR PARTE DEL DETALLE DE LA COTIZACION: " + e.getMessage());
		}
		return this.isFue();
	}
}
