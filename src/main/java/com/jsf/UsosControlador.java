/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.QuimicoMod;
import com.modelo.TipoUsoMod;
import com.objetos.TipoUso;
import com.objetos.UsoQuimico;
import com.objetos.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@ManagedBean(name = "usos")
@SessionScoped
public class UsosControlador implements Serializable {
	
	private List<TipoUso> listuso = new ArrayList<TipoUso>();
	private List<UsoQuimico> listusoq = new ArrayList<UsoQuimico>();
	private final TipoUsoMod modtuso = new TipoUsoMod();
	private String buscador;
	private String[] quimicosUso;
	private TipoUso uso = new TipoUso();
	private static final Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	public void todo() {
		this.listuso.clear();
		this.listuso = this.modtuso.todos();
	}
	
	public void buscar() {
		this.listuso = modtuso.buscando(buscador);
		
	}
	
	public String nombreq(int id) {
		QuimicoMod modqui = new QuimicoMod();
		return modqui.buscado(id).getQui_nombreC() + " | " + modqui.buscado(id).getQui_quimico();
	}
	
	public void guardar() {
		this.uso.setUsu_id_UltMod(usu.getUsu_id());
		try {
			TipoUso aux = modtuso.buscado(this.uso.getTuso_uso());
			if (aux.getTuso_id() != 0) {
				UtilitarioControlador.informativo("Ya Existe el Tipo de Uso");
				this.limpiar();
			} else {
				if (modtuso.guardar(uso)) {
					UtilitarioControlador.informativo("Se guardo el tipo \n de uso con exito");
					this.limpiar();
				} else {
					UtilitarioControlador.advertencia("No se guardo el uso");
				}
			}			
		} catch (Exception e) {
			UtilitarioControlador.error("ERROR AL GUARDAR TIPO DE USO: " + e.getMessage());
		}
	}
	
	public void guardar_uso() {
		/*this.uso.setUsu_id_UltMod(usu.getUsu_id());
		try {
			TipoUso aux = modtuso.buscado(this.uso.getTuso_uso());
			if (aux.getTuso_id() != 0) {
				UtilitarioControlador.informativo("Ya Existe el Tipo de Uso");
				this.limpiar();
			} else {
				if (modtuso.guardar(uso)) {
					UtilitarioControlador.informativo("Se guardo el tipo \n de uso con exito");
					this.limpiar();
				}else{
					UtilitarioControlador.advertencia("No se guardo el uso");
				}
			}			
		} catch (Exception e) {
			UtilitarioControlador.error("ERROR AL GUARDAR TIPO DE USO: " + e.getMessage());
		}*/
	}
	
	public void limpiar() {
		uso = new TipoUso();
		this.todo();
	}
	
	public void seleccionar(TipoUso us) {
		this.uso = us;
	}
	
	public List<String> quimicos() {
		QuimicoMod modqui = new QuimicoMod();
		List<String> esto = new ArrayList<String>();
		modqui.todos().stream().forEach((este) -> {
			esto.add(este.getQui_quimico());
		});
		return esto;
	}

	/*

	Constructor
	
	 */
	public UsosControlador() {
		todo();
	}

	/*
	
	GETTERS Y SETTERS
	
	 */
	public String getBuscador() {
		return buscador;
	}
	
	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}
	
	public List<TipoUso> getListuso() {
		return listuso;
	}
	
	public void setListuso(List<TipoUso> listuso) {
		this.listuso = listuso;
	}
	
	public TipoUso getUso() {
		return uso;
	}
	
	public void setUso(TipoUso uso) {
		this.uso = uso;
	}
	
	public List<UsoQuimico> getListusoq() {
		return listusoq;
	}
	
	public void setListusoq(List<UsoQuimico> listusoq) {
		this.listusoq = listusoq;
	}
	
	public String[] getQuimicosUso() {
		return quimicosUso;
	}
	
	public void setQuimicosUso(String[] quimicosUso) {
		this.quimicosUso = quimicosUso;
	}
	
}
