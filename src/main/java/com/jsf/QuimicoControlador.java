/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.DescQuimicosMod;
import com.modelo.QuimicoMod;
import com.objetos.DescripcionQuimico;
import com.objetos.Quimico;
import com.objetos.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
	private final DescQuimicosMod modquidesc = new DescQuimicosMod();
	
	private Quimico quimi = new Quimico();
	private DescripcionQuimico descqui = new DescripcionQuimico();
	
	private final static Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private static final long serialVersionUID = 4064848968085852173L;
	
	private String buscador, buscadorDesc;
	private List<String> estados = Arrays.asList("", "Solido", "Liquido", "Gaseoso", "Plasma");
	private List<String> sino = Arrays.asList("", "Si", "No");
	private List<Quimico> lista = new ArrayList<Quimico>();
	private List<DescripcionQuimico> listadesc = new ArrayList<DescripcionQuimico>();
	
	private void todo() {
		this.lista.clear();
		this.lista = this.modqui.todos();
	}
	
	public void seleccionar(Quimico qui) {
		this.quimi = qui;
		this.listadesc = modquidesc.todos(qui.getQui_id());
	}
	
	public void actualizar() {
		try {
			if (this.modqui.actualizar(this.quimi)) {
				UtilitarioControlador.informativo("SE ACTUALIZO CON EXITO");
				this.limpiar_qui();
				this.todo();
			} else {
				UtilitarioControlador.advertencia("NO SE PUDO ACTUALIZAR");
			}
			
		} catch (Exception e) {
			UtilitarioControlador.error("OCURRIO UN ERROR: " + e.getMessage());
		}
	}
	
	public void guardar() {
		try {
			if (this.quimi.getQui_CPC().equals("") || this.quimi.getQui_nombreC().equals("")
					|| this.quimi.getQui_quimico().equals("")) {
				UtilitarioControlador.advertencia("Existen Campos Vacios");
			} else {
				this.quimi.setUsu_id_UltMod(usu.getUsu_id());
				if (modqui.existe(this.quimi.getQui_quimico(), this.quimi.getQui_CPC())) {
					UtilitarioControlador.informativo("EL QUIMICO O EL CPC YA EXISTEN EN LA BASE");
				} else {
					if (modqui.guardar(this.quimi)) {
						UtilitarioControlador.informativo("QUIMICO GUARDADO CON EXITO");
						this.limpiar_qui();
						this.todo();
					} else {
						UtilitarioControlador.advertencia("Existio un problema al ingresar el quimico");
						this.limpiar_qui();
						this.todo();
					}
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("OCURRIO UN ERROR: " + e.getMessage());
		}
	}
	
	public void guardar_descripcion() {
		try {
			if (this.descqui.hasEmptyFields()) {
				UtilitarioControlador.advertencia("CAMPOS VACIOS");
			} else {
				DescripcionQuimico aux = modquidesc.buscado(this.descqui.getDesq_desc());
				if (aux == null) {
					UtilitarioControlador.advertencia("EXISTIO UN ERROR AL GUARDAR");
				} else if (aux != new DescripcionQuimico()) {
					if (this.descqui.getDesq_estm().equals(aux.getDesq_estm())
							&& this.descqui.getDesq_umedida().equals(aux.getDesq_umedida())
							&& this.descqui.getDesq_infla().equals(aux.getDesq_infla())
							&& this.descqui.getDesq_color().equals(aux.getDesq_color())) {
						UtilitarioControlador.informativo("YA EXISTE LA DESCRIPCION");
					} else {
						this.descqui.setUsu_id_UltMod(usu.getUsu_id());
						this.descqui.setQui_id(this.quimi.getQui_id());
						if (modquidesc.guardar(this.descqui)) {
							UtilitarioControlador.informativo("SE GUARDO CON EXITO");
							this.seleccionar(this.quimi);
						} else {
							UtilitarioControlador.advertencia("NO SE GUARDO LA DESCRIPCION");
						}
					}
				} else {
					UtilitarioControlador.advertencia("YA EXISTE LA DESCRIPCION");
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
				this.limpiar_qui();
				this.todo();
			} else {
				UtilitarioControlador.advertencia("NO SE PUDO BORRAR");
				this.limpiar_qui();
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
	
	public void limpiar_qui() {
		if (!this.quimi.equals(new Quimico())) {
			this.quimi = new Quimico();
		}
	}
	
	public void limpiar_desc() {
		if (!this.descqui.equals(new DescripcionQuimico())) {
			this.descqui = new DescripcionQuimico();
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
	
	public String getBuscadorDesc() {
		return buscadorDesc;
	}
	
	public void setBuscadorDesc(String buscadorDesc) {
		this.buscadorDesc = buscadorDesc;
	}
	
	public List<DescripcionQuimico> getListadesc() {
		return listadesc;
	}
	
	public void setListadesc(List<DescripcionQuimico> listadesc) {
		this.listadesc = listadesc;
	}
	
	public DescripcionQuimico getDescqui() {
		return descqui;
	}
	
	public void setDescqui(DescripcionQuimico descqui) {
		this.descqui = descqui;
	}
	
	public List<String> getEstados() {
		return estados;
	}
	
	public void setEstados(List<String> estados) {
		this.estados = estados;
	}
	
	public List<String> getSino() {
		return sino;
	}
	
	public void setSino(List<String> sino) {
		this.sino = sino;
	}
}
