/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.modelo.ClienteMod;
import com.objetos.Cliente;
import com.utilitarios.UtilitarioControlador;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "cliente")
@SessionScoped
public class ClienteControlador implements Serializable {

	private static final long serialVersionUID = -1113557213370916646L;
	private String buscador;
	private List<Cliente> lista;
	private Cliente cli = new Cliente();
	private final ClienteMod modcli = new ClienteMod();

	public void todo() {
		this.setLista(modcli.todos());
	}

	public void buscar() {
		if (buscador.isEmpty()) {
			this.todo();
		} else {
			this.setLista(this.modcli.buscando(buscador));
		}
	}

	public void seleccionar(Cliente cli) {
		this.cli = cli;
	}

	public void borrar(int id) {
		try {
			if (this.modcli.borrar(id, UtilitarioControlador.getUsu().getUsu_id())) {
				UtilitarioControlador.informativo("Borrado con Exito");
				this.todo();
			} else {
				UtilitarioControlador.advertencia("Existio un problema al eliminar");
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Existio un error al eliminar: " + e.getMessage());
		}
	}

	public void editar() {
		this.cli.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
		try {
			if (this.modcli.actualizar(this.cli)) {
				UtilitarioControlador.informativo("Cliente Actualizado con exito");
				this.limpiar();
			} else {
				UtilitarioControlador.advertencia("Existio un problema al actualizar");
				this.limpiar();
			}
		} catch (Exception e) {
			UtilitarioControlador.error("Ocurrio un error al editar el cliente" + e.getMessage());
		}
	}

	public void limpiar() {
		this.todo();
		this.setCli(new Cliente());
	}

	public void guardar() {
		this.cli.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
		try {
			if (this.cli.hasEmptyFields()) {
				UtilitarioControlador.advertencia("Existen Campos Vacios");
			} else {
				if (this.cli.getPais().equals("ecuador") || this.cli.getPais().equals("Ecuador")) {
					if (UtilitarioControlador.validaRuc(this.cli.getRuc())) {
						if (this.modcli.guardar(this.cli)) {
							UtilitarioControlador.informativo("Se registro con exito");
							limpiar();
						} else {
							UtilitarioControlador.advertencia("No se pudo guardar");
						}
					} else {
						UtilitarioControlador.advertencia("El RUC no es valido");
					}
				} else {
					if (this.modcli.guardar(this.cli)) {
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
	 * Getters Setters
	 */

	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}
}
