package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.Quimico;

public class QuimicoMod extends UtilitarioMod<Quimico> implements Serializable {
	private static final long serialVersionUID = 4763474013903330130L;
	// OPERACIONES DE CONSULTA
	@Override
	public List<Quimico> todos() {
		this.getLis().clear();
		try {
			ResultSet rst = this.getCn().conectar()
					.prepareStatement(
							"SELECT * FROM public.tbl_quimicos qui where qui.qui_estado!='C' ORDER BY qui.qui_id")
					.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Quimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getInt("qui_id"), rst.getString("qui_quimico"), rst.getString("qui_CPC"),
						rst.getString("qui_estado"), rst.getString("qui_nombreC")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER TODOS LOS QUIMICOS: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public List<Quimico> buscando(String nombre) {
		this.getLis().clear();
		try {
			PreparedStatement pst = this.getCn().conectar().prepareStatement(
					"SELECT * FROM public.tbl_quimicos qui where qui.qui_estado!='V' AND qui.\"qui_CPC\" LIKE ? OR qui.qui_quimico LIKE ? ORDER BY qui.qui_id");
			pst.setString(1, "%" + nombre + "%");
			pst.setString(2, "%" + nombre + "%");
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new Quimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"), rst.getDate("fecha_mod"),
						rst.getInt("qui_id"), rst.getString("qui_quimico"), rst.getString("qui_CPC"),
						rst.getString("qui_estado"), rst.getString("qui_nombreC")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL BUSCAR EL QUIMICO: " + e.getMessage());
		}
		return this.getLis();
	}

	@Override
	public Quimico buscado(int id) {
		try {
			PreparedStatement pst = this.getCn().conectar().prepareStatement(
					"SELECT * FROM public.tbl_quimicos qui WHERE qui.qui_estado!='V' AND qui.qui_id=?");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			if (rst.next()) {
				this.setObj(new Quimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("qui_id"), rst.getString("qui_quimico"),
						rst.getString("qui_CPC"), rst.getString("qui_estado"), rst.getString("qui_nombreC")));
			}
			this.getCn().desconectar();
			return this.getObj();
		} catch (Exception e) {
			this.getCn().desconectar();
			System.out.println("ERROR AL TRAER EL QUIMICO: " + e.getMessage());
		}
		return null;
	}

// OPERACIONES DE ESCRITURA
	@Override
	public boolean guardar(Quimico nuevo) throws Exception {
		this.setFue(false);
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"INSERT INTO public.tbl_quimicos(qui_id, qui_quimico, \"qui_CPC\", qui_estado, fecha_in, fecha_mod, \"usu_id_UltMod\", \"qui_nombreC\") VALUES (default, ?, ?, 'D', ?, ?, ?, ?);");
			pst.setString(1, nuevo.getQui_quimico());
			pst.setString(2, nuevo.getQui_CPC());
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setDate(4, Date.valueOf(LocalDate.now()));
			pst.setInt(5, nuevo.getUsu_id_UltMod());
			pst.setString(6, nuevo.getQui_nombreC());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR EL QUIMICO: " + e.getMessage());
		}
		return this.isFue();
	}

	@Override
	public boolean borrar(int id, int idusu) throws Exception {
		this.setFue(false);
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_quimicos SET qui_estado='C',fecha_mod=?, \"usu_id_UltMod\"=? WHERE qui_id=?;");
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
			System.out.println("ERROR AL GUARDAR EL QUIMICO: " + e.getMessage());
		}
		return this.isFue();
	}

	@Override
	public boolean actualizar(Quimico actual) throws Exception {
		this.setFue(false);
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement(
					"UPDATE public.tbl_quimicos SET qui_quimico=?, \"qui_CPC\"=?, fecha_mod=?, \"usu_id_UltMod\"=?, \"qui_nombreC\"=? WHERE qui_id=?;");
			pst.setString(1, actual.getQui_quimico());
			pst.setString(2, actual.getQui_CPC());
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setInt(4, actual.getUsu_id_UltMod());
			pst.setString(5, actual.getQui_nombreC());
			pst.setInt(6, actual.getQui_id());
			this.setFue((pst.executeUpdate() == 1));
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR EL QUIMICO: " + e.getMessage());
		}
		return this.isFue();
	}
}
