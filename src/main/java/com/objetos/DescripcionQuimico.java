package com.objetos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class DescripcionQuimico extends Auditoria implements Serializable {

	private static final long serialVersionUID = 455369788658023117L;
	private int desq_id, qui_id;
	private String desq_desc, desq_estm, desq_color, desq_umedida, desq_infla;

	public int getDesq_id() {
		return desq_id;
	}

	public void setDesq_id(int desq_id) {
		this.desq_id = desq_id;
	}

	public int getQui_id() {
		return qui_id;
	}

	public void setQui_id(int qui_id) {
		this.qui_id = qui_id;
	}

	public String getDesq_desc() {
		return desq_desc.toLowerCase();
	}

	public void setDesq_desc(String desq_desc) {
		this.desq_desc = desq_desc.toLowerCase();
	}

	public String getDesq_estm() {
		return desq_estm;
	}

	public void setDesq_estm(String desq_estm) {
		this.desq_estm = desq_estm.toLowerCase();
	}

	public String getDesq_color() {
		return desq_color.toLowerCase();
	}

	public void setDesq_color(String desq_color) {
		this.desq_color = desq_color.toLowerCase();
	}

	public String getDesq_umedida() {
		return desq_umedida.toLowerCase();
	}

	public void setDesq_umedida(String desq_umedida) {
		this.desq_umedida = desq_umedida.toLowerCase();
	}

	public String getDesq_infla() {
		return desq_infla;
	}

	public void setDesq_infla(String desq_infla) {
		this.desq_infla = desq_infla.toLowerCase();
	}

	public DescripcionQuimico(int usu_id_UltMod, Date fecha_in, Date fecha_mod, int desq_id, int qui_id,
			String desq_desc, String desq_estm, String desq_color, String desq_umedida, String desq_infla) {
		super(usu_id_UltMod, fecha_in, fecha_mod);
		this.desq_id = desq_id;
		this.qui_id = qui_id;
		this.desq_desc = desq_desc.toLowerCase();
		this.desq_estm = desq_estm.toLowerCase();
		this.desq_color = desq_color.toLowerCase();
		this.desq_umedida = desq_umedida.toLowerCase();
		this.desq_infla = desq_infla.toLowerCase();
	}

	public DescripcionQuimico() {
		this(0, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 0, "", "", "", "", "");
	}

	@Override
	public String toString() {
		return "DescripcionQuimico{" + "desq_id=" + desq_id + ", qui_id=" + qui_id + ", desq_desc=" + desq_desc + ", desq_estm=" + desq_estm + ", desq_color=" + desq_color + ", desq_umedida=" + desq_umedida + ", desq_infla=" + desq_infla + '}';
	}

	public boolean hasEmptyFields() {
		if (this.equals(new DescripcionQuimico())) {
			return true;
		}
		if(this.desq_color.equals("")){
			return true;
		}
		if(this.desq_desc.equals("")){
			return true;
		}
		if(this.desq_estm.equals("")){
			return true;
		}
		if(this.desq_infla.equals("")){
			return true;
		}
		if(this.desq_umedida.equals("")){
			return true;
		}
		return false;
	}
}
