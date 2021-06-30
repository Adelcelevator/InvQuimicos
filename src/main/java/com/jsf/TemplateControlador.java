package com.jsf;

import com.objetos.Usuario;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("deprecation")
@ManagedBean(name = "template")
@SessionScoped
public class TemplateControlador implements Serializable {

	private static final long serialVersionUID = 2173326729984611492L;
	private static Usuario usuariolog;

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

	public TemplateControlador() {
		UtilitarioControlador.guardar("usuario", new Usuario(1, 1, "adel", "holamonica", "Eduardo", "Montenegro", "", "", "A",
						Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now())));
		usuariolog = (Usuario) UtilitarioControlador.sacar("usuario");

	}

	public static Usuario getUsuariolog() {
		return usuariolog;
	}
}
