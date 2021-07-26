/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.objetos.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

	public final static void redirigir(String direcion) throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().redirect(direcion);
	}

	public final static void guardar(String clave, Object valor) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(clave, valor);
	}

	public final static Object sacar(String clave) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(clave);
	}

	public static Usuario getUsu() {
		return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}

	public final static void vamonos() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/InvQuimicos");
		} catch (Exception e) {
			System.out.println("ERROR AL CERRAR LA SESION");
		}
	}

	public final static Boolean validaRuc(String ruc) {
		try {
			Double.parseDouble(ruc);
			int[] ci = new int[ruc.length()];
			int suma = 0;
			for (int i = 0; i < ruc.length(); i++) {
				ci[i] = Integer.parseInt(String.valueOf(ruc.charAt(i)));
			}
			ci[0] = ci[0] * 2;
			ci[1] = ci[1] * 1;
			ci[2] = ci[2] * 2;
			ci[3] = ci[3] * 1;
			ci[4] = ci[4] * 2;
			ci[5] = ci[5] * 1;
			ci[6] = ci[6] * 2;
			ci[7] = ci[7] * 1;
			ci[8] = ci[8] * 2;
			for (int i = 0; i < 9; i++) {
				if (ci[i] >= 10) {
					ci[i] = ci[i] - 9;
				}
				suma = suma + ci[i];
			}
			int dv = (suma + (10 - (suma % 10))) - suma;
			dv = dv == 10 ? 0 : dv;
			if (dv == ci[9]) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public final static double dosDeci(double valor){
		return new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
