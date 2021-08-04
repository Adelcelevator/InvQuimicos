/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.UsuarioMod;
import com.objetos.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "index")
@SessionScoped
public class IndexControlador implements Serializable {
	private static final long serialVersionUID = 2237173281169220126L;
	private final UsuarioMod modus = new UsuarioMod();
	private String usuario = "", contra = "";

	public void ingresar() {
		try {
			if (this.usuario.equals("") || this.contra.equals("")) {
				UtilitarioControlador.advertencia("Campos Vacios");
			} else {
				Usuario usu = modus.buscado(usuario);
				if (usu == null) {
					UtilitarioControlador.advertencia("No Existe el Usuario");
				} else {
					if (!usu.getUsu_contra().equals(contra)) {
						UtilitarioControlador.advertencia("Contraseña Equivocada");
					} else {
						UtilitarioControlador.guardar("usuario", usu);
						UtilitarioControlador.redirigir("Protegidos/inventario.quimicos");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR AL INGRESAR: " + e.getMessage());
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}
	
	public void logueado(){
		try{
			
		if(UtilitarioControlador.getUsu() != null){
			UtilitarioControlador.redirigir("Protegidos/inventario.quimicos");
		}
		}catch(Exception e){
			System.out.println("ERROR AL REDIRIGIR DESDE EL INICIO CON SESION INICIADA");
		}
	}
}
