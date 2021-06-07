/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.objetos.Inventario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@ManagedBean(name="inventario")
@SessionScoped
public class InventarioControlador implements Serializable {
	
	public List<Inventario> todo(){
		return null;
	}
	
	public String descripcion(int id){
		return "";
	}
	
	public String quimico(int id){
		return "";
	}
}
