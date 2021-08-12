package com.modelo;

import com.utilitarios.UtilitarioMod;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Compra;

public class CompraMod extends UtilitarioMod<Compra> implements Serializable {
	private static final long serialVersionUID = 4739519160489910146L;

	// Peticiones de Lectura
	@Override
	public List<Compra> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_compras com where com.com_est!='A' ORDER BY com.\"com_numFac\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Compra(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("com_id"), rst.getInt("com_numFac"), rst.getInt("pro_id"),
								rst.getDouble("com_valorT"), rst.getDouble("com_valorIm"), rst.getDate("com_fecha"),
								rst.getString("com_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODAS LAS COMPRAS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Compra> historial() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_compras com ORDER BY com.\"com_numFac\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Compra(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("com_id"), rst.getInt("com_numFac"), rst.getInt("pro_id"),
								rst.getDouble("com_valorT"), rst.getDouble("com_valorIm"), rst.getDate("com_fecha"),
								rst.getString("com_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL HISTORIAL DE COMPRAS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Compra> buscando(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_compras com where com.com_est!='A' AND com.\"com_numFac\"::TEXT LIKE ? ORDER BY com.\"com_numFac\"");
			pst.setString(1, "%" + id + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Compra(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("com_id"), rst.getInt("com_numFac"), rst.getInt("pro_id"),
								rst.getDouble("com_valorT"), rst.getDouble("com_valorIm"), rst.getDate("com_fecha"),
								rst.getString("com_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR LAS COMPRAS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Compra buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_compras com where com.com_est!='A' AND com.\"com_numFac\"=? ORDER BY com.\"com_numFac\"");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Compra(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getInt("com_id"), rst.getInt("com_numFac"), rst.getInt("pro_id"),
						rst.getDouble("com_valorT"), rst.getDouble("com_valorIm"), rst.getDate("com_fecha"),
						rst.getString("com_est")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL LA COMPRA: " + e.getMessage());
		}
		return null;
	}

	// Peticiones de Escritura
	@Override
	public boolean guardar(Compra nuevo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_compras(com_id, \"com_numFac\", \"com_valorT\", \"com_valorIm\", pro_id, fecha_in, fecha_mod, \"usu_id_UltMod\", com_fecha, com_est) VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, 'G');");
			pst.setInt(1, nuevo.getCom_numFac());
			pst.setDouble(2, nuevo.getCom_valorT());
			pst.setDouble(3, nuevo.getCom_valorIm());
			pst.setInt(4, nuevo.getPro_id());
			pst.setDate(5, Date.valueOf(LocalDate.now()));
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setInt(7, nuevo.getUsu_id_UltMod());
			pst.setDate(8, nuevo.getCom_fecha());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR LA COMPRA: " + e.getMessage());
		}
		return this.isFue();
	}

	@Override
	public boolean borrar(int id, int idusu) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_compras SET com_estado='A',fecha_mod=?, \"usu_id_UltMod\"=? WHERE com_id=?;");
			pst.setDate(1, Date.valueOf(LocalDate.now()));
			pst.setInt(2, idusu);
			pst.setInt(3, id);
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL BORRAR LA COMPRA: " + e.getMessage());
		}
		return this.isFue();
	}

	@Override
	public boolean actualizar(Compra actual) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_compras SET \"com_numFac\"=?, \"com_valorT\"=?, \"com_valorIm\"=?, pro_id=?, com_fecha=?,fecha_mod=?, \"usu_id_UltMod\"=? WHERE com_id=?;");
			pst.setInt(1, actual.getCom_numFac());
			pst.setDouble(2, actual.getCom_valorT());
			pst.setDouble(3, actual.getCom_valorIm());
			pst.setInt(4, actual.getPro_id());
			pst.setDate(5, actual.getCom_fecha());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setInt(7, actual.getUsu_id_UltMod());
			pst.setInt(8, actual.getCom_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR LA COMPRA: " + e.getMessage());
		}
		return this.isFue();
	}

}
