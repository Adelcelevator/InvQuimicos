package com.jsf;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.modelo.ClienteMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleVentaMod;
import com.modelo.InventarioMod;
import com.modelo.QuimicoMod;
import com.modelo.VentaMod;
import com.objetos.Cliente;
import com.objetos.DetalleVenta;
import com.objetos.Inventario;
import com.objetos.Venta;
import com.utilitarios.UtilitarioControlador;

@SuppressWarnings("deprecation")
@ManagedBean(name = "agregarV")
@SessionScoped
public class AgregarVentaControlador implements Serializable {

	private static final long serialVersionUID = -4092049211943161462L;

	private final InventarioMod modinv = new InventarioMod();
	private final DescQuimicosMod moddescq = new DescQuimicosMod();
	private final ClienteMod modcli = new ClienteMod();
	private final QuimicoMod modqui = new QuimicoMod();
	private final VentaMod modven = new VentaMod();
	private final DetalleVentaMod moddetven = new DetalleVentaMod();

	private List<DetalleVenta> listadet = new ArrayList<>();
	private List<Cliente> listacli = modcli.todos();
	private List<Inventario> listaPro = modinv.todos();
	private Cliente cli = new Cliente();
	private String buscador = "", cantidad = "";
	private Venta venta = new Venta();

	public String hoy() {
		return LocalDate.now().toString();
	}

	public void limpiar() {
		this.buscador = "";
		this.listacli = this.modcli.todos();
		this.listaPro = this.modinv.todos();
	}

	public void limpiarF() {
		this.limpiar();
		this.cli = new Cliente();
		this.cantidad = "";
		this.venta = new Venta();
		this.listadet.clear();
	}

	public void seleccionarCli(Cliente se) {
		this.cli = se;
		this.venta.setCli_id(se.getCli_id());
		this.limpiar();
	}

	public void calcular(int id) {
		System.out.println("Le Apachurras a este : " + id);
	}

	public void seleccionarPro(Inventario inv) {
		if (this.cantidad.isEmpty()) {
			UtilitarioControlador.advertencia("Ingrese una cantidad");
		} else {
			try {
				int aux = Integer.parseInt(this.cantidad);
				this.cantidad = "";
				int stock = this.modinv.buscado(inv.getInv_id()).getInv_cantidad();
				if ((stock - aux) < 0) {
					UtilitarioControlador.advertencia("La Cantidad Supera el Stock");
					this.limpiar();
				} else {
					this.limpiar();
					if (this.listadet.isEmpty()) {
						this.listadet.add(new DetalleVenta(0, inv.getInv_id(), aux,
								UtilitarioControlador.dosDeci((inv.getInv_precioUI() * aux)), inv.getInv_precioUI()));
						this.valorF((inv.getInv_precioUI() * aux));
					} else {
						boolean prueba = false;
						for (int i = 0; i < this.listadet.size(); i++) {
							if (this.listadet.get(i).getInv_id() == inv.getInv_id()) {
								prueba = true;
								i = this.listadet.size();
							}
						}
						if (prueba) {
							UtilitarioControlador.advertencia("Ya Esta Seleccionado el Producto");
						} else {
							this.listadet.add(new DetalleVenta(0, inv.getInv_id(), aux,
									UtilitarioControlador.dosDeci((inv.getInv_precioUI() * aux)),
									inv.getInv_precioUI()));
							this.valorF((inv.getInv_precioUI() * aux));
						}
					}
				}
			} catch (Exception e) {
				this.limpiar();
				UtilitarioControlador.error("En el campo cantidad ingrese solo numeros");
			}
		}
	}

	public void eliminar(DetalleVenta detven) {
		this.valorF(-detven.getDetalle_valorTv());
		this.listadet.remove(detven);
	}

	/*
	 * Este es el metodo guardar y funciona de la siguiente manera: 1.- Se verifica
	 * que el numero de factura no haya sido ingresado con anterioridad. 2.- Se
	 * settea el id de usuario en el objeto venta 3.- Se evalua que no existan
	 * campos vacios 4.- Se registra la cabecera de la venta y/o el objeto venta 5.-
	 * Se iguala el objeto venta con el objeto venta traido desde la base de datos
	 * identificado por el numero de factura 6.- Se recorre la lista del detalle de
	 * la venta para hacer su insercion en la base 7.- Se comprueba una vez mas que
	 * la cantidad no sea mayor a lo que hay en stock 8.- Se actualiza el inventario
	 * y se guarda el datalle de la venta
	 */
	public void guardar() {
		try {
			if (this.modven.buscado(this.venta.getVen_numFac()) == null) {
				this.venta.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
				if (this.cli.equals(new Cliente()) || this.venta.hasEmptyFilds() || this.listadet.isEmpty()) {
					UtilitarioControlador.advertencia("Existen campos vacios");
				} else if (!this.modven.guardar(this.venta)) {
					UtilitarioControlador.advertencia("Existio un Error al guardar la venta");
				} else {
					this.venta = modven.buscado(this.venta.getVen_numFac());
					for (DetalleVenta ndetven : this.listadet) {
						ndetven.setVen_id(this.venta.getVen_id());
						Inventario inv = this.modinv.buscado(ndetven.getInv_id());
						if ((inv.getInv_cantidad() - ndetven.getDetalle_cantidad()) < 0) {
							break;
						} else {
							inv.setInv_cantidad((inv.getInv_cantidad() - ndetven.getDetalle_cantidad()));
							inv.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
							this.modinv.actualizar(inv);
							this.moddetven.guardar(ndetven);
						}
					}
					this.limpiarF();
					UtilitarioControlador.redirigir("ventas.quimicos");
				}
			} else {
				UtilitarioControlador.advertencia("Ya existe ese numero de factura");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Existio un Error general");
			try {
				UtilitarioControlador.redirigir("ventas.quimicos");
				this.limpiarF();
			} catch (Exception x) {

			}
		}
	}

	private void valorF(double cantidad) {
		this.venta.setVen_valorT(UtilitarioControlador.dosDeci(this.venta.getVen_valorT() + cantidad));
		this.venta.setVen_valorIm(UtilitarioControlador.dosDeci(this.venta.getVen_valorT() * 0.12));
		this.venta.setVen_subtotal(
				UtilitarioControlador.dosDeci((this.venta.getVen_valorT() - this.venta.getVen_valorIm())));
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

	public String nomFac(int id) {
		return this.nomQui(this.modinv.buscado(id).getQui_id()) + " | "
				+ this.nomDesc(this.modinv.buscado(id).getDescq_id());
	}

	public String uniMed(int id, int cantidad) {
		return cantidad + " " + this.moddescq.buscado(id).getDesq_umedida();
	}

	/*
	 * Getters Setters
	 */
	public List<DetalleVenta> getListadet() {
		return listadet;
	}

	public void setListadet(List<DetalleVenta> listadet) {
		this.listadet = listadet;
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

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
