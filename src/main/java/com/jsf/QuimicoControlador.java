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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@ManagedBean(name="quimico")
@SessionScoped
public class QuimicoControlador implements Serializable {
	QuimicoMod modqui = new QuimicoMod();
	Quimico quimi = new Quimico();
	private final static Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	public List<Quimico> todo(){
		return this.modqui.todos();
	}
	
	public void editarquim(Quimico qui){
		this.quimi=qui;
		System.out.println(this.quimi.toString());
	}
}
