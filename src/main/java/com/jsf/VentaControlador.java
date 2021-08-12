/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.utilitarios.UtilitarioControlador;
import com.modelo.ClienteMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleVentaMod;
import com.modelo.InventarioMod;
import com.modelo.VentaMod;
import com.objetos.DetalleVenta;
import com.objetos.Venta;
import java.io.Serializable;
import java.util.ArrayList;
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
	private List<DetalleVenta> listadet = new ArrayList<>();
	private Venta venta;
	private String buscador;
	private final VentaMod modventa = new VentaMod();
	private final ClienteMod modcli = new ClienteMod();

	public void todo() {
		this.setLista(modventa.todos());
	}

	public void buscar() {
		try {
			if (this.buscador.equals("")) {
				this.todo();
			} else {
				this.setLista(modventa.buscando(Integer.parseInt(buscador)));
			}
		} catch (Exception e) {
			UtilitarioControlador.advertencia("Solo ingresar numeros");
			this.todo();
		}
	}

	public String nomInv(int id) {
		InventarioMod modinv = new InventarioMod();
		DescQuimicosMod moddescq = new DescQuimicosMod();
		return moddescq.buscado(modinv.buscado(id).getDescq_id()).getDesq_desc();
	}

	public String cliente(int id) {
		return modcli.buscado(id).getNombreC();
	}

	public void seleccionar(Venta ven) {
		this.setVenta(ven);
		DetalleVentaMod moddetven = new DetalleVentaMod();
		this.setListadet(moddetven.todos(this.venta.getVen_id()));
	}

	public void agregar() {
		try {
			UtilitarioControlador.redirigir("agregarVenta.quimicos");
		} catch (Exception e) {
			UtilitarioControlador.error("Hubo un error al redirigir: " + e.getMessage());
		}
	}

	public double tot(double mult) {
		return UtilitarioControlador.dosDeci(mult);
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
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public List<DetalleVenta> getListadet() {
		return listadet;
	}

	public void setListadet(List<DetalleVenta> listadet) {
		this.listadet = listadet;
	}
}
