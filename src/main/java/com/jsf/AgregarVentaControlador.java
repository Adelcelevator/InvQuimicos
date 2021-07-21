package com.jsf;

import com.modelo.ClienteMod;
import com.modelo.DescQuimicosMod;
import com.modelo.InventarioMod;
import com.modelo.QuimicoMod;

import com.objetos.Cliente;
import com.objetos.DescripcionQuimico;
import com.objetos.DetalleVenta;
import com.objetos.Inventario;
import com.objetos.Venta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("deprecation")
@ManagedBean(name = "agregarV")
@ViewScoped
public class AgregarVentaControlador implements Serializable {

	private static final long serialVersionUID = -4092049211943161462L;

	private final InventarioMod modinv = new InventarioMod();
	private final DescQuimicosMod moddescq = new DescQuimicosMod();
	private final ClienteMod modcli = new ClienteMod();
	private final QuimicoMod modqui = new QuimicoMod();

	private List<DetalleVenta> listadet;
	private List<Cliente> listacli = modcli.todos();
	private List<Inventario> listaPro = modinv.todos();
	private Cliente cli = new Cliente();
	private String buscador;
	private Venta venta = new Venta();

	public String hoy() {
		return LocalDate.now().toString();
	}

	public List<DescripcionQuimico> productos() {
		List<DescripcionQuimico> nombre = new ArrayList<>();
		this.modinv.todos().stream().forEach((es) -> {
			nombre.add(this.moddescq.buscado(es.getDescq_id()));
		});
		return nombre;
	}

	public void limpiar() {
		this.buscador = "";
		this.venta = new Venta();
		this.listacli = this.modcli.todos();
		this.listaPro = this.modinv.todos();
	}

	public void limpiarF() {
		this.limpiar();
		this.cli = new Cliente();
	}

	public void seleccionarCli(Cliente se) {
		this.cli = se;
		this.venta.setCli_id(se.getCli_id());
		this.limpiar();
	}

	public void seleccionarPro(Inventario inv){
		System.out.println("inv: "+inv.toString());
	}
	
	public void buscarC() {
		if (buscador.isEmpty()) {
			this.setListacli(modcli.todos());
		} else {
			this.setListacli(this.modcli.buscando(buscador));
		}
	}

	public void buscarP() {
		if (this.buscador.isEmpty()) {
			this.listaPro = this.modinv.todos();
		} else {
			this.listaPro.clear();
			this.listaPro = this.modinv.buscando(buscador);
		}

	}

	public String nomQui(int id) {
		return this.modqui.buscado(id).getQui_quimico();
	}

	public String nomDesc(int id) {
		return this.moddescq.buscado(id).getDesq_desc();
	}

	public String uniMed(int id, int cantidad) {
		return cantidad + " " + this.moddescq.buscado(id).getDesq_umedida();
	}

	/*
	Getters Setters
	 */
	public List<DetalleVenta> getListadet() {
		return listadet;
	}

	public void setListadet(List<DetalleVenta> listadet) {
		this.listadet = listadet;
	}

	public AgregarVentaControlador() {
		this.productos();
	}

	public List<Cliente> getListacli() {
		return listacli;
	}

	public void setListacli(List<Cliente> listacli) {
		this.listacli = listacli;
	}

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public List<Inventario> getListaPro() {
		return listaPro;
	}

	public void setListaPro(List<Inventario> listaPro) {
		this.listaPro = listaPro;
	}
}
