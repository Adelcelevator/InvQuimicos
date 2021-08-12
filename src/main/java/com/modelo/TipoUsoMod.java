package com.modelo;

import com.utilitarios.UtilitarioMod;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.TipoUso;

public class TipoUsoMod extends UtilitarioMod<TipoUso> implements Serializable {

	private static final long serialVersionUID = 6339099347842658534L;

//PETICIONES DE LECTURA
	@Override
	public List<TipoUso> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_tipo_uso tuso WHERE tuso.tuso_est!='D';")
					.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new TipoUso(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("tuso_id"), rst.getString("tuso_uso"), rst.getString("tuso_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER LOS TIPOS DE USO: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<TipoUso> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_tipo_uso tuso WHERE tuso.tuso_est!='D' AND tuso.tuso_uso LIKE ?;");
			pst.setString(1, "%"+nombre+"%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis()
						.add(new TipoUso(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
								rst.getInt("tuso_id"), rst.getString("tuso_uso"), rst.getString("tuso_est")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER LOS TIPOS DE USO: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public TipoUso buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_tipo_uso tuso WHERE tuso.tuso_est!='D' AND tuso.tuso_id=?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new TipoUso(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getInt("tuso_id"), rst.getString("tuso_uso"), rst.getString("tuso_est")));
			}else{
				this.setObj(new TipoUso());
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER EL TIPO INDICADO: " + e.getMessage());
		}
		return null;
	}

	@Override
	public TipoUso buscado(String bus) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_tipo_uso tuso WHERE tuso.tuso_est!='D' AND tuso.tuso_uso= ?;");
			pst.setString(1, bus);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new TipoUso(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getInt("tuso_id"), rst.getString("tuso_uso"), rst.getString("tuso_est")));
			}else{
				this.setObj(new TipoUso());
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER EL TIPO INDICADO: " + e.getMessage());
		}
		return null;
	}

//PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(TipoUso nuevo) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_tipo_uso(tuso_id, tuso_uso, tuso_est, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (default, ?, 'A', ?, ?, ?);");
			pst.setString(1, nuevo.getTuso_uso().toLowerCase());
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setInt(4, nuevo.getUsu_id_UltMod());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR USO: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean borrar(int id, int idusu) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_tipo_uso SET tuso_est='D', fecha_mod=?, \"usu_id_UltMod\"=? WHERE tuso_id=?;");
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setInt(4, idusu);
			pst.setInt(5, id);
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL BORRAR USO: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean actualizar(TipoUso actual) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_tipo_uso SET tuso_uso=?, tuso_est=?, fecha_mod=?, \"usu_id_UltMod\"=? WHERE tuso_id=?;");
			pst.setString(1, actual.getTuso_uso().toLowerCase());
			pst.setString(2, actual.getTuso_est().toLowerCase());
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setInt(4, actual.getUsu_id_UltMod());
			pst.setInt(5, actual.getUsu_id_UltMod());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL ACTUALIZAR USO: " + e.getMessage());
		}
		return false;
	}

}
