/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.objetos.Usuario;
import java.sql.Date;
import java.time.LocalDate;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.SessionMap;

/**
 *
 * @author panchito
 */
@ManagedBean(name = "template")
@SessionMap
public class TemplateControlador {

	private static Usuario usuariolog;

	public String nombre() {
		try {
			if (TemplateControlador.usuariolog == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/InvQuimicos");
			} else {
				return TemplateControlador.usuariolog.getUsu_nombre() + " " + TemplateControlador.usuariolog.getUsu_apellido();
			}
		} catch (Exception e) {
			UtilitarioControlador.error("PUES SE DAÑO DEL TODO :v \n" + e.getMessage());
		}
		return "";
	}

	public void cerrar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/InvQuimicos");
		} catch (Exception e) {
		}
	}

	public TemplateControlador() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", new Usuario(1, 1, "adel", "holamonica", "Eduardo", "Montenegro", "", "", "A", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now())));
		usuariolog = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	}

	public static Usuario getUsuariolog() {
		return usuariolog;
	}
}
