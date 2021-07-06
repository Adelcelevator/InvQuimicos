/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.objetos.Proveedor;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "proveedor")
@SessionScoped
public class ProveedorControlador implements Serializable {

	private static final long serialVersionUID = -8404778928087984105L;
	private String buscador;
	private List<Proveedor> lista;

	public void buscar(){
		
	}
	
	public void seleccionar(Proveedor pro){
		
	}
	
	
	public void borrar(int id){
		
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

	public List<Proveedor> getLista() {
		return lista;
	}

	public void setLista(List<Proveedor> lista) {
		this.lista = lista;
	}

}
