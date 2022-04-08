package com.jsf;

import com.modelo.ClienteMod;
import com.modelo.CotizacionMod;
import com.modelo.DescQuimicosMod;
import com.modelo.DetalleCotizacionMod;
import com.modelo.InventarioMod;
import com.modelo.QuimicoMod;
import com.objetos.Cliente;
import com.objetos.DetalleVenta;
import com.objetos.Inventario;
import com.objetos.Venta;
import com.utilitarios.HiloEnvioCorreos;
import com.utilitarios.UtilitarioControlador;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("deprecation")
@ManagedBean(name = "agregarCo")
@SessionScoped

public class AgregarCotizacionControlador implements Serializable {

	private final InventarioMod modinv = new InventarioMod();
	private final DescQuimicosMod moddescq = new DescQuimicosMod();
	private final ClienteMod modcli = new ClienteMod();
	private final QuimicoMod modqui = new QuimicoMod();
	private final CotizacionMod modcoti = new CotizacionMod();
	private final DetalleCotizacionMod moddetcoti = new DetalleCotizacionMod();

	private List<DetalleVenta> listadet = new ArrayList<>();
	private List<Cliente> listacli = modcli.todos();
	private List<Inventario> listaPro = modinv.todos();
	private Cliente cli = new Cliente();
	private String buscador = "", cantidad = "", destino = "", mensaje = "";
	private Venta cotizacion = new Venta();

	public String hoy() {
		return LocalDate.now().toString();
	}

	public void limpiar() {
		this.destino = "";
		this.mensaje ="";
		this.buscador = "";
		this.listacli = this.modcli.todos();
		this.listaPro = this.modinv.todos();
	}

	public void limpiarF() {
		this.limpiar();
		this.cli = new Cliente();
		this.cantidad = "";
		this.cotizacion = new Venta();
		this.listadet.clear();
	}

	public void seleccionarCli(Cliente se) {
		this.cli = se;
		this.cotizacion.setCli_id(se.getCli_id());
		this.limpiar();
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
	Este es el metodo guardar y funciona de la siguiente manera: 
	1.- Se verifica que el numero de cotizacion no haya sido ingresado con anterioridad. 
	2.- Se settea el id de usuario en el objeto cotizacion 
	3.- Se evalua que no existan campos vacios 
	4.- Se registra la cabecera de la cotizacion y/o el objeto cotizacion 
	5.- Se iguala el objeto cotizacion con el objeto cotizacion traido desde la base de datos
		identificado por el numero de factura 
	6.- Se recorre la lista del detalle de la cotizacion para hacer su insercion en la base 
	7.- Se comprueba una vez mas que la cantidad no sea mayor a lo que hay en stock 
	8.- Se genera un hilo desde donde se enviara el correo
	 */
	public void guardar() {
		try {
			this.cotizacion.setVen_numFac((int) ((Math.random() * 3000000) + 2000000));
			this.cotizacion.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
			while (this.modcoti.buscado(this.cotizacion.getVen_numFac()) != null) {
				this.cotizacion.setVen_numFac(Integer.parseInt(String.valueOf((Math.random() * 300000) + 2000000)));
			}
			if (this.cli.equals(new Cliente()) || this.cotizacion.hasEmptyFilds() || this.listadet.isEmpty() || this.destino.isBlank() || this.destino.isEmpty()) {
				UtilitarioControlador.advertencia("Existen campos vacios");
			} else if (!this.modcoti.guardar(this.cotizacion)) {
				UtilitarioControlador.advertencia("Existio un Error al guardar la venta");
			} else {
				this.cotizacion = modcoti.buscado(this.cotizacion.getVen_numFac());
				for (DetalleVenta ndetven : this.listadet) {
					ndetven.setVen_id(this.cotizacion.getVen_id());
					this.moddetcoti.guardar(ndetven);
				}
				if(this.mensaje.isBlank() || this.mensaje.isEmpty()){
					Calendar calen = Calendar.getInstance();
					int hora = calen.get(Calendar.HOUR_OF_DAY);
					if(hora >= 6 && hora < 12){
						this.setMensaje("Buenos días");
					}else if(hora >=12 && hora<19){
						this.setMensaje("Buenos Tardes");
					}else if(hora>=19 && hora <6){
						this.setMensaje("Buenos Noches");
					}
					this.setMensaje(this.getMensaje()
							+"\n Estimando "+this.cli.getRepresentante()
							+"\n Adjunto envio la cotización.");
				}
				new Thread(new HiloEnvioCorreos(this.destino, this.mensaje, this.cotizacion.getVen_id())).start();
				this.limpiarF();
				UtilitarioControlador.redirigir("cotizaciones.quimicos");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Existio un Error general");
			try {
				UtilitarioControlador.redirigir("cotizaciones.quimicos");
				this.limpiarF();
			} catch (Exception x) {

			}
		}
	}

	private void valorF(double cantidad) {
		this.cotizacion.setVen_valorT(UtilitarioControlador.dosDeci(this.cotizacion.getVen_valorT() + cantidad));
		this.cotizacion.setVen_valorIm(UtilitarioControlador.dosDeci(this.cotizacion.getVen_valorT() * 0.12));
		this.cotizacion.setVen_subtotal(UtilitarioControlador.dosDeci((this.cotizacion.getVen_valorT() - this.cotizacion.getVen_valorIm())));
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

	public Venta getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Venta cotizacion) {
		this.cotizacion = cotizacion;
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

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
