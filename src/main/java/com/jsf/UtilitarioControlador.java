/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author panchito
 */
@SessionScoped
public class UtilitarioControlador implements Serializable {
	
	public final static void mensaje(FacesMessage.Severity seve,String titulo,String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(seve, titulo, mensaje));
	}
	
	public final static void informativo(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", mensaje.toUpperCase()));
	}
	
	public final static void advertencia(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", mensaje.toUpperCase()));
	}
	
	public final static void error(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", mensaje.toUpperCase()));
	}
}
