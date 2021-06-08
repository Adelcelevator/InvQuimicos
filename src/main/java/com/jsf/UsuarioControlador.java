/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.TipoUsuarioMod;
import com.modelo.UsuarioMod;
import com.objetos.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@ManagedBean(name = "usuario")
@SessionScoped
public class UsuarioControlador implements Serializable {
	
	private final UsuarioMod modus = new UsuarioMod();
	private final TipoUsuarioMod modtus = new TipoUsuarioMod();
	private Usuario pusu = new Usuario();
	private Usuario nusu = new Usuario();
	
	public List<Usuario> todos() {
		return modus.todos();
	}
	
	public String tipo(int id) {
		return modtus.buscado(id).getTus_tipo();
	}
	
	public void selecionar(Usuario usu) {
		this.pusu = usu;
	}
	
	public void eliminar() {
		try {
			if (modus.borrar(this.pusu.getUsu_id())) {
				UtilitarioControlador.informativo("USUARIO ELIMINADO CON EXITO");
				this.pusu = new Usuario();
			} else {
				UtilitarioControlador.advertencia("NO SE ELIMINO EL USUARIO");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("ERROR AL ELIMINAR EL USUARIO: " + e.getMessage());
		}
	}
	
	public void guardar() {
		try {
			Usuario aux = modus.buscado(nusu.getUsu_usuario());
			if (aux.getUsu_usuario().equals("")) {
				if (modus.guardar(nusu)) {
					UtilitarioControlador.informativo("GUARDADO CON EXITO");
					nusu = new Usuario();
				} else {
					UtilitarioControlador.advertencia("NO SE GUARDO EL USUARIO");
				}
			} else {
				nusu.setUsu_id(aux.getUsu_id());
				nusu.setTus_id(1);
				if (modus.actualizar(nusu)) {
					UtilitarioControlador.informativo("USUARIO ACTUALIZADO CON EXITO");
					nusu = new Usuario();
				} else {
					UtilitarioControlador.advertencia("NO SE PUDO ACTUALIZAR EL USUARIO");
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("ERRROR AL GUARDAR EL USUARIO: " + e.getMessage());
		}
	}
	
	public Usuario getPusu() {
		return pusu;
	}
	
	public void setPusu(Usuario pusu) {
		this.pusu = pusu;
	}
	
	public Usuario getNusu() {
		return nusu;
	}
	
	public void setNusu(Usuario nusu) {
		this.nusu = nusu;
	}
}
