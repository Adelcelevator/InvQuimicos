package com.jsf;

import com.modelo.ClienteMod;
import com.modelo.CotizacionMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleVentaMod;
import com.modelo.InventarioMod;
import com.modelo.VentaMod;
import com.objetos.DetalleVenta;
import com.objetos.Venta;
import com.utilitarios.UtilitarioControlador;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("deprecation")
@ManagedBean(name = "cotizacion")
@SessionScoped

public class CotizacionControlador implements Serializable {

	private static final long serialVersionUID = 1231918322852835640L;
	private List<Venta> lista;
	private List<DetalleVenta> listadet = new ArrayList<>();
	private Venta cotizacion;
	private String buscador;
	private final CotizacionMod modcoti = new CotizacionMod();
	private final ClienteMod modcli = new ClienteMod();

	public void todo() {
		this.setLista(modcoti.todos());
	}

	public void buscar() {
		try {
			if (this.buscador.equals("")) {
				this.todo();
			} else {
				this.setLista(modcoti.buscando(Integer.parseInt(buscador)));
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
		this.setCotizacion(ven);
		DetalleVentaMod moddetven = new DetalleVentaMod();
		this.setListadet(moddetven.todos(this.cotizacion.getVen_id()));
	}

	public double tot(double mult) {
		return UtilitarioControlador.dosDeci(mult);
	}

	/*
	 * Getters Setters
	 */
	public List<Venta> getLista() {
		return lista;
	}

	public void setLista(List<Venta> lista) {
		this.lista = lista;
	}

	public Venta getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Venta cotizacion) {
		this.cotizacion = cotizacion;
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
