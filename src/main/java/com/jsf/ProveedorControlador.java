/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.utilitarios.UtilitarioControlador;
import com.modelo.ProveedorMod;
import com.objetos.Proveedor;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "proveedor")
@SessionScoped
public class ProveedorControlador implements Serializable {

	private static final long serialVersionUID = -8404778928087984105L;
	private String buscador;
	private List<Proveedor> lista;
	private Proveedor prov = new Proveedor();
	private final ProveedorMod modprov = new ProveedorMod();

	public void todo() {
		this.setLista(modprov.todos());
	}

	public void buscar() {
		this.setLista(this.modprov.buscando(buscador));
	}

	public void seleccionar(Proveedor pro) {
		this.prov = pro;
	}

	public void borrar(int id) {
		try {
			if(this.modprov.borrar(id, UtilitarioControlador.getUsu().getUsu_id())){
				UtilitarioControlador.informativo("Borrado con Exito");
				this.todo();
			}else{
				UtilitarioControlador.advertencia("Existio un problema al eliminar");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Existio un error al eliminar: "+e.getMessage());
		}
	}
	
	public void editar(){
		this.prov.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
		try {
			if (this.modprov.actualizar(this.prov)) {
				UtilitarioControlador.informativo("Proveedor Actualizado con exito");
				this.limpiar();
			}else{
				UtilitarioControlador.advertencia("Existio un problema al actualizar");
				this.limpiar();
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Ocurrio un error al editar el proveedor"+e.getMessage());
		}
	}

	public void limpiar() {
		this.todo();
		this.setProv(new Proveedor());
	}

	public void guardar() {
		this.prov.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
		try {
			if (this.prov.hasEmptyFields()) {
				UtilitarioControlador.advertencia("Existen Campos Vacios");
			} else {
				if (this.prov.getPais().equals("ecuador") || this.prov.getPais().equals("Ecuador")) {
					if (UtilitarioControlador.validaRuc(this.prov.getRuc())) {
						if (this.modprov.guardar(this.prov)) {
							UtilitarioControlador.informativo("Se registro con exito");
							limpiar();
						} else {
							UtilitarioControlador.advertencia("No se pudo guardar");
						}
					} else {
						UtilitarioControlador.advertencia("El RUC no es valido");
					}
				} else {
					if (this.modprov.guardar(this.prov)) {
						UtilitarioControlador.informativo("Se registro con exito");
						limpiar();
					} else {
						UtilitarioControlador.advertencia("No se pudo guardar");
					}
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Ocurrio un error: " + e.getMessage());
		}
	}

	/*
	 * Getters y Setters
	 */
	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public List<Proveedor> getLista() {
		return lista;
	}

	public void setLista(List<Proveedor> lista) {
		this.lista = lista;
	}

	public Proveedor getProv() {
		return prov;
	}

	public void setProv(Proveedor prov) {
		this.prov = prov;
	}
}
