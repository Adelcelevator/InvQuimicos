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
			ResultSet rst = Conexion.conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_inventario inv where inv.inv_id= inv.inv_id ORDER BY inv.qui_id;")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("descq_id"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO :" + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Inventario> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_inventario inv where inv.inv_id= inv.inv_id AND inv.qui_id=? ORDER BY inv.inv_id;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("descq_id"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO CON ID " + id + " :" + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Inventario> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT inv.* FROM pruebas.public.tbl_inventario inv, pruebas.public.tbl_desc_quimi descq, pruebas.public.tbl_quimicos qui WHERE inv.descq_id = descq.desq_id AND inv.qui_id = descq.qui_id AND inv.qui_id = qui.qui_id AND descq.qui_id = qui.qui_id AND descq.desq_desc LIKE ? ORDER BY inv.inv_id;");
			pst.setString(1, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
								rst.getDate("fecha_mod"), rst.getInt("inv_id"), rst.getInt("inv_cantidad"),
								rst.getInt("qui_id"), rst.getInt("descq_id"), rst.getDouble("inv_precioCU"),
								rst.getDouble("inv_precioUI")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO POR DESCRIPCION " + nombre + " :" + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Inventario buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_inventario inv where inv.inv_id=?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(
						new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("inv_id"), rst.getInt("inv_cantidad"), rst.getInt("qui_id"),
								rst.getInt("descq_id"), rst.getDouble("inv_precioCU"), rst.getDouble("inv_precioUI")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODO EL INVENTARIO CON ID " + id + " :" + e.getMessage());
		}
		return this.getObj();
	}

	public Inventario buscado(int quiid, int descq) {
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("select * from tbl_inventario inv where inv.qui_id=? and inv.descq_id=?;");
			pst.setInt(1, quiid);
			pst.setInt(2, descq);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(
						new Inventario(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("inv_id"), rst.getInt("inv_cantidad"), rst.getInt("qui_id"),
								rst.getInt("descq_id"), rst.getDouble("inv_precioCU"), rst.getDouble("inv_precioUI")));
			}else{
				return new Inventario();
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR EN EL INVENTARIO :" + e.getMessage());
		}
		return this.getObj();
	}

	// PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(Inventario nuevo) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_inventario(inv_id, descq_id, inv_cantidad, \"inv_precioCU\", \"inv_precioUI\", qui_id, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getInv_desc());
			pst.setInt(2, nuevo.getInv_cantidad());
			pst.setDouble(3, nuevo.getInv_precioCU());
			pst.setDouble(4, nuevo.getInv_precioUI());
			pst.setInt(5, nuevo.getQui_id());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setInt(8, nuevo.getUsu_id_UltMod());
			this.setFue((pst.executeUpdate() == 1));
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
		Connection cn = Conexion.conectar();
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
			this.setFue((pst.executeUpdate() == 1));
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
