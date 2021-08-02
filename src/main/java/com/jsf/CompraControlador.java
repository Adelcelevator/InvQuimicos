/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.ProveedorMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleCompraMod;
import com.modelo.InventarioMod;
import com.modelo.CompraMod;
import com.objetos.Compra;
import com.objetos.DetalleCompra;
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
@ManagedBean(name = "compra")
@SessionScoped
public class CompraControlador implements Serializable {

	private static final long serialVersionUID = -7748260402553527151L;

	private List<Compra> lista;
	private List<DetalleCompra> listadet = new ArrayList<>();
	private Compra compra;
	private String buscador;
	
	private final CompraMod modcompra = new CompraMod();
	private final ProveedorMod modprov = new ProveedorMod();

	private void todo() {
		this.setLista(modcompra.todos());
	}

	public void buscar() {
		try {
			if (this.buscador.equals("")) {
				this.todo();
			} else {
				this.setLista(modcompra.buscando(Integer.parseInt(buscador)));
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

	public String proveedor(int id) {
		return modprov.buscado(id).getNombreC();
	}

	public void seleccionar(Compra ven) {
		this.setCompra(ven);
		DetalleCompraMod moddetven = new DetalleCompraMod();
		this.setListadet(moddetven.todos(this.compra.getCom_id()));
	}

	public void archivar(int id) {

	}

	public void agregar() {
		try {
			UtilitarioControlador.redirigir("agregarCompra.quimicos");
		} catch (Exception e) {
			UtilitarioControlador.error("Hubo un error al redirigir: " + e.getMessage());
		}
	}

	/*
	Getters Setters
	 */
	public List<Compra> getLista() {
		return lista;
	}

	public void setLista(List<Compra> lista) {
		this.lista = lista;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra venta) {
		this.compra = venta;
	}

	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public CompraControlador() {
		this.todo();
	}

	public List<DetalleCompra> getListadet() {
		return listadet;
	}

	public void setListadet(List<DetalleCompra> listadet) {
		this.listadet = listadet;
	}
}
