/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.QuimicoMod;
import com.objetos.Quimico;
import com.objetos.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@ManagedBean(name = "quimico")
@SessionScoped
public class QuimicoControlador implements Serializable {

	private final QuimicoMod modqui = new QuimicoMod();
	private Quimico quimi = new Quimico();
	private final static Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private String buscador;
	public List<Quimico> lista = new ArrayList<Quimico>();

	private void todo() {
		this.lista.clear();
		this.lista = this.modqui.todos();
	}

	public void seleccionar(Quimico qui) {
		this.quimi = qui;
	}

	public void guardar() {
		try {
			Quimico aux = modqui.buscado(this.quimi.getQui_quimico());
			this.quimi.setQui_id(aux.getQui_id());
			this.quimi.setUsu_id_UltMod(aux.getUsu_id_UltMod());
			if (0 == this.quimi.getQui_id()) {
				this.quimi.setUsu_id_UltMod(usu.getUsu_id());
				if (modqui.guardar(this.quimi)) {
					UtilitarioControlador.informativo("SE GUARDO CON EXITO EL QUIMICO");
					this.limpiar();
					this.todo();
				} else {
					UtilitarioControlador.advertencia("NO SE PUDO GUARDAR EL QUIMICO");
				}
			} else {
				if (this.modqui.actualizar(this.quimi)) {
					UtilitarioControlador.informativo("SE ACTUALIZO CON EXITO");
					this.limpiar();
					this.todo();
				} else {
					UtilitarioControlador.advertencia("NO SE PUDO ACTUALIZAR");
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("OCURRIO UN ERROR: " + e.getMessage());
		}
	}

	public void borrar(int id) {
		try {
			if (modqui.borrar(id, TemplateControlador.getUsuariolog().getUsu_id())) {
				UtilitarioControlador.informativo("ELIMINADO CON EXITO");
				this.limpiar();
				this.todo();
			} else {
				UtilitarioControlador.advertencia("NO SE PUDO BORRAR");
				this.limpiar();
				this.todo();
			}
		} catch (Exception e) {
			UtilitarioControlador.error("HUBO UN ERROR AL BORRAR EL QUIMICO: " + e.getMessage());
		}
	}

	public void buscar() {
		this.lista.clear();
		this.lista = modqui.buscando(buscador);
	}

	public void limpiar() {
		if (!this.quimi.equals(new Quimico())) {
			this.quimi = new Quimico();
		}
	}

	public Quimico getQuimi() {
		return quimi;
	}

	public void setQuimi(Quimico quimi) {
		this.quimi = quimi;
	}

	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public QuimicoControlador() {
		this.todo();
	}

	public List<Quimico> getLista() {
		return lista;
	}

	public void setLista(List<Quimico> lista) {
		this.lista = lista;
	}

}
