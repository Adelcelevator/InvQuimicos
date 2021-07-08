package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Proveedor;

public class ProveedorMod extends UtilitarioMod<Proveedor> implements Serializable {

	private static final long serialVersionUID = 5814670238509627176L;

	@Override
	public List<Proveedor> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_proveedores pro where pro.pro_est!='I' ORDER BY pro.\"pro_nombreC\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Proveedor(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("pro_ruc"), rst.getString("pro_telefono"),
						rst.getString("pro_dire"), rst.getString("pro_representante"), rst.getString("pro_pais"),
						rst.getString("pro_est"), rst.getString("pro_nombreC"), rst.getInt("pro_id")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODOS LOS PROVEEDORES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Proveedor> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_proveedores pro where pro.pro_est!='I' AND pro.\"pro_nombreC\" LIKE ? OR pro.pro_ruc Like ? ORDER BY pro.\"pro_nombreC\"");
			pst.setString(1, "%" + nombre + "%");
			pst.setString(2, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Proveedor(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("pro_ruc"), rst.getString("pro_telefono"),
						rst.getString("pro_dire"), rst.getString("pro_representante"), rst.getString("pro_pais"),
						rst.getString("pro_est"), rst.getString("pro_nombreC"), rst.getInt("pro_id")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER LOS PROVEEDORES BUSCADOS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Proveedor buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_proveedores pro where pro.pro_est!='I' AND pro.pro_id=?");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Proveedor(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("pro_ruc"), rst.getString("pro_telefono"),
						rst.getString("pro_dire"), rst.getString("pro_representante"), rst.getString("pro_pais"),
						rst.getString("pro_est"), rst.getString("pro_nombreC"), rst.getInt("pro_id")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL PROVEEDOR DE ID " + id + ": " + e.getMessage());
		}
		return null;
	}

	@Override
	public Proveedor buscado(String bus) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_proveedores pro where pro.pro_est!='I' AND pro.pro_ruc=?");
			pst.setString(1, bus);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Proveedor(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("pro_ruc"), rst.getString("pro_telefono"),
						rst.getString("pro_dire"), rst.getString("pro_representante"), rst.getString("pro_pais"),
						rst.getString("pro_est"), rst.getString("pro_nombreC"), rst.getInt("pro_id")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL PROVEEDOR DE RUC " + bus + " : " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean guardar(Proveedor nuevo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_proveedores(pro_id, pro_ruc, pro_telefono, pro_dire, pro_representante, pro_pais, pro_est, fecha_in, fecha_mod, \"usu_id_UltMod\", \"pro_nombreC\")  VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setString(1, nuevo.getRuc().toLowerCase());
			pst.setString(2, nuevo.getTelefono().toLowerCase());
			pst.setString(3, nuevo.getDire());
			pst.setString(4, nuevo.getRepresentante());
			pst.setString(5, nuevo.getPais());
			pst.setString(6, "A");
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setDate(8, Date.valueOf(LocalDate.now()));
			pst.setInt(9, nuevo.getUsu_id_UltMod());
			pst.setString(10, nuevo.getNombreC());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR PROVEEDOR: " + e.getMessage());
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
					"UPDATE public.tbl_proveedores SET pro_est='I', fecha_mod=?, \"usu_id_UltMod\"=? WHERE pro_id=?;");
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
			System.err.println("ERROR AL BORRAR EL USUARIO DE ID " + id + ": " + e.getMessage());
		}
		return this.isFue();
	}

	@Override
	public boolean actualizar(Proveedor actual) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_proveedores SET pro_telefono=?, pro_dire=?, pro_representante=?, fecha_mod=?, \"usu_id_UltMod\"=?, \"pro_nombreC\"=? WHERE pro_id=?;");
			pst.setString(1, actual.getTelefono().toLowerCase());
			pst.setString(2, actual.getDire());
			pst.setString(3, actual.getRepresentante());
			pst.setDate(4, Date.valueOf(LocalDate.now()));
			pst.setInt(5, actual.getUsu_id_UltMod());
			pst.setString(6, actual.getNombreC());
			pst.setInt(7, actual.getPro_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.err
					.println("ERROR AL ACTUALIZAR EL PROVEEDOR DE ID " + actual.getPro_id() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return this.isFue();
	}

}
