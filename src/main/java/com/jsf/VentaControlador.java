/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.ClienteMod;
import com.modelo.VentaMod;
import com.objetos.Venta;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "venta")
@SessionScoped
public class VentaControlador implements Serializable {

	private static final long serialVersionUID = 1231918322852835640L;
	private List<Venta> lista;
	private Venta venta;
	private String Buscador;
	private final VentaMod modventa = new VentaMod();

	private void todo() {
		this.setLista(modventa.todos());
	}

	public void buscar() {

	}

	public String cliente(int id) {
	ClienteMod modcli = new ClienteMod();
	return modcli.buscado(id).getNombreC();
	}

	public void Seleccionar(Venta ven) {
		this.setVenta(ven);
	}

	public void archivar(int id){
		
	}

	public void agregar() {
		try {
			UtilitarioControlador.redirigir("agregarVenta.quimicos");
		} catch (Exception e) {
			UtilitarioControlador.error("Hubo un error al redirigir: " + e.getMessage());
		}
	}

	/*
	Getters Setters
	 */
	public List<Venta> getLista() {
		return lista;
	}

	public void setLista(List<Venta> lista) {
		this.lista = lista;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public String getBuscador() {
		return Buscador;
	}

	public void setBuscador(String Buscador) {
		this.Buscador = Buscador;
	}

	public VentaControlador() {
		this.todo();
	}
}
