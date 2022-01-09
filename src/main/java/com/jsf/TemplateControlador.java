package com.jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.objetos.Usuario;
import com.utilitarios.UtilitarioControlador;

@SuppressWarnings("deprecation")
@ManagedBean(name = "template")
@SessionScoped
public class TemplateControlador implements Serializable {

	private static final long serialVersionUID = 2173326729984611492L;
	private static final Usuario usuariolog = (Usuario) UtilitarioControlador.sacar("usuario");

	public String nombre() {
		try {
			if (TemplateControlador.usuariolog == null) {
				UtilitarioControlador.redirigir("/InvQuimicos");
			} else {
				return TemplateControlador.usuariolog.getUsu_nombre() + " "
						+ TemplateControlador.usuariolog.getUsu_apellido();
			}
		} catch (Exception e) {
			UtilitarioControlador.error("PUES SE DAÑO DEL TODO :v \n" + e.getMessage());
		}
		return "";
	}

	public void cerrar() {
		UtilitarioControlador.vamonos();
	}

	public static Usuario getUsuariolog() {
		return usuariolog;
	}

	public void redirigir(String direc) {
		try {
			UtilitarioControlador.redirigir(direc);
		} catch (Exception e) {
			UtilitarioControlador.error("Error al redirigir");
		}
	}
}
