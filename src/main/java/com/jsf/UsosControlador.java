/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import com.modelo.QuimicoMod;
import com.modelo.TipoUsoMod;
import com.modelo.UsoQuimicoMod;
import com.objetos.TipoUso;
import com.objetos.UsoQuimico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author panchito
 */
@SuppressWarnings("deprecation")
@ManagedBean(name = "usos")
@ViewScoped
public class UsosControlador implements Serializable {

	private static final long serialVersionUID = -3102420725961614548L;
	private final UsoQuimicoMod modusq = new UsoQuimicoMod();
	QuimicoMod modqui = new QuimicoMod();
	private List<TipoUso> listuso = new ArrayList<>();
	private List<UsoQuimico> listusoq = new ArrayList<>();
	private final TipoUsoMod modtuso = new TipoUsoMod();
	private String buscador;
	private String[] quimicosUso;
	private TipoUso uso = new TipoUso();

	public void todo() {
		this.listuso.clear();
		this.listuso = this.modtuso.todos();
	}

	public void buscar() {
		this.listuso = modtuso.buscando(buscador);

	}

	public String nombreq(int id) {
		return this.modqui.buscado(id).getQui_nombreC() + " | " + modqui.buscado(id).getQui_quimico();
	}

	public void guardar() {
		this.uso.setUsu_id_UltMod(UtilitarioControlador.getUsu().getUsu_id());
		try {
			TipoUso aux = modtuso.buscado(this.uso.getTuso_uso());
			if (aux.getTuso_id() != 0) {
				UtilitarioControlador.informativo("Ya Existe el Tipo de Uso");
				this.limpiar();
			} else {
				if (modtuso.guardar(uso)) {
					UtilitarioControlador.informativo("Se guardo el tipo \n de uso con exito");
					this.limpiar();
				} else {
					UtilitarioControlador.advertencia("No se guardo el uso");
				}
			}
		} catch (Exception e) {
			UtilitarioControlador.error("ERROR AL GUARDAR TIPO DE USO: " + e.getMessage());
		}
	}

	public void guardar_uso() {
		try {
			int[] idsqui = new int[this.quimicosUso.length];
			for (int i = 0; i < this.quimicosUso.length; i++) {
				idsqui[i] = this.modqui.buscado(quimicosUso[i]).getQui_id();
			}
			for (int id : idsqui) {
				if (this.modusq.buscado(this.uso.getTuso_id(), id).getUsu_id_UltMod() == 0) {
					this.modusq.guardar(new UsoQuimico(UtilitarioControlador.getUsu().getUsu_id(), null, null, this.uso.getTuso_id(), id));
				}
			}
			UtilitarioControlador.informativo("Se Guardo de Forma exitosa");
			this.seleccionar(this.uso);
		} catch (Exception ex) {
			UtilitarioControlador.error("OCURRIO UN ERROR AL GUARDAR LOS QUIMICOS");
		}
	}

	public void limpiar() {
		uso = new TipoUso();
		this.todo();
	}

	public void seleccionar(TipoUso us) {
		this.uso = us;
		this.listusoq = this.modusq.todos(us.getTuso_id());
	}

	public List<String> quimicos() {
		List<String> esto = new ArrayList<>();
		this.modqui.todos().stream().forEach((st) -> esto.add(st.getQui_quimico()));
		Collections.sort(esto);
		return esto;
	}

	/*
	 * 
	 * Constructor
	 * 
	 */
	public UsosControlador() {
		this.todo();
	}

	/*
	 * 
	 * GETTERS Y SETTERS
	 * 
	 */
	public String getBuscador() {
		return buscador;
	}

	public void setBuscador(String buscador) {
		this.buscador = buscador;
	}

	public List<TipoUso> getListuso() {
		return listuso;
	}

	public void setListuso(List<TipoUso> listuso) {
		this.listuso = listuso;
	}

	public TipoUso getUso() {
		return uso;
	}

	public void setUso(TipoUso uso) {
		this.uso = uso;
	}

	public List<UsoQuimico> getListusoq() {
		return listusoq;
	}

	public void setListusoq(List<UsoQuimico> listusoq) {
		this.listusoq = listusoq;
	}

	public String[] getQuimicosUso() {
		return quimicosUso;
	}

	public void setQuimicosUso(String[] quimicosUso) {
		this.quimicosUso = quimicosUso;
	}

}
