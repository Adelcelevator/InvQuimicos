package com.jsf;

import com.modelo.ProveedorMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleCompraMod;
import com.modelo.InventarioMod;
import com.modelo.QuimicoMod;
import com.modelo.CompraMod;

import com.objetos.Proveedor;
import com.objetos.DescripcionQuimico;
import com.objetos.DetalleCompra;
import com.objetos.Inventario;
import com.objetos.Compra;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("deprecation")
@ManagedBean(name = "agregarC")
@SessionScoped
public class AgregarCompraControlador implements Serializable {

	private static final long serialVersionUID = 4738204847680474435L;

	private final InventarioMod modinv = new InventarioMod();
	private final DescQuimicosMod moddescq = new DescQuimicosMod();
	private final ProveedorMod modprov = new ProveedorMod();
	private final QuimicoMod modqui = new QuimicoMod();
	private final CompraMod modcom = new CompraMod();
	private final DetalleCompraMod moddetven = new DetalleCompraMod();

	private List<DetalleCompra> listadet = new ArrayList<>();
	private List<Proveedor> listaprov = modprov.todos();
	private List<Inventario> listaPro = modinv.todos();
	private Proveedor prov = new Proveedor();
	private String buscador, cantidad = "";
	private Compra compra = new Compra();

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
		this.listaprov = this.modprov.todos();
		this.listaPro = this.modinv.todos();
	}

	public void limpiarF() {
		this.limpiar();
		this.prov = new Proveedor();
		this.cantidad = "";
		this.compra = new Compra();
	}

	public void seleccionarProv(Proveedor se) {
		this.prov = se;
		this.compra.setPro_id(se.getPro_id());
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
				this.limpiar();
				if (this.listadet.isEmpty()) {
					this.listadet.add(new DetalleCompra(0, inv.getInv_id(), aux, UtilitarioControlador.dosDeci((inv.getInv_precioUI() * aux)), inv.getInv_precioUI()));
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
						this.listadet.add(new DetalleCompra(0, inv.getInv_id(), aux, UtilitarioControlador.dosDeci((inv.getInv_precioUI() * aux)), inv.getInv_precioUI()));
						this.valorF((inv.getInv_precioUI() * aux));
					}
				}
			} catch (Exception e) {
				this.limpiar();
				UtilitarioControlador.error("En el campo cantidad ingrese solo numeros");
			}
		}
	}

	public void eliminar(DetalleCompra detcom) {
		this.valorF(-detcom.getDetalle_valorTc());
		this.listadet.remove(detcom);
	}

	/*
	Este es el metodo guardar y funciona de la siguiente manera:
		1.- Se verifica que el numero de factura no haya sido ingresado con anterioridad por la misma entidad.
		2.- Se settea el id de usuario en el objeto compra
		3.- Se evalua que no existan campos vacios
		4.- Se registra la cabecera de la compra y/o el objeto compra
		5.- Se iguala el objeto compra con el objeto compra traido desde la base de datos
			identificado por el numero de factura y el id de la entdad emisora
		6.- Se recorre la lista del detalle de la venta para hacer su insercion en la base
		7.- Se actualiza el inventario y se guarda el datalle de la compra
	 */
	public void guardar() {
		try {
			if (this.modcom.buscado(this.compra.getCom_numFac()) == null) {
				this.compra.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
				if (this.prov.equals(new Proveedor()) || this.compra.hasEmptyFilds() || this.listadet.isEmpty()) {
					UtilitarioControlador.advertencia("Existen campos vacios");
				} else if (!this.modcom.guardar(this.compra)) {
					UtilitarioControlador.advertencia("Existio un Error al guardar la venta");
				} else {
					this.compra = modcom.buscado(this.compra.getCom_numFac());
					for (DetalleCompra ndetven : this.listadet) {
						ndetven.setCom_id(this.compra.getCom_id());
						Inventario inv = this.modinv.buscado(ndetven.getInv_id());
						inv.setInv_cantidad((inv.getInv_cantidad() + ndetven.getDetalle_cantidad()));
						inv.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
						this.modinv.actualizar(inv);
						this.moddetven.guardar(ndetven);
					}
					this.limpiarF();
					UtilitarioControlador.redirigir("compras.quimicos");
				}
			} else {
				UtilitarioControlador.advertencia("Ya existe ese numero de factura");
			}
		} catch (Exception e) {
			System.out.println("Existio un error general al guardar la compra: " + e);
			try {
				UtilitarioControlador.redirigir("compras.quimicos");
				this.limpiarF();
			} catch (Exception x) {
				System.out.println("Existio un error general al redirigir: " + x);
			}
		}
	}

	private final void valorF(double cantidad) {
		this.compra.setCom_valorT(UtilitarioControlador.dosDeci(this.compra.getCom_valorT() + cantidad));
		this.compra.setCom_valorIm(UtilitarioControlador.dosDeci(this.compra.getCom_valorT() * 0.12));
		this.compra.setCom_subtotal(UtilitarioControlador.dosDeci((this.compra.getCom_valorT() - this.compra.getCom_valorIm())));
	}

	public void buscarC() {
		if (buscador.isEmpty()) {
			this.setListaprov(modprov.todos());
		} else {
			this.setListaprov(this.modprov.buscando(buscador));
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
		return this.nomQui(this.modinv.buscado(id).getQui_id()) + " | " + this.nomDesc(this.modinv.buscado(id).getDescq_id());
	}

	public String uniMed(int id, int cantidad) {
		return cantidad + " " + this.moddescq.buscado(id).getDesq_umedida();
	}

	/*
	Getters Setters
	 */
	public List<DetalleCompra> getListadet() {
		return listadet;
	}

	public void setListadet(List<DetalleCompra> listadet) {
		this.listadet = listadet;
	}

	public AgregarCompraControlador() {
		this.productos();
	}

	public List<Proveedor> getListaprov() {
		return listaprov;
	}

	public void setListaprov(List<Proveedor> listaprov) {
		this.listaprov = listaprov;
	}

	public Proveedor getProv() {
		return prov;
	}

	public void setProv(Proveedor prov) {
		this.prov = prov;
	}

	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra venta) {
		this.compra = venta;
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
