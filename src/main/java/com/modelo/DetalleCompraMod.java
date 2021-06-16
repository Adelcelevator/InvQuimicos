package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.objetos.DetalleCompra;

public class DetalleCompraMod extends UtilitarioMod<DetalleCompra> implements Serializable {
	private static final long serialVersionUID = -2717325712551819689L;
	
	// Consulta de Lectura
	@Override
	public List<DetalleCompra> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_detalle_compra WHERE com_id=?");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(
						new DetalleCompra(rst.getInt("com_id"), rst.getInt("inv_id"), rst.getInt("detalle_cantidad"),
								rst.getDouble("detalle_valorTc"), rst.getDouble("detalle_valorU")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL DETALLE DE LA VENTA CON ID " + id + ": " + e.getMessage());
		}
		return this.getLis();
	}

	// Consulta de escritura
	@Override
	public boolean guardar(DetalleCompra nuevo) throws Exception {
		this.setFue(false);
		Connection cn =Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement("INSERT INTO public.tbl_detalle_compra(com_id, inv_id, \"detalle_valorTc\", detalle_cantidad, \"detalle_valorU\") VALUES (?, ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getCom_id());
			pst.setInt(2, nuevo.getInv_id());
			pst.setDouble(3, nuevo.getDetalle_valorTc());
			pst.setInt(4, nuevo.getDetalle_cantidad());
			pst.setDouble(5, nuevo.getDetalle_valorU());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR EL DETALLE: "+e.getMessage());
		}
		return this.isFue();
	}

	public boolean actualizar(DetalleCompra actual, DetalleCompra viejo) throws Exception {
		this.setFue(false);
		Connection cn =Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement("UPDATE public.tbl_detalle_compra SET com_id=?, inv_id=?, \"detalle_valorTv\"=?, detalle_cantidad=?, \"detalle_valorU\"=? WHERE com_id=? inv_id=?;");
			pst.setInt(1, actual.getCom_id());
			pst.setInt(2, actual.getInv_id());
			pst.setDouble(3, actual.getDetalle_valorTc());
			pst.setInt(4, actual.getDetalle_cantidad());
			pst.setDouble(5, actual.getDetalle_valorU());
			pst.setInt(6, actual.getCom_id());
			pst.setInt(7, actual.getInv_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL ACTUALIZAR PARTE DEL DETALLE: "+e.getMessage());
		}
		return this.isFue();
	}

	public boolean borrar(DetalleCompra actual) throws Exception {
		this.setFue(false);
		Connection cn =Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement("DELETE FROM public.tbl_detalle_comta WHERE com_id =? AND inv_id =?;");
			pst.setInt(1, actual.getCom_id());
			pst.setInt(2, actual.getInv_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL ELIMINAR PARTE DEL DETALLE: "+e.getMessage());
		}
		return this.isFue();
	}
}
