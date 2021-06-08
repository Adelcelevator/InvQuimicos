package com.modelo;

//import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion /*implements Serializable*/ {
	/*private static final long serialVersionUID = -2061601848852548900L;*/
	Connection cn;

	public Connection conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			this.cn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pruebas", "postgres", "holamonica");
			return this.cn;
		} catch (Exception e) {
			System.err.println("ERROR AL CONECTAR: " + e.getMessage());
			e.printStackTrace();
		}
		this.desconectar();
		return null;
	}

	public void desconectar() {
		try {
			if (this.cn != null && !this.cn.isClosed()) {
				this.cn.close();
			}
		} catch (Exception e) {
			System.err.println("ERROR AL DESCONECTAR: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
