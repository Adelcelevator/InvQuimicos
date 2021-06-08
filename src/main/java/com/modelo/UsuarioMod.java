package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Usuario;

public class UsuarioMod extends UtilitarioMod<Usuario> /*implements Serializable*/ {

	/*private static final long serialVersionUID = 8488918005199197806L;*/

	// PETICIONES DE LECTURA
	@Override
	public List<Usuario> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = this.getCn().conectar().prepareStatement(
					"SELECT * FROM public.tbl_usuarios usu WHERE usu.usu_estado != 'I' ORDER BY usu.usu_id")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Usuario(rst.getInt("usu_id"), rst.getInt("tus_id"), rst.getString("usu_usuario"),
						rst.getString("usu_contra"), rst.getString("usu_nombre"), rst.getString("usu_apellido"),
						rst.getString("usu_telefono"), rst.getString("usu_direccion"), rst.getString("usu_estado"),
						rst.getDate("fecha_in"), rst.getDate("fecha_mod")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.err.println("ERROR AL TRAER TODOS LOS USUARIOS: " + e.getMessage());
			e.printStackTrace();
		}
		return this.getLis();
	}

	@Override
	public Usuario buscado(String bus) {
		try {
			PreparedStatement pst = this.getCn().conectar().prepareStatement(
					"SELECT * FROM public.tbl_usuarios usu where usu.usu_usuario=?");
			pst.setString(1, bus);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Usuario(rst.getInt("usu_id"), rst.getInt("tus_id"), rst.getString("usu_usuario"),
						rst.getString("usu_contra"), rst.getString("usu_nombre"), rst.getString("usu_apellido"),
						rst.getString("usu_telefono"), rst.getString("usu_direccion"), rst.getString("usu_estado"),
						rst.getDate("fecha_in"), rst.getDate("fecha_mod")));
			}else{
				return new Usuario();
			}
			this.getCn().desconectar();
			return this.getObj();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.err.println("ERROR AL BUSCAR EL USUARIO " + bus + " : " + e.getMessage());
			e.printStackTrace();
		}
		return new Usuario();
	}

//PETICIONES DE MANIPULACION	
	@Override
	public boolean borrar(int id) throws Exception {

		Connection cn = this.getCn().conectar();
		if (cn != null) {
			cn.setAutoCommit(false);
			try {
				PreparedStatement pst = cn
						.prepareStatement("UPDATE public.tbl_usuarios SET usu_estado='I', fecha_mod=? WHERE usu_id=?");
				pst.setDate(1, Date.valueOf(LocalDate.now()));
				pst.setInt(2, id);
				this.setFue((pst.executeUpdate() == 1));
				cn.commit();
				cn.close();
				return this.isFue();
			} catch (Exception e) {
				cn.rollback();
				cn.close();
				System.err.println("ERROR AL TRAER TODOS LOS USUARIOS: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean actualizar(Usuario actual) throws Exception {
		this.setFue(false);
		Connection cn = this.getCn().conectar();
		if (cn != null) {
			cn.setAutoCommit(false);
			try {
				PreparedStatement pst = cn.prepareStatement(
						"UPDATE public.tbl_usuarios SET usu_contra=?, usu_nombre=?, usu_apellido=?, usu_telefono=?, usu_direccion=?, usu_estado=?, tus_id=?, fecha_mod=? WHERE usu_id=?");
				pst.setString(1, actual.getUsu_contra());
				pst.setString(2, actual.getUsu_nombre());
				pst.setString(3, actual.getUsu_apellido());
				pst.setString(4, actual.getUsu_telefono());
				pst.setString(5, actual.getUsu_direccion());
				pst.setString(6, actual.getUsu_estado());
				pst.setInt(7, actual.getTus_id());
				pst.setDate(8, java.sql.Date.valueOf(LocalDate.now()));
				pst.setInt(9, actual.getUsu_id());
				this.setFue((pst.executeUpdate() == 1));
				cn.commit();
				cn.close();
				return this.isFue();
			} catch (Exception e) {
				cn.rollback();
				cn.close();
				System.err
						.println("ERROR AL ACTUALIZAR EL USUARIO DE ID " + actual.getUsu_id() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean guardar(Usuario nuevo) throws Exception {
		this.setFue(false);
		Connection cn = this.getCn().conectar();
		if (cn != null) {
			cn.setAutoCommit(false);
			try {
				PreparedStatement pst = cn.prepareStatement(
						"INSERT INTO public.tbl_usuarios(usu_id, usu_usuario, usu_contra, usu_nombre, usu_apellido, usu_telefono, usu_direccion, tus_id, fecha_in, fecha_mod, usu_estado) VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pst.setString(1, nuevo.getUsu_usuario());
				pst.setString(2, nuevo.getUsu_contra());
				pst.setString(3, nuevo.getUsu_nombre());
				pst.setString(4, nuevo.getUsu_apellido());
				pst.setString(5, nuevo.getUsu_telefono());
				pst.setString(6, nuevo.getUsu_direccion());
				pst.setInt(7, nuevo.getTus_id());
				pst.setDate(8, Date.valueOf(LocalDate.now()));
				pst.setDate(9, Date.valueOf(LocalDate.now()));
				pst.setString(10, "A");
				this.setFue((pst.executeUpdate() == 1));
				cn.commit();
				cn.close();
				return this.isFue();
			} catch (Exception e) {
				cn.rollback();
				cn.close();
				System.err.println("ERROR AL TRAER TODOS LOS USUARIOS: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}
}
