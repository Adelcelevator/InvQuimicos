package com.modelo;

import com.utilitarios.UtilitarioMod;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.DescripcionQuimico;

public class DescQuimicosMod extends UtilitarioMod<DescripcionQuimico> implements Serializable {
	private static final long serialVersionUID = -505212644921952665L;

	// PETICIONES DE LECTURA
	@Override
	public List<DescripcionQuimico> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_desc_quimi descq ORDER BY descq.qui_id;")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODAS LAS DESCRIPCIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<DescripcionQuimico> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_desc_quimi descq where descq.qui_id=? ORDER BY descq.qui_id;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODAS LAS DESCRIPCIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<DescripcionQuimico> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement(
					"SELECT * FROM public.tbl_desc_quimi descq where descq.desq_desc LIKE ? ORDER BY descq.desq_id;");
			pst.setString(1, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}
			Conexion.desconectar();
			return this.getLis();
		} catch (Exception e) {
			Conexion.desconectar();
			System.out.println("ERROR AL TRAER TODAS LAS DESCRIPCIONES: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public DescripcionQuimico buscado(int id) {
		try {
			PreparedStatement pst = Conexion.conectar()
					.prepareStatement("SELECT * FROM public.tbl_desc_quimi descq WHERE descq.desq_id=?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER LA DESCRIPCION DESEADA: " + e.getMessage());
		}
		return null;
	}

	public DescripcionQuimico buscado(String bus, int id) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement("SELECT * FROM public.tbl_desc_quimi descq WHERE descq.desq_desc =? and descq.qui_id=?;");
			pst.setString(1, bus);
			pst.setInt(2, id);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				this.setObj(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}else{
				return new DescripcionQuimico();
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER LA DESCRIPCION: "+e.getMessage());
		}
		return null;
	}

	@Override
	public DescripcionQuimico buscado(String bus) {
		try {
			PreparedStatement pst = Conexion.conectar().prepareStatement("SELECT * FROM public.tbl_desc_quimi descq WHERE descq.desq_desc =?;");
			pst.setString(1, bus);
			ResultSet rst = pst.executeQuery();
			if(rst.next()){
				this.setObj(new DescripcionQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("desq_id"), rst.getInt("qui_id"),
						rst.getString("desq_desc"), rst.getString("desq_estm"), rst.getString("desq_color"),
						rst.getString("desq_umedida"), rst.getString("desq_infla")));
			}else{
				return new DescripcionQuimico();
			}
			Conexion.desconectar();
			return this.getObj();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER LA DESCRIPCION: "+e.getMessage());
		}
		return null;
	}

//PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(DescripcionQuimico nuevo) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_desc_quimi(desq_id, desq_desc, desq_estm, desq_color, desq_umedida, desq_infla, qui_id, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			pst.setString(1, nuevo.getDesq_desc().toLowerCase());
			pst.setString(2, nuevo.getDesq_estm().toLowerCase());
			pst.setString(3, nuevo.getDesq_color().toLowerCase());
			pst.setString(4, nuevo.getDesq_umedida().toLowerCase());
			pst.setString(5, nuevo.getDesq_infla().toLowerCase());
			pst.setInt(6, nuevo.getQui_id());
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
			System.out.println("ERROR AL GUARDAR LA DESCRIPCION: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean actualizar(DescripcionQuimico actual) throws Exception {
		Connection cn = Conexion.conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_desc_quimi SET desq_desc=?, desq_estm=?, desq_color=?, desq_umedida=?, desq_infla=?, fecha_mod=?, \"usu_id_UltMod\"=? WHERE desq_id=?;");
			pst.setString(1, actual.getDesq_desc().toLowerCase());
			pst.setString(2, actual.getDesq_estm().toLowerCase());
			pst.setString(3, actual.getDesq_color().toLowerCase());
			pst.setString(4, actual.getDesq_umedida().toLowerCase());
			pst.setString(5, actual.getDesq_infla().toLowerCase());
			pst.setDate(6, Date.valueOf(LocalDate.now()));
			pst.setInt(7, actual.getUsu_id_UltMod());
			pst.setInt(8, actual.getDesq_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR LA DESCRIPCION: " + e.getMessage());
		}
		return false;
	}

}
