/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.modelo.TipoUsuarioMod;
import com.modelo.UsuarioMod;
import com.objetos.Usuario;
import com.utilitarios.UtilitarioControlador;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "usuario")
@SessionScoped
public class UsuarioControlador implements Serializable {
	private static final long serialVersionUID = 8442698275724648556L;
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
		this.nusu = usu;
	}

	public void eliminar() {
		try {
			if (modus.borrar(this.pusu.getUsu_id())) {
				UtilitarioControlador.informativo("USUARIO ELIMINADO CON EXITO");
				limpiar();
			} else {
				UtilitarioControlador.advertencia("NO SE ELIMINO EL USUARIO");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("ERROR AL ELIMINAR EL USUARIO: " + e.getMessage());
		}
	}

	public void guardar() {
		try {
			if (0 == this.nusu.getUsu_id()) {
				this.nusu.setTus_id(1);
				if (modus.guardar(this.nusu)) {
					UtilitarioControlador.informativo("GUARDADO CON EXITO");
					limpiar();
				} else {
					UtilitarioControlador.advertencia("NO SE GUARDO EL USUARIO");
				}
			} else {
				this.nusu.setTus_id(1);
				if (modus.actualizar(this.nusu)) {
					UtilitarioControlador.informativo("USUARIO ACTUALIZADO CON EXITO");
					limpiar();
				} else {
					UtilitarioControlador.advertencia("NO SE PUDO ACTUALIZAR EL USUARIO");
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("ERRROR AL GUARDAR EL USUARIO: " + e.getMessage());
		}
	}

	private void limpiar() {
		pusu = new Usuario();
		nusu = new Usuario();
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
