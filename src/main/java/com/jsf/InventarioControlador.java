/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.DescQuimicosMod;
import com.modelo.QuimicoMod;
import com.objetos.DescripcionQuimico;
import com.objetos.Inventario;
import com.objetos.Quimico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "inventario")
@SessionScoped
public class InventarioControlador implements Serializable {

	private static final long serialVersionUID = -2388548534275774612L;
	private String buscador;
	private List<Inventario> lista;
	private Quimico quimico = new Quimico();
	private DescripcionQuimico desq = new DescripcionQuimico();
	private List<String> desc;
	public void buscar() {

	}

	public void seleccionar(Inventario inv) {

	}

	public void seleccionarQuimi() {
		QuimicoMod quimi = new QuimicoMod();
		this.quimico = quimi.buscado(this.quimico.getQui_quimico());
		DescQuimicosMod desqmod = new DescQuimicosMod();
	   List<String> esta = new ArrayList<>();
		desqmod.todos(this.quimico.getQui_id()).stream().forEach((s)->{
		esta.add(s.getDesq_desc());
		});
		this.setDesc(esta);
	}

	public List<String> quimicos() {
		List<String> esto = new ArrayList<String>();
		QuimicoMod quimi = new QuimicoMod();
		esto.add("");
		quimi.todos().stream().forEach((s) -> {
			esto.add(s.getQui_quimico());
		});
		return esto;
	}

	/*

	Getters y Setters

	 */
	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public List<Inventario> getLista() {
		return lista;
	}

	public void setLista(List<Inventario> lista) {
		this.lista = lista;
	}

	public Quimico getQuimico() {
		return quimico;
	}

	public void setQuimico(Quimico quimico) {
		this.quimico = quimico;
	}

	public List<String> getDesc() {
		return desc;
	}

	public void setDesc(List<String> desc) {
		this.desc = desc;
	}

	public DescripcionQuimico getDesq() {
		return desq;
	}

	public void setDesq(DescripcionQuimico desq) {
		this.desq = desq;
	}

}