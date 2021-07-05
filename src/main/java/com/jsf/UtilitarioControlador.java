/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.objetos.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@SessionScoped
public class UtilitarioControlador implements Serializable {

	private static final long serialVersionUID = 555260345240571896L;

	public final static void mensaje(FacesMessage.Severity seve, String titulo, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(seve, titulo, mensaje));
	}

	public final static void informativo(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", mensaje.toUpperCase()));
	}

	public final static void advertencia(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", mensaje.toUpperCase()));
	}

	public final static void error(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", mensaje.toUpperCase()));
	}

	public final static void redirigir(String direcion) throws Exception{
			FacesContext.getCurrentInstance().getExternalContext().redirect(direcion);
	}
	
	public final static void guardar(String clave,Object valor){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(clave, valor);
	}
	
	public final static Object sacar(String clave){
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(clave);
	}
	
	public static Usuario getUsu() {
		return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}
	
	public final static void vamonos(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/InvQuimicos");
		} catch (Exception e) {
			System.out.println("ERROR AL CERRAR LA SESION");
		}
	}
}
