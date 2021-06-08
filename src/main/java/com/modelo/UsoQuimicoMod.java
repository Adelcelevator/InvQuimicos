package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import com.objetos.UsoQuimico;

public class UsoQuimicoMod extends UtilitarioMod<UsoQuimico> implements Serializable {
	private static final long serialVersionUID = -2275096661687944863L;

//PETICIONES DE LECTURAS
	@Override
	public List<UsoQuimico> todos(int id) {
		this.getLis().clear();
		try {
			PreparedStatement pst = this.getCn().conectar()
					.prepareStatement("SELECT * FROM public.tbl_uso_quimicos tusoq where tusoq.qui_id=?;");
			pst.setInt(1, id);
			ResultSet rst = pst.executeQuery();
			while (rst.next()) {
				this.getLis().add(new UsoQuimico(rst.getInt("usu_id_UltMod"), rst.getDate("fecha_in"),
						rst.getDate("fecha_mod"), rst.getInt("tuso_id"), rst.getInt("qui_id")));
			}
			this.getCn().desconectar();
			return this.getLis();
		} catch (Exception e) {
			System.out.println("ERROR AL TRAER TODOS LOS USOS DEL QUIMICO " + id + ": " + e.getMessage());
		}
		return this.getLis();
	}

//PETICIONES DE ESCRITURA
	@Override
	public boolean guardar(UsoQuimico nuevo) throws Exception {
		Connection cn = this.getCn().conectar();
		cn.setAutoCommit(false);
		try {
			PreparedStatement pst = cn.prepareStatement("INSERT INTO public.tbl_uso_quimicos(tuso_id, qui_id, fecha_in, fecha_mod, \"usu_id_UltMod\") VALUES (?, ?, ?, ?, ?);");
			pst.setInt(1, nuevo.getTuso_id());
			pst.setInt(2, nuevo.getQui_id());
			pst.setDate(3, Date.valueOf(LocalDate.now()));
			pst.setDate(4, Date.valueOf(LocalDate.now()));
			pst.setInt(4, nuevo.getUsu_id_UltMod());
			this.setFue(pst.executeUpdate()==1?true:false);
			cn.commit();
			cn.close();
			return this.isFue();
		} catch (Exception e) {
			cn.rollback();
			cn.close();
			System.out.println("ERROR AL GUARDAR EL USO PARA EL QUIMICO: "+e.getMessage());
		}
		return false;
	}
}