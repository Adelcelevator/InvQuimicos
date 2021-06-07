package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Inventario;

public class InventarioMod extends UtilitarioMod<Inventario> implements Serializable {
	private static final long serialVersionUID = -4961196967819826955L;

	@Override
	public List<Inventario> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = this.getCn().conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_inventario inv where inv.inv_id= inv.inv_id ORDER BY inv.qui_id;")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("inv_desc"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO :" + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Inventario> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = this.getCn().conectar().prepareStatement(
					"SELECT * FROM public.tbl_inventario inv where inv.inv_id= inv.inv_id AND inv.qui_id=? ORDER BY inv.inv_id;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("inv_desc"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO CON ID " + id + " :" + e.getMessage());
		}
		return this.getLis();
	}

//
	@Override
	public List<Inventario> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = this.getCn().conectar().prepareStatement(
					"SELECT inv.* FROM public.tbl_inventario inv, tbl_desc_quimi descq WHERE descq.qui_id = inv.qui_id AND inv.inv_id =inv.inv_id AND descq.desq_id=descq.desq_id AND descq.desq_desc LIKE ? ORDER BY inv.inv_id;");
			pst.setString(1, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("inv_desc"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO POR DESCRIPCION " + nombre + " :" + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Inventario buscado(int id) {
		try {
			PreparedStatement pst = this.getCn().conectar()
					.prepareStatement("SELECT * FROM public.tbl_inventario inv where inv.inv_id=?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(
						new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("inv_id"), rst.getInt("inv_cantidad"), rst.getInt("qui_id"),
								rst.getInt("inv_desc"), rst.getDouble("inv_precioCU"), rst.getDouble("inv_precioUI")));
			}
			this.getCn().desconectar();
			return this.getObj();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO CON ID " + id + " :" + e.getMessage());
		}
		return this.getObj();
	}

	// PETICIONES DE ESCRITURA

	@Override
	public boolean guardar(Inventario nuevo) throws Exception {
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_inventario(inv_id, inv_desc, inv_cantidad, \"inv_precioCU\", \"inv_precioUI\", qui_id, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getInv_desc());
			pst.setInt(2, nuevo.getInv_cantidad());
			pst.setDouble(3, nuevo.getInv_precioCU());
			pst.setDouble(4, nuevo.getInv_precioUI());
			pst.setInt(5, nuevo.getQui_id());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setInt(8, nuevo.getUsu_id_UltMod());
			this.setFue(pst.executeUpdate() == 1 ? true : false);
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR INVENTARIO: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean actualizar(Inventario actual) throws Exception {
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_inventario SET inv_cantidad=?, \"inv_precioCU\"=?, \"inv_precioUI\"=?, fecha_mod=?, \"usu_id_UltMod\"=? WHERE inv_id=?;");
			pst.setInt(1, actual.getInv_cantidad());
			pst.setDouble(2, actual.getInv_precioCU());
			pst.setDouble(3, actual.getInv_precioUI());
			pst.setDate(4, Date.valueOf(LocalDate.now()));
			pst.setInt(5, actual.getUsu_id_UltMod());
			pst.setInt(6, actual.getInv_id());
			this.setFue(pst.executeUpdate() == 1 ? true : false);
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR INVENTARIO: " + e.getMessage());
		}
		return false;
	}

}
