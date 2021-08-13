package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Cliente;
import com.utilitarios.UtilitarioMod;

public class ClienteMod extends UtilitarioMod<Cliente> implements Serializable {

	private static final long serialVersionUID = -5886343541179052340L;

	// PETICIONES DE LECTURA
	@Override
	public List<Cliente> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_clientes cli WHERE cli.cli_est!='I' ORDER BY cli.\"cli_nombreC\"")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Cliente(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getString("cli_ruc"), rst.getString("cli_telefono"), rst.getString("cli_dire"),
						rst.getString("cli_representante"), rst.getString("cli_pais"), rst.getString("cli_est"),
						rst.getString("cli_nombreC"), rst.getInt("cli_id")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.err.println("ERROR AL TRAER TODOS LOS CLIENTES: " + e.getMessage());
			e.printStackTrace();
		}
		return this.getLis();
	}

	@Override
	public List<Cliente> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * from public.tbl_clientes cli WHERE cli.cli_est!='I' AND cli.cli_ruc LIKE ? OR cli.cli_representante LIKE ? AND cli.cli_est!='I' ORDER BY cli.cli_id;");
			pst.setString(1, "%" + nombre + "%");
			pst.setString(2, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Cliente(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getString("cli_ruc"), rst.getString("cli_telefono"), rst.getString("cli_dire"),
						rst.getString("cli_representante"), rst.getString("cli_pais"), rst.getString("cli_est"),
						rst.getString("cli_nombreC"), rst.getInt("cli_id")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR POR " + nombre + ": " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Cliente buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * from public.tbl_clientes cli WHERE cli.cli_id =?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Cliente(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("cli_ruc"), rst.getString("cli_telefono"),
						rst.getString("cli_dire"), rst.getString("cli_representante"), rst.getString("cli_pais"),
						rst.getString("cli_est"), rst.getString("cli_nombreC"), rst.getInt("cli_id")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL BUSCAR POR ID " + id + ": " + e.getMessage());
		}

		return null;
	}

	@Override
	public Cliente buscado(String bus) {
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * from public.tbl_clientes cli WHERE cli.cli_ruc =? AND cli.cli_est!='I';");
			pst.setString(1, bus);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Cliente(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getString("cli_ruc"), rst.getString("cli_telefono"),
						rst.getString("cli_dire"), rst.getString("cli_representante"), rst.getString("cli_pais"),
						rst.getString("cli_est"), rst.getString("cli_nombreC"), rst.getInt("cli_id")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER EL CLIENTE DE RUC " + bus + ": " + e.getMessage());
		}

		return null;
	}

//PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(Cliente nuevo) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_clientes(cli_id, cli_ruc, cli_telefono, cli_dire, cli_representante, cli_pais, cli_est, fecha_in, fecha_mod, \"usu_id_ultMod\", \"cli_nombreC\") VALUES (default, ?, ?, ?, ?, ?, 'A', ?, ?, ?, ?)");
			pst.setString(1, nuevo.getRuc().toLowerCase());
			pst.setString(2, nuevo.getTelefono().toLowerCase());
			pst.setString(3, nuevo.getDire());
			pst.setString(4, nuevo.getRepresentante());
			pst.setString(5, nuevo.getPais());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setDate(7, Date.valueOf(LocalDate.now()));
			pst.setInt(8, nuevo.getUsu_id_UltMod());
			pst.setString(9, nuevo.getNombreC());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.err.println("ERROR AL GUARDAR EL CLIENTE NUEVO " + e.getMessage());
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
					"UPDATE public.tbl_clientes SET cli_est='I', fecha_mod=?, \"usu_id_ultMod\"=? WHERE cli_id=?");
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
	public boolean actualizar(Cliente actual) throws Exception {
		this.setFue(false);
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_clientes SET cli_ruc=?, cli_telefono=?, cli_dire=?, cli_representante=?, cli_pais=?, fecha_mod=?, \"usu_id_ultMod\"=?, \"cli_nombreC\"=? WHERE cli_id=?;");
			pst.setString(1, actual.getRuc().toLowerCase());
			pst.setString(2, actual.getTelefono().toLowerCase());
			pst.setString(3, actual.getDire());
			pst.setString(4, actual.getRepresentante());
			pst.setString(5, actual.getPais());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setInt(7, actual.getUsu_id_UltMod());
			pst.setString(8, actual.getNombreC());
			pst.setInt(9, actual.getCli_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.err
					.println("ERROR AL ACTUALIZAR EL CLIENTE DE ID " + actual.getCli_id() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return this.isFue();
	}
}
