package com.utilitarios;

import com.modelo.Peticiones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UtilitarioMod<T> implements Serializable,Peticiones<T> {
	private static final long serialVersionUID = -9213506424687712430L;
	private final List<T> lis = new ArrayList<>();
	private T obj;
	private boolean fue;

	@Override
	public List<T> todos() {
		// TODO Auto-generated method stub
		return Peticiones.super.todos();
	}
	@Override
	public List<T> todos(int id) {
		// TODO Auto-generated method stub
		return Peticiones.super.todos(id);
	}
	@Override
	public List<T> historial() {
		// TODO Auto-generated method stub
		return Peticiones.super.historial();
	}
	@Override
	public List<T> buscando(int id) {
		// TODO Auto-generated method stub
		return Peticiones.super.buscando(id);
	}
	@Override
	public List<T> buscando(String nombre) {
		// TODO Auto-generated method stub
		return Peticiones.super.buscando(nombre);
	}
	@Override
	public T buscado(int id) {
		// TODO Auto-generated method stub
		return Peticiones.super.buscado(id);
	}
	@Override
	public T buscado(String bus) {
		// TODO Auto-generated method stub
		return Peticiones.super.buscado(bus);
	}
	@Override
	public boolean guardar(T nuevo) throws Exception {
		// TODO Auto-generated method stub
		return Peticiones.super.guardar(nuevo);
	}
	@Override
	public boolean borrar(int id) throws Exception {
		// TODO Auto-generated method stub
		return Peticiones.super.borrar(id);
	}
	@Override
	public boolean borrar(int id, int idusu) throws Exception {
		// TODO Auto-generated method stub
		return Peticiones.super.borrar(id, idusu);
	}
	@Override
	public boolean actualizar(T actual) throws Exception {
		// TODO Auto-generated method stub
		return Peticiones.super.actualizar(actual);
	}
	public List<T> getLis() {
		return lis;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public boolean isFue() {
		return fue;
	}
	public void setFue(boolean fue) {
		this.fue = fue;
	}
}
