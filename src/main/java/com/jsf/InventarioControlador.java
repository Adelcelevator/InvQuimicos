/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.modelo.DescQuimicosMod;
import com.modelo.InventarioMod;
import com.modelo.QuimicoMod;
import com.objetos.DescripcionQuimico;
import com.objetos.Inventario;
import com.objetos.Quimico;
import com.utilitarios.UtilitarioControlador;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "inventario")
@SessionScoped
public class InventarioControlador implements Serializable {

	private static final long serialVersionUID = -2388548534275774612L;
	private final InventarioMod modinv = new InventarioMod();
	private final DescQuimicosMod desqmod = new DescQuimicosMod();
	private final QuimicoMod modqui = new QuimicoMod();

	private String buscador, precioCU, precioUI, existencias;
	private List<Inventario> lista = new ArrayList<>();
	private List<DescripcionQuimico> desc = new ArrayList<>();
	private Inventario inv = new Inventario();

	public void buscar() {
		this.lista.clear();
		this.setLista(this.modinv.buscando(buscador));
	}

	public final void todo() {
		this.setLista(this.modinv.todos());
	}

	public void seleccionar(Inventario inv) {
		this.inv = inv;
	}

	public void seleccionarQuimi() {
		List<DescripcionQuimico> esta = new ArrayList<>();
		esta.add(new DescripcionQuimico());
		esta.addAll(this.desqmod.todos(this.inv.getQui_id()));
		this.setDesc(esta);
	}

	public List<Quimico> quimicos() {
		List<Quimico> esto = new ArrayList<>();
		esto.add(new Quimico());
		esto.addAll(this.modqui.todos());
		return esto;
	}

	public void guardarInv() {
		if (this.modinv.buscado(this.inv.getQui_id(), this.inv.getDescq_id()).equals(new Inventario())) {
			UtilitarioControlador.advertencia("ya existe en el inventario");
			this.limpiar();
		} else {
			try {
				this.inv.setInv_precioCU(Double.parseDouble(precioCU));
				this.inv.setInv_precioUI(Double.parseDouble(precioUI));
				this.inv.setInv_cantidad(Integer.parseInt(existencias));
				this.inv.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
				if (this.modinv.guardar(this.inv)) {
					UtilitarioControlador.informativo("Guardado con exito");
					this.limpiar();
				} else {
					UtilitarioControlador.advertencia("Error al Guardar");
				}
			} catch (Exception e) {
				UtilitarioControlador.error(
						"No ingresar letras en los precios o cantidades. \n y utilizar el punto como separador de dolares y monedas");
			}
		}
	}

	public void limpiar() {
		this.todo();
		this.inv = new Inventario();
		this.setPrecioCU("");
		this.setPrecioUI("");
		this.desc.clear();
		this.setExistencias("");
	}

	public String nomQui(int id) {
		return this.modqui.buscado(id).getQui_quimico();
	}

	public String nomDesc(int id) {
		return this.desqmod.buscado(id).getDesq_desc();
	}

	public String uniMed(int id, int cantidad) {
		return cantidad + " " + this.desqmod.buscado(id).getDesq_umedida();
	}

	/*
	 * 
	 * Getters y Setters
	 * 
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

	public List<DescripcionQuimico> getDesc() {
		return desc;
	}

	public void setDesc(List<DescripcionQuimico> desc) {
		this.desc = desc;
	}

	public Inventario getInv() {
		return inv;
	}

	public void setInv(Inventario inv) {
		this.inv = inv;
	}

	public String getPrecioCU() {
		return precioCU;
	}

	public void setPrecioCU(String precioCU) {
		this.precioCU = precioCU;
	}

	public String getPrecioUI() {
		return precioUI;
	}

	public void setPrecioUI(String precioUI) {
		this.precioUI = precioUI;
	}

	public String getExistencias() {
		return existencias;
	}

	public void setExistencias(String existencias) {
		this.existencias = existencias;
	}
}
