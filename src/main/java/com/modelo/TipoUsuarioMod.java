package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.objetos.TipoUsuario;

public class TipoUsuarioMod extends UtilitarioMod<TipoUsuario> implements Serializable {
	private static final long serialVersionUID = 5782337078176395916L;

	// PETICIONES DE CONSULTA
	@Override
	public List<TipoUsuario> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_tipo_usuarios tus where tus.tus_est !='I'")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(
						new TipoUsuario(rst.getInt("tus_id"), rst.getString("tus_tipo"), rst.getString("tus_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODOS LOS TIPOS DE USUARIOS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public TipoUsuario buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_tipo_usuarios tus where tus.tus_est !='I' AND tus.tus_id=?");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new TipoUsuario(rst.getInt("tus_id"), rst.getString("tus_tipo"), rst.getString("tus_est")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODOS LOS TIPOS DE USUARIOS: " + e.getMessage());
		}
		return null;
	}

	// PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(TipoUsuario nuevo) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_tipo_usuarios(tus_id, tus_tipo, tus_est) VALUES (default, ?, 'A');");
			pst.setString(1, nuevo.getTus_tipo().toLowerCase());
this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR TIPO USUARIO: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean borrar(int id) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn
					.prepareStatement("UPDATE public.tbl_tipo_usuarios SET tus_est='D' WHERE tus_id=?;");
			pst.setInt(1, id);
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR TIPO USUARIO: " + e.getMessage());
		}
		return false;
	}
}
