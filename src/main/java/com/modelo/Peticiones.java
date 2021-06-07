package com.modelo;

import java.util.List;

public interface Peticiones<T> {

	public default List<T> todos() {
		return null;
	};
	
	public default List<T> todos(int id) {
		return null;
	};

	public default List<T> historial(){
		return null;
	};
	
	public default List<T> buscando(int id) {
		return null;
	};

	public default List<T> buscando(String nombre) {
		return null;
	};

	public default T buscado(int id) {
		return null;
	};

	public default T buscado(String bus) {
		return null;
	};

	public default boolean guardar(T nuevo) throws Exception {
		return false;
	};

	public default boolean borrar(int id) throws Exception {
		return false;
	};
	
	public default boolean borrar(int id, int idusu) throws Exception {
		return false;
	};

	public default boolean actualizar(T actual) throws Exception {
		return false;
	}
}
