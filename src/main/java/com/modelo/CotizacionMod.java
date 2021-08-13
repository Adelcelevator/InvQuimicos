package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Venta;
import com.utilitarios.UtilitarioMod;

public class CotizacionMod extends UtilitarioMod<Venta> implements Serializable {
	private static final long serialVersionUID = 8831609339314578411L;

	// Peticiones de Lectura
	@Override
	public List<Venta> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_ventas ven where ven.ven_estado='C' ORDER BY ven.\"ven_numFac\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Venta(rst.getInt("ven_id"), rst.getInt("ven_numFac"), rst.getInt("cli_id"),
								rst.getDouble("ven_valorT"), rst.getDouble("ven_valorIm"),
								rst.getDouble("ven_descuento"), rst.getString("ven_estado"), rst.getDate("ven_fecha"),
								rst.getDate("fecha_in"), rst.getDate("fecha_mod"), rst.getInt("usu_id_UltMod")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODAS LAS COTIZACIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Venta> historial() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_ventas ven where ven.ven_estado='C' ORDER BY ven.\"ven_numFac\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Venta(rst.getInt("ven_id"), rst.getInt("ven_numFac"), rst.getInt("cli_id"),
								rst.getDouble("ven_valorT"), rst.getDouble("ven_valorIm"),
								rst.getDouble("ven_descuento"), rst.getString("ven_estado"), rst.getDate("ven_fecha"),
								rst.getDate("fecha_in"), rst.getDate("fecha_mod"), rst.getInt("usu_id_UltMod")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL HISTORIAL DE COTIZACIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Venta> buscando(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_ventas ven where ven.ven_estado='C' AND ven.\"ven_numFac\"::TEXT LIKE ? ORDER BY ven.\"ven_numFac\"");
			pst.setString(1, "%" + id + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new Venta(rst.getInt("ven_id"), rst.getInt("ven_numFac"), rst.getInt("cli_id"),
								rst.getDouble("ven_valorT"), rst.getDouble("ven_valorIm"),
								rst.getDouble("ven_descuento"), rst.getString("ven_estado"), rst.getDate("ven_fecha"),
								rst.getDate("fecha_in"), rst.getDate("fecha_mod"), rst.getInt("usu_id_UltMod")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR COTIZACIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Venta buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_ventas ven where ven.ven_estado='C' AND ven.\"ven_numFac\"=? ORDER BY ven.\"ven_numFac\"");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Venta(rst.getInt("ven_id"), rst.getInt("ven_numFac"), rst.getInt("cli_id"),
						rst.getDouble("ven_valorT"), rst.getDouble("ven_valorIm"), rst.getDouble("ven_descuento"),
						rst.getString("ven_estado"), rst.getDate("ven_fecha"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("usu_id_UltMod")));
			} else {
				return null;
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR COTIZACIONES: " + e.getMessage());
		}
		return null;
	}

	// Peticiones de Escritura
	@Override
	public boolean guardar(Venta nuevo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_ventas(ven_id, \"ven_numFac\", \"ven_valorT\", \"ven_valorIm\", ven_descuento, cli_id, ven_estado, ven_fecha, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (default, ?, ?, ?, ?, ?, 'C', ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getVen_numFac());
			pst.setDouble(2, nuevo.getVen_valorT());
			pst.setDouble(3, nuevo.getVen_valorIm());
			pst.setDouble(4, nuevo.getVen_descuento());
			pst.setInt(5, nuevo.getCli_id());
			pst.setDate(6, nuevo.getVen_fecha());
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setDate(8, Date.valueOf(LocalDate.now()));
			pst.setInt(9, nuevo.getUsu_id_UltMod());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR LA COTIZACION: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean borrar(int id, int idusu) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_ventas SET ven_estado='C',fecha_mod=?, \"usu_id_UltMod\"=? WHERE ven_id=?;");
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
			System.out.println("ERROR AL GUARDAR LA COTIZACION: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean actualizar(Venta actual) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_ventas SET \"ven_numFac\"=?, \"ven_valorT\"=?, \"ven_valorIm\"=?, ven_descuento=?, cli_id=?, ven_fecha=?,fecha_mod=?, \"usu_id_UltMod\"=? WHERE ven_id=?;");
			pst.setInt(1, actual.getVen_numFac());
			pst.setDouble(2, actual.getVen_valorT());
			pst.setDouble(3, actual.getVen_valorIm());
			pst.setDouble(4, actual.getVen_descuento());
			pst.setInt(5, actual.getCli_id());
			pst.setDate(6, actual.getVen_fecha());
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setInt(8, actual.getUsu_id_UltMod());
			pst.setInt(9, actual.getVen_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR LA COTIZACION: " + e.getMessage());
		}
		return false;
	}
}
