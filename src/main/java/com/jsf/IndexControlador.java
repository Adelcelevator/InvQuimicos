/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.UsuarioMod;
import com.objetos.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@ManagedBean(name = "index")
@SessionScoped
public class IndexControlador implements Serializable {
	private static final long serialVersionUID = 2237173281169220126L;
	private final UsuarioMod modus = new UsuarioMod();
	private String usuario = "", contra = "";

	public void ingresar() {
		try {
			if (this.usuario.equals("") || this.contra.equals("")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", "CAMPOS VACIOS"));
			} else {
				Usuario usu = modus.buscado(usuario);
				if (usu == null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", "NO EXISTE EL USUARIO"));
				} else {
					if (!usu.getUsu_contra().equals(contra)) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", "CONTRASEÑA INCORRECTA"));
					} else {
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usu);
						FacesContext.getCurrentInstance().getExternalContext().redirect("Protegidos/inv.quimicos");
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
}
