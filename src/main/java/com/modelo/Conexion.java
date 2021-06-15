package com.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion implements Serializable {
	private static final long serialVersionUID = -2061601848852548900L;
	public static Connection cn;

	public static Connection conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			Conexion.cn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pruebas", "postgres", "holamonica");
			return Conexion.cn;
		} catch (Exception e) {
			System.err.println("ERROR AL CONECTAR: " + e.getMessage());
			e.printStackTrace();
		}
		Conexion.desconectar();
		return null;
	}

	public static void desconectar() {
		try {
			if (Conexion.cn != null && !Conexion.cn.isClosed()) {
				Conexion.cn.close();
			}
		} catch (Exception e) {
			System.err.println("ERROR AL DESCONECTAR: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
